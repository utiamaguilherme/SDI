import argparse
import contextlib
import datetime
import os
import six
import sys
import time
import unicodedata
import socket
import dropbox
import sys
from dropbox.files import WriteMode
from threading import Thread

#TCP Login informations
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_address = ('localhost', 8888)
sock.connect(server_address)



acess_token = 'alo'
dbx = dropbox.Dropbox(acess_token)
msg=""

def leitura():
	linha = 0
	while(True):
		cont = 0
		dbx.files_download_to_file(inp, '/client/'+ inp)
		arq = open(inp, 'r')
		for line in arq:
			cont += 1
			if(cont > linha):
				print(line)
				linha += 1
		if(msg == name+': /quit'):
			return;
		time.sleep(0.1)

def escrita():
	global msg
	while(msg != name+': /quit'):
		msg = input()
		msg = name+': '+ msg
		dbx.files_download_to_file(output, '/client/'+ output)
		with open(output, 'a') as arq:
			arq.write(msg)
			arq.write('\n')
		dbx.files_upload(open(output).read().encode(),'/client/' + output, mode=WriteMode('overwrite'))

name = raw_input("Nome: ")

while True:
	try:
		sock.sendall(name)
		print("sendall worked")
		data = sock.recv(16)
		print("rcv worked"+ data + " <<")
		if(data == 'invalid'):
			name = input('invalid name, choose another one: ')
			continue
		else:
			num = int(data)
			print("num: " + num)
	finally:
		sock.close()
		break



output = 'f-saida_'+name+'.txt'
inp = 'f-ent_'+name+'.txt'

dbx.files_upload(''.encode(), '/client/' + output, mode=WriteMode('overwrite'))
dbx.files_upload(''.encode(), '/client/' + inp, mode=WriteMode('overwrite'))
dbx.files_download_to_file(output, '/client/'+ output)
dbx.files_download_to_file(inp, '/client/'+ inp)

leitura_ = Thread(target=leitura)
leitura_.start()
escrita()
