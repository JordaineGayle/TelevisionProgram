package helpers;

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
        if(hour < 12){
            if(hour == 0){
                return 12+":"+mins+" AM";
            }else{
                return hour+":"+mins+" AM";
            }
        }else{
            if(hour != 12){
                return (12-(24-hour))+":"+mins+" PM";
            }else{
                return hour+":"+mins+" PM";
            }
        }
    }
}
