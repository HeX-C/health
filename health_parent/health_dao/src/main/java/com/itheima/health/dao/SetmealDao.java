package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 上午 11:52 19/9/2020
 */
public interface SetmealDao {

    /*
    * 添加套餐
    * */
    void add(Setmeal setmeal);
    /*
    * 添加套餐和检查组的关系
    * */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkGroupId") Integer checkGroupId);
    /*
    * 添加查询套餐
    * */
    Page<Setmeal> findByCondition(String queryString);
    /*
    * 编辑套餐回显套餐数据
    * */
    Setmeal findById(Integer setmealId);
    /*
    * 编辑套餐时根据id获取检查组
    * */
    List<Integer> findCheckgroupIdsBySetmealId(Integer setmealId);
    /*
    * 删除套餐与检查组的旧关系
    * */
    void deleteSetmealCheckGroup(Integer setmealId);
    /*
    *   更新套餐数据
    * */
    void update(Setmeal setmeal);
    /*
    * 删除套餐
    * */
    void deleteById(Integer id) throws HealthException;
    /*
    * 判断使用有订单在使用此套餐
    * */
    int findOrderCountBySetmealId(Integer id);
}
