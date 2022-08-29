def openFile(name,size,filename):
	f=open(name,"r")
	p=open(filename+"_result.txt","w")
	for i in range(size):
		temp=f.readline()
		#temp=temp.split(" ")
		temp=temp.split()
		p.write(temp[0]+"\n")
		print temp[0]
	f.close()
	p.close()
name="4gram_ben_sorted_200files.txt"
size=300
openFile(name,size,"4gram_chisqare_Top300_benign.txt")
name="4ram_mal_sorted_200files.txt"
openFile(name,size,"4gram_chisquare_Top300_malware.txt")