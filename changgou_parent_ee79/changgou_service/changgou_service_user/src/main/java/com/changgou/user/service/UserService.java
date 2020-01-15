package com.changgou.user.service;

import com.changgou.user.pojo.User;
import com.github.pagehelper.PageInfo;
import entity.Result;

import java.util.List;

/****
 * @Author:sz.itheima
 * @Description:User业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface UserService {

    /***
     * User多条件分页查询
     * @param user
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(User user, int page, int size);

    /***
     * User分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(int page, int size);

    /***
     * User多条件搜索方法
     * @param user
     * @return
     */
    List<User> findList(User user);

    /***
     * 删除User
     * @param id
     */
    void delete(String id);

    /***
     * 修改User数据
     * @param user
     */
    void update(User user);

    /***
     * 新增User
     * @param user
     */
    void add(User user);

    /**
     * 根据ID查询User
     * @param id
     * @return
     */
     User findById(String id);

    /***
     * 查询所有User
     * @return
     */
    List<User> findAll();

    /***
     * 添加用户积分
     * @param username
     * @param pint
     * @return
     */
    int addUserPoints(String username,Integer pint);
    /**
     * 根据昵称查询用户
     *
     * @param username
     * @param nickName
     * @return
     */
    User findByNickName(String username, String nickName);

    /**
     * 修改用户
     *
     */
    void UpdateUser(User user);

    /**
     * 用户密码重置
     * @param user
     */
    Result resetPassword(User user);

    /**
     * 用户登录状态密码重置
     * @param user
     */
    void resetPasswordLogin(User user);

    User findByName(String username);
}
