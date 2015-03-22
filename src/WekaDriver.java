import java.io.IOException;

public class WekaDriver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FilePaths filepaths = new FilePaths();
		filepaths = FileDriver.filesreader();
		for(String filepath: filepaths.File_Paths.keySet()){
			FileReaderWeka filereader = new FileReaderWeka();
			filereader = FileReaderWeka.WordBuilder(filepath);
			ArffBuilder.arffdeveloper(filepath, filereader);
		}
	}

}
