package api;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;

public class API{


    public static ArrayList<Crime> accessAPI(){
        ArrayList<Crime> crimes = new ArrayList<>();

        // access the API
        String response = HTTP.getRequest("https://data.buffalony.gov/resource/d6g9-xbgu.csv");

        // Write the API data to a file
        String filename = "data.csv";
        try{
            Files.write(Paths.get(filename), response.getBytes());
        }catch(IOException e){
            e.printStackTrace();
        }

        // parse the file with the CSV parsing library
        Reader reader = null;
        try{
            reader = new BufferedReader(new FileReader(filename));
            // skip the header line
            reader.skip(1);
        for(CSVRecord record : CSVFormat.DEFAULT.parse(reader)){
            String dateTime = record.get(15);
            String type = record.get(18);
            String latitude = record.get(19);
            String longitude = record.get(21);

            try{
                Double.valueOf(latitude);
                Double.valueOf(longitude);
            }catch(NumberFormatException ex){
//                System.out.println(record);
                continue;
            }

            api.Crime crime = new api.Crime(type, dateTime, Double.valueOf(latitude), Double.valueOf(longitude));
            crimes.add(crime);
//            System.out.println(crime);
        }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return crimes;
    }


    /**
     * Truncate a double to only display 2 decimal places
     */
    private static Object truncateDouble(double input){
        Format format = new DecimalFormat("#.##");
        return format.format(input);
    }


    public static void main(String[] args){

        ArrayList<Crime> crimes = accessAPI();

        // Use to sort by time
//        Collections.sort(crimes, new DateCompare());

        // sort by distance from UB
        double latitudeUB = 43.000558;
        double longitudeUB = -78.783778;

        DistanceComparator distanceToUB = new DistanceComparator(latitudeUB, longitudeUB);
        Collections.sort(crimes, distanceToUB);

        for(Crime crime : crimes){
            double distance = distanceToUB.distance(crime.getLatitude(), crime.getLongitude());
            System.out.println(truncateDouble(distance) + " km | " + crime);
        }

    }

}
