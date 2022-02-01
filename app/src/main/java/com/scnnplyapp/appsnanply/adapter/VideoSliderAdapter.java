package com.scnnplyapp.appsnanply.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scnnplyapp.appsnanply.ContentPlayActivity;
import com.scnnplyapp.appsnanply.R;
import com.scnnplyapp.appsnanply.model.ContentItemList;

import java.util.ArrayList;

public class VideoSliderAdapter extends RecyclerView.Adapter<VideoSliderAdapter.ViewHolder> {

    private ArrayList<ContentItemList> VideosliderArrayList;
    Context context;

    // RecyclerView recyclerView;
    public VideoSliderAdapter(Context context, ArrayList<ContentItemList> listdata) {
        this.VideosliderArrayList = listdata;
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.video_slider_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            Glide.with(context)
                    .load(VideosliderArrayList.get(position).getThumbnail())
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                    .into(holder.videoImgview);
        } catch (Exception e) {
            e.printStackTrace();
           // LogUtil.e("Adapter","Exception : "+e.getMessage());
        }
        holder.titleTExt.setText("" + VideosliderArrayList.get(position).getTitle());

        holder.titleTExt.setVisibility(View.VISIBLE);

        holder.videoplaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.e("Adapter ","OnClick");
                //LogUtil.e("Adapter ","OnClick");
                Intent intent=new Intent(context, ContentPlayActivity.class);
                intent.putExtra("link", VideosliderArrayList.get(position).getContent());
                ( (Activity)context).startActivity(intent);

            }
        });

        holder.videoImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.e("Adapter ","OnClick");
               // LogUtil.e("Adapter ","OnClick");
                Intent intent=new Intent(context, ContentPlayActivity.class);
                intent.putExtra("link", VideosliderArrayList.get(position).getContent());
                ( (Activity)context).startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return VideosliderArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView videoImgview, videoplaybtn;
        FrameLayout VideoFMLayout;
        TextView titleTExt;
        public ViewHolder(View itemView) {
            super(itemView);
            videoImgview = (ImageView) itemView.findViewById(R.id.videocat_pager);
            videoplaybtn = (ImageView) itemView.findViewById(R.id.VPlayimg);
            VideoFMLayout =(FrameLayout) itemView.findViewById(R.id.FMLayout);
            titleTExt = (TextView) itemView.findViewById(R.id.Vtitletext);
        }
    }
}
