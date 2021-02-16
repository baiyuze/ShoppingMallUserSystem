package com.ruan.usersystem.controller;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruan.usersystem.config.LoginRequired;
import com.ruan.usersystem.entity.Product;
import com.ruan.usersystem.entity.Response;
import com.ruan.usersystem.service.ProductService;
import com.ruan.usersystem.utils.Util;
import com.ruan.usersystem.utils.uploadFile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class ProductInfo {
    Util utilFunc = new Util();
    Response res = new Response();
    private static Logger logger = LogManager.getLogger(UserInfo.class.getName());
    @Autowired
    ProductService productService;

    uploadFile upload = new uploadFile();

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

    @LoginRequired
    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public Response inSetProduct(@RequestBody Product product) {
        Date date = new Date();
        Long t = date.getTime() / 1000;
        String proV = "pro_" + t;
        product.setPro_no(proV);
        product.setAdd_time(t);
        Integer productCount = productService.insertData(product);
        if(productCount >= 1) {
            res.setCode(200);
            res.setMsg("保存成功");
        } else {
            res.setCode(500);
            res.setMsg("保存失败");
        }
        return res;
    }

//    @LoginRequired
    @RequestMapping(value = "/product/img/upload", method = RequestMethod.POST)
    public Response uploadImgReturnUrl(@RequestParam("file") MultipartFile file) throws Exception {
        String url = upload.start(file.getOriginalFilename(), file);
        System.out.println(url);
        Map<String,String> data = new HashMap();
        data.put("url",url);
        res.setMsg("上传成功");
        res.setCode(200);
        res.setResult(data);
        return res;
    }

}
