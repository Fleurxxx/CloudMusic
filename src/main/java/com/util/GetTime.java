package src.main.java.com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {
    public String getTime(){
        Date date = new Date();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss ");
        String time=formatter.format(date);
        System.out.println(time);
        return time;
    }


}
