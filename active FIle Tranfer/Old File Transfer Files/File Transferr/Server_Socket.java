


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_Socket {

  public final static int SOCKET_PORT = 5555;  
  
  
  public static void main (String [] args ) throws IOException, ClassNotFoundException {
    FileInputStream file_ipstream = null;
    BufferedInputStream buf_ipstream = null;
    OutputStream op_stream = null;
    ServerSocket server_socket = null;
    Socket socket = null;
    boolean flag=false;
    try {
    	server_socket = new ServerSocket(SOCKET_PORT);
      while (true) {
        System.out.println("\n"+"Waiting for connection...");
        try {
          socket = server_socket.accept();
          System.out.println("Accepted connection : " + socket);
          
          InputStream ip_stream = socket.getInputStream();
           InputStreamReader ip_streader = new InputStreamReader(ip_stream);
          BufferedReader buf_reader = new BufferedReader(ip_streader);
          String filename = buf_reader.readLine();
          System.out.println("Message(FileName) received from client is "+filename);
         
          
          // send file
         
         
          String dir="C:/ACN/";
          String path=dir.concat(filename);
          System.out.println(path);
          
          File myFile = new File (path);
          flag=myFile.exists();
          if (flag==true)
          {
          System.out.println("File Transfer Started.");
          byte [] mybytearray  = new byte [(int)myFile.length()];
          file_ipstream = new FileInputStream(myFile);
          buf_ipstream = new BufferedInputStream(file_ipstream);
          buf_ipstream.read(mybytearray,0,mybytearray.length);
          op_stream = socket.getOutputStream();
          System.out.println("Sending " + filename + "(" + mybytearray.length + " bytes)");
          
          
          
          op_stream.write(mybytearray,0,mybytearray.length);
          
        
          op_stream.flush();
          
          System.out.println("File Transfer Completed.");
          String t_time = buf_reader.readLine();
	
          System.out.print(t_time);

          ip_stream.close();
          
          
         
        }
          
          else
          {
        	  String command="";
        	  System.out.println("name: "+filename);
        	  if (!(filename.equalsIgnoreCase("ping") || filename.equalsIgnoreCase("netstat")
        			  	|| filename.equalsIgnoreCase("arp -a") || filename.equalsIgnoreCase("tracert")))
        		  	command="ping";
        	  
        	  else
        	  		command=filename;
             
              
              promptTest prompt=new promptTest(command);
        	  
              System.out.println("File Transfer Started.");
              
              String filepath="C:/ACN/"+command+".txt";
              File myFile1 = new File (filepath);
        	  byte [] mybytearray  = new byte [(int)myFile1.length()];
        	  file_ipstream = new FileInputStream(myFile1);
        	  buf_ipstream = new BufferedInputStream(file_ipstream);
        	  buf_ipstream.read(mybytearray,0,mybytearray.length);
        	  op_stream = socket.getOutputStream();
              System.out.println("Sending " + filename + "(" + mybytearray.length + " bytes)");
              
              
              op_stream.write(mybytearray,0,mybytearray.length);
              Long sent_time=System.nanoTime();
              
              op_stream.flush();
             
              
              System.out.println("File Transfer Completed.");
              String t_time = buf_reader.readLine();
    	
              System.out.print(t_time);
              
              ip_stream.close();
             
          }
          
        }
        finally {
        	
          if (buf_ipstream != null) buf_ipstream.close();
          if (op_stream != null) op_stream.close();
        }
      }
    }
    finally {
      if (server_socket != null) server_socket.close();
    }
  }
}