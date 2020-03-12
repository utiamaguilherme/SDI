import SOAPpy
server = SOAPpy.SOAPProxy("http://localhost:6666/")
print server.hello()
