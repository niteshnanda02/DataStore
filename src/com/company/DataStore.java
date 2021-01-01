package com.company;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DataStore implements CRDInterace {
    //use it to parse or deserialize JSON content into a Java object.
    private ObjectMapper objectMapper;
    //LocalDateTime class is an immutable date-time object that represents a date-time, with the default format as yyyy-MM-dd-HH-mm-ss.zzz.
    private LocalDateTime localDateTime;
    /*DataStore is store in the form of map.
        {
            "key" : {
                "value" : "value",
                "timeout" : "2021-01-01T18:00:35.007271"
             }
        }

        This is a local dataStore which is getting permanent values form a json file
     */
    private Map<String, Map<String, String>> dataStore;
    //It contain the whole file path.
    private String filePath = "";
    //This string is used to convert hashmap into json & then store in a json file.
    private String json = "";
    //The File class from the java.io package, allows us to work with files.
    private File file;
    //Java FileReader class is used to read data from the file.
    private FileReader fileReader;
    //Java FileWriter class is used to write data in the file.
    private FileWriter fileWriter;
    //The GSON JsonParser class can parse a JSON string or stream into a tree structure of Java objects.
    private JSONParser jsonParser;
    //A Java NIO FileChannel is a channel that is connected to a file. Using a file channel you can read data from a file, and write data to a file.
    private FileChannel fileChannel;
    //FileLock which is used to provide lock over whole file or on a part of file,so that file or its part doesn't get shared or accessible.
    private FileLock fileLock;

    //construct for initialize filepath,file and dataStore
    public DataStore(String filePath) throws Exception {
        dataStore = new HashMap<>();
        this.filePath = filePath;

        file = new File(filePath);

        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exist!!");
            readDataFromFile();
        }
        initializeVariable();

        //Once file is accessed by one client, it can't access by other clients
        lockFile();

    }

    //for initialize some variables
    private void initializeVariable() throws IOException {
        objectMapper = new ObjectMapper();
        fileChannel=new RandomAccessFile(file,"rw").getChannel();
        fileLock=fileChannel.tryLock();
        jsonParser = new JSONParser();

    }


    @Override
    public void create(String key, String value, long timeToLive) throws Exception {
        if (dataStore.containsKey(key)) {
            throw new Exception("This key is already exist in dataStore");
        } else {
            if (isStringOnlyAlphabet(key)) {
                //convert map to json
                //Key length should not be greater than 32 character
                if (key.length() <= 32) {
                    //getting the current time
                    localDateTime = LocalDateTime.now();
                    //increase the localDateTime by timeToLive given by client
                    localDateTime=localDateTime.plusSeconds(timeToLive);

                    //Store values of the key in the form of map
                    Map<String, String> map = new HashMap<>();
                    map.put("value", value);
                    map.put("timeout", String.valueOf(localDateTime));
                    dataStore.put(key, map);
                    //save local DataStore to the permanent json file
                    writeDataToFile();
                    System.out.println("Data stored in DataStore");
                } else {
                    throw new Exception("Key has more than 32 character");
                }
            } else {
                throw new Exception("Invalid key name!! Key name only contains alphabets");
            }
        }

    }

    @Override
    public String read(String key) throws Exception {
        String value = "";
        //updating local Data Store from permanent file
        readDataFromFile();
        if (dataStore.containsKey(key)) {
            localDateTime = LocalDateTime.now();
            Map<String, String> map = dataStore.get(key);
            LocalDateTime timeToLive = LocalDateTime.parse(map.get("timeout"));
            if (timeToLive.compareTo(localDateTime) >= 0)
                value = "{ "+key + " : " + map.get("value")+" }";
            else
                value = "Time-To-Live for a key has expired";
        } else {
            throw new Exception("given key doesn't exist in dataStore");
        }
        return value;
    }

    @Override
    public void delete(String key) throws Exception {
        //updating local Data Store from permanent file
        readDataFromFile();
        if (dataStore.containsKey(key)) {
            localDateTime = LocalDateTime.now();
            Map<String, String> map = dataStore.get(key);
            LocalDateTime timeToLive = LocalDateTime.parse(map.get("timeout"));
            if (timeToLive.compareTo(localDateTime) >= 0) {
                dataStore.remove(key);
                writeDataToFile();
                System.out.println("Key deleted successfully!!");
            } else {
                System.out.println("Time-To-Live for a key has expired");
            }
        } else {
            throw new Exception("given key doesn't exist in dataStore");
        }
    }

    synchronized void readDataFromFile() throws IOException, ParseException {
        fileReader = new FileReader(filePath);
        if (file.length() != 0) {
            //parsing the file to json
            json = jsonParser.parse(fileReader).toString();
            //convert the json to dataStore
            dataStore = objectMapper.readValue(json, HashMap.class);
        }
    }

    synchronized void writeDataToFile() throws Exception {
        //converting dataStore to json for permanent store
        json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataStore);
        //if file size less than 1GB
        if(getFileSizeMegaBytes(file) <= 1024) {
            fileWriter = new FileWriter(filePath, false);
            fileWriter.write(json);
            fileWriter.flush();
        }else {
            throw new Exception("File limit exceed more than 1GB");
        }
    }

    //checking if the key is alphabet or not
    private boolean isStringOnlyAlphabet(String key) {
        String regx = "^[a-zA-Z]*$";

        return ((!key.equals("")) && (key != null) && (key.matches(regx)));

    }

    //calculating the file size
    private static double getFileSizeMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024);
    }

    //locking the file so that only one client can use once
    private void lockFile() throws IOException {
        if(fileLock == null)
        {
            // File is lock by other application
            fileChannel.close();
            throw new RuntimeException("Only 1 instance of MyApp can run.");
        }
    }

    //unlocking the file after done all operating from client, so that other client can access the file
    public  void unlockFile() throws IOException {
        // release and delete file lock
        if(fileLock != null) {
            fileLock.release();
            fileChannel.close();
        }
    }

}
