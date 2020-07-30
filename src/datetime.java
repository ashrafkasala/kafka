import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.lang3.time.DateUtils;

public class datetime {
    public static void main(String ...args){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("UTC Time is: " + dateFormat.format(date));
        int addMinuteTime = 5;

        Date targetTime = DateUtils.addMinutes(date, addMinuteTime); //add minute
        System.out.println("UTC Time afetr adding 5 mins is: " + dateFormat.format(targetTime));

        Calendar calendar = Calendar.getInstance();
        System.out.println("Original = " + calendar.getTime());

        // Substract 2 hour from the current time
        calendar.add(Calendar.HOUR, -2);

        // Add 30 minutes to the calendar time
        calendar.add(Calendar.MINUTE, 30);

        // Add 300 seconds to the calendar time
        calendar.add(Calendar.SECOND, 300);
        System.out.println("Updated  = " + calendar.getTime());

        System.out.println("date get time "+date.getTime());
        Timestamp ts=new Timestamp(date.getTime());
        System.out.println("time stamp "+ts.toString());

//        int addMinuteTime = 5;
//
//        Date targetTime = DateUtils.addMinutes(date, addMinuteTime); //add minute
//        System.out.println("UTC Time afetr adding 5 mins is: " + dateFormat.format(targetTime));
    }
}
