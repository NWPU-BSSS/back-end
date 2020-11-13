package com.nwpu.bsss.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetArticleResponse {
    private String author;  //email
    private String content;
    private String title;
    private String time;
}
