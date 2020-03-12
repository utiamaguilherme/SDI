#!/usr/bin/env python3
import pika

#connection = pika.BlockingConnection(pika.ConnectionParameters(
#        host='localhost'))
connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost',
        credentials=pika.PlainCredentials(username="sdi", password="sdi")))
channel = connection.channel()


channel.queue_declare(queue='maphello')

def callback(ch, method, properties, body):
    print(" [x] Received %r" % body)

#channel.basic_consume(callback,
#                      queue='maphello',
#                      no_ack=True)

channel.basic_consume(queue='maphello',
                      auto_ack=True,
                      on_message_callback=callback)

print(' [*] Waiting for messages. To exit press CTRL+C')
channel.start_consuming()
