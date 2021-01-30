package com.ruan.usersystem.controller;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruan.usersystem.config.LoginRequired;
import com.ruan.usersystem.entity.Product;
import com.ruan.usersystem.entity.Response;
import com.ruan.usersystem.service.ProductService;
import com.ruan.usersystem.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProductInfo {
    Util utilFunc = new Util();
    Response res = new Response();
    @Autowired
    ProductService productService;

    @LoginRequired
    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public Response getProductList(HttpServletRequest request, int pageSize, int pageNum) {
        Object userInfo = request.getAttribute("userInfo");
        pageSize = utilFunc.AssignDefaultValue(pageSize, 10);
        pageNum = utilFunc.AssignDefaultValue(pageNum, 1);
        Integer total = productService.getTotal();
        List<Product> productList = productService.getProductList(pageSize, pageNum);
        res.setList(200,(List) productList,"获取成功", pageNum, pageSize,total);
        return res;

    }
}
