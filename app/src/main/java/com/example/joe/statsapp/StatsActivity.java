package com.example.joe.statsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joe.model.GitHubService;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.entity.mime.Header;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatsActivity extends AppCompatActivity implements View.OnTouchListener {
    private Button retrieveButton;
    private TextView resultText;
    private EditText yearText, monthText;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        findViewById(R.id.stats_mainlayout).setOnTouchListener(this); // used to hide the soft keyboard
        //getWindow().getDecorView().setBackgroundColor(Color.parseColor("#4682B4"));
        resultText  = (TextView)findViewById(R.id.result_textView);
        yearText  = (EditText)findViewById(R.id.year_editbox);
        monthText  = (EditText)findViewById(R.id.month_editbox);
        addRetrieveButonListener();
    }

    public void addRetrieveButonListener() {
        final Context context = this;
        retrieveButton = (Button) findViewById(R.id.retrieve_button);
        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String year = new StringBuilder(yearText.getText()).toString().trim();
                String month = new StringBuilder(monthText.getText()).toString().trim();

                if (year.length() == 0 || month.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter year and month", Toast.LENGTH_SHORT).show();
                    return;
                }

                sb.setLength(0);
                callStatsService(year, month);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String text = sb.toString();
                resultText.setText(Html.fromHtml(text));
            }
        });
    }

    private void callStatsService(final String year, final String month) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://rotastaxi.herokuapp.com/stats/"+year+"/"+month);
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while (data != -1) {
                        char achar = (char)data;
                        sb.append(achar);
                        data = isr.read();
                    }
                }
                catch (Exception ex) {
                }

                //System.out.println("WebService return: "+sb.toString());
            }
        });

        thread.start();
    }

    /*
        Used to hide the soft keyboard when the user touches outside the text edit box
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return false;
    }

/*
    private void callMyService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rotastaxi.herokuapp.com/stats/")
                .build();


        GitHubService service = retrofit.create(GitHubService.class);

        Call call = service.getBody("2017", "5");
        String result = null;

        try {
            Response response = service.getBody("2017", "5").execute();
            //result = response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Just called heroku", Toast.LENGTH_SHORT).show();
    }
*/

}
