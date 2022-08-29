/* Server */

#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<sys/stat.h>
#include<unistd.h>
#include<stdlib.h>
#include<stdio.h>
#include<fcntl.h>
#include <sys/time.h>
#include <time.h>
#include <arpa/inet.h>
#include <sys/uio.h>
#include <strings.h>

#define LENGTH 20480

int main()
{
	int cont,create_socket,new_socket,addrlen,fd,ch,i;
  	int bufsize = 20480;
	int fs_block_sz; 
  	char *buffer = malloc(bufsize);
  	char fname[256];
	/* variab;es from yu.c*/
	FILE *fp;
	//char fname[40]="ddd";
    	char word[30],word1[30],line[20480],command[50],sdbuf[LENGTH],word2[30],command2[50];
	//int file = open("DOMU1.txt", O_CREAT|O_WRONLY, S_IRWXU);
	/* variab;es from yu.c*/
  	struct sockaddr_in address;

  	if ((create_socket = socket(AF_INET,SOCK_STREAM,0)) > 0)
    		printf("The socket was created\n");

  	address.sin_family = AF_INET;
  	address.sin_addr.s_addr = INADDR_ANY;
  	address.sin_port = htons(15000);

  	if (bind(create_socket,(struct sockaddr *)&address,sizeof(address)) == 0)
    		printf("Binding Socket\n");
  
	listen(create_socket,3);
  	addrlen = sizeof(struct sockaddr_in);
  	new_socket = accept(create_socket,(struct sockaddr *)&address,&addrlen);

  	if (new_socket > 0)
     		printf("The Client %s is Connected...\n", inet_ntoa(address.sin_addr));
  
	recv(new_socket,fname, 255,0);

	printf("A request for filename %s Received..\n", fname);

	int file = open(fname, O_CREAT|O_WRONLY, S_IRWXU);
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
			word1[0]='\0';
			word2[0]='\0';
    			sscanf(line, "%s %s %s", word, word1, word2);
			if(strcmp(fname,word)==0)
			{
				strcpy(command,word1);
				strcpy(command2," ");
				strcat(command, command2);
				strcat(command,word2);
				printf ("Executing command %s\n",command);
    				dup2(file,1);
				system(command);
				break;
			}
    			printf("%s\t%s\n", word,word1);
        	}
    	}

	FILE *fs = fopen(fname, "r");

	if(fs == NULL)
	{
		fprintf(stderr, "ERROR: File %s not found on server\n", fname);
		exit(1);
	}
	bzero(sdbuf, LENGTH); 
	
	while((fs_block_sz = fread(sdbuf, sizeof(char), LENGTH, fs))>0)
	{
		if(send(new_socket, sdbuf, fs_block_sz, 0) < 0)
		{
			fprintf(stderr, "ERROR: Failed to send file %s\n", fname);
		      	exit(1);
		}
		bzero(sdbuf, LENGTH);
	}

}
