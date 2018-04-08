package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Message;

public class Util {

	public static byte[] convertMessageToData(Message message) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        byte[] data = baos.toByteArray();
		return data;
	}
	
	public static Message convertDataToMessage(byte[] buffer) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Message mensagem = (Message) ois.readObject();
		return mensagem;
	}
	
}
