/** HelloClient.java **/
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.rmi.*;
import java.rmi.registry.*;

public class HelloClient implements Runnable {
	private static Thread playRMI = new Thread(new HelloClient());
	private static BufferedReader is = null;
	private static String userName = null;
	private static int codUser = 0;
	private static int lastMsg = 0;
	private static int firstMsg = 0;
	private static String textao;
	private static Idl stub = null;
	private static String msg;
	private static boolean flag = true;
	private static int cont = 0;

	public static void main(String[] args) {
		String host = (args.length < 1) ? "localhost" : args[0];
		is = new BufferedReader(new InputStreamReader(System.in));

		try {
			// Obtém uma referência para o registro do RMI
			Registry registry = LocateRegistry.getRegistry(host, 1997);
			// System.out.println(host);
			stub = (Idl) registry.lookup("Hello");
			lastMsg = stub.qtdmsg();
			
			System.out.println("\nSeja bem-vindo ao chat RMI!");
			System.out.print("Digite seu nome: ");
			while(true) {
				//lê o nome do usuario
				userName = is.readLine().trim();
				codUser = stub.addClient(userName);
				
				if (codUser != -1)	break;

				System.err.println("Nome em uso. Digite outro: ");
			}
			flag = false;
			playRMI.start();
			while (!flag){
				msg = is.readLine().trim();
				// System.out.print("Aqui deu pau1\n"+msg);
				if (msg.equals("exit")){
					System.out.println(codUser);
					stub.clientExit(codUser);
					flag = true;
					break;
				}
				// System.out.println("deu pau!\n\n");
				stub.addMsg(codUser, msg);
			}
			playRMI.join();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Consulta do servidor de tempos em tempos (polling)
	public void run(){
		try	{
			while (!flag) {
				// System.out.print("\n\naaaaaaaaaaan\n\n\n\n\n\n");
				firstMsg = stub.qtdmsg();
				String v[] = new String[2];
				if (firstMsg > lastMsg) {
					// System.out.print("\n\nbbbbbbbbbbbn\n\n\n\n\n\n");
					textao = stub.recMsg(lastMsg, firstMsg);
					lastMsg = firstMsg;
					System.out.print(textao);
				}
				
				if(textao.contains(">>>:")){
					// cont+=1;
					FileWriter file = new FileWriter(userName + "-01"+ ".client"+codUser);
					PrintWriter write = new PrintWriter(file);
					v = textao.split(">>>: ");
					String agora = v[1].substring(0, v[1].length()-1);
					write.printf(agora);
					file.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
