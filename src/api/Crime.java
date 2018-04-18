package api;

public class Crime{

    private String type;
    private String dateTime;
    private double latitude;
    private double longitude;

    public Crime(String type, String dateTime, double latitude, double longitude){
        this.type = type;
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getDateTime(){
        return dateTime;
    }

    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return type + " on " + dateTime + " @ (" + latitude + ", " + longitude + ") " +
                "http://maps.google.com/maps?t=m&q=loc:" + latitude + "+" + longitude;
    }
}
