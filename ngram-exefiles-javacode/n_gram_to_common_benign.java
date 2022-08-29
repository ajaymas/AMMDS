import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class n_gram_to_common_benign {
	public static void main(String args[]) throws Exception {
		int flag = 0;
		Set<String> set1 = new TreeSet<String>();
		PrintWriter out = new PrintWriter(new FileWriter(
				"/home/envy/Desktop/Rahul-Programs/benign_log.txt"));
		File folder = new File(
				"/home/envy/Desktop/Rahul-Programs/benign_n_gram");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				String fname = listOfFiles[i].getName();
				try {
					File fin = new File(folder.getCanonicalPath()
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
	}

}
