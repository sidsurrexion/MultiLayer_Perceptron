import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.csvreader.CsvWriter;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class ArffBuilder {
	public static void arffdeveloper (String filepath, FileReaderWeka filereader) throws IOException{
		
		String file = "data.csv";
		String file_arff = "data.arff";
		String arff_concatenate = filepath + file_arff;
		String ham = "ham";
		String spam = "spam";
		String edge;
		
		File csvfile = new File(filepath + "\\" + file);
		if (!csvfile.exists()){
			csvfile.createNewFile();
		}
		CsvWriter csvwriter = new CsvWriter(new FileWriter(csvfile,true),',');
		String nullstring = " ";
		
		
		for (String word: filereader.Word_Life.keySet()){
			if (word.equals(nullstring)){
				continue;
			}
			csvwriter.write(word);
		}
		
		csvwriter.write("class_value");
		csvwriter.endRecord();
		
		for (String path: filereader.File_Boolean.keySet()){
			
			Map<String, Integer> Vocabulary = new HashMap<String, Integer>();
			
			if (filereader.Vocabulary_Spam.containsKey(path)){
				Vocabulary = filereader.Vocabulary_Spam.get(path);
			} else if (filereader.Vocabulary_Ham.containsKey(path)) {
				Vocabulary = filereader.Vocabulary_Ham.get(path);
			} else {
				continue;
			}			
		
			for (String word: filereader.Word_Life.keySet()){
				if (Vocabulary.containsKey(word)){
					csvwriter.write(String.valueOf(Vocabulary.get(word))); 
				} else {
					csvwriter.write("0");
				}
			}			
			if(filereader.File_Boolean.get(path) == 1){
				edge = spam;
			} else {
				edge = ham;
			}
			csvwriter.write(edge);
			csvwriter.endRecord();
		}
		csvwriter.flush();
		csvwriter.close();
		CSVLoader loader = new CSVLoader();
		loader.setSource(csvfile);
		Instances data = loader.getDataSet();
		
		ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(arff_concatenate));
	    saver.writeBatch();
	}
}
