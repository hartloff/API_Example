package api;

import java.util.Comparator;

public class DateCompare implements Comparator<Crime>{

    @Override
    public int compare(Crime o1, Crime o2){

        return o1.getDateTime().compareTo(o2.getDateTime());
    }

}
