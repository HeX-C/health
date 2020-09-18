package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 下午 8:03 14/9/2020
 */
public interface CheckItemDao {
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
    Page<CheckItem> findByCondition(String query);
    /*
    * 根据id删除检查项
    * */
    void deleteById(Integer id);
    /*
    * 根据检查项id查询检查项是否存在于检查组
    * */
    int findCountByCheckItemId(Integer id);

    /*
    * 根据id回显检查项数据
    * */
    CheckItem findById(Integer id);

    /*
    * 根据id编辑检查项数据
    * */
    void update(CheckItem checkItem);
}
