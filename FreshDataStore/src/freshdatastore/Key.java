//This Class is created for "Key" in the data store
package freshdatastore;

import java.io.*;
import java.util.*;

public class Key implements Serializable{
    
    String keyID;       //holds the unique Key's name (32 characters long)
    int timeToLive;     //optional property which defines lifetime of the key
    
    public void setKeyID(String keyID)
    {
        this.keyID = keyID;
    }
    
    public void setTimeToLive(int timeToLive)
    {
        this.timeToLive = timeToLive;
    }
}
