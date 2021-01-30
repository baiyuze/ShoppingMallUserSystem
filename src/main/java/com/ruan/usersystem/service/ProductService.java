package com.ruan.usersystem.service;

import com.ruan.usersystem.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductService {
    List<Product> getProductList(int pageSize, int pageNum);
    Integer getTotal();
}
