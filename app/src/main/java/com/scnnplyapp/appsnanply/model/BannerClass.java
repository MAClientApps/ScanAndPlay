package com.scnnplyapp.appsnanply.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BannerClass {
    Integer id;
    String streamname;
    String imgurl;
    String pageurl;

    ArrayList<ContentItemList> contentItemList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreamname() {
        return streamname;
    }

    public void setStreamname(String streamname) {
        this.streamname = streamname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getPageurl() {
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }

    public ArrayList<ContentItemList> getContentItemArrayList() {
        return contentItemList;
    }

    public void setContentItemArrayList(ArrayList<ContentItemList> contentItemLists) {
        this.contentItemList = contentItemLists;
    }

    public static ArrayList<BannerClass> getCategoryList(JSONArray jsonArray) {

        ArrayList<BannerClass> gamesList = new ArrayList<BannerClass>();
        try {

            if (jsonArray != null && jsonArray.length()>0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);

                    gamesList.add(getCategoryFromJson(jObj));

                }
            }
            return gamesList;
        } catch (Exception e) {
           //  TODO: handle exception
              // VLogView.e(MainActivity.tag, "Error : " + e.toString());
            return gamesList;
        }
    }

    private static BannerClass getCategoryFromJson(JSONObject jObj) {
        BannerClass contentData = new BannerClass();
        try {

            if(jObj.has("ID") && !jObj.isNull("ID"))
                contentData.setId(jObj.getInt("ID"));

            if (jObj.has("Name") && !jObj.isNull("Name"))
                contentData.setStreamname(jObj.getString("Name"));

            if(jObj.has("Icon") && !jObj.isNull("Icon"))
                contentData.setImgurl(jObj.getString("Icon"));

            if(jObj.has("Content") && !jObj.isNull("Content"))
                contentData.setContentItemArrayList(ContentItemList.getContentList(jObj.getJSONArray("Content")));
            return contentData;
        } catch (JSONException e) {
            // VLogView.e(MainActivity.tag, "Error : " + e.toString());
            return contentData;
        }
    }
}
