package com.itheima.health.service;


import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 下午 3:32 18/9/2020
 */
public interface CheckGroupService {
    /*
    * 展示检查组包含的所有检查项
    * */
    List<CheckItem> findAll();
    /*
    * 新增检查组
    * */
    void add(CheckGroup checkGroup,Integer[] checkitemIds);
    /*
    * 分页查询检查组
    * */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);
    /*
    * 编辑检查组时根据id回显数据
    * */
    CheckGroup findById(Integer checkGroupId);
    /*
    * 编辑检查组时根据id获取检查项
    * */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);
    /*
    * 编辑检查组时更新数据
    * */
    void update(CheckGroup checkGroup,Integer[] checkitemIds);
    /*
    * 删除检查组
    * */
    void deleteById(Integer id);
    /*
    *展示套餐包含的所有检查组
    * */
    List<CheckGroup> findAllCheckgroup();
}
