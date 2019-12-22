package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;
@Component
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    @Scheduled(cron = "* * 1/10 * * ?")
    private void  ClearImg(){
        // 根据缓存set集合差值进行计算
        Set<String> set =
                jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                        RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        if(set!=null) {
            for (String s : set) {
                //删除七牛云上面的图片
                QiniuUtils.deleteFileFromQiniu(s);
                // 从缓存集合中删除图片
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
            }
        }

    }

}
