package com.itheima.health.dao;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : qiangshengchen
 * @date : 下午 3:43 21/9/2020
 */
public interface OrderSettingDao {
    /**
     * 批量上传预约文件
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 通过日期来判断是否存在该预约
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 同一天则更新以预约人数
     * @param orderSetting
     */
    void update(OrderSetting orderSetting);

    /**
     * 查询当前月份的预约设置
     * @param map
     * @return
     */
    List<Map<String,Integer>> getOrderSettingByMonth(Map<String,String> map);

}
