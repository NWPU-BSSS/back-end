package com.nwpu.bsss.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PPTShareService {
    long uploadFile(MultipartFile file,long userId) throws IOException;
    void downloadFile(HttpServletResponse response,long fileId) throws IOException;
}
