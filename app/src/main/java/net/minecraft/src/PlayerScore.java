package net.minecraft.src;

public class PlayerScore {
	public String playerName;
	public int playerScore;

	public PlayerScore(String string1, int i2) {
		this.playerName = string1;
		this.playerScore = i2;
	}

	public void setScore(int i1) {
		this.playerScore = i1;
	}
}
