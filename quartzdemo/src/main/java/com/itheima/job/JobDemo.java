package com.itheima.job;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : qiangshengchen
 * @date : 上午 9:47 21/9/2020
 */

public class JobDemo {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   public void Job(){
            System.out.println(simpleDateFormat.format(new Date()));
        }
    }

