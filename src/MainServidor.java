import java.io.IOException;
import java.net.UnknownHostException;

import cliente.Cliente;
import servidor.ServidorBackup;

public class MainServidor {

	public static void main(String[] args) throws IOException {
		new ServidorBackup();
	}
	
}
