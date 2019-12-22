package com.example.birinci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView idText, dersText, durumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idText = findViewById(R.id.idText);
        dersText = findViewById(R.id.dersText);
        durumText = findViewById(R.id.durumText);


    }

    public void getRates(View view){
        DownloadData downloadData = new DownloadData();
        try{
            String url ="https://service.inciryazilim.com.tr/WebServicebesici.asmx/Dersler";
            downloadData.execute(url);

        }catch (Exception e){

        }

    }

    private class DownloadData extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpsURLConnection httpsURLConnection;
            try{
                url = new URL(strings[0]);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                while(data >0){
                    char character = (char) data;
                    result +=  character;
                    data= inputStreamReader.read();
                }

                return result;
            }catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println("alınan veri:"+s);

            try{
                JSONObject jsonObject = new JSONObject(s);
                String dersAdi = jsonObject.getString("DersAdi");
                dersText.setText("DersAdi" + dersAdi);
                //System.out.println("DersAdi:"+dersAdi);
                String durum = jsonObject.getString("Durum");
                durumText.setText("Durum"+ durum);
                //System.out.println("Durum:"+durum);


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
