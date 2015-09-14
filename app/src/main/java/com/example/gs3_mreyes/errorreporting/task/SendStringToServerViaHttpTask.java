package com.example.gs3_mreyes.errorreporting.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by GS3-MREYES on 9/14/2015.
 */
public class SendStringToServerViaHttpTask extends AsyncTask<Void, Void, Void> {

    public static final String TAG = SendStringToServerViaHttpTask.class.getSimpleName();

    private String errorMessage;

    public SendStringToServerViaHttpTask(String errorMessage) {
        this.errorMessage = errorMessage;
        Log.d(TAG, "TASK STARTED");
    }


    @Override
    protected Void doInBackground(Void... params) {

        try{
            URL url = new URL("http://192.168.0.144:8084/API/write_error_log");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("errormessage", errorMessage);

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(getQuery(parameters));
            out.close();

            conn.connect();

            //start listening to the stream
            Scanner inStream = new Scanner(conn.getInputStream());
            String response = "";
            while(inStream.hasNextLine())
                response+=(inStream.nextLine());

            Log.d(TAG,"response :" + response);

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG,"TASK ENDED");
    }

    private String getQuery(Map<String, String> parameters) throws UnsupportedEncodingException {

        Iterator iterator;
        StringBuilder result = new StringBuilder();
        boolean first = true;

        iterator = parameters.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = (String) parameters.get(key);

            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value, "UTF-8"));

        }

        return result.toString();
    }
}
