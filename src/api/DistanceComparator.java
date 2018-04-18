package api;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Crime>{

    private double referenceLatitude;
    private double referenceLongitude;

    public DistanceComparator(double latitude, double longitude){
        this.referenceLatitude = latitude;
        this.referenceLongitude = longitude;
    }


    /**
     * Returns the distance from the input location from the reference
     * location (stored in the instance variables) in kilometers
     *
     * @param inputLatitude  latitude of the input location
     * @param inputLongitude longitude of the input location
     * @return distance from reference location in kilometers
     */
    public double distance(double inputLatitude, double inputLongitude){
        // Updated to return Great-Circle Distance
        // ref: https://www.movable-type.co.uk/scripts/latlong.html

        double radiusOfEarth = 6371000.0;

        double deltaLatitude = Math.toRadians(inputLatitude - this.referenceLatitude);
        double deltaLongitude = Math.toRadians(inputLongitude - this.referenceLongitude);

        double a = Math.pow(Math.sin(deltaLatitude / 2.0), 2.0) +
                Math.cos(Math.toRadians(this.referenceLatitude)) * Math.cos(Math.toRadians(inputLatitude)) *
                        Math.pow(Math.sin(deltaLongitude / 2.0), 2.0);

        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a));

        double d = radiusOfEarth * c;

        return d/1000.0;
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
