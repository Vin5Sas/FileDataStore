package freshdatastore;

import java.io.*;
import java.util.*;

public class TimeToLive extends Thread implements Runnable {
    
    public void removeKey(Key key,long createdTime) {           //removes key after TTL expires
        
        Thread keyTimeToLive = new Thread()                     //a new thread is created for every key with TTL, which runs concurrently in the background
        {
            @Override
            public void run()
            {
                while(System.currentTimeMillis() <= (createdTime + key.timeToLive))
                {
                    //runs till the specified TTL value in System clock
                }
                System.out.println(key.keyID + " removed in " + (System.currentTimeMillis()-createdTime)/1000 + " seconds!!");
                FreshDataStore.dataStore.remove(key.keyID);     //removes the key from dataStore
            }
        };
        keyTimeToLive.start();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}