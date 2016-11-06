package com.training.al.tutapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {

  public static   String [] urlList;
    WebView wv;
    String url = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=98235b8fa38b42e68ba2d35d65409671";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NYTAsyncTask().execute(url);
            }
        });
    }

     public class NYTAsyncTask extends AsyncTask<String, String, String[]>{

         ProgressDialog progressBar = new ProgressDialog(MainActivity.this);


         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             progressBar.setMessage("Loading...");
             progressBar.setIndeterminate(true);
             progressBar.show();
         }

         @Override
         protected String[] doInBackground(String... params) {
             try {
                 URL oracle = new URL(params[0]);
                 URLConnection yc = oracle.openConnection();
                 BufferedReader in = new BufferedReader(new InputStreamReader(
                         yc.getInputStream()));
                 String inputLine;

                 while ((inputLine = in.readLine()) != null) {
                     JSONObject obj = new JSONObject(inputLine);
                     JSONArray arr = obj.getJSONArray("results");
                     String[] list = new String[arr.length()];
                      urlList = new String[arr.length()];
                        for (int i = 0; i < arr.length(); i++){
                            list[i] = arr.getJSONObject(i).getString("title");
                            urlList[i]= arr.getJSONObject(i).getString("url");
                        }
                     return list;

                 }
                 in.close();

             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (JSONException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             return null;
         }


         protected void onPostExecute(String[] s) {
             super.onPostExecute(s);
             progressBar.dismiss();
             Log.d("faiz",""+urlList[0]);
             Log.d("Faiz", ""+s[0]);
             ListAdapter la = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, s);
             ListView mylist = (ListView) findViewById(R.id.listviewnyt);
             mylist.setAdapter(la);

             mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     wv = (WebView)findViewById(R.id.webview);
                     wv.getSettings().setJavaScriptEnabled(true);
                     wv.loadUrl(urlList[position]);
                     //wv.setWebViewClient(new MyBrowser());

                 }
             });



         }
     }

}
