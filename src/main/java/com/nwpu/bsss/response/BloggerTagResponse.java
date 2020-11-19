package com.nwpu.bsss.response;


import java.util.ArrayList;

//Todo
public class BloggerTagResponse {
    private ArrayList tagList = new ArrayList<String>();
    public BloggerTagResponse(long userId){
        this.tagList = null;
    }
    public ArrayList<String> getTagList(){
        return tagList;
    }
}
