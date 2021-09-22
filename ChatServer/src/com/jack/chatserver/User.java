package com.jack.chatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import com.chat.packets.Packet;
import com.chat.packets.Packet1Auth;
import com.chat.packets.Packet2Message;

public class User {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean hasAuth = false;
	private boolean endConnection = false;
	private UUID uuid;
	private String name;
	private Server server;
	
	public User(Socket socket, Server server) throws IOException{
		this.server = server;
		this.uuid = UUID.randomUUID();
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
		
		server.addUser(this, uuid);
		
		while(!endConnection){
			if(!socket.isClosed()){
				try {
					tick();
				} catch (ClassNotFoundException e) {
					System.out.println("Error: User sent something weird");
				}
			}
		}
	}
	
	private void tick() throws ClassNotFoundException, IOException{
		Object obj = in.readObject();
		if(obj instanceof Packet){
			if(!hasAuth){
				if(obj instanceof Packet1Auth){
					Packet1Auth auth = (Packet1Auth) obj;
					name = auth.name;
					hasAuth = true;
					System.out.println(name + " has authed");
				} else {
					endConnection();
				}
			} else {
				if(obj instanceof Packet1Auth){
					endConnection();
				}
				
				if(obj instanceof Packet2Message){
					Packet2Message message = (Packet2Message) obj;
					server.broadcast(message, name, uuid);
				}
			}
		}
	}
	
	public void sendPacket(Packet packet){
		try {
			out.writeObject(packet);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endConnection(){
		try {
			endConnection = true;
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public UUID getUUID(){
		return uuid;
	}
}
