package ms3project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScanCsv {

    public static void main(String[] args) throws IOException {
        String fileName= "/Users/robertsmiles1/post31119/eclipse-java/ms3project/src/cleanedms3Interview.csv";
        
        List<List<String>> lines = readTextFile(fileName);
        
        List<List<String>> cleanList = new ArrayList<>(), dirtyList = new ArrayList<>();
       
        cleanList.addAll(lines);

        int failureCount = 0;
        int totalCount = lines.size();
        outerloop:
        for(List al:lines) {  
        		innerLoop:
    			for (int i = 0; i < al.size(); i++) {
    				if (al.get(i).equals("")) {
//    					System.out.println(al);
    					failureCount ++;
    					dirtyList.add(al);
    					cleanList.remove(al);
    					break innerLoop;
    				};
    			} 
        } 
        
        contactDatabase(cleanList);
            
        int successCount = totalCount - 1 - failureCount;
        
		System.out.println(totalCount - 1);
		System.out.println(successCount);
		System.out.println(failureCount);
    }

	public static void contactDatabase (List<List<String>> records) {
      SqliteDB db = new SqliteDB();
      List<String> columnHeadings = records.get(0);
      
      db.createTable(columnHeadings);
      
      records.remove(0);
      
	    for(List<String> row: records) {	
	    		db.insertRecord(row);
	    }
      db.closeConnection();
    }
    
    public static List<List<String>> readTextFile(String fileName) throws IOException {
        File file= new File(fileName);
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file).useDelimiter("\n");
            while(inputStream.hasNext()){
                String line= inputStream.next();
               String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                lines.add(Arrays.asList(values));
            }

            inputStream.close();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
