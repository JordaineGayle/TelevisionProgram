package helpers;

import interfaces.IProgram;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static String convertToHumanReadableFormat(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMMM dd, yyyy hh:mm a");
        String converted = localDateTime.format(formatter);
        return converted;
    }

    public static boolean isDateEqualToNow(LocalDateTime localDateTime){
        return LocalDateTime.now().toLocalDate().isEqual(localDateTime.toLocalDate());
    }

    public static LocalDateTime correctProgramDate(IProgram prog){
        LocalDateTime dt = prog.getProgramAirDateTime().plusDays((long)(prog.getDuration())).plusHours((long)(prog.getLength()));
        //System.out.println(dt.toString());
        return dt;
    }


    public static LocalDateTime correctProgramDateLength(IProgram prog){
        LocalDateTime dt = prog.getProgramAirDateTime().plusHours((long)(prog.getLength()));
        //System.out.println(dt.toString());
        return dt;
    }
}
