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


    public boolean checkExist(String filename,long userId,String filepath){
        File file = new File(filepath + userId + "_" + filename);
        return file.exists();
    }

    public String updateFile(MultipartFile file, long userId,String oldFilePath,String avatarPath) throws IOException {
        //判断存放的文件夹是否存在
        File targetFile = new File(avatarPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        targetFile = new File(oldFilePath);
        if(targetFile.exists()){
            log.info("旧文件：" + oldFilePath +"已被删除.");
            targetFile.delete();
        }
        return uploadFile(file,userId,avatarPath);


    }
    public String uploadFile(MultipartFile file, long userId,String filepath) throws IOException {

        File targetFile = new File(filepath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        String filename = userId + "_" + file.getOriginalFilename();

        FileOutputStream out = new FileOutputStream(filepath + filename);
        out.write(file.getBytes());

        out.close();

        return filepath + filename;

    }

    public void downloadFile(HttpServletResponse response,String filename,String filepath) throws IOException {

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
