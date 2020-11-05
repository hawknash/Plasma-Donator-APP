package com.example.plasmadonator.ui.send;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void,Void,Void> {

    String data = "";
    String data1 = "", data2 = "";


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.covid19india.org/data.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

        } catch (MalformedURLException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        JSONObject da = null;
        try {
            JSONObject ja = new JSONObject(data);
            JSONObject jo= ja.getJSONArray("statewise").getJSONObject(0);
            //da = new JSONObject(data);
         //   JSONObject jo = ja.getJSONObject("Uttar Pradesh").getJSONObject("districtData").getJSONObject("Kanpur Nagar");

            // Log.e("doo","doo"+ja.get("cases_time_series"));

         //   Log.e("doo", "doo" + jo.get("active"));
            SendFragment.active.setText(jo.get("active").toString());
            SendFragment.confirmed.setText(jo.get("confirmed").toString());
            SendFragment.deceased.setText(jo.get("deaths").toString());
           SendFragment.recovered.setText(jo.get("recovered").toString());



        } catch (Exception e) {
            Log.e("hhh", "hhh" + e);
            e.printStackTrace();
        }


    }
}