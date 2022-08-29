from scipy.stats import norm
from collections import defaultdict
import os.path
import math

lne=math.log(2.71)
dict1=defaultdict(int)
dict2=defaultdict(int)
dict3=defaultdict(int)
dict4=defaultdict(int)
count=0
count2=0
os.chdir("/home/envy/Desktop/3grams/benign_n_grams")
for file in os.listdir("/home/envy/Desktop/3grams/benign_n_grams"):
	with open(file,'r') as f:
		for line in f:
			line = line.strip('\n')
			if line not in dict1:
				dict1[line]=1
				dict2[line]=0
			else:
				dict1[line]=dict1[line]+1
	count=count+1
	f.close()
os.chdir("/home/envy/Desktop/3grams/malware_n_grams")
for file in os.listdir("/home/envy/Desktop/3grams/malware_n_grams"):
	with open(file,'r') as f:
		for line in f:
			line = line.strip('\n')
			if line not in dict2:
				dict2[line]=1
				dict1[line]=0
			else:
				dict2[line]=dict2[line]+1
	count2=count2+1
	f.close()
N=count+count2
qw=len(dict1)
w=[0]*qw
i=0
os.chdir("/home/envy/Desktop/3grams/Mutual-Information")
f10=open('frequency_table','w')
#f11=open('values','w')
for key in dict1:
	
	
	nfk=dict1[key]
	y=count-dict1[key]
	nf2k=y
	
	nfk2=dict2[key]
	#print dict2[key]
	z=count2-dict2[key]
	nf2k2=z
	nf=dict1[key]+dict2[key]
	nf2=y+z	
	#print y
	st=str(key)+'::'+str(dict1[key])+'::'+str(y)+'::'+str(dict2[key])+'::'+str(z)+str('\n')
	f10.write(st)
	n1=count
	n2=count2
	if nf==0 or nf2==0 or n1==0 or n2==0 or nfk==0 or nfk2==0 or nf2k==0 or nf2k2==0:
		m1=0.0
	else:
		m1=(nfk/N)*((math.log((N*nfk)/nf*n1))/lne)+(nfk2/N)*((math.log((N*nfk2)/nf*n2))/lne)+(nf2k/N)*((math.log((N*nf2k)/nf2*n1))/lne)+(nf2k2/N)*((math.log((N*nf2k2)/nf2*n2))/lne)
	n1=count2
	n2=count
	nfk=dict2[key]
	nf2k=z
	nf2k2=y
	nfk2=dict1[key]
	if nf==0 or nf2==0 or n1==0 or n2==0 or nfk==0 or nfk2==0 or nf2k==0 or nf2k2==0:
		m2=0.0
	else:
		m2=(nfk/N)*((math.log((N*nfk)/nf*n1))/lne)+(nfk2/N)*((math.log((N*nfk2)/nf*n2))/lne)+(nf2k/N)*((math.log((N*nf2k)/nf2*n1))/lne)+(nf2k2/N)*((math.log((N*nf2k2)/nf2*n2))/lne)
	w[i]=(m1*0.5)+(m2*0.5)

	#st2=str(key)+'='+str(w[i])+str('\n')
	dict3[key]=i
	i=i+1
	#f11.write(st2)
	

f10.close()
os.chdir("/home/envy/Desktop/3grams/Mutual-Information")
f2=open('ben_gram','w')
f3=open('ben_gram2','w')
f6=open('ben_frequency','w')
f8=open('ben_sorted','w')
os.chdir("/home/envy/Desktop/3grams/benign_n_grams")
for file in os.listdir("/home/envy/Desktop/3grams/benign_n_grams"):
	with open(file,'r') as f:
		for line in f:
			line = line.strip('\n')
			if line not in dict4:
				y=dict3[line]
				z=dict1[line]
				s1=str(line)+'='+str(w[y])+('\n')
				s2=str(line)+'\t'+str(z)+('\n')
				if w[y]==0:
					f2.write(s1)
				else:
					f3.write(s1)
				f6.write(s2)
				dict4[line]=w[y]

f2.close()
f3.close()
f6.close()
for we in sorted(dict4, key=dict4.get, reverse=True):
	s4=str(we)+'\t'+str(dict4[we])+'\n'
	f8.write(s4)	
f8.close()
dict4=defaultdict(int)
os.chdir("/home/envy/Desktop/3grams/Mutual-Information")
f4=open('mal_ware','w')
f5=open('mal_ware2','w')
f7=open('mal_frequency','w')
f9=open('mal_sorted','w')
os.chdir("/home/envy/Desktop/3grams/malware_n_grams")
for file in os.listdir("/home/envy/Desktop/3grams/malware_n_grams"):
	with open(file,'r') as f:
		for line in f:
			line = line.strip('\n')
			if line not in dict4:
				y=dict3[line]
				z=dict2[line]
				s1=str(line)+'='+str(w[y])+('\n')
				s2=str(line)+'\t'+str(z)+('\n')
				if w[y]==0:
					f4.write(s1)
				else:
					f5.write(s1)
				f7.write(s2)
				dict4[line]=w[y]

f3.close()
f4.close()
f7.close()
for we in sorted(dict4, key=dict4.get, reverse=True):
	s4=str(we)+'\t'+str(dict4[we])+'\n'
	f9.write(s4)
f9.close()




