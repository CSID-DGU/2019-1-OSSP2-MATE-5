package edu.stlawu.stopwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton = (TextView)findViewById(R.id.registerButton);

        //버튼이 눌리면 RegisterActivity로 가게함
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseLisner = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("Login Success")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("Check your ID or PASSWORD")
                                        .setNegativeButton("다시시도", null)
                                        .create();
                                dialog.show();
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                LoginActivity.this.startActivity(intent);
//                                finish();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLisner);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });

    }

    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null){//다이얼로그가 켜져있을때 함부로 종료가 되지 않게함
            dialog.dismiss();
            dialog = null;
        }
    }


}
