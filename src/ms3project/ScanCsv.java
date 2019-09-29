package ms3project;



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
        

        
        SqliteDB db = new SqliteDB();
        
//        String query1 = "INSERT INTO test(name, age) VALUES('Angel',1000);";	
//        
//        db.insertRecords(query1);
//        
//        db.getRecords();
        
        
        

        
        
    
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
        
        System.out.println("********** " + cleanList.get(0).getClass().getName());
        
        List<String> columnHeadings = cleanList.get(0);
        
        db.createTable(columnHeadings);
        
//        for (int x = 0; x < cleanList.size(); x++) {
//        		if (x == 0) {
//        			
//        		}
//        		for (List record:cleanList) {
//        			
//        		}
//        }
        
        int successCount = totalCount - 1 - failureCount;

		System.out.println(totalCount - 1);
		System.out.println(successCount);
		System.out.println(failureCount);
		System.out.println("DirtyList: " + dirtyList);
		System.out.println("Revised CleanList :" + cleanList);
		
        db.closeConnection();

    }

}


