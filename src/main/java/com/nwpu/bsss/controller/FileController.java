package com.nwpu.bsss.controller;

import com.nwpu.bsss.exceptions.NoFileFoundException;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.PPTShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {


    @Resource
    PPTShareService shareService;

    /**
     * 处理文件上传
     */
    @PostMapping(value = "/upload")
    public MyResponseEntity<Object> uploading(@RequestParam("file") MultipartFile file,
                                              @RequestParam("userId") String userId,
                                              @RequestHeader("accessToken") String accessToken) {
        long id;
        try {
            id = shareService.uploadFile(file,Long.parseLong(userId));
        }catch (IOException e){
            log.info("上传失败");
            return new MyResponseEntity<>(Code.BAD_OPERATION,"文件上传失败",null);
        }
        log.info("上传成功");
        return new MyResponseEntity<>(Code.BAD_OPERATION,"文件上传成功",id);

    }

    /**
     * 文件下载
     */
    @RequestMapping("/download")
    public void downLoad(HttpServletResponse response,
                         @RequestHeader("accessToken") String accessToken,
                         @RequestParam("userId") String userId,
                         @RequestParam("fileId")long fileId) {
        try{
            shareService.downloadFile(response,fileId);
        }catch (NoFileFoundException e){
            e.printStackTrace();
           log.error("文件"+fileId+"不存在");
        }catch (Exception e){
            log.error("内部错误");
        }

    }
}
