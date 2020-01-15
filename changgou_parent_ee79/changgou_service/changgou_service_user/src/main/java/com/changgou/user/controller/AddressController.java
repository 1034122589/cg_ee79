package com.changgou.user.controller;

import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private TokenDecode tokenDecode;
    /****
     * 用户收件地址
     */
    @GetMapping(value = "/user/list")
    public Result<List<Address>> list(){
        //获取用户登录信息
        Map<String, String> userMap = TokenDecode.getUserInfo();
        String username = userMap.get("username");
        //查询用户收件地址
        List<Address> addressList = addressService.list(username);
        return new Result(true, StatusCode.OK,"查询成功！",addressList);
    }

    /**
     * 添加收件地址
     * @param address
     * @return
     */
    @PostMapping
    public  Result add(@RequestBody Address address){
        //获取用户登录信息
        Map<String, String> userMap = TokenDecode.getUserInfo();
        String username = userMap.get("username");
        address.setUsername(username);
        addressService.add(address);
        return new Result(true, StatusCode.OK,"添加成功！");

    }

    /**
     * 查询收件地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public  Result findAdderssById(@PathVariable Integer id) {
        Address adderss = addressService.findAdderssById(id);
        return new Result(true, StatusCode.OK,"查询成功！",adderss);
    }

    /**
     * 删除收件地址
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
   public  Result deleteAddressById(@PathVariable Integer id){
                addressService.deleteAddressById(id);
        return new Result(true, StatusCode.OK,"删除成功！");
   }
}
