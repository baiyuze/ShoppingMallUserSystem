package com.ruan.usersystem.service.impl;

import com.ruan.usersystem.entity.Product;
import com.ruan.usersystem.entity.User;
import com.ruan.usersystem.mapper.ProductMapper;
import com.ruan.usersystem.service.ProductService;
import com.ruan.usersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    public List<Product> getProductList(int pageSize, int pageNum) {
        List <Product> productList = productMapper.getProductList(pageSize, pageNum);
        return productList;
    }

    public Integer getTotal(){
        Integer total = productMapper.getTotal();
        return total;
    }
}
