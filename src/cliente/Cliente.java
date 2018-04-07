package cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import model.Message;
import model.ServiceConstants;
import model.ServicoBackupInfo;
import util.Util;

public class Cliente {
	
	private static final int BUFFER = 1024 * 4; 

	MulticastSocket socket;
	final int portaEnvio = 52684;
	final int portaRecebimento = 52683;
	String address = "224.0.0.1";
	
	public void realizarBackup() {
		try {
			socket = new MulticastSocket(portaRecebimento);
			identificarServidor();
		} catch (Exception e) {
			System.err.println("Erro ao realizar backup " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void identificarServidor() throws IOException, ClassNotFoundException {
        InetAddress group = InetAddress.getByName(address);
        socket.joinGroup(group);

        Message message = new Message(ServiceConstants.BUSCAR_IDENTIFICACAO, null);
        byte[] data = Util.convertMessageToData(message);
 
        socket.send(new DatagramPacket(data, data.length, group, portaEnvio));
        
        byte[] buffer = new byte[BUFFER];
        DatagramPacket pacoteRecebido = new DatagramPacket(buffer, BUFFER, group, portaRecebimento);
		socket.receive(pacoteRecebido);
		message = Util.convertDataToMessage(buffer);
		
		ServicoBackupInfo payload = (ServicoBackupInfo) message.getPayload();
		InetAddress ipServicoBackup = payload.getIp();
		int portaServicoBackup = payload.getPorta();
		
		System.out.println("Foi encontrado o servidor de backup com ip " + ipServicoBackup.getHostAddress() + " e porta " + portaServicoBackup);
	}

	

}
