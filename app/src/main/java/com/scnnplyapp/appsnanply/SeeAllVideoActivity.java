package com.scnnplyapp.appsnanply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.scnnplyapp.appsnanply.adapter.VideoAdapter;
import com.scnnplyapp.appsnanply.model.BannerClass;
import com.scnnplyapp.appsnanply.model.ContentItemList;
import com.scnnplyapp.appsnanply.utils.UtilitiesClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeeAllVideoActivity extends AppCompatActivity {

    RecyclerView rcvallvideodata;
    GridLayoutManager mLayoutManager1;
    private ArrayList<BannerClass> videodata = new ArrayList<>();
    private ArrayList<ContentItemList> videolist = new ArrayList<>();
    private String mTypeValue= "VIDEO.json";
    public static final String tag = "videos";
    VideoAdapter adapter1;
    String position;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_video);

        textView=findViewById(R.id.videotext);

        position=getIntent().getStringExtra("position");

        if (UtilitiesClass.isNetworkCheck(this)) {
            new GetVideos().execute();
            UtilitiesClass.BannerAdsShow(SeeAllVideoActivity.this, R.id.Ad_banner);
        }
        rcvallvideodata = (RecyclerView) findViewById(R.id.rcvallvideo);

        mLayoutManager1 = new GridLayoutManager(SeeAllVideoActivity.this,2);
        mLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        rcvallvideodata.setLayoutManager(mLayoutManager1);
    }

    public class GetVideos extends AsyncTask<String, Integer, JSONObject> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new ProgressDialog(SeeAllVideoActivity.this);
                pd.show();
            } catch (Exception e) {

            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                return UtilitiesClass.loadJSONFromAsset(SeeAllVideoActivity.this, mTypeValue);
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
                        videodata.addAll(BannerClass.getCategoryList(jsonArrayAction));
                    }
                    int pos=0;
                    pos= Integer.parseInt(position);
                    videolist.addAll(videodata.get(pos).getContentItemArrayList());
                    textView.setText(videodata.get(pos).getStreamname());

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
        adapter1 = new VideoAdapter(videolist, this);
        rcvallvideodata.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

    }
}