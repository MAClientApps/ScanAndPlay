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


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyView>{
    private ArrayList<ContentItemList> ContentItemArrayList;
    Activity context;

    public GameAdapter(ArrayList<ContentItemList> contentArrayList, Activity context) {
        this.ContentItemArrayList = contentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_gamecardview_design, parent, false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, @SuppressLint("RecyclerView") int position) {
        try {
            final String imageUrl = ContentItemArrayList.get(position).getThumbnail();
            Glide.with(context)
                    .load(new GlideUrl(imageUrl))
                    .placeholder(R.drawable.transparent_background)
                    .into(holder.gamefirstimg);
        } catch (Exception e) {
        }
        Log.e("sizearray", String.valueOf(ContentItemArrayList.size()));
        holder.playbtn.setVisibility(View.VISIBLE);
        holder.playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilitiesClass.RewardedAdsshow(context);
                Intent intent = new Intent(context, ContentPlayActivity.class);
                intent.putExtra("link", ContentItemArrayList.get(position).getContent());
                ((Activity) context).startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilitiesClass.RewardedAdsshow(context);
                Intent intent = new Intent(context, ContentPlayActivity.class);
                intent.putExtra("link", ContentItemArrayList.get(position).getContent());
                ((Activity) context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ContentItemArrayList.size();
    }

    public class MyView extends RecyclerView.ViewHolder {

        public ImageView gamefirstimg, playbtn;
        public MyView(View view) {
            super(view);
            gamefirstimg =(ImageView) view.findViewById(R.id.gamefstimg);
            playbtn =(ImageView) view.findViewById(R.id.buttonplay);

        }
    }
}
