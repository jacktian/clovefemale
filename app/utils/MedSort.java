package utils;

import models.Medicine;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by boxizen on 15/8/22.
 */
public class MedSort implements Comparator{
    public int compare(Object arg0,Object arg1){
        Medicine med1 = (Medicine)arg0;
        Medicine med2 = (Medicine)arg1;
        Date date1 = med1.deadline;
        Date date2 = med2.deadline;
        int flag = date1.compareTo(date2);
        return flag;
    }
}
