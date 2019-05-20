
public class ValueData {
	private int server;
	private int time;

	public ValueData(int currServer, int currTime) {
		server = currServer;
		time = currTime;
	}

	public void setServer(int currServer) {
		server = currServer;
	}

	public void setTime(int currTime) {
		time = currTime;
	}

	public int getTime() {
		return time;
	}

	public int getServer() {
		return server;
	}
}
