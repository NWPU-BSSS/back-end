package com.nwpu.bsss.utils;

import com.nwpu.bsss.exceptions.NoFileFoundException;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class FileComponent {

    @Value("${filepath}")
    private String filepath;

    public boolean checkExist(String filename,long userId){
        File file = new File(filepath + userId + "_" + filename);
        return file.exists();
    }

    public void uploadFile(MultipartFile file, long userId) throws IOException {

        File targetFile = new File(filepath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        String filename = userId + "_" + file.getOriginalFilename();

        FileOutputStream out = new FileOutputStream(filepath + filename);
        out.write(file.getBytes());

        out.close();

    }

    public void downloadFile(HttpServletResponse response,String filename) throws IOException {

        File file = new File(filepath + "/" + filename);

        if(file.exists()){
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            FileInputStream fis= new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            os = response.getOutputStream();
            int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer);
                i = bis.read(buffer);
            }
        }
        else{
            throw new NoFileFoundException("NO FILE FOUND");
        }

    }
}
