import os
from collections import defaultdict
import time
import operator
import math

start_time = time.time()

path = "/home/itdept/Desktop/NGL/"
os.chdir(path)
#   <<<<----------------------    Global Data Structure   ----------------------->>>>>>

global_term = set() #contains All term
benign_term = set()  #contains benign term
malware_term = set()  #contains malware term
benign_term_cnt = defaultdict(int)     #no of benign docs contains term
malware_term_cnt = defaultdict(int)    #no of malware docs contains term

#   <<<<----------------------    Global Data Structure  section ends here ----------------------->>>>>>

#   <<<<------Method to check term in respective dir and retrieves  count from files  ----->>>>>>

def search_term(term_loc,path_sub):
	count=0	
	if path_sub == '4gram_benign_n_grams':		
		if term_loc in benign_term:
			count = benign_term_cnt[term_loc]
	else:		
		if term_loc in malware_term:
			count = malware_term_cnt[term_loc]
	
	return count

#   <<<<----------------------   Pre-Processing    ----------------------->>>>>>

def add_global_term():
	temp = ['4gram_benign_n_grams','4gram_malware_n_grams']
	benign_tmp = set()
	malware_tmp = set()
	for tmp in temp:
		os.chdir(path+tmp)
		for doc in os.listdir(path+tmp):
			benign_tmp.clear()
			malware_tmp.clear()
			for line in open(doc):
				if tmp == '4gram_benign_n_grams':					
					if line not in benign_tmp :
						benign_term_cnt[line]+=1
						
					benign_term.add(line)
					benign_tmp.add(line)

				else:
					malware_term.add(line)
					if line not in malware_tmp:
						malware_term_cnt[line]+=1
					malware_tmp.add(line)
			
				#global_term.add(line)
	if not os.path.exists("/home/itdept/Desktop/NGL/Result/"):
		os.makedirs("/home/itdept/Desktop/NGL/Result/")
		os.chdir("/home/itdept/Desktop/NGL/Result/")
	else:
		os.chdir("/home/itdept/Desktop/NGL/Result/")

#   <<<<--------------    MAIN Starts Here    -------------------->>>>>

benign_doc_cnt = len(os.listdir(path+'4gram_benign_n_grams'))
malware_doc_cnt = len(os.listdir(path+'4gram_malware_n_grams'))
print "No of Docs in Benign Dir->",str(benign_doc_cnt) 
print "No of Docs in Malware Dir->",str(malware_doc_cnt)
print "\n----------------------------------------------\n"
add_global_term()  #Pre_Processing Done here  Method call

#print "Total unique count of terms->",len(global_term)
print "Total unique Benign count of terms->",len(benign_term)
print "Total unique Malware count of terms->",len(malware_term)
print "\n---------------------------------------------------\n"


#   <<<<<<--------   For Sorting in reverse Order   ----------------->>>>>>

#temp_1 = list(sorted(benign_term_cnt.items(), key=operator.itemgetter(1) ,reverse = True))
#temp_2 = list(sorted(malware_term_cnt.items(), key=operator.itemgetter(1) ,reverse = True))

#  <<<<<<----------- Respective term_counts are written down in file   --------->>>>>>>

nc = 1
while os.path.isfile("freq_benign"+str(nc)+".txt"):
	os.remove("freq_benign"+str(nc)+".txt")
	nc += 1
nc = 1
while os.path.isfile("freq_malware"+str(nc)+".txt"):
	os.remove("freq_malware"+str(nc)+".txt")
	nc += 1
nc = 1
while os.path.isfile("out_OR_benign"+str(nc)+".txt"):
	os.remove("out_OR_benign"+str(nc)+".txt")
	nc += 1
nc = 1
while os.path.isfile("out_OR_malware"+str(nc)+".txt"):
	os.remove("out_OR_malware"+str(nc)+".txt")
	nc += 1

nc = 1
while os.path.isfile("out_OR_benign_sorted"+str(nc)+".txt"):
	os.remove("out_OR_benign_sorted"+str(nc)+".txt")
	nc += 1
nc = 1
while os.path.isfile("out_OR_malware_sorted"+str(nc)+".txt"):
	os.remove("out_OR_malware_sorted"+str(nc)+".txt")
	nc += 1

