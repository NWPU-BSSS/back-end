package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.FileEntity;
import com.nwpu.bsss.exceptions.NoFileFoundException;
import com.nwpu.bsss.repository.FileRepository;
import com.nwpu.bsss.service.PPTShareService;
import com.nwpu.bsss.utils.FileComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class PPTShareServiceImpl implements PPTShareService {

    @Autowired
    private FileComponent fileComponent;

    @Resource
    private FileRepository fileRepository;

    @Override
    public long uploadFile(MultipartFile file, long userId) throws IOException {

        Optional<FileEntity> lookFile = fileRepository.findByUserIdAndAndFileName(userId,file.getOriginalFilename());
        long fileId;
        if(lookFile.isEmpty()){
            FileEntity newFile = new FileEntity();

            newFile.setFileName(file.getOriginalFilename());
            newFile.setTime(new Timestamp(new Date().getTime()));
            newFile.setUserId(userId);

            fileId = fileRepository.save(newFile).getFileId();
        }
        else{
            fileId = lookFile.get().getFileId();
            log.info(" 文件ID:" + fileId +" 已更新");
        }

        fileComponent.uploadFile(file,userId);

        log.info("文件："+file.getOriginalFilename() + " 文件ID:" + fileId +" 已成功存储");
        return fileId;
    }

    @Override
    public void downloadFile(HttpServletResponse response,long fileId) throws IOException {
        Optional<FileEntity> file = fileRepository.findById(fileId);
        if(file.isPresent()){
            String filename = file.get().getUserId() + "_" + file.get().getFileName();
            log.info("filename: " + filename);
            fileComponent.downloadFile(response,filename);
        }
        else{
            throw new NoFileFoundException("NO File FOUND");
        }

    }
}
