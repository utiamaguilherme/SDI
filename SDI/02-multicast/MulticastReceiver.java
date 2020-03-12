import java.io.*;
import java.net.*;

public class MulticastReceiver {
  public static void main(String[] args) {
    MulticastSocket socket = null;
    DatagramPacket inPacket = null;
    byte[] inBuf = new byte[256];
    String ip = "224.0.0.3";
    try {
      //Prepare to join multicast group
      socket = new MulticastSocket(8524);
      //InetAddress address = InetAddress.getByName("224.0.0.1");      
      InetAddress address = InetAddress.getByName(ip);

      socket.joinGroup(address);
 
      while (true) {
        inPacket = new DatagramPacket(inBuf, inBuf.length);
        socket.receive(inPacket);
        String msg = new String(inBuf, 0, inPacket.getLength());
        System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
      }
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
  }
}
