package api;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        // parse the file
        Reader reader = null;
        try{
            reader = new BufferedReader(new FileReader(filename));

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

    public static void main(String[] args){

        ArrayList<Crime> crimes = accessAPI();

        Collections.sort(crimes, new DistanceComparator(43.000558, -78.783778));
        for(Crime crime : crimes){
            System.out.println(crime);
        }
    }

}
