package com.jack.loic;
import java.io.IOException;

public class StresserThread implements Runnable {

	private Thread thread;
	private String threadName;
	private boolean stop;
	private Main main;
	
	public StresserThread(String name, Main main){
		threadName = name;
		System.out.println("Creating");
		this.main = main;
	}
	
	@Override
	public void run() {		
		try {
			while(!stop){
				main.getSocket().send(main.getPacket());	
			}
		} catch (IOException e) {
			System.out.println("ERROR: Sending packet failed!");
		}
	}
	
	public void StopStresser(){
		stop = true;
		thread.interrupt();
	}
	
	public void Start(){
		System.out.println("Starting " +  threadName);
		if(thread == null){
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
}
