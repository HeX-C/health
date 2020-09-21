package com.itheima.health.dao;


import com.github.pagehelper.Page;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 下午 3:34 18/9/2020
 */
public interface CheckGroupDao {
    /*
    * 展示检查组包含的所有检查项
    * */
    List<CheckItem> findAll();
    /*
    * 新增检查组
    * */
    void add(CheckGroup checkGroup);
    /*
    * 新增检查组（添加检查组与检查项的关系）
    * */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemId") Integer checkitemId);
    /*
    * 根据条件分页查询检查组
    * */
    Page<CheckGroup> findByCondition(String queryString);
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
    void update(CheckGroup checkGroup);
    /*
    * 删除旧检查组（编辑检查组与检查项的关系）
    * */
    void deleteCheckGroupCheckItem(Integer checkGroupId);
    /*
    * 删除检查组
    * */
    void deleteById(Integer checkGroupId) throws HealthException;
    /*
    * 判断检查组是否在套餐内存在
    * */
    int findSetmealCountByCheckGroup(Integer checkGroupId);
    /*
    * 展示套餐包含的所有检查组
    * */
    List<CheckGroup> findAllCheckgroup();

}
