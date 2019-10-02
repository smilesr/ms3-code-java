package ms3project;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.util.List;
import java.io.IOException;

public class ExportCsv {
	public void writeToCsv(List<List<String>> list) {
		CSVWriter csvWriter;
		try {
			csvWriter = new CSVWriter(new FileWriter("records-bad.csv"));
			for(List<String> row : list) {
				String[] array = row.toArray(new String[0]);
					csvWriter.writeNext(array);
				}
			csvWriter.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
}
