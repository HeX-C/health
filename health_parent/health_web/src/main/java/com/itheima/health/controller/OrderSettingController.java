package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.Utils.POIUtils;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;

/**
 * @author : qiangshengchen
 * @date : 下午 3:11 21/9/2020
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量上传预约预约文件
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {

        try {
//        读取excel内容
            List<String[]> strings = POIUtils.readExcel(excelFile);

            /*
            * 转成List<OrderSettting> OrderSettting
            * */
            ArrayList<OrderSetting> orderSettings = new ArrayList<>();

//        解析日期格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(POIUtils.DATE_FORMAT);

            Date orderDate = null;
            OrderSetting orderSetting = null;

            for (String[] string : strings) {
                orderDate = simpleDateFormat.parse(string[0]);
                int number = Integer.valueOf(string[1]);

    //            为每个ordersetting对象赋值
                orderSetting = new OrderSetting(orderDate,number);
    //            将对象添加至list集合中
                orderSettings.add(orderSetting);

            }

//      调用服务
            orderSettingService.add(orderSettings);

            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
              return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 通过月份来获取预约设置信息
     * @param month
     * @return
     */
    @GetMapping ("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){

      List<Map<String,Integer>> list =   orderSettingService.getOrderSettingByMonth(month);

      return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
    }

    /**
     * 编辑预约信息
     * @param orderSetting
     * @return
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS);
    }
}
