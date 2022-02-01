package com.scnnplyapp.appsnanply.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.scnnplyapp.appsnanply.ContentPlayActivity;
import com.scnnplyapp.appsnanply.R;
import com.scnnplyapp.appsnanply.model.ContentItemList;
import com.scnnplyapp.appsnanply.utils.UtilitiesClass;

import java.util.ArrayList;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyView>{
    private ArrayList<ContentItemList> contentItemArrayLists;
    Activity context;

    public VideoAdapter(ArrayList<ContentItemList> contentArrayList, Activity context) {
        this.contentItemArrayLists = contentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_videocardview_design, parent, false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, @SuppressLint("RecyclerView") int position) {
        try {
            final String imageUrl = contentItemArrayLists.get(position).getThumbnail();
            Glide.with(context)
                    .load(new GlideUrl(imageUrl))
                    .placeholder(R.drawable.transparent_background)
                    .into(holder.videofirstimg);
        } catch (Exception e) {
        }
        Log.e("sizearray", String.valueOf(contentItemArrayLists.size()));
        holder.playvideo.setVisibility(View.VISIBLE);
        holder.playvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilitiesClass.RewardedAdsshow(context);
                Intent intent = new Intent(context, ContentPlayActivity.class);
                intent.putExtra("link", contentItemArrayLists.get(position).getContent());
                ((Activity) context).startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilitiesClass.RewardedAdsshow(context);
                Intent intent = new Intent(context, ContentPlayActivity.class);
                intent.putExtra("link", contentItemArrayLists.get(position).getContent());
                ((Activity) context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentItemArrayLists.size();
    }

    public class MyView extends RecyclerView.ViewHolder {

        public ImageView videofirstimg, playvideo;
        public MyView(View view) {
            super(view);
            videofirstimg =(ImageView) view.findViewById(R.id.videofstimg);
            playvideo =(ImageView) view.findViewById(R.id.btplay);

        }
    }
}
