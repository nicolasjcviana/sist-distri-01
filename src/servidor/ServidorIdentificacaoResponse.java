package servidor;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.UUID;

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
		// envia resposta q
		pacoteResposta.getAddress();
		pacoteResposta.getPort();
		
		UUID.randomUUID();
		
	}

}
