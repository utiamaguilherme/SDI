/** HelloServer.java **/
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

public class HelloServer implements Idl {
	
	private ArrayList<String> msg = new ArrayList<String>();
	private ArrayList<String> client = new ArrayList<String>();
	private static int qtdUser = 0;
	private static int sizeMsg = 0;
	
	public HelloServer() {}
	
	public static void main(String[] args) {
		try {

			HelloServer server = new HelloServer();
			Idl stub = (Idl) UnicastRemoteObject.exportObject(server, 0);

			Registry registry = LocateRegistry.createRegistry(1997);
			//Registry registry = LocateRegistry.getRegistry(9999);
			registry.bind("Hello", stub);
			System.out.println("Em funcionamento...");
		} catch (Exception erro) {
			erro.printStackTrace();
		}
	}

	public void addMsg(int id, String msgm) throws RemoteException {
		String user = client.get(id);
		msg.add(String.format("<<<" + user +">>>: " + msgm +"\n"));
		String vet[] = new String[2];
		
		try{
			FileWriter file = new FileWriter(user + "-01" + ".serv"+id);
			PrintWriter write = new PrintWriter(file);
			write.printf(msgm);
			file.close();
			
		}catch(Exception erro){
			erro.printStackTrace();
		}
	}

	public String recMsg(int idxLast, int idxFirst) throws RemoteException {
		StringBuilder msgReceive = new StringBuilder();
		if (idxLast == idxFirst)
			return null;
		// System.out.println(idxFirst + "  " + idxLast);
		for (int i = idxLast; i < idxFirst; i++){
			msgReceive.append(msg.get(i));
		}
		return msgReceive.toString();
	}

	public int addClient(String novoCliente) throws RemoteException {
		int idClient = retCodUser(novoCliente);
		// System.out.println(idClient);
		if (idClient != -1){
			return -1; //existe
		}
		client.add(novoCliente);
		msg.add(String.format("*** " + novoCliente + " entered the chat room! ***\n"));
		qtdUser++;
		return retCodUser(novoCliente);
	}

	public int retCodUser(String name){
		for (int i = 0; i < qtdUser; i++){
			if (name.equals(client.get(i)))
				return i;
		}
		return -1;
	}

	public void clientExit(int id) throws RemoteException {
		msg.add(String.format("*** " + client.get(id) + " is leaving the chat room! ***\n"));
		client.remove(id);
		qtdUser--;
	}

	

	public int qtdmsg() throws RemoteException {
		sizeMsg = msg.size();
		return sizeMsg;
	}

	public int qtdUsers() throws RemoteException{
		return qtdUser;
	}
}
