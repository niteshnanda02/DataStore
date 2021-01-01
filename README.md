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
    
  
  
