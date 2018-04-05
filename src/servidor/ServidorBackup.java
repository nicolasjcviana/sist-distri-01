package servidor;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import model.Message;

public class ServidorBackup {

	private DatagramSocket socket;
	private int porta;
	public ServidorBackup(int porta) {
		this.porta = porta;
		criarServidor();
		iniciarEscutaPacote();
	}

	private void criarServidor() {
		try {
			socket = new DatagramSocket(porta);
		} catch (SocketException e) {
			System.err.println("Erro ao iniciar servidor backup " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Iniciado servidor");
	}
	
	private void iniciarEscutaPacote() {
		while (true) {
			byte data[] = new byte[5000];
            DatagramPacket pacote = new DatagramPacket(data, data.length);
            System.out.println("Escutando requisições na porta " + porta);
            try {
            	socket.receive(pacote);
            	System.out.println("Pacote recebido, iniciando a conversão...");
            } catch (IOException e) {
				System.err.println("Erro ao receber mensagem " + e.getMessage());
			}
            
            Message mensagem = null;
            try {
	            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
	            ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
	            mensagem = (Message) is.readObject();
	            System.out.println("Recebida a mensagem " + mensagem);
            } catch (IOException e) {
				System.err.println("Erro de IO: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				System.err.println("Deve ser usada a classe Message.class para comunicação!");
 			}
            
            if (mensagem != null) {
            	
            }
        
		}
	}
	
	
}
