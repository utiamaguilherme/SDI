/** ChatStub.java **/
import java.rmi.*;

public interface Idl extends Remote {
	public void addMsg(int codUsuario, String msg) throws RemoteException;
	public String recMsg(int codUltMsg, int codPrimMsg) throws RemoteException;
	public int addClient(String novoCliente) throws RemoteException;
	public void	clientExit(int codUsuario) throws RemoteException;
	public int retCodUser(String name) throws RemoteException;
	public int qtdmsg() throws RemoteException;
	public int qtdUsers() throws RemoteException;
}
