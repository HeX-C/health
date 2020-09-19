package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : qiangshengchen
 * @date : 下午 3:20 18/9/2020
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

//    引入服务
    @Reference
    private CheckGroupService checkGroupService;

    /*
    * 展示检查组包含的所有检查项
    * */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list =  checkGroupService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /*
    * 新增检查项
    * */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /*
    * 分页查询检查组
    * */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
      PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
      return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /*
    * 编辑检查组时根据id回显数据
    * */
    @GetMapping("/findById")
    public Result findById(Integer checkGroupId){
      CheckGroup checkGroup = checkGroupService.findById(checkGroupId);
      return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /*
    * 编辑检查组时根据id获取检查项
    * */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer checkGroupId){
      List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }

    /*
    * 编辑检查组时更新数据
    * */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /*
    * 删除检查组
    * */
    @PostMapping("/deleteById")
    public Result delete(Integer checkGroupId){
        checkGroupService.deleteById(checkGroupId);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /*
    * 查询所有检查组
    * */
    @GetMapping("/findAllCheckgroup")
    public Result findAllCheckgroup(){
      List<CheckGroup> list =   checkGroupService.findAllCheckgroup();
      return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }


}