line_break_cnt = 4000000
'''
nc = 2
ben_len = len(benign_term)
cnt_ben = ben_len/line_break_cnt+1
tmp_cnt = 0
g=open("freq_benign1.txt",'w')
for item in temp_1:
	tmp_cnt += 1
	if tmp_cnt > line_break_cnt and cnt_ben >= 1:
		tmp_cnt = 1
		cnt_ben -= 1
		g.close()
		g = open("freq_benign"+str(nc)+".txt",'w')
		nc+=1
	g.write(str(item[0].rstrip("\n"))+'\t'+str(item[1])+'\n')
g.close()

g=open("freq_malware1.txt",'w')
nc = 2
mal_len = len(malware_term)
cnt_mal = mal_len/line_break_cnt+1
tmp_cnt = 0
for item in temp_2:
	tmp_cnt += 1
	if tmp_cnt > line_break_cnt and cnt_mal >= 1:
		tmp_cnt = 1
		cnt_mal -= 1
		g.close()
		g = open("freq_malware"+str(nc)+".txt",'w')
		nc+=1
	g.write(str(item[0].rstrip("\n"))+'\t'+str(item[1])+'\n')
g.close()
'''
g = open("out_OR_benign1.txt",'w')


nc = 2
benign_term = list(benign_term)
ben_len = len(benign_term)
cnt_ben = ben_len/line_break_cnt+1
tmp_cnt = 0

print '\n------------Benign For Loop------------------\n'
#for term in benign_term:
for i in range(ben_len):
	NfCk = 0;Nf_Ck = 0
	tmp_cnt +=1
	NfCk = search_term(benign_term[i],'4gram_benign_n_grams')	
	Nf_Ck = search_term(benign_term[i] , '4gram_malware_n_grams')
	tmp1 = float(NfCk)/benign_doc_cnt
	tmp2 = float(Nf_Ck)/malware_doc_cnt
	a1 = tmp1*(1-tmp2)
	b1 = tmp2*(1-tmp1)
	if a1 > 0 and b1 > 0:
		OR = math.log(a1) - math.log(b1)
		OR = round(OR , 4)
	elif a1 == 0 and b1 > 0:
		OR = math.log(b1)
		OR = -(round(OR , 4))
	elif b1 == 0 and a1 > 0:
		OR = math.log(a1)
		OR = round(OR , 4)
	else:  # a1=0 and b1=0
		OR = 0
		#OR = 'Undefined'+str(tmp1)+'  '+str(tmp2)+' '+str(a1)+' '+str(b1)
	
	if tmp_cnt > line_break_cnt and cnt_ben >= 1:
		tmp_cnt = 1
		cnt_ben -= 1
		g.close()
		g = open("out_OR_benign"+str(nc)+".txt",'w')
		nc+=1
	g.write(benign_term[i].rstrip('\n') + '\t' + str(OR) + '\n')

g.close()

h = open("out_OR_malware1.txt",'w')
nc = 2
mal_len = len(malware_term)
cnt_mal = mal_len/line_break_cnt+1
tmp_cnt = 0

for term in malware_term:
	NfCk = 0;Nf_Ck = 0
	tmp_cnt +=1
	NfCk = search_term(term,'4gram_malware_n_grams')	
	Nf_Ck = search_term(term , '4gram_benign_n_grams')
	tmp1 = float(NfCk)/malware_doc_cnt
	tmp2 = float(Nf_Ck)/benign_doc_cnt
	a1 = tmp1*(1-tmp2)
	b1 = tmp2*(1-tmp1)
	if a1 > 0 and b1 > 0:
		OR = math.log(a1) - math.log(b1)
		OR = round(OR , 4)
	elif a1 == 0 and b1 > 0:
		OR = math.log(b1)
		OR = -(round(OR , 4))
	elif b1 == 0 and a1 > 0:
		OR = math.log(a1)
		OR = round(OR , 4)
	else:  # a1=0 and b1=0
		OR = 0

	if tmp_cnt > line_break_cnt and cnt_mal >= 1:
		tmp_cnt = 1
		cnt_mal -= 1
		h.close()
		h = open("out_OR_malware"+str(nc)+".txt",'w')
		nc+=1
	h.write(term.rstrip('\n') + '\t' + str(OR) + '\n')

h.close()



nc = 1
while os.path.isfile("out_OR_benign"+str(nc)+".txt"):
	lines = open("out_OR_benign"+str(nc)+".txt").readlines()
	with open('out_OR_benign_sorted'+str(nc)+'.txt', 'w') as fout:
		lines = sorted(lines, key=lambda x:x.split('\t')[1],reverse=True)
		for line in lines:
			fout.write(line)
	fout.close()
	nc+=1
nc = 1
while os.path.isfile("out_OR_malware"+str(nc)+".txt"):
	lines = open("out_OR_malware"+str(nc)+".txt").readlines()
	with open('out_OR_malware_sorted'+str(nc)+'.txt', 'w') as fout:
		lines = sorted(lines, key=lambda x:x.split('\t')[1],reverse=True)
		for line in lines:
			fout.write(line)
	fout.close()
	nc+=1



print("--- %s seconds ---" % (time.time() - start_time))
