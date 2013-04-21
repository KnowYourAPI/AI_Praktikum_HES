package util;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class IntIntTuple {

	@Id
	@GeneratedValue
	private int TupleId;
	private int produktId;
	private int menge;

	public IntIntTuple(int produktId, int menge) {
		this.produktId = produktId;
		this.menge = menge;
	}

	
	public int getTupleId() {
		return TupleId;
	}

	public void setTupleId(int tupleId) {
		TupleId = tupleId;
	}

	public int getProduktId() {
		return produktId;
	}

	public void setProduktId(int produktId) {
		this.produktId = produktId;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	@Override
	public String toString() {
		return "<" + produktId + "," + menge + ">";
	}
	
}
