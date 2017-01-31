package com.example.palyndiel.httprequest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private List<Video> mListItems;
    private VideoAdapter mAdapter;
    private TextView mSelectedTrackTitle;
    private String output;

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListItems = new ArrayList<Video>();
        try {
            sendPostRequest("getVideos", 0);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            mListItems = new XMLParser().Parser(output);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListView listView = (ListView)findViewById(R.id.video_list_view);
        mAdapter = new VideoAdapter(this, mListItems);
        listView.setAdapter(mAdapter);

        mSelectedTrackTitle = (TextView)findViewById(R.id.selected_video_title);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video video = mListItems.get(position);
                int num = video.getID();

                String req = "http://192.168.0.19:4040/rest/stream.view?u=admin&p=admin&v=1.12.0&c=myapp&id="+num;

                /*mSelectedTrackTitle.setText(req);
                Intent myIntent = new Intent(MainActivity.this, VideoViewActivity.class);
                myIntent.putExtra("request", req);
                startActivity(myIntent);*/
                Intent myIntent = new Intent(MainActivity.this, VideoViewActivity.class);
                myIntent.setData(Uri.parse(req));
                startService(myIntent);

            }
        });
    }

    public void sendPostRequest(String req, int num) throws ExecutionException, InterruptedException {
        output = new GetVideos(this, MainActivity.this, req, 0).execute().get();
        //Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
        //mSelectedTrackTitle.setText(output);
    }

}
