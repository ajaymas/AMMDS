import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class common_to_arff {
	public static void main(String args[]) throws Exception {
		File benign = new File(
				"/home/envy/Desktop/Rahul-Programs/benign_common.txt");
		Set<String> set_benign = make_set(benign);
		File malware = new File(
				"/home/envy/Desktop/Rahul-Programs/malware_common.txt");
		Set<String> set_malware = make_set(malware);
		Set<String> set_unique = new TreeSet<String>();
		if (set_benign == null) {
			set_unique = set_malware;
		} else if (set_malware == null) {
			set_unique = set_benign;
		} else {
			set_unique = union(difference(set_benign, set_malware),
					difference(set_malware, set_benign));
		}
		
		PrintWriter out1 = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/unique_intermediate.txt"));
		for (String str : set_unique) {
			out1.write(str + "\n");
		}
		out1.close();

		out1 = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/new.arff"));
		out1.write("@relation grams\n");
		for (String str : set_unique) {
			out1.write("@attribute a" + str + " {0,1}\n");
			
		}
		
		out1.write("@attribute class {Benign, Malware}\n@data\n");
		out1.close();
		if(set_benign==null){
			File dir3 = new File("/home/envy/Desktop/Rahul-Programs/malware_n_gram");
			arff(dir3 , "Malware");
		}
		else if(set_malware == null){
			File dir3 = new File("/home/envy/Desktop/Rahul-Programs/benign_n_gram");
			arff(dir3 , "Benign");
		}
		else{
			File dir3 = new File("/home/envy/Desktop/Rahul-Programs/benign_n_gram");
			arff(dir3 , "Benign");
			dir3 = new File("/home/envy/Desktop/Rahul-Programs/malware_n_gram");
			arff(dir3 , "Malware");
		}
		
		


	}

	public static Set<String> make_set(File f) {

		Set<String> set = new TreeSet<String>();

		try {

			FileInputStream fis = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;

			while ((line = br.readLine()) != null) {
				set.add(line);

			}
			br.close();
		} catch (Exception e) {
		}
		return set;
	}

	public static <String> Set<String> difference(Set<String> setA,
			Set<String> setB) {
		Set<String> tmp = new TreeSet<String>(setA);
		tmp.removeAll(setB);
		return tmp;
	}

	public static <String> Set<String> union(Set<String> setA, Set<String> setB) {
		Set<String> tmp = new TreeSet<String>(setA);
		tmp.addAll(setB);
		return tmp;
	}
	public static void arff(File f, String s) throws Exception{
		File[] listOfFiles = f.listFiles();
		PrintWriter out1 = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/new.arff", true));
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
