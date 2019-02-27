public class Player {
	private int playerNum;
	private String playerName;
	
	public Player(int playerNum) {
		this.playerNum = playerNum;
		this.playerName = "Unknown";
	}
	
	public Player(int playerNum, String name) {
		this.playerNum = playerNum;
		this.playerName = name;
	}
	
	public void setName(String name) {
		this.playerName = name;
	}
	
	public String getName() {
		return this.playerName;
	}
}