package demo_cn;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class promptTest {
	static String str;
	public promptTest(String command) {
    str=command;
try
{
	//System.out.println("In try block");
    Process process = Runtime.getRuntime().exec(str);
    Scanner kb = new Scanner(process.getInputStream());
    String data = null;
    data=kb.nextLine();
    while(kb.hasNext()){
    	data += kb.nextLine();  
    	data+="\n";
   }
    String filename=null;
    filename="C:/ACN/"+str+".txt";
    File file=new File(filename);
    FileWriter fw=new FileWriter(file);
    BufferedWriter bw=new BufferedWriter(fw);
    bw.write(data);
    bw.close();
    
} catch (IOException e)
{
    e.printStackTrace();
}

	}

	public static void main(String args[]){
		promptTest p=new promptTest(str);
		
		
	}
	
}



