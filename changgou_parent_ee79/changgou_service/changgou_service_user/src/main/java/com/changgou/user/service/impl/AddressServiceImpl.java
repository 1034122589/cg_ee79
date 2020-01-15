package com.changgou.user.service.impl;

import com.changgou.user.dao.AddressMapper;

import com.changgou.user.dao.AreasMapper;
import com.changgou.user.dao.CitiesMapper;
import com.changgou.user.dao.ProvincesMapper;
import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ProvincesMapper proviceMapper;
    @Autowired
    private CitiesMapper cityMapper;
    @Autowired
    private AreasMapper areaMapper;


    /**
     * 收件地址查询
     *
     * @param username
     * @return
     */
    @Override
    public List<Address> list(String username) {
        Address address = new Address();
        address.setUsername(username);
        return addressMapper.select(address);
    }

    /**
     * 添加收件地址
     */
    @Override
    public void add(Address address) {
        String is_default = address.getIsDefault();
        Address addre = new Address();
        addre.setUsername(address.getUsername());
        addre.setIsDefault("1");
        if (address.getId() == null) {
            if (is_default.equals("1")) {
                Address addr = addressMapper.selectOne(addre);
                if (addr == null) {
                    addressMapper.insertSelective(address);
                } else {
                    addr.setIsDefault("0");
                    addressMapper.updateByPrimaryKeySelective(addr);
                    addressMapper.insertSelective(address);
                }
            } else {
                addressMapper.insertSelective(address);
            }
        } else {
            if (is_default.equals("1")) {
                Address addr = addressMapper.selectOne(addre);
                if (addr == null) {
                    addressMapper.updateByPrimaryKeySelective(address);
                } else {
                    addr.setIsDefault("0");
                    addressMapper.updateByPrimaryKeySelective(addr);
                    addressMapper.updateByPrimaryKeySelective(address);
                }
            } else {
                addressMapper.updateByPrimaryKeySelective(address);
            }

        }

    }

    /**
     * 查询收件地址
     *
     * @param id
     * @return
     */
    @Override
    public Address findAdderssById(Integer id) {
        Address address = new Address();
        address.setId(id);
        Address address1 = addressMapper.selectByPrimaryKey(address);
        return address1;


    }
    /**
     * 删除收件地址
     * @param id
     */
    @Override
    public void deleteAddressById(Integer id) {
        Address address = new Address();
        address.setId(id);
        addressMapper.deleteByPrimaryKey(address);
    }
}
