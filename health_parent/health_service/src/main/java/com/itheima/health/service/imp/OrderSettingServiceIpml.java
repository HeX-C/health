package com.itheima.health.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : qiangshengchen
 * @date : 下午 3:41 21/9/2020
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceIpml implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    /**
     * 批量上传预约文件
     * @param orderSettings
     */
    @Override
    @Transactional
    public void add(ArrayList<OrderSetting> orderSettings) {

        for (OrderSetting orderSetting : orderSettings) {
//            通知日期来判断是否存在该预约
          OrderSetting os =  orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
       if (null != os){
//           以存在则判断以预约人数是否大于最大预约数
           if (os.getReservations() > orderSetting.getNumber()){
               throw new HealthException(orderSetting.getOrderDate() + " 中已预约数量不能大于可预约数量");
           }else{
//               同一天则更新预约人数
               orderSettingDao.update(orderSetting);
           }

       }else {
//           不存在则添加该预约日期
           orderSettingDao.add(orderSetting);
              }
        }
    }

    /**
     * 通过月份来获取预约设置信息
     * @param month
     * @return
     */
    @Override
    public List<Map<String,Integer>> getOrderSettingByMonth(String month) {

//         1.组织查询Map，dateBegin表示月份开始时间，dateEnd月份结束时间
        String dateBegin = month + "-01";
        String dateEnd = month + "-31";
        Map<String,String> map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);

//        2.查询当前月份的预约设置
       List<Map<String,Integer>> list = orderSettingDao.getOrderSettingByMonth(map);

        return list;
        /**
         * 方法二：（sql语句为 select * from t_ordersetting where from t_ordersetting where orderDate between #{dateBegin} and #{dateEnd}）
         */
//        ArrayList<Map<String,Integer>> data = new ArrayList<>();
//
////         3.将List<OrderSetting>，组织成List<Map>
//        for (OrderSetting orderSetting : list) {
//
//            HashMap orderSettingMap = new HashMap();
//
////            orderSetting.getOrderDate().getDate() == 获得日期（几号）
//            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
//
//            orderSettingMap.put("number",orderSetting.getNumber());
//
//            orderSettingMap.put("reservations",orderSetting.getReservations());
//
//            data.add(orderSettingMap);
//        }


    }

    /**
     * 编辑预约信息
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
//        根据日期查询出预约信息
      OrderSetting orderSetting1 =  orderSettingDao.findByOrderDate(orderSetting.getOrderDate());

//    判断
        if (orderSetting1 != null){
            if (orderSetting.getNumber() < orderSetting1.getReservations()) {
                throw new HealthException("最大预约人数不能小已预约人数！");
            }

            orderSettingDao.update(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }

    }
}
