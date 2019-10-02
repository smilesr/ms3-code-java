package ms3project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScanCsv {

    public static void main(String[] args) throws IOException {
    	
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println(currentRelativePath);
        String path = s + "/resources";
        String fileName = path + "/cleanedms3Interview.csv"; 
        // Read in data records
        List<List<String>> fileContent = readTextFile(fileName);
        List<List<String>> cleanList = new ArrayList<>(), dirtyList = new ArrayList<>();
       
        cleanList.addAll(fileContent);

        //Parse the records and separate by records that have all fields vs records that do not;  also count the failed records
        int failureCount = 0;
        int totalCount = fileContent.size() - 1;
        for(List<String> line : fileContent) {  
        		innerLoop:
    			for (int i = 0; i < line.size(); i++) {
    				if (line.get(i).equals("")) {
    					failureCount ++;
    					dirtyList.add(line);
    					cleanList.remove(line);
    					break innerLoop;
    				};
    			} 
        } 
        // send the good records to be added to SQLite3 database
        contactDatabase(cleanList);
        // print the counts of total records, good records and bad records to log file
        printLogFile(totalCount, failureCount);
        // send bad records to a .csv file
        sendToCsv(dirtyList);
        
    }
    
    private static void sendToCsv (List<List<String>> cleanList) {
    		ExportCsv exp = new ExportCsv();
    		exp.writeToCsv(cleanList);
    }
   
    private static void printLogFile (Integer totalCount, Integer failureCount) {
        Logger logger = Logger.getLogger("MyLog");  
        FileHandler fh;  

        try {   
            fh = new FileHandler("/Users/robertsmiles1/post31119/eclipse-java/ms3project/logFile.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);
            Integer successCount = totalCount - failureCount;
            logger.info("\nTotal number of records imported:" + totalCount.toString() + "\nNumber of  records that contained all columns: " + successCount.toString() + "\nNumber of records that were missing columns: " + failureCount.toString());   
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }

	public static void contactDatabase (List<List<String>> records) {	
      SqliteDB db = new SqliteDB();
      List<String> columnHeadings = records.get(0); 
      db.createTable(columnHeadings);  
      // already created column headings so removing row with column headings from the records to be inserted into DB
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
        	// suppressed a warning here indicating Scanner object not closed
        		inputStream = new Scanner(file).useDelimiter("\n");
        		while(inputStream.hasNext()){
            		String line = inputStream.next();
            		// used regex to split field on comma and to ignore excess quotes
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
