package com.scnnplyapp.appsnanply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.scnnplyapp.appsnanply.adapter.GameAdapter;
import com.scnnplyapp.appsnanply.model.BannerClass;
import com.scnnplyapp.appsnanply.model.ContentItemList;
import com.scnnplyapp.appsnanply.utils.UtilitiesClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeeAllGameActivity extends AppCompatActivity {
    RecyclerView rcvallgamedata;
    GridLayoutManager mLayoutManager1;
    private ArrayList<BannerClass> gamedata = new ArrayList<>();
    private ArrayList<ContentItemList> gamelist = new ArrayList<>();
    private String mTypeValue= "GAME.json";
    public static final String tag = "videos";
    GameAdapter adapter1;
    String position;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_game);
        textView=findViewById(R.id.videotext);

        position=getIntent().getStringExtra("position");

        if (UtilitiesClass.isNetworkCheck(this)) {
            new GetVideos().execute();
            UtilitiesClass.BannerAdsShow(SeeAllGameActivity.this, R.id.Ad_banner);
        }
        rcvallgamedata = (RecyclerView) findViewById(R.id.rcvallvideo);

        mLayoutManager1 = new GridLayoutManager(SeeAllGameActivity.this,2);
        mLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        rcvallgamedata.setLayoutManager(mLayoutManager1);
    }

    public class GetVideos extends AsyncTask<String, Integer, JSONObject> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new ProgressDialog(SeeAllGameActivity.this);
                pd.show();
            } catch (Exception e) {

            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                return UtilitiesClass.loadJSONFromAsset(SeeAllGameActivity.this, mTypeValue);
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
                        gamedata.addAll(BannerClass.getCategoryList(jsonArrayAction));
                    }
                    int pos=0;
                    pos= Integer.parseInt(position);
                    gamelist.addAll(gamedata.get(pos).getContentItemArrayList());
                    textView.setText(gamedata.get(pos).getStreamname());

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
        adapter1 = new GameAdapter(gamelist, this);
        rcvallgamedata.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

    }
}