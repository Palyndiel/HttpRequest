package com.example.palyndiel.httprequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maxym on 29/01/2017.
 */
public class GetVideos extends AsyncTask<String, Void, Void> {

    private final Context context;
    private final Activity activity;
    private ProgressDialog progress;

    public GetVideos(Context c, Activity a){

        this.context = c;
        this.activity = a;
//            this.error = status;
//            this.type = t;
    }

    protected void onPreExecute(){
        progress= new ProgressDialog(this.context);
        progress.setMessage("Loading");
        progress.show();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {

            final TextView outputView = (TextView) activity.findViewById(R.id.showOutput);
            URL url = new URL("http://192.168.0.19:4040/rest/getVideos.view");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            String urlParameters = "u=admin&p=admin&v=1.12.0&c=myapp";
            connection.setRequestMethod("POST");
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(urlParameters);
            dStream.flush();
            dStream.close();
            int responseCode = connection.getResponseCode();

            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            final StringBuilder output = new StringBuilder("Request URL " + url);
            output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
            output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
            output.append(System.getProperty("line.separator")  + "Type " + "POST");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder responseOutput = new StringBuilder();
            System.out.println("output===============" + br);
            while((line = br.readLine()) != null ) {
                responseOutput.append(line);
            }
            br.close();

            output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    outputView.setText(output);
                    progress.dismiss();
                }
            });


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute() {
        progress.dismiss();
    }
}
