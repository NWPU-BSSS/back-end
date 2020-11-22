package com.nwpu.bsss.domain.dto;
//Todo:
public class Tag {
    private String tagName;
    private long count;

    public Tag() {
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
