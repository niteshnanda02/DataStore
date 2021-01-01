package com.company;

public interface CRDInterace {
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

}
