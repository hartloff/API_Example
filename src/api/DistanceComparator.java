package api;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Crime>{

    private double latitude;
    private double longitude;

    public DistanceComparator(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private double distance(double latitude, double longitude){
        return Math.sqrt(Math.pow(latitude - this.latitude, 2.0) +
                Math.pow(longitude - this.longitude, 2.0));
    }

    @Override
    public int compare(Crime o1, Crime o2){
        double distance1 = distance(o1.getLatitude(), o1.getLongitude());
        double distance2 = distance(o2.getLatitude(), o2.getLongitude());

        // return >0 if o1 come after o2
        // return <0 if o1 comes before o2
        // return 0 if their order doesn't matter

        if(distance1 > distance2){
            return 1;
        } else if(distance1 < distance2){
            return -1;
        }else{
            return 0;
        }

    }


}
