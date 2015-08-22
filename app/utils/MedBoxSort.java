package utils;

import models.Medicine;
import models.MedicineBox;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by boxizen on 15/8/22.
 */
public class MedBoxSort implements Comparator {
    public int compare(Object arg0,Object arg1){
        MedicineBox med1 = (MedicineBox)arg0;
        MedicineBox med2 = (MedicineBox)arg1;
        Integer w1 = med1.weight;
        Integer w2 = med2.weight;
        int flag = w1.compareTo(w2);
        return flag;
    }
}
