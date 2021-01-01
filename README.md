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
    
  
  
