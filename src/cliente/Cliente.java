package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;

import model.BackupData;
import model.BackupDataResponse;
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
	
	private String nomeUsuario = "tiago";
	
	public void realizarBackup() {
		try {
			System.out.println("Buscando serviço de backup");
			ServicoBackupInfo payload = buscarServicoBackupInfo();
			InetAddress ipServicoBackup = payload.getIp();
			int portaServicoBackup = payload.getPorta();
			System.out.println("Foi encontrado o servidor de backup com ip " + ipServicoBackup.getHostAddress() + " e porta " + portaServicoBackup);
	    
			System.out.println("Iniciando backup...");
			realizarBackup(ipServicoBackup, portaServicoBackup);
			System.out.println("Backup realizado com sucesso!");
			socket.close();
		} catch (Exception e) {
			System.err.println("Erro ao realizar backup " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void realizarBackup(InetAddress ipServicoBackup, int portaServicoBackup) throws IOException, ClassNotFoundException {
		BackupData backupData = new BackupData();
		backupData.setNomeUsuario(nomeUsuario);
		backupData.setData("Gravando dados do usuário " + nomeUsuario + " às " + LocalDate.now().toString());
		Message message = new Message(ServiceConstants.REALIZAR_BACKUP, backupData);
 
		Socket socketTCP = new Socket(ipServicoBackup, portaServicoBackup);
		ObjectOutputStream oos = new ObjectOutputStream(socketTCP.getOutputStream());
		oos.writeObject(message);
		
		ObjectInputStream ois = new ObjectInputStream(socketTCP.getInputStream());
		Message mensagem = (Message) ois.readObject();
		BackupDataResponse response = (BackupDataResponse) mensagem.getPayload();
		System.out.println("Arquivo salvo na pasta " + response.getLocalSalvo() + " com o nome " + response.getNomeArquivo());
		socketTCP.close();
	}

	private ServicoBackupInfo buscarServicoBackupInfo() throws UnknownHostException, IOException, ClassNotFoundException {
		InetAddress group = InetAddress.getByName(address);
		socket = new MulticastSocket(portaRecebimento);
		socket.joinGroup(group);

		Message message = new Message(ServiceConstants.BUSCAR_IDENTIFICACAO, null);
		byte[] data = Util.convertMessageToData(message);
 
		socket.send(new DatagramPacket(data, data.length, group, portaEnvio));
		
		byte[] buffer = new byte[BUFFER];
		DatagramPacket pacoteRecebido = new DatagramPacket(buffer, BUFFER, group, portaRecebimento);
		socket.receive(pacoteRecebido);
		message = Util.convertDataToMessage(buffer);
		
		ServicoBackupInfo payload = (ServicoBackupInfo) message.getPayload();
		return payload;
	}

}
