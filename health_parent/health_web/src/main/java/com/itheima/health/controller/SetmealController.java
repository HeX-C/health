package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.Utils.QiNiuUtils;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author : qiangshengchen
 * @date : 上午 11:51 19/9/2020
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

//    注入redis客户端
    @Autowired
    private JedisPool jedisPool;


    /*
    * 套餐上传图片
    * */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
//        获取图片原始名称并截取后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));

//        生成唯一文件名
        String filename = UUID.randomUUID() + substring;

//        使用七牛云上传文件
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),filename);
            //- 返回数据给页面
            //{
            //    flag:
            //    message:
            //    data:{
            //        imgName: 图片名,
            //        domain: QiNiuUtils.DOMAIN
            //    }
            //}
            Map<String,String> map = new HashMap<>();
            map.put("imgName",filename);
            map.put("domain",QiNiuUtils.DOMAIN);

//            将图片名称存入Redis中，(  jedisPool.getResource()从池中获取redis)
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
            jedisPool.getResource().close();

            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            e.printStackTrace();
        }
            return new Result(true,MessageConstant.PIC_UPLOAD_FAIL);
    }

    /*
    * 添加套餐
    * */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setmealService.add(setmeal,checkgroupIds);
//        添加成功，将有用的图片存储至redis中
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        jedisPool.getResource().close();

        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /*
    *分页展示所有套餐
    * */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult<Setmeal> pageResult =  setmealService.findPage(queryPageBean);
       return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }

    /*
    * 编辑套餐回显套餐数据
    * */
    @GetMapping("/findById")
    public Result findById(Integer setmealId){
//        查询套餐数据 + 图片的完整路径
      Setmeal setmeal = setmealService.findById(setmealId);

      Map<String,Object> map = new HashMap<>();
      map.put("setmeal",setmeal);
      map.put("domain",QiNiuUtils.DOMAIN);
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,map);
    }

    /*
    * 编辑套餐时根据id获取检查组
    * */
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(Integer setmealId){
      List<Integer> CheckgroupIds = setmealService.findCheckgroupIdsBySetmealId(setmealId);
      return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,CheckgroupIds);
    }

    /*
    * 编辑套餐更新数据
    * */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
//      获取原有图片的名称，判断图片是否更改了，如果更改了，那么旧的图片应该从有用的集合中移除
        Setmeal setmealInDb = setmealService.findById(setmeal.getId());

//        更新套餐
        setmealService.update(setmeal,checkgroupIds);

//         判断是否是需要从有用的集合中删除
        if (!setmealInDb.getImg().equals(setmeal.getImg())) {

//         图片修改了，旧的就没用，就要删除
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmealInDb.getImg());
        }

//        存储新图片
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        jedisPool.getResource().close();

        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS);
    }

    /*
    * 删除套餐
    * */
    @PostMapping("/deleteById")
    public Result deleteById(Integer id){

//        获取图片名称
        Setmeal setmeal = setmealService.findById(id);

//        删除套餐
        setmealService.deleteById(id);

//        从redis中删除图片
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        jedisPool.getResource().close();

        return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
