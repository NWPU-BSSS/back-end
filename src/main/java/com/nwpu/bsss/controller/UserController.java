package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.exceptions.ValidationException;
import com.nwpu.bsss.repository.UserRepository;
import com.nwpu.bsss.service.UserService;
import com.nwpu.bsss.utils.UserInfoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserRepository userRepository;

    @ResponseBody
    @RequestMapping("/allUser")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


    @Resource
    private UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user, BindingResult bindingResult) {

        //add time stamp
        user.setTime(new Timestamp(new Date().getTime()));

        //validate email format
        new UserInfoValidator().validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException("Validation errors occurred");
        } else {
            long userId = userService.createUser(user); //get userId
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/info")
    public ResponseEntity<UserEntity> getUserInfo(@RequestParam("id") int id) {
        return new ResponseEntity<>(userService.findByUserID(id), HttpStatus.OK);
    }

}
