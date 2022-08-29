import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;


public class testing_arff {
	public static void main(String args[]) throws Exception{
		
		Set<String> set_unique = new TreeSet<String>();
		File f = new File("/home/envy/Desktop/Rahul-Programs/unique_intermediate.txt");

		try {

			FileInputStream fis = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;

			while ((line = br.readLine()) != null) {
				set_unique.add(line);

			}
			br.close();
		} catch (Exception e) {
		}
		
		
		
		PrintWriter out1 = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/testing.arff"));
		out1.write("@relation grams\n");
		for (String str : set_unique) {
			out1.write("@attribute " + str + " {0,1}\n");
			
		}
		
		out1.write("@attribute class {Benign, Malware}\n@data\n");
		out1.close();
		File benign_test = new File("/home/envy/Desktop/Rahul-Programs/benign_test");
		File malware_test = new File("/home/envy/Desktop/Rahul-Programs/malware_test");

		if(!benign_test.exists()){
			File dir3 = new File("/home/envy/Desktop/Rahul-Programs/malware_test");
			arff(dir3 , "Malware");
		}
		else if(!malware_test.exists()){
			File dir3 = new File("/home/envy/Desktop/Rahul-Programs/benign_test");
			arff(dir3 , "Benign");
		}
		else{
			File dir3 = new File("/home/envy/Desktop/Rahul-Programs/benign_test");
			arff(dir3 , "Benign");
			dir3 = new File("/home/envy/Desktop/Rahul-Programs/malware_test");
			arff(dir3 , "Malware");
		}
	}
	
	public static void arff(File f, String s) throws Exception{
		File[] listOfFiles = f.listFiles();
		PrintWriter out1 = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/testing.arff", true));
		for (int i = 0; i < listOfFiles.length; i++) {
			File fin1 = new File(
					"/home/envy/Desktop/Rahul-Programs/unique_intermediate.txt");
			FileInputStream fis1 = new FileInputStream(fin1);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
			if (listOfFiles[i].isFile()) {
				Set<String> sett = new TreeSet<String>();
				String fname = listOfFiles[i].getName();
				try {
					File fin = new File(f.getCanonicalPath()
							+ File.separator + fname);
					FileInputStream fis = new FileInputStream(fin);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fis));

					String line = null;

					while ((line = br.readLine()) != null) {
						sett.add(line);

					}
					br.close();
				} catch (Exception e) {
				}

				String line = null;

				while ((line = br1.readLine()) != null) {
					if (sett.contains(line)) {
						out1.print("1,");
					} else {
						out1.print("0,");
					}

				}
				out1.print(s + "\n");

			}
			br1.close();
		}
		out1.close();
	}

}
