package edu.stlawu.stopwatch;

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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView no;

    // Define variables for our views
    private TextView tv_count = null;
    private Button bt_start = null;
    private Button bt_stop = null;
    private Button bt_reset = null;
    private Timer t = null;
    private Counter ctr = null; //Timertask

    // audio variables
    private AudioAttributes aa = null;
    private SoundPool soundPool = null;
    private int bloopSound = 0;

    String url = "http://106.10.34.39/try.php";
    public GettingPHP gPHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize views
        this.tv_count = findViewById(R.id.tv_count);
        this.bt_start = findViewById(R.id.bt_start);
        this.bt_stop = findViewById(R.id.bt_stop);
        this.bt_reset = findViewById(R.id.bt_reset);

        no=findViewById(R.id.no);
        // start button enables timer
        this.bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if the permission already exits
                int permission= ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE);
                if(permission== PackageManager.PERMISSION_GRANTED)
                {
                    gPHP = new GettingPHP();
                    no = (TextView)findViewById(R.id.no);
                    gPHP.execute(url);

                    callNumber();
                }
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},121);
                }
                bt_start.setEnabled(false);
                bt_stop.setEnabled(true);
                bt_reset.setEnabled(true);
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
                ctr.cancel();
            }
        });


        // reset button
        this.bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_start.setEnabled(true);
                bt_start.setText("Start");
                bt_stop.setEnabled(false);
                // reset count
                getPreferences(MODE_PRIVATE).edit().putInt("COUNT", 0).apply();
                ctr.cancel();
                // set text view back to zero
                MainActivity.this.tv_count.setText("00:00.0");
            }
        }


        );


    }
    void callNumber () {
        String telno=no.getText().toString();
        Uri uri=Uri.parse("tel:"+telno);
        Intent i =new Intent(Intent.ACTION_CALL,uri);
        startActivity(i);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // reload the count from a previous run, if first time running, start at 0
        // getint needs a default value
        int count = getPreferences(MODE_PRIVATE).getInt("COUNT", 0);

        this.tv_count.setText(String.format("%02d:%02d.%d", count / 600, (count / 10) % 60, count % 10));
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

    public void set_display(){
        this.tv_count.setText(String.format("%02d:%02d.%d", ctr.count / 600, (ctr.count / 10 ) % 60, ctr.count % 10));

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

    class Counter extends TimerTask{
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

    class GettingPHP extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
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
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }
        protected void onPostExecute(String str) {
            try {
                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                JSONObject jObject = new JSONObject(str);
                // results라는 key는 JSON배열로 되어있다.
                JSONArray results = jObject.getJSONArray("results");
                String zz="";
                int result_num = results.length();
                Random random = new Random();
                int i=random.nextInt(result_num);
                JSONObject temp = results.getJSONObject(i);
                zz += temp.get("number");
                System.out.println(zz);
                no.setText(zz);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
;