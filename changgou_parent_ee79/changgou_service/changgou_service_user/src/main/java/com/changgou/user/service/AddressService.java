package com.changgou.user.service;



import com.changgou.user.pojo.Address;

import java.util.List;

public interface AddressService {
    /***
     * 收件地址查询
     * @param username
     * @return
     */
    List<Address> list(String username);

    /**
     * 添加收件地址
     */
    void add(Address address);

    /**
     * 查询收件地址
     * @param id
     *
     * @return
     */
    Address findAdderssById(Integer id);

    /**
     * 删除收件地址
     * @param id
     */
    void deleteAddressById(Integer id);
}
