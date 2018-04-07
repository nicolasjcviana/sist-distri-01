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
        String multiCastAddress = "224.0.0.1";
        final int multiCastPort = 52684;
 
        InetAddress group = InetAddress.getByName(multiCastAddress);
        MulticastSocket s = new MulticastSocket(multiCastPort);
        s.joinGroup(group);
 
        Message message = new Message(ServiceConstants.BUSCAR_IDENTIFICACAO, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        byte[] data = baos.toByteArray();
 
        s.send(new DatagramPacket(data, data.length, group, multiCastPort));
        
	}

}
