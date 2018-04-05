package model;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -8004522208282000597L;

	private String primitive;
	private Object payload;
	
	public Message(String primitive, Object payload) {
		super();
		this.primitive = primitive;
		this.payload = payload;
	}

	public String getPrimitive() {
		return primitive;
	}
	
	public void setPrimitive(String primitive) {
		this.primitive = primitive;
	}
	
	public Object getPayload() {
		return payload;
	}
	
	public void setPayload(Object payload) {
		this.payload = payload;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
		result = prime * result + ((primitive == null) ? 0 : primitive.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (payload == null) {
			if (other.payload != null)
				return false;
		} else if (!payload.equals(other.payload))
			return false;
		if (primitive == null) {
			if (other.primitive != null)
				return false;
		} else if (!primitive.equals(other.primitive))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [primitive=" + primitive + ", payload=" + payload + "]";
	}
	
}