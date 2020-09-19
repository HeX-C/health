package com.itheima.health.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.itheima.health.exception.HealthException;
import org.springframework.util.StringUtils;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 上午 11:53 19/9/2020
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /*
    * 添加套餐
    * */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
//        添加套餐
        setmealDao.add(setmeal);

//        获取套餐id
        Integer setmealId = setmeal.getId();

//        变量检查组id
        if (null != checkgroupIds){
            for (Integer checkGroupId : checkgroupIds) {
//                添加套餐和检查组的关系
                setmealDao.addSetmealCheckGroup(setmealId,checkGroupId);
            }
        }
    }

    /*
    * 分页展示所有套餐
    * */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
//      调用分页插件的方法
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

//        判断查询条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
//         模糊查询拼接
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        PageResult<Setmeal> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    /*
    * 编辑套餐回显套餐数据
    * */
    @Override
    public Setmeal findById(Integer setmealId) {
        return setmealDao.findById(setmealId);
    }

    /*
    * 编辑套餐时根据id获取检查组
    * */
    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(Integer setmealId) {
        return setmealDao.findCheckgroupIdsBySetmealId(setmealId);
    }

    /*
    * 编辑套餐时更新数据
    * */
    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
//        更新套餐数据
        setmealDao.update(setmeal);

//        获取套餐id
        Integer setmealId = setmeal.getId();

        if (null!=checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
//        删除套餐与检查组的旧关系
                setmealDao.deleteSetmealCheckGroup(setmealId);
//        添加套餐和检查组的新关系
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }

    }

    /*
    * 删除套餐
    * */
    @Override
    public void deleteById(Integer id) throws HealthException {
//        先判断套餐是否存在订单
      int cnt =  setmealDao.findOrderCountBySetmealId(id);
      if (cnt > 0){
          throw  new HealthException("已经有订单使用了这个套餐，不能删除！");
      }
//      先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        setmealDao.deleteById(id);
    }
}
