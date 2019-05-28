package freshdatastore;

import java.io.*;
import java.util.*;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

public class FreshDataStore implements Serializable{

    public static HashMap<String,Value> dataStore = new HashMap<>();        //this HashMap stores the key-value pair
    
    public static Operations operations = new Operations();
    public static FileHandler fileHandle = new FileHandler();

    public static void main(String[] args) throws JSONException, IOException, ParseException {
        
        Key key;
        Value value;
        
        Scanner input = new Scanner(System.in);
        InputStreamReader isinput = new InputStreamReader(System.in);
        BufferedReader bfinput = new BufferedReader(isinput);
        
        char repeat = 'Y';
        int choice;

        //key-value pair is split into two separate files while storing to facilitate easy retrieval of JSON objects
        File file1 = new File("keyFile.dat");       //initially creates in project direcotry by default (stores keys)
        File file2 = new File("valueFile.dat");     //initially creates in project direcotry by default (stores correspoding JSON objects)
                
        System.out.println("1.Create File or 2.Load an Existing File? (1/2)");
        choice = input.nextInt();
        if(choice==1)
        {    
             file1 = FileHandler.createFile(file1);
             file2 = FileHandler.createFile(file2);
        }
        else
        {    
            fileHandle.loadFromFile(file1 ,file2);
        }
        while(repeat=='Y')
        {
            key = new Key();
            value = new Value();
            
            System.out.println("Menu:\n1.Create\n2.Read\n3.Delete");
            choice = input.nextInt();
            
            switch(choice)
            {
                case 1: operations.create(key,value);
                break;
                
                case 2: operations.read();
                break;
                
                case 3: operations.delete();
                break;
            }
            
            System.out.println("Revisit the menu? (Y/N)");
            repeat = input.next().charAt(0);
        }
        
        if((file1.length() + file2.length()) <= 1e+9) //checks if File size is lesser than 1GB (Non-Functional Requirement) , 1e+9 = 1000000000 bytes = 1GB
            fileHandle.saveToFile(file1,file2);
        else
            System.out.println("File size exceeds 1GB!!!");
    }
}