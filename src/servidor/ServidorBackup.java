package servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.BackupData;
import model.BackupDataResponse;
import model.Message;
import model.ServiceConstants;
import util.Util;

public class ServidorBackup {

	String caminho = "224.0.0.1";
	final int portaMulticast = 52684;
	final int portaBackup = 52685;
	final InetAddress localhost = InetAddress.getLocalHost();
	private static final int BUFFER = 1024 * 4;

	public ServidorBackup() throws IOException {
		System.out.println("Criando socket no ip " + caminho + ".");
		InetAddress group = InetAddress.getByName(caminho);
		MulticastSocket socket = new MulticastSocket(portaMulticast);
		socket.joinGroup(group);

		criarServidorBackup();

		System.out.println("Aguardando requisições de identificação...");

		while (true) {

			byte[] buffer = new byte[BUFFER];
			DatagramPacket pacoteRecebido = new DatagramPacket(buffer, BUFFER, group, portaMulticast);
			socket.receive(pacoteRecebido);
			try {
				Message mensagem = Util.convertDataToMessage(buffer);
				if (mensagem.getPrimitive().equals(ServiceConstants.BUSCAR_IDENTIFICACAO)) {
					System.out.println("Recebida mensagem de busca de identificação, respondendo...");
					new ServidorIdentificacaoResponse(localhost, portaBackup, pacoteRecebido).start();
				}

			} catch (ClassNotFoundException e) {
				System.err.println("Erro ao realizar leitura do objeto");
			}
		}
	}

	private void criarServidorBackup() {
		Runnable backup = new Runnable() {
			public void run() {
				try {
					ServerSocket server = new ServerSocket(portaBackup, 100, localhost);
					System.out.println("Aguardando requisições de backup...");
					while (true) {
						Socket connection = server.accept();
						ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
						Message mensagem = (Message) input.readObject();
						BackupData data = (BackupData) mensagem.getPayload();
						System.out.println("Recebido mensagem de backup, iniciando...");
						try {
							realizarBackupCliente(connection, data);
							System.out.println("Backup realizado com sucesso para usuário " + data.getNomeUsuario());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}

			private void realizarBackupCliente(Socket connection, BackupData data)
					throws IOException, FileNotFoundException {
				String nmUsuario = data.getNomeUsuario();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yy_HH.mm.ss");
				LocalDateTime now = LocalDateTime.now();
				String nomeArquivo = "info_" + nmUsuario + "_" + dtf.format(now);
				String caminho = "C:\\servBackupJava\\" + nomeArquivo + ".txt";
				
				File file = new File(caminho);
				if (file.exists()) {
					file.delete();
				}
				
				file.getParentFile().mkdirs();
				new File(caminho).createNewFile();
				
				PrintWriter writer = new PrintWriter(file);
				writer.println(data.getData());
				writer.close();

				ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
				BackupDataResponse response = new BackupDataResponse();
				response.setLocalSalvo(caminho);
				response.setNomeArquivo(nomeArquivo);
				Message message = new Message(ServiceConstants.REALIZAR_BACKUP_RESPONSE, response);
				oos.writeObject(message);
			}
		};

		new Thread(backup).start();
	}

}
