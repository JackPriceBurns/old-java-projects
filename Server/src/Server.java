import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Server extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private boolean ableToType;
	
	public Server(){
		super("Jacks instant messaging software");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					userText.setText("");
					sendMessage(event.getActionCommand(), "server");
				}
			}
		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300,150);
		setVisible(true);
	}
	
	public void startRunning(){
		try{
			server = new ServerSocket(5934, 256);
			while(true){
				try{
					waitForConnection();
					setupStreams();
					whileChatting();
				} catch(EOFException eofException){
					sendMessage("\n Server ended the connection!");
				}finally{
					closeCrap();
				}
			}
		} catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	private void sendMessage(String text, String name){
		if(name.equals("server")){
			try{
				output.writeObject("SERVER - " + text);
				output.flush();
			}catch(IOException ioException){
				chatWindow.append("\n" + "");
			}
		} else {
			
		}
	}
	
	private void sendMessage(String text){
		chatWindow.append(text);
	}

	private void whileChatting() throws IOException {
		String message = "\nYou are now connected!";
		sendMessage("\n You are now connected!");
		ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				sendMessage("\n" + message);
			} catch(ClassNotFoundException classNotFoundException){
				sendMessage("\n" + "They sent something, I dont know what it is tho :/");
			}
		}while(!message.equals("CLIENT - END"));
	}

	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		sendMessage("\n Streams are now setup!");			
	}

	private void closeCrap() {
		sendMessage("\n Closing connections");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		} catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	private void ableToType(boolean b) {
		
	}

	private void waitForConnection() throws IOException {
		sendMessage("\n Waiting for conxions :-)");
		connection = server.accept();
		sendMessage("\n Now connected to " + connection.getInetAddress().getHostName());
	}
}
