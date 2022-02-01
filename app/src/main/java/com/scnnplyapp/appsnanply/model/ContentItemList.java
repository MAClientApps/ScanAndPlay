package com.scnnplyapp.appsnanply.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContentItemList {
    public static String tag = "ContentItem";

    String Id,Content,Thumbnail,Title,Thumbnail_Large;

    public static String getTag() {
        return tag;
    }

    public static void setTag(String tag) {
        ContentItemList.tag = tag;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getThumbnail_Large() {
        return Thumbnail_Large;
    }

    public void setThumbnail_Large(String thumbnail_Large) {
        Thumbnail_Large = thumbnail_Large;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public static ArrayList<ContentItemList> getContentList(JSONArray jsonArray) {

        ArrayList<ContentItemList> gamesList = new ArrayList<ContentItemList>();
        try {

            if (jsonArray != null && jsonArray.length()>0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);

                    gamesList.add(getContentFromJson(jObj));

                }
            }
            return gamesList;
        } catch (Exception e) {
            // TODO: handle exception

            return gamesList;
        }
    }

    private static ContentItemList getContentFromJson(JSONObject jObj) {
        ContentItemList contentItemListData = new ContentItemList();
        try {

            //AppLog.e("getContentFromJson", "Title : " + jObj.getString("Title"));

            if (jObj.has("ID") && !jObj.isNull("Id"))
                contentItemListData.setId(jObj.getString("Id"));

            if(jObj.has("Content") && !jObj.isNull("Content"))
                contentItemListData.setContent(jObj.getString("Content"));

            if(jObj.has("Thumbnail") && !jObj.isNull("Thumbnail"))
                contentItemListData.setThumbnail(jObj.getString("Thumbnail"));

            if(jObj.has("Thumbnail_Large") && !jObj.isNull("Thumbnail_Large"))
                contentItemListData.setThumbnail_Large(jObj.getString("Thumbnail_Large"));


            if (jObj.has("Title") && !jObj.isNull("Title"))
                contentItemListData.setTitle(jObj.getString("Title"));


            return contentItemListData;

        } catch (JSONException e) {

            return contentItemListData;
        }
    }
}
