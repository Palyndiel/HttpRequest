package com.example.palyndiel.httprequest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private List<Video> mListItems;
    private VideoAdapter mAdapter;
    private String output;

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            sendPostRequest("getVideos", 0);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            mListItems = new XMLParser().Parser(output);
            if (mListItems == null){
                Video defau = new Video();
                defau.setID(0);
                defau.setTitle("Pas de video disponible");
                mListItems.add(defau);
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListView listView = (ListView)findViewById(R.id.video_list_view);
        mAdapter = new VideoAdapter(this, mListItems);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video video = mListItems.get(position);
                int num = video.getID();

                String req = String.valueOf(num);

                Intent myIntent = new Intent(MainActivity.this, VideoViewActivity.class);
                myIntent.putExtra("id", req);
                startActivity(myIntent);
                /*Intent myIntent = new Intent(Intent.ACTION_VIEW);
                myIntent.setDataAndType(Uri.parse(req), "video/mp4");
                // Always use string resources for UI text.
                // This says something like "Share this photo with"
                String title = "Selectionner un lecteur video";
                // Create intent to show the chooser dialog
                Intent chooser = Intent.createChooser(myIntent, title);

                // Verify the original intent will resolve to at least one activity
                if (myIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }*/
                /*int vlcRequestCode = 42;
                Uri uri = Uri.parse(req);
                Intent vlcIntent = new Intent(Intent.ACTION_VIEW);
                vlcIntent.setPackage("org.videolan.vlc");
                vlcIntent.setDataAndTypeAndNormalize(uri, "video*//*");
                startActivityForResult(vlcIntent, vlcRequestCode);*/

            }
        });
    }

    public void sendPostRequest(String req, int num) throws ExecutionException, InterruptedException {
        output = new GetVideos(this, MainActivity.this, req, 0).execute().get();
        //Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
    }

}
