package util;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IntIntTuple {

	@Id
	private int TupleId;
	private int erste;
	private int zweite;

	public IntIntTuple(int erste, int zweite) {
		this.erste = erste;
		this.zweite = zweite;
	}

	
	public int getTupleId() {
		return TupleId;
	}

	public void setTupleId(int tupleId) {
		TupleId = tupleId;
	}

	public int getErste() {
		return erste;
	}

	public void setErste(int erste) {
		this.erste = erste;
	}

	public int getMenge() {
		return zweite;
	}

	public void setMenge(int zweite) {
		this.zweite = zweite;
	}

	@Override
	public String toString() {
		return "<" + erste + "," + zweite + ">";
	}
	
}
