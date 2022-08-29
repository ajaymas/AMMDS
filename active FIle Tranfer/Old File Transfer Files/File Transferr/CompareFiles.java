package demo_cn;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CompareFiles {
	
	public String getMisMatchString(File file1, File file2) throws IOException{
		
		FileInputStream fis1=new FileInputStream(file1);
		FileOutputStream fos=new FileOutputStream("C:/ACN/similar.txt");
		
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fos));
		BufferedReader bfr1=new BufferedReader(new InputStreamReader(fis1));
	
		
		String s1=null;
		String s3=null;
		String s4="";
		boolean flag=false;
		
		
		s1=bfr1.readLine();
		if(s1!=null)
			s1=s1.trim().replaceAll("\\s+", "\t");
		
		 while(s1!=null){
			
			flag=false;
			
			FileInputStream fis2=new FileInputStream(file2);
			BufferedReader bfr2=new BufferedReader(new InputStreamReader(fis2));
			
			String s2=bfr2.readLine();
			if(s2!=null)
				s2=s2.trim().replaceAll("\\s+", "\t");
			
			while(s2!=null){
				
				if(s1.equalsIgnoreCase(s2)){
					flag=true;
					s2=null;
					if(s4==null)
						s4="";
					s4=s4+s1+"\n";
				}
				
				else{
					s2=bfr2.readLine();
					if(s2!=null){
					s2=s2.trim().replaceAll("\\s+", "\t");
					}	
				}
			}
			
			if(flag==false){
				if(s3==null){
					s3="";
				}
				
				s3=s3+s1+"\n";
			}
			s1=bfr1.readLine();
			if(s1!=null){
			s1=s1.trim().replaceAll("\\s+", "\t");
			}
		}
		bw.write(s4);
		bw.flush();
		bw.close();
		
		return s3;
	}
	
	
	
	public static void main(String args[]) throws IOException{
		
		}

}
