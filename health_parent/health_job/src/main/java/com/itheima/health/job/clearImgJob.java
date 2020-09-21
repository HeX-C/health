package com.itheima.health.job;

import com.itheima.health.Utils.QiNiuUtils;
import com.itheima.health.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author : qiangshengchen
 * @date : 上午 11:29 21/9/2020
 */
@Component
public class clearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
//        计算出需要删除的旧照片(==SDIFF key1 [key2] 返回给定所有集合的差集)
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

//        删除七牛云上的照片(set转list)
        QiNiuUtils.removeFiles(set.toArray(new String[]{}));

//        删除redis上的图片(del == 删除key)
        jedisPool.getResource().del(RedisConstant.SETMEAL_PIC_DB_RESOURCES,RedisConstant.SETMEAL_PIC_RESOURCES);

    }
}
