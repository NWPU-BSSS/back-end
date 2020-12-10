package com.nwpu.bsss.response;


import com.nwpu.bsss.domain.dto.Tag;

import java.util.ArrayList;
import java.util.HashMap;

//Todo:Tag的view还没做，现在反回空的tag列表
public class BloggerTagResponse {
    private HashMap<String,Tag> tagList ;
    public BloggerTagResponse(){
        this.tagList = new HashMap<String,Tag>();
    }
    public HashMap<String,Tag> getTagList(){
        return tagList;
    }

    public void setTagList(HashMap<String, Tag> tagList) {
        this.tagList = tagList;
    }
}
