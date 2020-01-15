package com.changgou.user.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import com.github.pagehelper.PageInfo;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private  TokenDecode tokenDecode;

    /***
     * User分页条件搜索实现
     * @param user
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  User user, @PathVariable  int page, @PathVariable  int size){
        //调用UserService实现分页条件查询User
        PageInfo<User> pageInfo = userService.findPage(user, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * User分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用UserService实现分页查询User
        PageInfo<User> pageInfo = userService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param user
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<User>> findList(@RequestBody(required = false)  User user){
        //调用UserService实现条件查询User
        List<User> list = userService.findList(user);
        return new Result<List<User>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result delete(@PathVariable String id){
        //调用UserService实现根据主键删除
        userService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改User数据
     * @param user
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  User user,@PathVariable String id){
        //设置主键值
        user.setUsername(id);
        //调用UserService实现修改User
        userService.update(user);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增User数据
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody   User user){
        //调用UserService实现添加User
        userService.add(user);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
    @GetMapping({"/{id}","/load/{id}"})
    public Result<User> findById(@PathVariable String id){
        //调用UserService实现根据主键查询User
        User user = userService.findById(id);
        return new Result<User>(true,StatusCode.OK,"查询成功",user);
    }

    /***
     * 查询User全部数据
     * @return
     */
    @GetMapping("/all")
    public Result<List<User>> findAll(){
        //调用UserService实现查询所有User
        List<User> list = userService.findAll();
        return new Result<List<User>>(true, StatusCode.OK,"查询成功",list) ;
    }


    /***
     * 添加用户积分
     * @param pint 加的分数
     * @return
     */
    @GetMapping("/points/add")
    public Result addUserPoints(@RequestParam("pint") Integer pint){
        String username = TokenDecode.getUserInfo().get("username");
        int count = userService.addUserPoints(username, pint);
        if(count > 0){
            return new Result<User>(true, StatusCode.OK, "添加积分成功");
        }else{
            return new Result<User>(false, StatusCode.ERROR, "添加积分失败");
        }
    }
    /**
     * 根据昵称查询用户
     *
     * @param nickName
     * @return
     */
    @GetMapping("/nickName/{nickName}")
    public Result findByNickName(@PathVariable String nickName) {
        //获取用户名
        Map<String, String> userMap = TokenDecode.getUserInfo();
        String username = userMap.get("username");
        User user = userService.findByNickName(username, nickName);
        if (user == null) {
            return new Result<>(true, StatusCode.OK, "", user);
        } else {
            return new Result<>(false, StatusCode.OK, "昵称重复", user);
        }
    }
    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @PostMapping
    Result UpdateUser(@RequestBody User user) {
        userService.UpdateUser(user);
        return new Result(true, StatusCode.OK, "更新成功！");
    }

    /**
     * 用户密码重置(非登录状态)
     * @param user
     * @return
     */
    @PostMapping("/reset")
    public  Result resetPassword(@RequestBody User user){
        Result result = userService.resetPassword(user);
        return  result;
    }

    /**
     * 用户密码重置(登录状态)
     * @param user
     * @return
     */
    @PutMapping("/resetlogin")
    public  Result resetPasswordLogin(@RequestBody User user){
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 重置密码
        userService.resetPasswordLogin(user);
        // 清楚cookie
        Cookie cookie = new Cookie("Authorization","");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);
        // 获取来源地址
        String referer = request.getHeader("referer");

        return new Result(true, StatusCode.OK, "重置成功,3秒之后跳转到登录界面！",referer);
    }
    /**
     * 查询单个用户信息
     *
     * @return
     */
    @GetMapping
    public Result findOne() {
        //获取用户名
        Map<String, String> userMap = TokenDecode.getUserInfo();
        String username = userMap.get("username");
        User users = userService.findByName(username);
        return new Result<>(true, StatusCode.OK, "查询成功", users);

    }
}
