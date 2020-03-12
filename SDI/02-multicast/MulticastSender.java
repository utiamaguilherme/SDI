import java.io.*;
import java.net.*;

public class MulticastSender {
  public static void main(String[] args) {
    DatagramSocket socket = null;
    DatagramPacket outPacket = null;
    byte[] outBuf;
    final int PORT = 8524;
    String ip = "224.0.0.3";
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
  }
}
