package com.jack.chatserver;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;

import com.chat.packets.Packet2Message;

public class Server {
	
	private ServerSocket server;
	private HashMap<UUID, User> users = new HashMap<UUID, User>();
	
	public Server() throws IOException{
		
		System.out.println("Starting");
		
		server = new ServerSocket(7777);
		Socket socket;
		while(true){
			socket = server.accept();
			createUser(socket);
		}
	}

	private void createUser(Socket socket) {
		try {
			new User(socket, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addUser(User user, UUID uuid){
		users.put(uuid, user);
	}

	public void broadcast(Packet2Message message, String name, UUID uuid) {
		System.out.println(name + ": " + message.message);
		System.out.println(users.toString());
		for(UUID uuidKey : users.keySet()){
			Packet2Message msg = new Packet2Message();
			msg.name = name;
			msg.message = message.message;
			users.get(uuidKey).sendPacket(msg);
		}
	}
}