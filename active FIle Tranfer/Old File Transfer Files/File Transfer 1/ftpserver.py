import os
import socket
import threading



def initiate(): 
	w=raw_input(" ")
	if w[:]=='Y' or w[:]=='y':
               
		ip=raw_input("Enter Client Which U want to connect->")
		filename="dns.txt"
		f= open(filename)  
		a=f.read()
		attr=a.split(" ")
		flag=0
		for i in range(0,10,2):
			if attr[i]==ip :
				host=str(ip) #server ip address 
				port=str(attr[i+1])
				port=int(port[:4]) #server port
				flag=1
				m=socket.socket() #socket object will be created
				m.connect((host,port)) #bind the ipaddress and port
				q = threading.Thread(target=client, args=("RetrThread",m))
				q.start()			
				#client() #call the client function
				break
		if flag==0:
			print "Invalid IP"
	else:
		initiate()



def client(nm,m):#client function definition
                dir1='/home/pgadmin12/Recv/' #by Default Directory
                filenm = raw_input("Enter Filename which U Want To Download? -> ") #here one end request the filename which it concern to access
                if filenm != 'q':  #q for quit mean not want to download any file
                    m.send(filenm) #filename will be sent to other end as desired
                    samp = m.recv(1024) #here we get feedback message from other end  like "Exists" and size of file if not error 
                    m.send("OK")
                    fsize = long(samp) #this will contain filesize as we use long which is dynamic means any no can be represented 
                         
                         
                    if not os.path.exists(dir1):os.makedirs(dir1) #if directory not exixts then it will create
                    os.chdir(dir1) #it will point to newly created directory
                    f = open(filenm, 'wb') #then it will open  file in write mode
                    samp = m.recv(1024)# here first 1024 bytes are going to receive
                    totRecv = len(samp) #here length will be calculated
                    f.write(samp) #Now the incoming 1024 bytes will be written to desired location
                    while totRecv < fsize: #if total recieve file size is less than actual size in that case loop will continue
                        samp = m.recv(1024) #evry time 1024 bytes will be recieved
                        totRecv += len(samp) #total lenght which is recieved will be updated here
                        f.write(samp) #then recieved dat will be write
                    print "Transfer Completed Successfully!" # display message
                    os.chdir('/home/pgadmin12/')
                    f.close() #file will be closed
	
                                    
                m.close();#connection will be closed
		
                initiate()







def Main():
        host='10.100.55.171'; #local ip sddress
        port=5002;    #local port                                                   
        s=socket.socket();     #socket object will be created                                                   
        s.bind((host,port));   #bind the ipaddress and port                                                   
        s.listen(5);#It means that 5 connections are kept waiting if the server is busy and if a 6th socket trys to connect then the connection is refused only available to perticular ip address which was bind previously
        #print("SERVER_connection Started:");
	p = threading.Thread(target=initiate)
	p.start()
        while True: #infinite loop
                a, addr = s.accept();  #here if connection established then client ip and connection object  are returned
                print "client connedted to the ip:<" + str(addr) + ">" #will display client which have successful connection
                t = threading.Thread(target=starter, args=("RetrThread", a)) #here thread will start each will contain individual connection host
                t.start() #here thread starts
        s.close()    #connection closed    
def starter(nm,s): #starter function defnition

                     dir1='/home/pgadmin12/Send/'   #by Default Directory
                
                     if not os.path.exists(dir1):os.makedirs(dir1) #if directory not exixts then it will create
                
                     os.chdir(dir1)   #it will point to newly created directory
   
                     filenm = s.recv(1024) #it will recieve filename which server wants to take
                     if os.path.isfile(filenm): #if the requested file is available 
                        s.send(str(os.path.getsize(filenm))) #it will send size of file in bytes
                        uresp=s.recv(1024)
			print uresp
                        if uresp[:2] == 'OK':                      
		                with open(filenm, 'rb') as f: #if above case successful then it will open requested file in read mode
		                        bToSend = f.read(1024) #it will read 1024 bytes from starting of the file
		                        s.send(bToSend) #here the 1024 bytes are send to other side
		                        while bToSend != "": #if more bytes are still available to send
		                            bToSend = f.read(1024) # will read again 1024 bytes
		                            s.send(bToSend)  #and send it again
					os.chdir('/home/pgadmin12/')
				
                     else:
                        
			fo = open(filenm, "wb")
			fo.write( "File Does Not Exists.But We made sample File for You");
			fo.close()
			
			s.send(str(os.path.getsize(filenm))) #it will send size of file in bytes
                        uresp=s.recv(1024)
                        if uresp[:2] == 'OK': 
                        
		                with open(filenm, 'rb') as f: #if above case successful then it will open requested file in read mode
				        bToSend = f.read() #it will read 1024 bytes from starting of the file
				        s.send(bToSend) #here the 1024 bytes are send to other side
				        while bToSend != "": #if more bytes are still available to send
				            bToSend = f.read(1024) # will read again 1024 bytes
				            s.send(bToSend)  #and send it again
					f.close()
					os.chdir('/home/pgadmin12/')

	             s.close() # here connection will be closed
                     
                


            
Main()

                                                       
