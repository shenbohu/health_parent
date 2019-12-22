package com;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobDemo {
    //定时要执行的业务操作
    public void run(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()));
    }
}
