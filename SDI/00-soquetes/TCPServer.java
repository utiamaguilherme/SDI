
import java.io.*;
import java.net.*;

class TCPServer {

    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(8524);
        String ip = "224.0.0.3";
        final int PORT = 8524;
        MulticastSocket socket = null;
        DatagramPacket inPacket = null;

        while (true) {
                try{
                        socket = new DatagramSocket();
                        String msg; // parei aqui

                }

                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient
                        = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient
                        = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                capitalizedSentence
                        = "TCPServer: " + clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);
                System.out.println("Deu certo");
        }
    }
}

try {
      socket = new DatagramSocket();
      long counter = 0;
      String msg;
 
      while (true) {
        msg = "This is multicast! " + counter;
        counter++;
        outBuf = msg.getBytes(); // pega a mensagem e armazena
 
        //Send to multicast IP address and port
        //InetAddress address = InetAddress.getByName("224.0.0.1");
        InetAddress address = InetAddress.getByName(ip);
        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT); // montar a mensagem
 
        socket.send(outPacket); // envia a mensagem
 
        System.out.println("Server sends : " + msg);
        try {
          Thread.sleep(500);
        } catch (InterruptedException ie) {
        }
      }
    } catch (IOException ioe) {
      System.out.println(ioe);
    }