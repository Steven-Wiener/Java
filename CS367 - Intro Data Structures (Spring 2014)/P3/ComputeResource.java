package P3;

public class ComputeResource {
	private int value;
	
	public ComputeResource(int v) {
		value = v;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return "(compute amount: " + value + ")";
	}
}