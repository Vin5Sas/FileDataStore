//This class defines the CRD operations that can be performed on the data store
package freshdatastore;

import java.io.*;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

public class Operations {
    
    InputStreamReader isinput = new InputStreamReader(System.in);
    BufferedReader bfinput = new BufferedReader(isinput);
    
    Scanner input = new Scanner(System.in);
    Key key;
    Value value;
    TimeToLive TTLObject;
    
    //support methods
    public boolean keyExists(String keyID)  //method to check if key exists in Data Store
    {
        return FreshDataStore.dataStore.containsKey(keyID); //returns "true" if the specified key exists
    }
    
    //method to calculate size of "value" (in bytes)
    public int sizeOfValue(String name, String placementStatus)             //arguments can be added as and when needed and their sizes can be summed
    {
        return name.getBytes().length + placementStatus.getBytes().length;  //adds byte length of both attributes (name and placement status) of value
    }
    
    public void parseToJSON(Value value) throws JSONException, IOException   //receive data and parse to JSON object
    {
        String name,placementStatus;
        
        System.out.println("Enter Name: ");
        name = bfinput.readLine();
        
        System.out.println("Enter your placement status: ");
        placementStatus = bfinput.readLine();
        
        int valueSize = sizeOfValue(name,placementStatus); 
        
        if(valueSize > 16000)   //check if "value" size is not more than 16KB (16000 bytes)
            System.out.println("Size of Value exceeds 16KB...");
        else
            value.setJSONData(name, placementStatus);
    }
    
    //CREATE
    public void create(Key key, Value value) throws JSONException, IOException
    {
        String keyID;
        char choice;        //choice for specifying Time To Live (TTL)
        
        System.out.println("Enter Key ID:");
        keyID = bfinput.readLine();
        
        if(keyID.length()>32)
            keyID = keyID.substring(0,32);  //restricts key ID to 32 characters  
        
        if(!keyExists(keyID))
        {       
            key.setKeyID(keyID);
            
            parseToJSON(value);
            FreshDataStore.dataStore.put(keyID, value);
            
            System.out.println("Do you want to set TTL? (Y/N)");
            choice = input.next().charAt(0);
            
            if(choice=='Y')
            {
                TTLObject = new TimeToLive();
                int timeToLive;
                long createdTime; 
                
                System.out.print("Specify TTL (in seconds): ");
                timeToLive = input.nextInt() * 1000;        //seconds converted to milliseconds
                key.setTimeToLive(timeToLive);
                createdTime = System.currentTimeMillis();
                
                TTLObject.removeKey(key,createdTime);       //sent to Threaded class for removing key after its time-to-live expires
            }
        }
        else
        {   System.out.println("Key already exists! Try a unique key!"); }
    }
    
    //READ
    public void read() throws IOException
    {
        String keyID;
        Value readData;
        
        System.out.println("Enter Key ID:");
        keyID = bfinput.readLine();
        
        if(keyExists(keyID))
        {       
            readData = FreshDataStore.dataStore.get(keyID);
            System.out.println(readData.data);
        }
        else
        {   System.out.println("The Key-value pair does not exist!");   }
    }
    
    //DELETE
    public void delete() throws IOException
    {
        String keyID;
        
        System.out.println("Enter Key ID:");
        keyID = bfinput.readLine();  
        
        if(keyExists(keyID))
        {       
            FreshDataStore.dataStore.remove(keyID);
            System.out.println(keyID + " mapping removed!");
        }
        else
        {   System.out.println("The Key does not exist!");   }
    }
}