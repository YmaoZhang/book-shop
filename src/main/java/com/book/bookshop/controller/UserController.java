package com.book.bookshop.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.book.bookshop.entity.User;
import com.book.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.Query;
import javax.servlet.http.HttpSession;

/**
 * @Description: 用户控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //验证用户名是否存在
    @ResponseBody
    @RequestMapping("/checkUserName")
    public String checkUserName(String username){
        return userService.checkUser(username);
    }

    /**
     * 用户注册
     */
    @ResponseBody
    @RequestMapping("register")
    public String register(User user){
        userService.save(user);
        return "success";
    }

    /**
     * 用户登录
     */
    @ResponseBody
    @RequestMapping("/login")
    public String login(User user, HttpSession session){
        return userService.loginCheck(user,session);
    }

    /**
     * 修改用户密码
     */
    @ResponseBody
    @RequestMapping("/alter")
    public String alter(String newPwd,HttpSession session){
        User user = (User) session.getAttribute("user");
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("password",newPwd).eq("username",user.getUsername());
        // System.out.println("test"+newPwd);
        userService.update(updateWrapper);
        return "success";
    }
    @ResponseBody
    @RequestMapping("/checkOldPwd")
    public String checkOldPwd(String oldPwd,HttpSession session){
        User user = (User) session.getAttribute("user");
        String username=user.getUsername();
        QueryWrapper<User> wrapper=new QueryWrapper<User>();
        wrapper.eq("username",user.getUsername());
        userService.getOne(wrapper);
        return userService.checkUserPwd(username,oldPwd);
    }
    /**
     * 安全退出
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/book/index";
    }
}
