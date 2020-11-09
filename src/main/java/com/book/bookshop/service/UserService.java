package com.book.bookshop.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.bookshop.entity.User;
import com.book.bookshop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Description:用户业务层
 */
@Service
@SessionAttributes("user")
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private UserMapper userMapper;

    /**
     * 验证用户的存在性
     */
    public String checkUser(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return "101";//用户不存在
        } else {
            return "102";//用户已存在
        }
    }

    /**
     * 登录验证
     */
    public String loginCheck(User loginUser, HttpSession session){
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername,loginUser.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return "101";//用户不存在
        } else {
            //判断密码
            if(loginUser.getPassword().equals(user.getPassword())){
                session.setAttribute("user",user);
                return "100";
            } else {
                return "102";//密码不正确
            }
        }
    }
    public String checkUserPwd(String username, String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username).eq("password",password);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return "101";//密码错误
        } else {
            return "102";//密码正确
        }
    }
   /* public String alterCheck(String username,String password, HttpSession session){
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername,loginUser.getUsername()).set(User::getPassword,loginUser.getPassword())
        if(user == null){
            return "101";//用户不存在
        } else {
            //判断密码
            if(loginUser.getPassword().equals(user.getPassword())){
                session.setAttribute("user",user);
                return "100";
            } else {
                return "102";//密码不正确
            }
        }
    }*/
}
