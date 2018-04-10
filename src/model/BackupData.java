package model;

import java.io.Serializable;

public class BackupData implements Serializable {

	private static final long serialVersionUID = 4534066717530680068L;

	private String nomeUsuario;
	private String data;

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BackupData [nomeUsuario=" + nomeUsuario + ", data=" + data + "]";
	}

}
