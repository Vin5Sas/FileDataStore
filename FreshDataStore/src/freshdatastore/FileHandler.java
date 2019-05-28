//This class is repsonsible for handling file related operations
package freshdatastore;

import java.io.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileHandler {
    
    public static InputStreamReader isinput = new InputStreamReader(System.in);
    public static BufferedReader bfinput = new BufferedReader(isinput);
    
    public static File createFile(File file) throws IOException
    {
        String path;
        System.out.println("Enter path for " + file + " (separate by '/'): (press Enter to use Default location) ");
        path = bfinput.readLine();
        
        if(path!=null)                          //for creating file in user specified locations
            file = new File(path+file);
        
        return file;                            //returns the created file object
    }
    
    public void saveToFile(File file1, File file2) throws IOException
    {   
        try (FileWriter fileWriter = new FileWriter(file1))     //Creates File Writer for "keyFile.dat"
            {
                for (Map.Entry<String, Value> entry : FreshDataStore.dataStore.entrySet()) 
                { 
                    fileWriter.write(entry.getKey());
                    fileWriter.write(System.getProperty( "line.separator" )); //inserts new line
                }    
		System.out.println("DataStore successfully written to Key File...");
            }
        try (FileWriter fileWriter = new FileWriter(file2))     //Creates File Writer for "valueFile.dat"
            {
                for (Map.Entry<String, Value> entry : FreshDataStore.dataStore.entrySet()) 
                { 
                    fileWriter.write(entry.getValue().data + "");
                    fileWriter.write(System.getProperty( "line.separator" )); //inserts new line
                }    
		System.out.println("DataStore successfully written to Value File...");
            }
    }
    
    public void loadFromFile(File file1, File file2) throws IOException, ParseException
    {
        String path;
        System.out.println("Enter path for file (separate by '/'): (press Enter to use Default location) ");
        path = bfinput.readLine();
        
        if(path!=null)                      //for retrieving files from user specified locations
        {    
            file1 = new File(path+file1);   //file1 points to "keyFile.dat"
            file2 = new File(path+file2);   //file2 points to "valueFile.dat"
        }
        
        ArrayList<String> keys = new ArrayList<>();                 //holds retrieved keys in List
        ArrayList<JSONObject> jsonValues = new ArrayList<>();       //holds retrieved JSON Obejcts in List
        
        String line = null;
        JSONObject jsonObject;
        
         try {
              FileReader fileReader = new FileReader(file1);                //reads data from "keyFile.dat"
              BufferedReader bufferedReader = new BufferedReader(fileReader);
              
              while((line = bufferedReader.readLine()) != null) //reads line by line till end of the file
              {
                keys.add(line);                                 //adds each key to ArrayList
              }
              bufferedReader.close();         
            }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file" + ex);                
             }
        catch(IOException ex) {
            System.out.println("Error reading file " + ex); 
             }
         
         try {
              FileReader fileReader = new FileReader(file2);                //reads data from "valueFile.dat"
              BufferedReader bufferedReader = new BufferedReader(fileReader);
              
              while((line = bufferedReader.readLine()) != null)            //reads line by line till end of the file
              {
                jsonObject = (JSONObject) new JSONParser().parse(line);    //parses json object in every line
                jsonValues.add(jsonObject);
              }
              bufferedReader.close();         
            }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file" + ex);                
             }
        catch(IOException ex) {
            System.out.println("Error reading file " + ex); 
             }
         
         for(int i=0; i<keys.size(); i++)                           //creates key-value hashmap with retrieved data
         { 
           Value value = new Value();
           value.data = jsonValues.get(i);
           FreshDataStore.dataStore.put(keys.get(i), value);
         }
         
         System.out.println("Data Loaded...");
    }
}
