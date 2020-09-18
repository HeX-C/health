package com.itheima.health.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 下午 3:34 18/9/2020
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

//    引入检查组dao
    @Autowired
    private CheckGroupDao checkGroupDao;

    /*
     * 查询检查组包含的所以检查项
     * */
    @Override
    public List<CheckItem> findAll() {
        return checkGroupDao.findAll();
    }

    /*
    * 新增检查组
    * */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup,Integer[] checkitemIds) {
//        新增检查组
        checkGroupDao.add(checkGroup);

//        获取检查组的id
        Integer checkgroupId = checkGroup.getId();

//         遍历检查项id, 添加检查组与检查项的关系
        if (null !=checkitemIds ){
            for (Integer checkitemId : checkitemIds) {

//                添加检查组和检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkgroupId,checkitemId);
            }
        }
    }

    /*
     * 分页查询检查组
     * */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
//        分页插件提供的方法
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

//        判断查询条件是否为空
       if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
           queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
       }

//        根据条件查询
        Page<CheckGroup> page =  checkGroupDao.findByCondition(queryPageBean.getQueryString());

//        封装结果
        PageResult<CheckGroup> pageResult = new PageResult<>(page.getTotal(), page.getResult());

        return pageResult;
    }

    /*
    * 编辑检查组时根据id回显数据
    * */
    @Override
    public CheckGroup findById(Integer checkGroupId) {
        return checkGroupDao.findById(checkGroupId);
    }

    /*
     * 编辑检查组时根据id获取检查项
     * */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    /*
    * 编辑检查组时根据id获取检查项
    * */
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
//        编辑检查组
        checkGroupDao.update(checkGroup);


//        获取检查组的id
        Integer checkGroupId = checkGroup.getId();

//        先删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroupId);
//         遍历检查项id, 添加检查组与检查项的关系
        if (null !=checkitemIds ){
            for (Integer checkitemId : checkitemIds) {

//                编辑检查组和检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }
    }




}
