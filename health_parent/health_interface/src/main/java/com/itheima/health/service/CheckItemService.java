package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 下午 8:26 14/9/2020
 */
public interface CheckItemService {
     /*
     * 展示所有检查项
     * */
     List<CheckItem> findAll();
     /*
     * 添加检查项
     * */
     void add(CheckItem checkItem);
     /*
     * 分页查询检查项
     * */
     PageResult<CheckItem> findPage(QueryPageBean queryPageBean);
     /*
     * 根据id删除检查项
     * */
     void deleteById(Integer id) throws HealthException;
     /*
     * 根据id回显检查项
     * */
     CheckItem findById(Integer id);
     /*
     * 根据id编辑检查项
     * */
     void update(CheckItem checkItem);
}
