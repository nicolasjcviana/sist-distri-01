package servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import model.Message;
import model.ServiceConstants;
import model.ServicoBackupInfo;
import util.Util;

public class ServidorIdentificacaoResponse extends Thread {
	InetAddress ip; 
	int portaServicoBackup;
	DatagramPacket pacoteResposta;
	
	public ServidorIdentificacaoResponse(InetAddress ip, int portaServicoBackup, DatagramPacket pacoteResposta) {
		this.ip = ip;
		this.portaServicoBackup = portaServicoBackup;
		this.pacoteResposta = pacoteResposta;
	}
	
	@Override
	public void run() {
		InetAddress addressResposta = pacoteResposta.getAddress();
		int portaResposta = pacoteResposta.getPort();
		
		try {
			Message message = new Message(ServiceConstants.BUSCAR_IDENTIFICACAO_RESPONSE, new ServicoBackupInfo(addressResposta, portaServicoBackup));
			byte[] data = Util.convertMessageToData(message); 
			DatagramPacket pacote = new DatagramPacket( data,
                     data.length,
                     addressResposta,
                     portaResposta );
		
			DatagramSocket socket = new DatagramSocket();
			socket.send(pacote);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
