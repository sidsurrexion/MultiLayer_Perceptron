import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileDriver {

	public static FilePaths filesreader() {
		// TODO Auto-generated method stub
		String filedirectory = new File("").getAbsolutePath();
		String file2 = new File("").getAbsolutePath();
		file2 = file2 + "\\" + "output";
		File file = null;
		file = new File(filedirectory);
		String[] paths = file.list();
		String extension;
		String zip = "zip";
		FilePaths filepaths = new FilePaths();
		String filepath;
		int count = 0;
		for (String path: paths){
			extension = path.substring(path.lastIndexOf(".") + 1, path.length());
			if (extension.equals(zip)){
				count++;
				path = filedirectory + "\\" + path;
				FileDriver filedriver = new FileDriver();
				filepath = filedriver.unzip(path, file2);
				filepaths.File_Paths.put(filepath, count);
			}
		}
		return filepaths;
	}
	
	public String unzip(String zipfile, String filedirectory){
		Boolean bool = null;
		byte[] buffer = new byte[1024];
		String string1 = "enron1"+"/"+"test"+"/";
		String string2 = "enron1"+"/"+"train"+"/";
		String string3 = "enron4"+"/"+"test"+"/";
		String string4 = "enron4"+"/"+"train"+"/";
		String string5 = "test"+"/";
		String string6 = "train"+"/";
		String string_1 = "enron1"+"\\"+"test"+"\\";
		String string_2 = "enron1"+"\\"+"train"+"\\";
		String string_3 = "enron4"+"\\"+"test"+"\\";
		String string_4 = "enron4"+"\\"+"train"+"\\";
		String string_5 = "test"+"\\";
		String string_6 = "train"+"\\";
		String filepaths = null;
		
		try{
			File folder = new File(filedirectory);
			if(!folder.exists()){
				folder.mkdir();
			}
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipfile));
			ZipEntry ze = zis.getNextEntry();
			while (ze!=null){
				String edge = ze.getName();
				File newfile = new File (filedirectory + "\\" + edge);
				if (edge.equals(string1)){
					filepaths = filedirectory + "\\" + string_1;
				} else if (edge.equals(string2)){
					filepaths = filedirectory + "\\" + string_2;
				} else if (edge.equals(string3)){
					filepaths = filedirectory + "\\" + string_3;
				} else if (edge.equals(string4)){
					filepaths = filedirectory + "\\" + string_4;
				} else if (edge.equals(string5)){
					filepaths = filedirectory + "\\" + string_5;
				} else if (edge.equals(string6)){
					filepaths = filedirectory + "\\" + string_6;
				}
				new File(newfile.getParent()).mkdirs();
				
				FileOutputStream fos = new FileOutputStream(newfile);
				
				int length;
				
				length = zis.read(buffer);
				
				if ( length > 0){
					while(length > 0){
						fos.write(buffer,0,length);
						length = zis.read(buffer);
					}
				} else {					
					bool = true;
				}
				fos.flush();
				fos.close();
				if (bool.equals(true)){
					newfile.delete();
					bool = false;
				}
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return filepaths;
	}

}
