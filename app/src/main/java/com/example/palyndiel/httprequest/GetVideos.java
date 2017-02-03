package com.example.palyndiel.httprequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
public class GetVideos extends AsyncTask<String, Void, String> {

    private final Context context;
    private final Activity activity;
    private String requete;
    private String parametres;
    private ProgressDialog progress;

    public GetVideos(Context c, Activity a){

        this.context = c;
        this.activity = a;
        this.requete = "";
        this.parametres = "";
//            this.error = status;
//            this.type = t;
    }

    protected void onPreExecute(){
        this.requete = "http://192.168.8.28:4040/rest/getVideos.view";
        this.parametres = "u=admin&p=admin&v=1.12.0&c=myapp";
        progress= new ProgressDialog(this.context);
        progress.setMessage("Loading");
        progress.show();
    }

    @Override
    protected String doInBackground(String... params) {

        final StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(requete);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            String urlParameters = parametres;
            connection.setRequestMethod("POST");
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(urlParameters);
            dStream.flush();
            dStream.close();
            //int responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder responseOutput = new StringBuilder();
            while((line = br.readLine()) != null ) {
                responseOutput.append(line);
            }
            br.close();

            output.append(responseOutput.toString());

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
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
        return output.toString();
    }

    protected void onPostExecute(String output) {
        progress.dismiss();
        //Toast.makeText(activity, output, Toast.LENGTH_LONG).show();
    }
}
