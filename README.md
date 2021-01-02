# DataStore
A file-based key-value data store that supports the basic CRD (create, read, and delete) operations. This data store is meant to be used as a local storage for one single process on one laptop. 
The code for dataStore is in :- https://github.com/niteshnanda02/dataStore/blob/master/src/com/company/DataStore.java

# Dependency
Download jackson-all-1.9.0.jar : 
http://www.java2s.com/Code/Jar/j/Downloadjacksonall190jar.htm 
Download json-simple-1.1.jar:
http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm

# Technology Used
  Java

# DataStore 
  It is store in data.json file under the project directory, if client has provided the optional the path, then file will be at that location.
 
# Functionality of project

  Interface CRDInterace  
  
    DataType and Function name  
    void	create (java.lang.String key, java.lang.String value, long timeToLive)  	 
    void	delete (java.lang.String key)  	 
    String	read(java.lang.String key)  
    
  Class DataStore which implementing interface and give body to all it's function  
    
    //for creating key value with timeToLive.  
    void create(String key, String value, long timeToLive) throws Exception;  

    /*read function returns the value of the key,
      if timeToLive is greater then current time,  it will return the JSONObject
      else it will not return JSONObject.
     */
     
    String read(String key) throws Exception;  
    /*delete function delete the key,
      if timeToLive is greater then current time,  it will delete the JSONObject
      else it will not delete JSONObject.
     */  
    void delete(String key) throws Exception;  
    
# Output
    
    Welcome to DataStore, here you can create, read and delete the key-value pair.
    Default path is projectDirectory/dataStoreFile
    For manual path these are some convention that you need to follows
    for window:- C:/Users/<current user>/directory/
    for linux:- /home/<current user>/directory/

    Default path for dataStore, please enter (y/n) :- y
    File created: data.json
    Select form 1-4 for your purpose.
    1. Create new key - value pair.
    2. Read key - value pair.
    3. Delete key - value pair.
    4. For exit.
    1
    Enter key:- 
    data
    Enter value:- 
    value
    Enter timeToLive(in seconds) :- 
    400
    Data stored in DataStore
    Select form 1-4 for your purpose.
    1. Create new key - value pair.
    2. Read key - value pair.
    3. Delete key - value pair.
    4. For exit.
    1
    Enter key:- 
    key
    Enter value:- 
    check
    Enter timeToLive(in seconds) :- 
    200
    Data stored in DataStore
    Select form 1-4 for your purpose.
    1. Create new key - value pair.
    2. Read key - value pair.
    3. Delete key - value pair.
    4. For exit.
    2
    Enter key:- 
    data
    { data : value }
    Select form 1-4 for your purpose.
    1. Create new key - value pair.
    2. Read key - value pair.
    3. Delete key - value pair.
    4. For exit.
    3
    Enter key:- 
    data
    Key deleted successfully!!
    Select form 1-4 for your purpose.
    1. Create new key - value pair.
    2. Read key - value pair.
    3. Delete key - value pair.
    4. For exit.





