package servidor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import model.Message;
import model.ServiceConstants;

public class ServidorBackup {

	String caminho = "224.0.0.1";
	final int porta = 52684;
	private static final int BUFFER = 1024 * 4; 

	public ServidorBackup() throws IOException {
        System.out.println("Criando socket no ip " + caminho + ".");
        InetAddress group = InetAddress.getByName(caminho);
        MulticastSocket socket = new MulticastSocket(porta);
        socket.joinGroup(group);
 
        while (true) {
            System.out.println("Aguardando requisições...");
 
            byte[] buffer = new byte[BUFFER];
            DatagramPacket pacoteRecebido = new DatagramPacket(buffer, BUFFER, group, porta);
			socket.receive(pacoteRecebido);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = new ObjectInputStream(bais);
            try {
				Message mensagem = (Message) ois.readObject();
				if (mensagem.getPrimitive().equals(ServiceConstants.BUSCAR_IDENTIFICACAO)) {
					new ServidorIdentificacaoResponse(socket.getInetAddress(), porta, pacoteRecebido).start();
				}
				
			} catch (ClassNotFoundException e) {
				System.err.println("Erro ao realizar leitura do objeto");
			}
            	
 
        }
	}
	
}
