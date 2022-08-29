import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class N_gram {
	public static void main(String args[]) throws Exception {
		
		Set set_malware = malware_n_gram.malware();
		Set set1 = benign();
		
		
		Set<String> set2 = new TreeSet<String>();
		
		if(set1 == null){
			set2 = set_malware;
		}
		else if(set_malware == null){
			set2 = set1;
		}
		else{
		set2 = union(difference(set1, set_malware), difference(set_malware, set1));
		}
		PrintWriter out1 = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/unique_intermediate.txt"));
		for (String str : set2) {
			out1.write(str + "\n");
		}
		out1.close();

		out1 = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/new.arff"));
		out1.write("@relation grams\n");
		for (String str : set2) {
			out1.write("@attribute " + str + " {0,1}\n");
			
		}
		
		out1.write("@attribute class {Benign, Malware}\n@data\n");
		out1.close();
		if(set1==null){
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

	private static void readFile1(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);
		File f = new File(
				"/home/envy/Desktop/Rahul-Programs/benign_intermediate");
		if (!f.exists()) {
			try {
				// f.createNewFile();
				f.mkdir();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		PrintWriter out = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/benign_intermediate/"
						+ fin.getName()));
		while ((line = br.readLine()) != null) {

			String x = line.substring(9, 48);
			x = x.replaceAll(" ", "");
			out.write(x);
			// System.out.println(x);
		}

		out.close();
		br.close();
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
	public static Set<String> benign() throws Exception{
		int n = 4;
		String s;
		Process p;

		File folder = new File(
				"/home/envy/Desktop/Rahul-Programs/benign_exe_files");
		if(!folder.exists()){
			return null;
		}
		File[] listOfFiles = folder.listFiles();
		File f = new File("/home/envy/Desktop/Rahul-Programs/benign_hex");
		if (!f.exists()) {
			try {
				// f.createNewFile();
				f.mkdir();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		///home/envy/Desktop/Desktop/Rahul-Programs/benign_exe_files/executable.144.exe
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				String fname = listOfFiles[i].getName();
				try {
					String str = "xxd "
							+ "/home/envy/Desktop/Rahul-Programs/benign_exe_files/"
							+ fname;
					// System.out.println(str);
					p = Runtime.getRuntime().exec(str);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(p.getInputStream()));

					PrintWriter out = new PrintWriter(new FileWriter(
							"/home/envy/Desktop/Rahul-Programs/benign_hex/"
									+ fname.split(".exe")[0] + ".txt"));

					while ((s = br.readLine()) != null) {

						out.write(s + "\n");
					}
					try {
						p.waitFor();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// System.out.println ("exit: " + p.exitValue());
					p.destroy();

					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		listOfFiles = f.listFiles();
		File f2 = new File("/home/envy/Desktop/Rahul-Programs/benign_n_gram");
		File dir = new File("/home/envy/Desktop/Rahul-Programs/benign_hex");

		if (!f2.exists()) {
			try {
				// f.createNewFile();
				f2.mkdir();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				String fname = listOfFiles[i].getName();
				try {
					File fin = new File(dir.getCanonicalPath() + File.separator
							+ fname);
					readFile1(fin);
				} catch (Exception e) {
				}
			}

		}

		File dir2 = new File(
				"/home/envy/Desktop/Rahul-Programs/benign_intermediate");
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				String fname = listOfFiles[i].getName();
				try {
					File fin = new File(dir2.getCanonicalPath()
							+ File.separator + fname);
					FileInputStream fis = new FileInputStream(fin);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fis));
					String line = null;
					line = br.readLine();
					PrintWriter out = new PrintWriter(new FileWriter(
							"/home/envy/Desktop/Rahul-Programs/benign_n_gram/"
									+ fin.getName()));
					for (int j = 0; j < line.length() - (2 * n); j = j + 2) {

						out.write(line.substring(j, j + (2 * n)) + "\n");

					}
					out.close();
					br.close();
				} catch (Exception e) {
				}
			}
		}
		File dir3 = new File("/home/envy/Desktop/Rahul-Programs/benign_n_gram");

		Set<String> set = new TreeSet<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				String fname = listOfFiles[i].getName();
				try {
					File fin = new File(dir3.getCanonicalPath()
							+ File.separator + fname);
					FileInputStream fis = new FileInputStream(fin);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fis));

					String line = null;

					while ((line = br.readLine()) != null) {
						set.add(line);

					}
					br.close();
				} catch (Exception e) {
				}
			}

		}
		PrintWriter out = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/final.txt"));
		for (String str : set) {
			out.write(str + "\n");
		}
		out.close();

		int flag = 0;
		Set<String> set1 = new TreeSet<String>();
		out = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/benign_log.txt"));
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				String fname = listOfFiles[i].getName();
				try {
					File fin = new File(dir3.getCanonicalPath()
							+ File.separator + fname);
					FileInputStream fis = new FileInputStream(fin);

					BufferedReader br = new BufferedReader(
							new InputStreamReader(fis));

					String line = null;
					Set<String> set2 = new TreeSet<String>();

					while ((line = br.readLine()) != null) {

						if (flag == 0) {

							set1.add(line);
						}

						else {
							if (set1.contains(line)) {
								set2.add(line);
							}

						}

					}
					if (flag != 0)
						set1 = set2;
					out.write(fname + "\t" + set1.size() + "\n");
					br.close();
				} catch (Exception e) {
				}
			}
			flag = 1;
		}
		PrintWriter out1 = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/benign_common.txt"));
		for (String str : set1) {
			out.write(str + "\n");
			out1.write(str + "\n");
		}
		out.close();
		out1.close();
		return set1;
		
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