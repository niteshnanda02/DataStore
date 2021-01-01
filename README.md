# DataStore
A file-based key-value data store that supports the basic CRD (create, read, and delete) operations. This data store is meant to be used as a local storage for one single process on one laptop. 

# Dependency
Download jackson-all-1.9.0.jar : 
http://www.java2s.com/Code/Jar/j/Downloadjacksonall190jar.htm 
Download json-simple-1.1.jar:
http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm

# Technology Used
  Java
 
# Functionality of project

  Interface CRDInterace
    DataType and Function name	
    void	create (java.lang.String key, java.lang.String value, long timeToLive)	 
    void	delete (java.lang.String key)	 
    String	read(java.lang.String key)
    
  
  Class DataStore implement CRDInterface 
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
    
    
