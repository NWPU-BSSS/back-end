package com.nwpu.bsss.domain.dto;
//Todo:
public class Tag {
    private String tag;
    private long count;

    public Tag() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tagName) {
        this.tag = tagName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
