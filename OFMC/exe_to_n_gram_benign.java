import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class exe_to_n_gram_benign {
	public static void main(String args[]) throws Exception {
		int n = 4;
		String s;
		Process p;

		File folder = new File(
				"/home/envy/Desktop/Rahul-Programs/benign_exe_files");
		/*
		 * if(!folder.exists()){ return null; }
		 */
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
		// /home/envy/Desktop/Desktop/Rahul-Programs/benign_exe_files/executable.144.exe
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
		File f2 = new File(
				"/home/envy/Desktop/Rahul-Programs/benign_n_gram");
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
		File dir3 = new File(
				"/home/envy/Desktop/Rahul-Programs/benign_n_gram");

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
				"/home/envy/Desktop/Rahul-Programs/benign_final.txt"));
		for (String str : set) {
			out.write(str + "\n");
		}
		out.close();
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

}
