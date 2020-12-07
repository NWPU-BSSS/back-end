package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.AnnouncementsEntity;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author alecHe
 * @desc 管理员接口
 * @date 2020-12-07 10:28:25
 */
@Slf4j
@RequestMapping("admin")
@RestController
public class AdminController {

    @Resource
    AdminService adminService;
    final String InternalError = "内部错误";

    @PostMapping("announcement")
    public MyResponseEntity<Object> makeAnnouncement(@RequestParam("admin")String admin,
                                                     @RequestParam("password")String password,
                                                     @RequestParam("content")String content,
                                                     @RequestParam("title")String title,
                                                     @RequestParam("endTime")String endTime,
                                                     @RequestParam("startTime")String startTime){

        long publisher = adminService.check(admin,password);
        Timestamp start;
        Timestamp end;
        long annId;

        if(publisher == -1) {
            log.error("管理员密码错误");
            return new MyResponseEntity<>(Code.BAD_OPERATION, "管理员账号或密码错误", null);
        }
        try {
            start = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime).getTime());
            end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime).getTime());

            if(start.after(end)) {
                throw new ParseException("",0);
            }

            annId = adminService.makeAnnounce(new AnnouncementsEntity(title,publisher,start,end,
                    new Timestamp(System.currentTimeMillis()),content));

        } catch (ParseException e) {
            log.error("时间格式错误");
            return new MyResponseEntity<>(Code.BAD_OPERATION, "时间格式错误", null);
        }catch (Exception e){
            e.printStackTrace();
            return new MyResponseEntity<>(Code.BAD_OPERATION, InternalError, null);
        }

        return new MyResponseEntity<>(Code.OK, "公告已发布", null);

    }

    @DeleteMapping("blog/{blogId}")
    public MyResponseEntity<Object> deleteBlog(@RequestParam("admin")String admin,
                                               @RequestParam("password")String password,
                                               @PathVariable("blogId") long blogId){
        log.info(String.valueOf(blogId));
        long admin_ = adminService.check(admin,password);
        if(admin_ == -1) {
            log.error("管理员密码错误");
            return new MyResponseEntity<>(Code.BAD_OPERATION, "管理员账号或密码错误", null);
        }
        if(adminService.deleteBlog(blogId)){
            return new MyResponseEntity<>(Code.OK, "成功了", null);
        }
        else{
            return new MyResponseEntity<>(Code.BAD_OPERATION, "博客不存在", null);
        }

    }

    @DeleteMapping("user/{userId}")
    public MyResponseEntity<Object> deleteUser(@RequestParam("admin")String admin,
                                               @RequestParam("password")String password,
                                               @PathVariable("userId")long userId){
        long admin_ = adminService.check(admin,password);
        if(admin_ == -1) {
            log.error("管理员密码错误");
            return new MyResponseEntity<>(Code.BAD_OPERATION, "管理员账号或密码错误", null);
        }

        if(adminService.deleteUser(userId)){
            return new MyResponseEntity<>(Code.OK, "成功了", null);
        }
        else{
            return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
        }
    }

}
