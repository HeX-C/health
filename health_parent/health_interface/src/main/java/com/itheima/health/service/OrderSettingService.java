package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : qiangshengchen
 * @date : 下午 3:13 21/9/2020
 */
public interface OrderSettingService {
    /**
     * 批量上传预约文件
     * @param orderSettings
     */
    void add(ArrayList<OrderSetting> orderSettings) throws HealthException;

    /**
     * 通过月份来获取预约设置信息
     * @param date
     * @return
     */
    List<Map<String,Integer>> getOrderSettingByMonth(String date);

    /**
     * 编辑预约信息
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
