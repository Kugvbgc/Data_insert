package com.khair.data_insert;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editText,editText1,editText2;
    Button button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=findViewById(R.id.ProgressBar);
        button=findViewById(R.id.Upload_button);
        editText=findViewById(R.id.edName);
        editText1=findViewById(R.id.edNumber);
        editText2=findViewById(R.id.edEmail);

      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name=editText.getText().toString();
              String number=editText1.getText().toString();
              String email=editText2.getText().toString();


              String url="https://abulk77912.000webhostapp.com/apps/hello.php?"+"n="+name
                      +"&m="+number+"&e="+email;
              progressBar.setVisibility(View.VISIBLE);

              StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                  @Override
                  public void onResponse(String string) {
                      progressBar.setVisibility(View.GONE);

                      new AlertDialog.Builder(MainActivity.this)
                              .setTitle("saver Response")
                              .setMessage(string)
                              .show();


                  }
              }, new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError volleyError) {
                      progressBar.setVisibility(View.GONE);


                      if (!isInternetAvailable()) {
                          showAlertDialog();
                      }
                  }
              });

            if (editText.length()>0&&editText1.length()>0&&editText2.length()>0){
                RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);

            }else {
                editText.setError("please enter your name");
                editText1.setError("please enter your number");
                editText2.setError("please enter your email");
                progressBar.setVisibility(View.GONE);

            }





         }
      });





    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No internet connection detected");
        builder.setMessage(" Please check your connection");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}