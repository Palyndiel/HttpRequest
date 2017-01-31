package com.example.palyndiel.httprequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by maxym on 31/01/2017.
 */
public class VideoAdapter extends BaseAdapter {

    private Context Context;
    private List<Video> Videos;

    public VideoAdapter(Context context, List<Video> tracks) {
        Context = context;
        Videos = tracks;
    }

    @Override
    public int getCount() {
        return Videos.size();
    }

    @Override
    public Video getItem(int position) {
        return Videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Video video = getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(Context).inflate(R.layout.video_list_row, parent, false);
            holder = new ViewHolder();
            //holder.trackImageView = (ImageView) convertView.findViewById(R.id.track_image);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.video_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleTextView.setText(video.getTitle());

        // Trigger the download of the URL asynchronously into the image view.
        //Picasso.with(mContext).load(track.getArtworkURL()).into(holder.trackImageView);

        return convertView;
    }

    static class ViewHolder {
        //ImageView trackImageView;
        TextView titleTextView;
    }
}
