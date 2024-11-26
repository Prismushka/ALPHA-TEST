package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet8UpdateHealth extends Packet {
	public int healthMP;

	public Packet8UpdateHealth() {
	}

	public Packet8UpdateHealth(int i1) {
		this.healthMP = i1;
	}

	public void readPacketData(DataInputStream dataInputStream) throws IOException {
		this.healthMP = dataInputStream.readShort();
	}

	public void writePacket(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeShort(this.healthMP);
	}

	public void processPacket(NetHandler netHandler) {
		netHandler.handleHealth(this);
	}

	public int getPacketSize() {
		return 2;
	}
}
