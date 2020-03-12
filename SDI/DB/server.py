#-*- coding: utf-8 -*-
#from __future__ import print_function

#import argparse
#import contextlib
#import datetime
import os
#import six
import sys
import time
#import unicodedata
#import linecache
import dropbox
import socket
from threading import Thread


#TCP Login informations
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_address = ('localhost', 8888)
sock.bind(server_address)


dbx = dropbox.Dropbox('alo')
#dbx.users_get_current_account()


class Client:
    def __init__(self, name, n):
        self.num = n
        self.name = name
        self.input = '/FP-Ent' + str(n) + '/'
        self.output = '/FP-Saida' + str(n) + '/'
        # talvez seja necessario criar as pastas acima
        self.sentfiles = []
        self.rcvnum = 0
        self.sentnum = 0
        os.mkdir('FP-Ent' + str(n))
        os.mkdir('FP-Saida'+ str(n))

    def markSent(filename):
        self.sentfiles += [filename]
        self.sentnum += 1

clients = []

def sendfile(filename1, filename2, c): # filename1 é o nome do arquivo dado pelo cliente, filename2 é o nome do arquivo a ser tranferido pro servidor
    global dbx
    dbx.files_download_to_file(c.output + filename1, '/Server/' + filename2)
    for i in clients:
        filename3 = i.name + '-' + i.rcvnum + '.client' + i.num # nome do arquivo no cliente i
        dbx.files_upload(open(c.output + filename1).read().encode(), filename3, mode=WriteMode('overwrite'))
        i.rcvnum += 1
    c.markSent(filename1)

def logins():
    global clients

    sock.listen(1)
    while True:
        connection, client_address = sock.accept()
        try:
            name = connection.recv(16)
            print("name"+name)
            if len(name) > 3:
                print("entrounoif>3")
                flag = False
                for i in clients:
                    print("entrou no for dos clients")
                    if i.name == name:
                        connection.sendall("invalid")
                        flag = True
                if not flag:
                    print("notflag")
                    num = len(clients)
                    clients += [Client(name, num)]
                    connection.sendall(str(num))
                    print("sent number " +str(num))
                if(len(clients) == 0):
                    num = 0
                    clients+=[Client(name, num)]
                    connection.sendall(str(num))
                    print("sent0 "+str(num))
            else:
                connection.sendall("invalid")
        finally:
            connection.close()


def serverLoop():
    while True:
        for i in clients:
                files = dbx.files_list_folder(i.output)
                for file in file.entries:
                    filename1 = file.name
                    if filename1 in i.sentfiles: continue
                    sendfile(filename1, i.name + '-' + i.sentnum + '.serv', i)


thread = Thread(target = logins, args = ())
thread.start()
#thread2 = Thread(target = quit, args = ())
#thread2.start()
serverLoop()
