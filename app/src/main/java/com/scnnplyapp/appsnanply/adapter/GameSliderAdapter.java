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

public class GameSliderAdapter extends RecyclerView.Adapter<GameSliderAdapter.ViewHolder> {

    private ArrayList<ContentItemList> GamesliderArrayList;
    Context context;

    // RecyclerView recyclerView;
    public GameSliderAdapter(Context context, ArrayList<ContentItemList> listdata) {
        this.GamesliderArrayList = listdata;
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.game_slider_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            Glide.with(context)
                    .load(GamesliderArrayList.get(position).getThumbnail_Large())
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                    .into(holder.gamefistimgview);
        } catch (Exception e) {
            e.printStackTrace();
           // LogUtil.e("Adapter","Exception : "+e.getMessage());
        }
        holder.TItletxview.setText("" + GamesliderArrayList.get(position).getTitle());

        holder.TItletxview.setVisibility(View.VISIBLE);

        holder.gameplayimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.e("Adapter ","OnClick");
                //LogUtil.e("Adapter ","OnClick");
                Intent intent=new Intent(context, ContentPlayActivity.class);
                intent.putExtra("link", GamesliderArrayList.get(position).getContent());
                ( (Activity)context).startActivity(intent);

            }
        });

        holder.gamefistimgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.e("Adapter ","OnClick");
               // LogUtil.e("Adapter ","OnClick");
                Intent intent=new Intent(context, ContentPlayActivity.class);
                intent.putExtra("link", GamesliderArrayList.get(position).getContent());
                ( (Activity)context).startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return GamesliderArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gamefistimgview, gameplayimg;
        FrameLayout FMLayout;
        TextView TItletxview;
        public ViewHolder(View itemView) {
            super(itemView);
            gamefistimgview = (ImageView) itemView.findViewById(R.id.cat_pager);
            gameplayimg = (ImageView) itemView.findViewById(R.id.GPlayimg);
            FMLayout =(FrameLayout) itemView.findViewById(R.id.FMLyut);
            TItletxview = (TextView) itemView.findViewById(R.id.titletext);
        }
    }
}
