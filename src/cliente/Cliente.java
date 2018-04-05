package cliente;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import model.Message;
import model.ServiceConstants;

public class Cliente {

	public void realizarBackup() {
		try {
			identificarServidor();
		} catch (IOException e) {
			System.err.println("Erro ao realizar backup " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void identificarServidor() throws IOException {
		MulticastSocket socket = new MulticastSocket();
		InetAddress grupo = InetAddress.getByName("224.0.0.0");
		socket.joinGroup(grupo);

		Message mensagem = new Message(ServiceConstants.BUSCAR_IDENTIFICACAO, null);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(mensagem);
		byte[] data = baos.toByteArray();

		DatagramPacket sendPacket = new DatagramPacket(data, data.length, grupo, 1500);
		socket.send(sendPacket);
	}

}
