package edu.stlawu.stopwatch;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "MainActivity";

    private static final int startCall = 1001;
    private static final int stopCall = 1002;

    TextView no;

    // Define variables for our views
    private TextView tv_count = null;
    private Button bt_start = null;
    private Button bt_stop = null;
    private Button bt_reset = null;
    private Timer t = null;
    private Counter ctr = null; //Timertask
    private AlertDialog dialog;

    String userID = LoginActivity.userID;

    // audio variables
    private AudioAttributes aa = null;
    private SoundPool soundPool = null;
    private int bloopSound = 0;

    String url = "http://hssoft.kr:9878/phone.php";
    public GettingPHP gPHP;

    ITelephony iPhoney = null;
    PackageManager pm = null;
    TelephonyManager tm = null;

    boolean isFirstStart = true;
    boolean callPhonePermission = false;
    boolean readPhoneStatePermission = false;
    private boolean isRunnable = true;
    private boolean endCall = false;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case startCall:
                    startCallPhone();
                    break;

                case stopCall:
                    stopCallPhone();
                    break;

            }

            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize views
        this.tv_count = findViewById(R.id.tv_count);
        this.bt_start = findViewById(R.id.bt_start);
        this.bt_stop = findViewById(R.id.bt_stop);
        this.bt_reset = findViewById(R.id.bt_reset);

        bt_stop.setEnabled(false);
        bt_reset.setEnabled(false);

        no = findViewById(R.id.no);

        pm = getPackageManager();
        callPhonePermission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.CALL_PHONE", this.getPackageName()));

        readPhoneStatePermission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.READ_PHONE_STATE", this.getPackageName()));

        tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        iPhoney = getITelephony(this);

        // start button enables timer
        this.bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if the permission already exits
                int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    gPHP = new GettingPHP();
                    no = (TextView) findViewById(R.id.no);
                    isRunnable = true;
                    gPHP.execute(url);
                    startCallPhone();
                    //  callNumber();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 121);
                }
                bt_start.setEnabled(false);
                bt_stop.setEnabled(true);
                bt_reset.setEnabled(false);
                resume();
            }
        });

        // stop button
        this.bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_stop.setEnabled(false);
                bt_start.setEnabled(true);
                bt_start.setText("Resume");
                bt_reset.setEnabled(true);
                getPreferences(MODE_PRIVATE).edit().putInt("COUNT", ctr.count).apply();

                isRunnable = false;
                ctr.cancel();
            }
        });


        // SEND button
        this.bt_reset.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 int hour = ctr.count / 36000;
                                                 String hour_s = String.format("%02d", hour);
                                                 int min = (ctr.count / 600) % 60;
                                                 String min_s = String.format("%02d", min);
                                                 int sec = (ctr.count / 10) % 60;
                                                 String sec_s = String.format("%02d", sec);

                                                 String userTime = hour_s + min_s + sec_s;

                                                 Response.Listener<String> responseListen = new Response.Listener<String>() {

                                                     @Override
                                                     public void onResponse(String response) {
                                                         try {
                                                             JSONObject jsonResponse = new JSONObject(response);
                                                             //boolean success = jsonResponse.getBoolean("success");
                                                             AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                                             dialog = builder.setMessage("SEND Success")
                                                                     .setPositiveButton("확인", null)
                                                                     .create();
                                                             dialog.show();
//                             if(success){
//                                 AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                 dialog = builder.setMessage("SEND Success")
//                                         .setPositiveButton("확인", null)
//                                         .create();
//                                 dialog.show();
////                                 // reset count
////                                 getPreferences(MODE_PRIVATE).edit().putInt("COUNT", 0).apply();
////                                 ctr.cancel();
////                                 // set text view back to zero
////                                 MainActivity.this.tv_count.setText("00:00:00:00");
//                             }else {
//                                 AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                 dialog = builder.setMessage("SEND failed")
//                                         .setNegativeButton("확인", null)
//                                         .create();
//                                 dialog.show();
//                             }

                                                         } catch (Exception e) {
                                                             e.printStackTrace();
                                                         }
                                                     }
                                                 };

                                                 TimeRequest timeRequest = new TimeRequest(userID, userTime, responseListen);
                                                 RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                                 queue.add(timeRequest);

                                                 bt_start.setEnabled(true);
                                                 bt_start.setText("Start");
                                                 bt_stop.setEnabled(false);
                                                 // reset count
                                                 getPreferences(MODE_PRIVATE).edit().putInt("COUNT", 0).apply();
                                                 ctr.cancel();
                                                 // set text view back to zero
                                                 MainActivity.this.tv_count.setText("00:00:00:00");
                                             }
                                         }


        );


    }

    void callNumber() {
        String telno = no.getText().toString();
        Uri uri = Uri.parse("tel:" + telno);
        Intent i = new Intent(Intent.ACTION_CALL, uri);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(i);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // reload the count from a previous run, if first time running, start at 0
        // getint needs a default value
        int count = getPreferences(MODE_PRIVATE).getInt("COUNT", 0);

        this.tv_count.setText(String.format("%02d:%02d:%02d:%02d", count / 3600, count / 600, (count / 10) % 60, count % 10));
        System.out.println(count);

        // create timer
        this.t = new Timer();

        // factory method: an example of a design pattern
        Toast.makeText(this, "Stopwatch has started", Toast.LENGTH_LONG).show();


    }

    public void resume() {
        int count = getPreferences(MODE_PRIVATE).getInt("COUNT", 0);
        ctr = new Counter();
        this.ctr.count = count;
        t.scheduleAtFixedRate(ctr, 0, 100);
    }

    public void set_display() {
        this.tv_count.setText(String.format("%02d:%02d:%02d:%02d", ctr.count / 3600, ctr.count / 600, (ctr.count / 10) % 60, ctr.count % 10));

    }

    @Override
    protected void onPause() {
        // removes view
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // saves count to reopen when app is created
        getPreferences(MODE_PRIVATE).edit().putInt("COUNT", ctr.count).apply();
    }

    class Counter extends TimerTask {
        private int count = 0;

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    set_display();
                    count++;
                }
            });
        }
    }

    void startCallPhone() {
        if (callPhonePermission) {
            if (isFirstStart) {
                isFirstStart = false;
            } else {
                try {
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (isRunnable) {
                int state = tm.getCallState();
                Log.d(TAG,"----------Getcallstate :  "+tm.getCallState());
                if(state==0) {
                    Log.d(TAG, "TRY Calling---------------");
                    String telno = no.getText().toString();
                    Uri uri = Uri.parse("tel:" + telno);
                    Intent i = new Intent(Intent.ACTION_CALL, uri);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details
                    }
                    startActivity(i);
                    Log.d(TAG, "Calling---------------");

                    Message msg = Message.obtain();
                    msg.what = stopCall;
                    mHandler.sendMessage(msg);

                }
                if(state ==2){
                    Message msg = Message.obtain();
                    msg.what = stopCall;
                    mHandler.sendMessage(msg);
                }

            }
            else
                Log.d(TAG, "isRunnable false-----------");
        }
        else
            Log.d(TAG, "callpermission failed---------");
    }
    private void stopCallPhone() {

        try {
            Thread.sleep(3000);
            do {
                Class c = Class.forName(tm.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                iPhoney = (ITelephony) m.invoke(tm);
                //endCall = iPhoney.endCall();

                Log.d(TAG,"END CALL TRYING!!!!!!!!!!!!!!!!");

                endCall=true;
            } while (!endCall);

            Message msg = Message.obtain();
            msg.what = startCall;
            mHandler.sendMessage(msg);

        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static ITelephony getITelephony(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager) context
                .getSystemService(TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        Method getITelephonyMethod = null;
        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony",
                    (Class[]) null); // 获取声明的方法
            getITelephonyMethod.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        ITelephony iTelephony = null;
        try {
            iTelephony = (ITelephony) getITelephonyMethod.invoke(
                    mTelephonyManager, (Object[]) null); // 获取实例
            return iTelephony;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iTelephony;
    }
    class GettingPHP extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String data = "";
            try {
                URL phpUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();

                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            data+=line;
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return data;
        }

        protected void onPostExecute(String str) {
            try {
                JSONArray results = new JSONArray(str);
                //json [] 형태를 string으로
                int result_num = results.length();
                Random random = new Random();
                int i=random.nextInt(result_num);
                JSONObject temp = results.getJSONObject(i);

                String randomPhoneNumber = temp.get("phonenum").toString();
                System.out.println(randomPhoneNumber);
                no.setText(randomPhoneNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}