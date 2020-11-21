package helpers;

import java.time.LocalDateTime;
import java.util.HashMap;

public class TimeHelper {
    private HashMap<Integer, String> clock24Hr = new HashMap<>();

    public TimeHelper(){
        for(int x = 0; x < 24; x++){
            clock24Hr.put(x,formatTo12Hour(x,"00"));
        }
    }

    public HashMap<Integer,String> get24HrClock(){
        return this.clock24Hr;
    }

    public static String formatTo12Hour(int hour, String mins){

        if(hour > 23){
            hour = hour - 24;
        }

        if(hour < 12){
            if(hour == 0){
                return 12+":"+mins+" AM";
            }else{
                if (hour < 10){
                    return "0"+hour+":"+mins+" AM";
                }
                return hour+":"+mins+" AM";
            }
        }else{
            if(hour != 12){
                if (hour < 22){
                    return "0"+(12-(24-hour))+":"+mins+" PM";
                }
                return (12-(24-hour))+":"+mins+" PM";
            }else{

                return hour+":"+mins+" PM";
            }
        }
    }

    public static boolean isDateEqualToNow(LocalDateTime localDateTime){
        return LocalDateTime.now().toLocalDate().isEqual(localDateTime.toLocalDate());
    }
}
