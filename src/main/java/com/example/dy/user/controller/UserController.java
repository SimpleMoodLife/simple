package com.example.dy.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dy.user.entity.User;
import com.example.dy.user.service.IUserService;
import com.example.dy.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.dy.base.BaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-01-04
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    IUserService userService;

    @GetMapping
    public List<User> findList(){
        return userService.list(null);
    }

    @PostMapping
    public void save(@RequestBody User user){
         userService.save(user);
    }

    @GetMapping("/{id}")
    public User findObjById(@PathVariable("id") String id){
        return userService.getOne(new QueryWrapper<User>().eq("id",id));
    }
}
