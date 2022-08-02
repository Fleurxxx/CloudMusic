package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
    public String getTime(){
        Date date = new Date();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss ");
        String time=formatter.format(date);
        System.out.println(time);
        return time;
    }

    public static String getAgeByBirth(String date) throws ParseException {
        //如果有空格
        int index = date.indexOf(" ");
        if (index != -1){
            date = date.substring(0 , index);
        }

        String[] data = date.split("-");

        Calendar birthday =new GregorianCalendar(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]));

        Calendar now = Calendar.getInstance();

        int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        //月份从0开始计算，所以需要+1
        int month = now.get(Calendar.MONTH) +1 - birthday.get(Calendar.MONTH);

        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);

        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。

        if (day <0) {

            month -=1;

            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。

            day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);

        }

        if (month <0) {

            month = (month +12) %12;

            year--;

        }

        //System.out.println("年龄：" + year +"岁" + month +"月" + day +"天");

        StringBuffer tag =new StringBuffer();

        if (year >0) {

            tag.append(year +"岁");

        }

        if (month >0) {

            tag.append(month +"个月");

        }

        if (day >0) {

            tag.append(day +"天");

        }

        if (year ==0 && month ==0 && day ==0) {

            tag.append("今日出生");

        }

        return String.valueOf(tag);
    }

}

