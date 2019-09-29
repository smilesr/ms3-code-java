package ms3project;

//9/29 you now have two lists: clean and dirty. You also have counts of total, dirty and clean records.  Next export clean list to SQLite.  Write dirty list to csv.  Log the counts.  Then refactor, including breaking code up into classes.


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ScanCsv {

    public static void main(String[] args) {
        String fileName= "/Users/robertsmiles1/post31119/eclipse-java/ms3project/src/ms3sample-cleaned.csv";
        File file= new File(fileName);
        
        String query1 = "INSERT INTO test(name, age) VALUES('Angel',1000);";	
        
        SqliteDB db = new SqliteDB();
        
        db.insertRecords(query1);
        
        db.getRecords();
        
        db.closeConnection();
        
        
    
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file).useDelimiter("\n");
       //     String reg = "\\p{javaWhitespace}+";
            while(inputStream.hasNext()){
                String line= inputStream.next();
               String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<List<String>> cleanList = new ArrayList<>(), dirtyList = new ArrayList<>();
//        List<String> cleanList = new ArrayList<String>(lines);
//        Collections.copy(lines, cleanList); 
        cleanList.addAll(lines);
        System.out.println("Orginal cleanList: "  + cleanList);
        int failureCount = 0;
        int totalCount = lines.size();
        outerloop:
        for(List al:lines) {  
        		innerLoop:
    			for (int i = 0; i < al.size(); i++) {
//    				System.out.println(al.get(i));
    				if (al.get(i).equals("")) {
    					failureCount ++;
    					dirtyList.add(al);
    					cleanList.remove(al);
    					break innerLoop;
    				};

    			} 
        }  
          
        
        int successCount = totalCount - 1 - failureCount;

		System.out.println(totalCount - 1);
		System.out.println(successCount);
		System.out.println(failureCount);
		System.out.println("DirtyList: " + dirtyList);
		System.out.println("Revised CleanList :" + cleanList);

    }

}


