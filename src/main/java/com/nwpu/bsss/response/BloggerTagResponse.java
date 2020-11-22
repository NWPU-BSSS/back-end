package com.nwpu.bsss.response;


import com.nwpu.bsss.domain.dto.Tag;

import java.util.ArrayList;

//Todo:Tag的view还没做，现在反回空的tag列表
public class BloggerTagResponse {
    private ArrayList<Tag> tagList = new ArrayList<Tag>();
    public BloggerTagResponse(long userId){
        this.tagList = null;
    }
    public ArrayList<Tag> getTagList(){
        return tagList;
    }
}
