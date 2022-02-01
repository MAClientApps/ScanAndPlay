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

import com.scnnplyapp.appsnanply.adapter.GameAdapter;
import com.scnnplyapp.appsnanply.adapter.GameSliderAdapter;
import com.scnnplyapp.appsnanply.model.BannerClass;
import com.scnnplyapp.appsnanply.model.ContentItemList;
import com.scnnplyapp.appsnanply.utils.UtilitiesClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator2;

public class GameDataActivity extends AppCompatActivity  {
    RecyclerView GameSliderRCV, GameRcv1,GameRcv2,GameRcv3,GameRcv4;
    private String mTypeValue= "GAME.json";
    public static final String tag = "MainActivity";
    GameSliderAdapter gameSliderAdapter;
    ZoomScrollLayout zoomScrollLayout;
    ProgressDialog pd;
    private ArrayList<ContentItemList> Gamedatalist1 = new ArrayList<>();
    private ArrayList<ContentItemList> Gamedatalist2 = new ArrayList<>();
    private ArrayList<ContentItemList> Gamedatalist3 = new ArrayList<>();
    private ArrayList<ContentItemList> Gamedatalist4 = new ArrayList<>();
    private ArrayList<BannerClass> Gamelist = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    LinearLayoutManager mLayoutManager1;
    LinearLayoutManager mLayoutManager2;
    LinearLayoutManager mLayoutManager3;
    GameAdapter adapter;
    GameAdapter adapter1;
    GameAdapter adapter2;
    GameAdapter adapter3;
    TextView gametxtall1,gametxtall2,gametxtall3,gametxtall4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GameSliderRCV =(RecyclerView)findViewById(R.id.SliderRCV);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        GameRcv1 =(RecyclerView)findViewById(R.id.GRcvall1);
        GameRcv1.setLayoutManager(mLayoutManager);

        mLayoutManager1 = new LinearLayoutManager(this);
        mLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        GameRcv2 =(RecyclerView)findViewById(R.id.GRcvall2);
        GameRcv2.setLayoutManager(mLayoutManager1);

        mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        GameRcv3 =(RecyclerView)findViewById(R.id.GRcvall3);
        GameRcv3.setLayoutManager(mLayoutManager2);

        mLayoutManager3 = new LinearLayoutManager(this);
        mLayoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        GameRcv4 =(RecyclerView)findViewById(R.id.GRcvall4);
        GameRcv4.setLayoutManager(mLayoutManager3);

        gametxtall1=findViewById(R.id.Gseeall1);
        gametxtall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDataActivity.this,SeeAllGameActivity.class);
                intent.putExtra("position", "1");
                startActivity(intent);
            }
        });

        gametxtall2=findViewById(R.id.Gseeall2);
        gametxtall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDataActivity.this,SeeAllGameActivity.class);
                intent.putExtra("position", "2");
                startActivity(intent);
            }
        });
        gametxtall3=findViewById(R.id.Gseeall3);
        gametxtall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDataActivity.this,SeeAllGameActivity.class);
                intent.putExtra("position", "3");
                startActivity(intent);
            }
        });
        gametxtall4=findViewById(R.id.Gseeall4);
        gametxtall4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDataActivity.this,SeeAllGameActivity.class);
                intent.putExtra("position", "4");
                startActivity(intent);
            }
        });


        if (UtilitiesClass.isNetworkCheck(GameDataActivity.this)) {
            UtilitiesClass.BannerAdsShow(GameDataActivity.this, R.id.Ad_banner);
            new GetVideos().execute();
        }


    }

    public class GetVideos extends AsyncTask<String, Integer, JSONObject> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new ProgressDialog(GameDataActivity.this);
                pd.show();
            } catch (Exception e) {

            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                return UtilitiesClass.loadJSONFromAsset(GameDataActivity.this, mTypeValue);
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

                    zoomScrollLayout = new ZoomScrollLayout(GameDataActivity.this,Dp2px(30));
                    GameSliderRCV.setLayoutManager(zoomScrollLayout);
                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zoomScrollLayout.scrollToPosition(0);
                        }}, 100);

                    gameSliderAdapter = new GameSliderAdapter(GameDataActivity.this, Gamelist.get(0).getContentItemArrayList()) ;
                    GameSliderRCV.setAdapter(gameSliderAdapter);

                    PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                    pagerSnapHelper.attachToRecyclerView(GameSliderRCV);

                    CircleIndicator2 indicator = (CircleIndicator2) findViewById(R.id.gameindicator);
                    indicator.attachToRecyclerView(GameSliderRCV, pagerSnapHelper);

                    Gamedatalist1.addAll(Gamelist.get(1).getContentItemArrayList());
                    Gamedatalist2.addAll(Gamelist.get(2).getContentItemArrayList());
                    Gamedatalist3.addAll(Gamelist.get(3).getContentItemArrayList());
                    Gamedatalist4.addAll(Gamelist.get(4).getContentItemArrayList());


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
        adapter = new GameAdapter(Gamedatalist1, this);
        adapter1 = new GameAdapter(Gamedatalist2, this);
        adapter2 = new GameAdapter(Gamedatalist3, this);
        adapter3 = new GameAdapter(Gamedatalist4, this);

        GameRcv1.setAdapter(adapter);
        GameRcv2.setAdapter(adapter1);
        GameRcv3.setAdapter(adapter2);
        GameRcv4.setAdapter(adapter3);
        adapter.notifyDataSetChanged();
    }
    public int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}