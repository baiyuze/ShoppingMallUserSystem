package com.ruan.usersystem.service;

import com.ruan.usersystem.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getProductList(int pageSize, int pageNum);
    Integer getTotal();
    Integer insertData(Product productMap);
}
