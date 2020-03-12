import SOAPpy
def hello():
    return "Hello World"
server = SOAPpy.SOAPServer(("localhost", 6666))
server.registerFunction(hello)
server.serve_forever()

