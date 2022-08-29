package demo_cn;



import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

 public final static int SOCKET_PORT = 5555;      
 public final static String SERVER = "10.100.55.146";  
 

 public final static int FILE_SIZE = 60223860; 
 public static void main (String [] args ) throws IOException {
   int bytesRead;
   int current = 0;
   FileOutputStream file_opstream = null;
   BufferedOutputStream buffer_opstream = null;
   Socket socket = null;
   try {
	   socket = new Socket(SERVER, SOCKET_PORT);
	 System.out.println("Connecting...");

     // receive file
     byte [] mybytearray  = new byte [FILE_SIZE];
     InputStream input_stream = socket.getInputStream();
     System.out.println("Enter filename");
     BufferedReader buffer_reader = new BufferedReader (new InputStreamReader(System.in));
     String filename = buffer_reader.readLine();
    OutputStream op_stream = socket.getOutputStream();
     OutputStreamWriter op_writer = new OutputStreamWriter(op_stream);
     BufferedWriter bw = new BufferedWriter(op_writer);
     String sendMessage = filename + "\n";
     //System.out.println(sendMessage);
     bw.write(sendMessage);
     bw.flush();
     System.out.println("Message sent to the server : "+sendMessage);
     String folder="c:/ACN/";
     
     String s1=folder.concat(filename);
     file_opstream = new FileOutputStream(s1);
     buffer_opstream = new BufferedOutputStream(file_opstream);
    
     
     Long receive_time;
     receive_time=System.nanoTime();
     
     bytesRead = input_stream.read(mybytearray,0,mybytearray.length);
     Long time=System.nanoTime();
     Long Transfer_time=time-receive_time;
     String t_time=Transfer_time+"\n";
     
     
     
     bw.write("Transfer time:"+t_time+"\n");
     bw.flush();

    
     current = bytesRead;
     do {
    	    
         bytesRead =input_stream.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      buffer_opstream.write(mybytearray, 0 , current);
      buffer_opstream.flush();
    
     
    
    
     System.out.println("File " + s1
         + " downloaded (" + current + " bytes read)");
     
     
    
    
    
        File file1=new File("C:/ACN/"+filename);
        if (!(filename.equalsIgnoreCase("ping") || filename.equalsIgnoreCase("netstat")
			  	|| filename.equalsIgnoreCase("arp -a") || filename.equalsIgnoreCase("tracert")))
        			filename="ping";
        File file2=new File("C:/ACN/stored/"+filename+".txt");
 		File file3=new File("C:/ACN/difference.txt");
 		
 		CompareFiles compareTwoFiles=new CompareFiles();
 		FileOutputStream fos=new FileOutputStream(file3);
 		BufferedWriter bw1=new BufferedWriter(new OutputStreamWriter(fos));
 		
 		String s=compareTwoFiles.getMisMatchString(file1,file2);
 		String s2=compareTwoFiles.getMisMatchString(file2,file1);
 		
 		String mismatch="";
 		if(s!=null)
 		{
 			if(s2!=null)
 			{
 				mismatch=s+s2;
 				System.out.println("Mismatch is found");
 			}else{
 				mismatch=s;
 				System.out.println("Mismatch is found");
 			}
 		}
 		else{
 			if(s2!=null){
 				mismatch=s2;
 				System.out.println("Mismatch is found");
 			}else
 				System.out.println("No mismatch Strings");
 				
 		}
 		
 		bw1.write(mismatch);
 		bw1.flush();
 		bw1.close();
 	
   }
   finally {
     if (file_opstream != null) file_opstream.close();
     if (buffer_opstream != null) buffer_opstream.close();
     if (socket != null) socket.close();
   }
 }

}
