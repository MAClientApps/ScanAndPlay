package com.scnnplyapp.appsnanply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.scnnplyapp.appsnanply.adapter.VideoAdapter;
import com.scnnplyapp.appsnanply.adapter.VideoSliderAdapter;
import com.scnnplyapp.appsnanply.model.BannerClass;
import com.scnnplyapp.appsnanply.model.ContentItemList;
import com.scnnplyapp.appsnanply.utils.UtilitiesClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator2;

public class VideoDataActivity extends AppCompatActivity {
    RecyclerView VideoSliderRCV, VideoRcv1,VideoRcv2,VideoRcv3,VideoRcv4;
    private String mTypeValue= "VIDEO.json";
    VideoSliderAdapter videoSliderAdapter;
    ZoomScrollLayout zoomScrollLayout;
    ProgressDialog pd;
    private ArrayList<ContentItemList> Vidodatalist1 = new ArrayList<>();
    private ArrayList<ContentItemList> Vidodatalist2 = new ArrayList<>();
    private ArrayList<ContentItemList> Vidodatalist3 = new ArrayList<>();
    private ArrayList<ContentItemList> Vidodatalist4 = new ArrayList<>();
    private ArrayList<BannerClass> Gamelist = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    LinearLayoutManager mLayoutManager1;
    LinearLayoutManager mLayoutManager2;
    LinearLayoutManager mLayoutManager3;
    VideoAdapter adapter;
    VideoAdapter adapter1;
    VideoAdapter adapter2;
    VideoAdapter adapter3;
    TextView txtall1,txtall2,txtall3,txtall4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        if (UtilitiesClass.isNetworkCheck(VideoDataActivity.this)) {
            UtilitiesClass.BannerAdsShow(VideoDataActivity.this, R.id.Ad_banner);
            new GetVideos().execute();
        }

        VideoSliderRCV =(RecyclerView)findViewById(R.id.VideoSliderRCV);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        VideoRcv1 =(RecyclerView)findViewById(R.id.VRcvall1);
        VideoRcv1.setLayoutManager(mLayoutManager);

        mLayoutManager1 = new LinearLayoutManager(this);
        mLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        VideoRcv2 =(RecyclerView)findViewById(R.id.Vrcvall2);
        VideoRcv2.setLayoutManager(mLayoutManager1);

        mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        VideoRcv3 =(RecyclerView)findViewById(R.id.VRcvall3);
        VideoRcv3.setLayoutManager(mLayoutManager2);

        mLayoutManager3 = new LinearLayoutManager(this);
        mLayoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        VideoRcv4 =(RecyclerView)findViewById(R.id.VRcvall4);
        VideoRcv4.setLayoutManager(mLayoutManager3);

        txtall1=findViewById(R.id.Vseeall1);
        txtall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoDataActivity.this,SeeAllVideoActivity.class);
                intent.putExtra("position", "1");
                startActivity(intent);
            }
        });
        txtall2=findViewById(R.id.Vseeall2);
        txtall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoDataActivity.this,SeeAllVideoActivity.class);
                intent.putExtra("position", "2");
                startActivity(intent);
            }
        });
        txtall3=findViewById(R.id.Vseeall3);
        txtall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoDataActivity.this,SeeAllVideoActivity.class);
                intent.putExtra("position", "3");
                startActivity(intent);
            }
        });
        txtall4=findViewById(R.id.Vseeall4);
        txtall4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoDataActivity.this,SeeAllVideoActivity.class);
                intent.putExtra("position", "4");
                startActivity(intent);
            }
        });

    }

    public class GetVideos extends AsyncTask<String, Integer, JSONObject> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new ProgressDialog(VideoDataActivity.this);
                pd.show();
            } catch (Exception e) {

            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                return UtilitiesClass.loadJSONFromAsset(VideoDataActivity.this, mTypeValue);
            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject jsonGames) {
            super.onPostExecute(jsonGames);
            try {

                try {
                    if (jsonGames != null && jsonGames.has("Category") && !jsonGames.isNull("Category")) {
                        final JSONArray jsonArrayAction = jsonGames.getJSONArray("Category");
                        Gamelist.addAll(BannerClass.getCategoryList(jsonArrayAction));
                    }

                    zoomScrollLayout = new ZoomScrollLayout(VideoDataActivity.this,Dp2px(30));
                    VideoSliderRCV.setLayoutManager(zoomScrollLayout);
                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zoomScrollLayout.scrollToPosition(0);
                        }}, 100);

                    videoSliderAdapter = new VideoSliderAdapter(VideoDataActivity.this, Gamelist.get(0).getContentItemArrayList()) ;
                    VideoSliderRCV.setAdapter(videoSliderAdapter);

                    PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                    pagerSnapHelper.attachToRecyclerView(VideoSliderRCV);

                    CircleIndicator2 indicator = (CircleIndicator2) findViewById(R.id.gameindicator);
                    indicator.attachToRecyclerView(VideoSliderRCV, pagerSnapHelper);

                    Vidodatalist1.addAll(Gamelist.get(1).getContentItemArrayList());
                    Vidodatalist2.addAll(Gamelist.get(2).getContentItemArrayList());
                    Vidodatalist3.addAll(Gamelist.get(3).getContentItemArrayList());
                    Vidodatalist4.addAll(Gamelist.get(4).getContentItemArrayList());


                } catch (Exception e) {

                }
            } catch (Exception e) {
            }
            try {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            } catch (Exception e) {

            }
            loadList();
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadList() {
        adapter = new VideoAdapter(Vidodatalist1, this);
        adapter1 = new VideoAdapter(Vidodatalist2, this);
        adapter2 = new VideoAdapter(Vidodatalist3, this);
        adapter3 = new VideoAdapter(Vidodatalist4, this);

        VideoRcv1.setAdapter(adapter);
        VideoRcv2.setAdapter(adapter1);
        VideoRcv3.setAdapter(adapter2);
        VideoRcv4.setAdapter(adapter3);
        adapter.notifyDataSetChanged();
    }
    public int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }




}