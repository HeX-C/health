package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 上午 11:53 19/9/2020
 */
public interface SetmealService {

    /*
    * 添加套餐
    * */
    void add(Setmeal setmeal, Integer[] checkgroupIds);
    /*
    * 分页展示所有套餐
    * */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);
    /*
    * 编辑套餐回显套餐数据
    * */
    Setmeal findById(Integer setmealId);
    /*
    * 编辑套餐时根据id获取检查组
    * */
    List<Integer> findCheckgroupIdsBySetmealId(Integer setmealId);
    /*
    * 编辑套餐时更新数据
    * */
    void update(Setmeal setmeal, Integer[] checkgroupIds);
    /*
    * 删除套餐
    * */
    void deleteById(Integer id);
}
