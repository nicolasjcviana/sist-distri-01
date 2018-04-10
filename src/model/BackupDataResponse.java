package model;

import java.io.Serializable;

public class BackupDataResponse implements Serializable {

	private static final long serialVersionUID = 1481117442668237025L;

	private String localSalvo;
	private String nomeArquivo;
	public String getLocalSalvo() {
		return localSalvo;
	}
	public void setLocalSalvo(String localSalvo) {
		this.localSalvo = localSalvo;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	@Override
	public String toString() {
		return "BackupDataResponse [localSalvo=" + localSalvo + ", nomeArquivo=" + nomeArquivo + "]";
	}
	
}
