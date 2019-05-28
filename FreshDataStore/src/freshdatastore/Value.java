//This class is created for "Value" attribute in key-value pair
package freshdatastore;

import java.io.*;
import org.json.JSONException;
import org.json.simple.JSONObject;

//Value holds JSONObject data
public class Value implements Serializable{

    public JSONObject data = new JSONObject(); //This object contains 'Name' and 'Placement Status' ; Further attributes can be added if needed
    
    public void setJSONData(String name, String placementStatus) throws JSONException
    {
        data.put("Name", name);
        data.put("PlacementStatus", placementStatus);
    }
    
}