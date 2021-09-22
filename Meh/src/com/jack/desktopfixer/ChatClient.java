package com.jack.desktopfixer;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;



public class ChatClient extends Frame implements Runnable {
  protected DataInputStream i;
  protected DataOutputStream o;

  protected TextArea output;
  protected TextField input;

  protected Thread listener;

  public ChatClient (String title, InputStream i, OutputStream o) {
    super (title);
    this.i = new DataInputStream (new BufferedInputStream (i));
    this.o = new DataOutputStream (new BufferedOutputStream (o));
    setLayout (new BorderLayout ());
    add ("Center", output = new TextArea ());
    output.setEditable (false);
    add ("South", input = new TextField ());
    pack ();
    show ();
    input.requestFocus ();
    listener = new Thread (this);
    listener.start ();
  }

  public void run () {
    try {
      while (true) {
        String line = i.readUTF ();
        output.appendText (line + "\n");
      }
    } catch (IOException ex) {
      ex.printStackTrace ();
    } finally {
      listener = null;
      input.hide ();
      validate ();
      try {
        o.close ();
      } catch (IOException ex) {
        ex.printStackTrace ();
      }
    }
  }

  public boolean handleEvent (Event e) {
    if ((e.target == input) && (e.id == Event.ACTION_EVENT)) {
      try {
        o.writeUTF ((String) e.arg);
        o.flush ();
      } catch (IOException ex) {
        ex.printStackTrace();
        listener.stop ();
      }
      input.setText ("");
      return true;
    } else if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {
      if (listener != null)
        listener.stop ();
      hide ();
      return true;
    }
    return super.handleEvent (e);
  }

  public static void main (String args[]) throws IOException {
    if (args.length != 2)
      throw new RuntimeException ("Syntax: ChatClient <host> <port>");

    Socket s = new Socket (args[0], Integer.parseInt (args[1]));
    new ChatClient ("Chat " + args[0] + ":" + args[1],
                    s.getInputStream (), s.getOutputStream ());
  }
}


public class ChatApplet extends Applet implements Runnable {
  protected DataInputStream i;
  protected DataOutputStream o;

  protected TextArea output;
  protected TextField input;

  protected Thread listener;

  public void init () {
    setLayout (new BorderLayout ());
    add ("Center", output = new TextArea ());
    output.setEditable (false);
    add ("South", input = new TextField ());
    input.setEditable (false);
  }

  public void start () {
    listener = new Thread (this);
    listener.start ();
  }

  public void stop () {
    if (listener != null)
      listener.stop ();
    listener = null;
  }

  public void run () {
    try {
      String host = getParameter ("host");
      if (host == null)
  host = getCodeBase ().getHost ();
      String port = getParameter ("port");
      if (port == null)
  port = "9830";
      output.appendText ("Connecting to " + host + ":" + port + "...");
      Socket s = new Socket (host, Integer.parseInt (port));
      i = new DataInputStream (new BufferedInputStream (s.getInputStream ()));
      o = new DataOutputStream (new BufferedOutputStream (s.getOutputStream ()));
      output.appendText (" connected.\n");
      input.setEditable (true);
      input.requestFocus ();
      execute ();
    } catch (IOException ex) {
      ByteArrayOutputStream out = new ByteArrayOutputStream ();
      ex.printStackTrace (new PrintStream (out));
      output.appendText ("\n" + out);
    }
  }

  public void execute () {
    try {
      while (true) {
        String line = i.readUTF ();
        output.appendText (line + "\n");
      }
    } catch (IOException ex) {
      ByteArrayOutputStream out = new ByteArrayOutputStream ();
      ex.printStackTrace (new PrintStream (out));
      output.appendText (out.toString ());
    } finally {
      listener = null;
      input.hide ();
      validate ();
      try {
        o.close ();
      } catch (IOException ex) {
        ex.printStackTrace ();
      }
    }
  }

  public boolean handleEvent (Event e) {
    if ((e.target == input) && (e.id == Event.ACTION_EVENT)) {
      try {
        o.writeUTF ((String) e.arg);
        o.flush ();
      } catch (IOException ex) {
        ex.printStackTrace();
        listener.stop ();
      }
      input.setText ("");
      return true;
    } else if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {
      if (listener != null)
        listener.stop ();
      hide ();
      return true;
    }
    return super.handleEvent (e);
  }
}

public class ChatHandler extends Thread {
  protected Socket s;
  protected DataInputStream i;
  protected DataOutputStream o;

  public ChatHandler (Socket s) throws IOException {
    this.s = s;
    i = new DataInputStream (new BufferedInputStream (s.getInputStream ()));
    o = new DataOutputStream (new BufferedOutputStream (s.getOutputStream ()));
  }

  protected static Vector handlers = new Vector ();

  public void run () {
    String name = s.getInetAddress ().toString ();
    try {
      broadcast (name + " has joined.");
      handlers.addElement (this);
      while (true) {
        String msg = i.readUTF ();
        broadcast (name + " - " + msg);
      }
    } catch (IOException ex) {
      ex.printStackTrace ();
    } finally {
      handlers.removeElement (this);
      broadcast (name + " has left.");
      try {
        s.close ();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  protected static void broadcast (String message) {
    synchronized (handlers) {
      Enumeration e = handlers.elements ();
      while (e.hasMoreElements ()) {
        ChatHandler c = (ChatHandler) e.nextElement ();
        try {
          synchronized (c.o) {
            c.o.writeUTF (message);
          }
          c.o.flush ();
        } catch (IOException ex) {
          c.stop ();
        }
      }
    }
  }
}

public class ChatServer {
  public ChatServer (int port) throws IOException {
    ServerSocket server = new ServerSocket (port);
    while (true) {
      Socket client = server.accept ();
      System.out.println ("Accepted from " + client.getInetAddress ());
      ChatHandler c = new ChatHandler (client);
      c.start ();
    }
  }

  public static void main (String args[]) throws IOException {
    if (args.length != 1)
      throw new RuntimeException ("Syntax: ChatServer <port>");
    new ChatServer (Integer.parseInt (args[0]));
  }
}

   