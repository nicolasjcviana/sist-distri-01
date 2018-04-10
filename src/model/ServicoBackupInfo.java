package model;

import java.io.Serializable;
import java.net.InetAddress;

public class ServicoBackupInfo implements Serializable {

	private InetAddress ip;
	private int porta;

	public ServicoBackupInfo() {

	}
	
	public ServicoBackupInfo(InetAddress ip, int porta) {
		super();
		this.ip = ip;
		this.porta = porta;
	}

	public InetAddress getIp() {
		return ip;
	}
	
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	
	public int getPorta() {
		return porta;
	}
	
	public void setPorta(int porta) {
		this.porta = porta;
	}
	
}
