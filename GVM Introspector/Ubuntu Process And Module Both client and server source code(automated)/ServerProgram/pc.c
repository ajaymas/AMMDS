/*Client*/
#include<sys/socket.h>
#include<sys/types.h>
#include<netinet/in.h>
#include<unistd.h>
#include<stdlib.h>
#include<stdio.h>
#include<sys/stat.h>
#include<fcntl.h>
#include <sys/time.h>
#include <time.h>
#include <arpa/inet.h>
#include <sys/uio.h>
#include <strings.h>
#include <errno.h>
#include<time.h>

#define LENGTH 20480

int main(int argc,char *argv[])
{
	clock_t begin, end;
	double time_spent;
	
	begin = clock();

	int create_socket, cont;
  	int bufsize = 20480;
  	char *buffer = malloc(bufsize);
  	char fname[256], file[256],revbuf[LENGTH];
  	struct sockaddr_in address;
	FILE *f1;
	/* variab;es from yu.c*/
	FILE *fp;
	//char fname[40]="ccc";
    	char word[30],word1[30],line[20480],command[50],command2[50],word2[30];
	//int file = open(fname, O_CREAT|O_WRONLY, S_IRWXU);
	/* variab;es from yu.c*/
  	if ((create_socket = socket(AF_INET,SOCK_STREAM,0)) > 0)
    		printf("The Socket was created\n");
  		
	address.sin_family = AF_INET;
  	address.sin_port = htons(15000);
  	inet_pton(AF_INET,argv[1],&address.sin_addr);

    		printf("The connection was accepted with the server %s...\n", argv[1]);
  
	printf("Enter The Filename to Request : "); 
  	if (connect(create_socket,(struct sockaddr *) &address, sizeof(address)) == 0)
	scanf("%s",fname);
  
	send(create_socket, fname, sizeof(fname), 0);

  	printf("Request Accepted... Receiving File...\n\n");

	//printf("Enter The Filename to receive the data transfered from the server: "); 
	//scanf("%s",file);

  	//printf("The contents of file are transfered to the %s\n\n",fname);
	
	//f1 = freopen(file, "w+", stdout);

  	/*while((cont=recv(create_socket, buffer, bufsize, 0))>0) 
	{
    		write(1, buffer, cont);	
  	}*/

	FILE *fr = fopen("DOMU-PS.txt", "a");
	if(fr == NULL)
		printf("File %s Cannot be opened.\n", fname);
	else
	{
		bzero(revbuf, LENGTH); 
		int fr_block_sz = 0;
	    	while((fr_block_sz = recv(create_socket, revbuf, LENGTH, 0)) > 0)
	    	{
			int write_sz = fwrite(revbuf, sizeof(char), fr_block_sz, fr);
	        	if(write_sz < fr_block_sz)
			{
	            		error("File write failed.\n");
	        	}
			bzero(revbuf, LENGTH);
		}
	    	printf("Ok received from server!\n");
	    	fclose(fr);
	}
	
	int fill = open("DOM0-PS.txt", O_CREAT|O_WRONLY, S_IRWXU);
	fp = fopen("db.txt", "r");
    	if (fp == NULL) 
	{
        	printf("Error\n");
    	} 
	else 
	{
        	while (fgets(line, sizeof line, fp) != NULL) 
		{
            		word[0] = '\0';
			word1[0] = '\0';
			word2[0] = '\0';

    			sscanf(line, "%s %s %s", word, word1, word2);
			if(strcmp(fname,word)==0)
			{
				strcpy(command,word1);
				strcpy(command2," ");
				strcat(command, command2);
				strcat(command,word2);
				printf ("Executing command %s\n",command);
    				dup2(fill,1);
				system(command);
				break;
			}
    			//printf("%s\t%s\n", word,word1);
        	}
    	}
	
  	//printf("\nEOF\n");
 	fclose(fp);
	
	if(strcmp(fname,"aaa")==0)
	{
		char* com = "./SR";
    		system(com);
	}

	if(strcmp(fname,"bbb")==0)
	{
		char* comm = "./nmod";
    		system(comm);	
	}
	
  	return close(create_socket);

	end = clock();
	
	time_spent = (double)(end - begin) / CLOCKS_PER_SEC;
	
	printf("\nTime taken is: %f", time_spent);

	return 0;

}



