import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileReaderWeka {
	Map<String, Integer> Word_Life = new HashMap<String, Integer>();
    Map<String, Integer> File_Boolean = new HashMap<String, Integer>();
    Map<String, Map<String,Integer>> Vocabulary_Spam = new HashMap<String, Map<String,Integer>>();
    Map<String, Map<String,Integer>> Vocabulary_Ham = new HashMap<String, Map<String,Integer>>();
    float weight_initial = 0.0f;
   
   @SuppressWarnings("resource")
   public static FileReaderWeka WordBuilder(String filepath){
	    int spam = 1, ham = -1;
		int weight;
		String punctuation = "[\\p{Punct}]";
		String numbers = "[0-9]";	
		String alphabets = "[a-zA-Z]+";
		String set;
		String subject = "Subject";
		String from = "From";
		String to = "To";
		String re = "re";
		Pattern pattern = null;
		Pattern p2 = null;
		pattern = Pattern.compile(punctuation);
		p2 = Pattern.compile(numbers);
		Matcher matcher = null;
		Matcher m2 = null;
		String filedirectory = filepath;
		String filedirectory2;
		String filepath_spam, filepath_ham;
		filedirectory2 = filedirectory + "ham";
	    filedirectory = filedirectory + "spam";        
	    File file = null;
	    File file2 = null;
	    BufferedReader reader = null;
	    FileInputStream fis = null;
	    String[] paths;
	    String[] ham_paths;
	    Map<String, Integer> Word_Life = new HashMap<String, Integer>();
	    Map<String, Integer> File_Boolean = new HashMap<String, Integer>();
	    Map<String, Map<String,Integer>> Vocabulary_Spam = new HashMap<String, Map<String,Integer>>();
	    Map<String, Map<String,Integer>> Vocabulary_Ham = new HashMap<String, Map<String,Integer>>();
	    int count = 0;
	    Boolean bool;
	    try{
	    	// Creating the files
       	file = new File(filedirectory);	
       	file2 = new File(filedirectory2);
       	
       	// Array of all files and directory
       	paths = file.list();
       	ham_paths = file2.list();
       	
       	for(String path:paths){
       		filepath_spam = filedirectory + "\\" + path;
       		File_Boolean.put(filepath_spam, spam);
       		fis = new FileInputStream(filepath_spam); 
               reader = new BufferedReader(new InputStreamReader(fis));
               
               // Read the first line of the file
               String line;
               StringTokenizer stringtokenizer = null;
               while ((line = reader.readLine()) != null){
               	stringtokenizer = new StringTokenizer(line, ".:;, ");
               	while(stringtokenizer.hasMoreTokens()){
               		set = stringtokenizer.nextToken();
               		matcher = pattern.matcher(set);
               		m2 = p2.matcher(set);
               		if (set.equals(null)){
               			continue;
               		} else if (set.equalsIgnoreCase(subject)){
               			continue;
               		} else if (set.equalsIgnoreCase(from)){
               			continue;
               		} else if (set.equalsIgnoreCase(to)){
               			continue;
               		}else if (set.equalsIgnoreCase(re)){
               			continue;
               		}else if (matcher.find() || m2.find()){
               			continue;
               		} else if(Pattern.matches(alphabets, set) == false) {
               			continue;
               		} else {
               		
               			if (Vocabulary_Spam.isEmpty()){
               				count++;
               				Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
               				vocab_builder.put(set, count);
               				Vocabulary_Spam.put(filepath_spam, vocab_builder);
               			} else {
               				bool = Vocabulary_Spam.containsKey(filepath_spam);
               				if(bool == true){
               					Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
               					vocab_builder = Vocabulary_Spam.get(filepath_spam);
               					if(vocab_builder.containsKey(set)){
               						count = vocab_builder.get(set);
               						count++;
               						vocab_builder.put(set, count);
               						Vocabulary_Spam.put(filepath_spam, vocab_builder);
               					} else{
               						count++;
               						vocab_builder.put(set, count);
               						Vocabulary_Spam.put(filepath_spam, vocab_builder);
               					}
               				}else{
               					Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
               					count++;
               					vocab_builder.put(set, count);
               					Vocabulary_Spam.put(filepath_spam, vocab_builder);
               				}
               			}
               			count = 0;
               			if (Word_Life.isEmpty()){
               				weight = (int)(Math.random()*20000);
               				Word_Life.put(set, weight);
               			}else{
               				if(Word_Life.containsKey(set)){
               					continue;
               				}else{
               					weight = (int)(Math.random()*20000);
               					Word_Life.put(set, weight);
               				}
               			}
               			
               		}
               	}
               }
               filepath_spam = "";
       	}
       	
       	for(String path:ham_paths){
       		filepath_ham = filedirectory2 + "\\" + path;
       		File_Boolean.put(filepath_ham, ham);
       		fis = new FileInputStream(filepath_ham); 
               reader = new BufferedReader(new InputStreamReader(fis));
               
               // Read the first line of the file
               String line;
               StringTokenizer stringtokenizer = null;
               while ((line = reader.readLine()) != null){
               	stringtokenizer = new StringTokenizer(line, ".:;, ");
               	while(stringtokenizer.hasMoreTokens()){
               		set = stringtokenizer.nextToken();
               		matcher = pattern.matcher(set);
               		m2 = p2.matcher(set);
               		if (set.equals(null)){
               			continue;
               		}else if (set.equalsIgnoreCase(subject)){
               			continue;
               		} else if (set.equalsIgnoreCase(from)){
               			continue;
               		} else if (set.equalsIgnoreCase(to)){
               			continue;
               		}else if (set.equalsIgnoreCase(re)){
               			continue;
               		}else if (matcher.find() || m2.find()){
               			continue;
               		} else if(Pattern.matches(alphabets, set) == false) {
               			continue;
               		} else {
               			if (Vocabulary_Ham.isEmpty()){
               				count++;
               				Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
               				vocab_builder.put(set, count);
               				Vocabulary_Ham.put(filepath_ham, vocab_builder);
               			} else {
               				bool = Vocabulary_Ham.containsKey(filepath_ham);
               				if(bool == true){
               					Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
               					vocab_builder = Vocabulary_Ham.get(filepath_ham);
               					if(vocab_builder.containsKey(set)){
               						count = vocab_builder.get(set);
               						count++;
               						vocab_builder.put(set, count);
               						Vocabulary_Ham.put(filepath_ham, vocab_builder);
               					} else{
               						count++;
               						vocab_builder.put(set, count);
               						Vocabulary_Ham.put(filepath_ham, vocab_builder);
               					}
               				}else{
               					count++;
               					Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
               					vocab_builder.put(set, count);
               					Vocabulary_Ham.put(filepath_ham, vocab_builder);
               				}
               			}
               			count = 0;
               			if (Word_Life.isEmpty()){
               				weight = (int)(Math.random()*20000);
               				Word_Life.put(set, weight);
               			}else{
               				if(Word_Life.containsKey(set)){
               					continue;
               				}else{
               					weight = (int)(Math.random()*20000);
               					Word_Life.put(set, weight);
               				}
               			}
               			
               		}
               	}
               }
               filepath_ham = "";
       	}
	    }
	    catch (Exception e){
       	// In case of any error
       	e.printStackTrace();
       }
	    FileReaderWeka filereader = new FileReaderWeka();
	    filereader.Vocabulary_Spam = Vocabulary_Spam;
	    filereader.Vocabulary_Ham = Vocabulary_Ham;
	    filereader.Word_Life = Word_Life;
	    filereader.File_Boolean = File_Boolean;
	    return filereader;
   }
}
