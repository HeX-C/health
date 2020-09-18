package com.itheima.health.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 下午 7:58 14/9/2020
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

//    引入检查项接口
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /*
    * 添加检查项
    * */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /*
    * 分页查询检查项
    * */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {

//        分页插件提供的方法
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

//        判断查询条件是否为空
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

//        根据条件查询
        Page<CheckItem> page =  checkItemDao.findByCondition(queryPageBean.getQueryString());

//        封装结果
        PageResult<CheckItem> pageResult = new PageResult<>(page.getTotal(), page.getResult());

        return pageResult;
    }

    /*
    * 删除检查项
    * */
    @Override
    public void deleteById(Integer id) throws HealthException {
        /*
        * 判断检查项是否被检查组使用，使用了则不能删除
        * */
//        根据checkid去t_chenckitem_checkgroup查记录
        int cnt = checkItemDao.findCountByCheckItemId(id);

        if (cnt > 0){
//            大于0表示存在记录，则不能删除
            throw new HealthException(MessageConstant.DELETE_CHECKITEM_FAIL);
        }
//       不大于0则可以删除
        checkItemDao.deleteById(id);
    }

    /*
    * 根据id回显检查项
    * */
    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    /*
    * 根据id编辑检查项
    * */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

}
