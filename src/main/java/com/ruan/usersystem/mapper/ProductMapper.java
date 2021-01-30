package com.ruan.usersystem.mapper;

import com.ruan.usersystem.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ProductMapper {
    List<Product> getProductList(@Param("pageSize") int pageSize, @Param("pageNum") int pageNum);
    Integer getTotal();
}
