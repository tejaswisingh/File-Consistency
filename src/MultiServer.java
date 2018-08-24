// Name - Tejaswi Singh , Student Id - 1001387430 

// References-
// https://www.journaldev.com/861/java-copy-file
// https://www.javatpoint.com/java-jtextarea
// https://stackoverflow.com/questions/24104313/how-to-delay-in-java 
// https://github.com/akshaywaikar30/DistributedSystems-Lab2/tree/master/src 

import java.io.BufferedWriter;
import java.io.DataInputStream; // All the header files 
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;

// Server class
public class MultiServer extends JFrame {
	static ArrayList<ClientHandler> clientlist = new ArrayList<ClientHandler>();			//creates list for handling active clients

	public static int clien = 1; // counter for clients
	
	static JTextArea lblWord;

static BufferedWriter writer=null;
	public MultiServer() { // Initialised at the time of main method call
		this.setTitle("Server");
		this.setSize(766, 768); // -- Set all the GUI properties
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		lblWord = new JTextArea(); // creating text field to show client response on server
		lblWord.setAutoscrolls(true);
		lblWord.setBounds(350, 15, 1050, 1025);
		lblWord.setEditable(false);
		add(lblWord);

		JScrollPane scroll = new JScrollPane(lblWord, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,		//adds scrollbar to the gui
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll);

		this.setVisible(true); // making GUI visible
	}

	public static void main(String[] args) throws IOException {
		new MultiServer(); // Calling Constructor
		
		
		ServerSocket ss = new ServerSocket(3214); // Declaring the port number for communication
		Socket s; // Creating socket for transferring messages
		// running infinite loop for getting messages, runs until socket number is legit
		while (true) {
			s = ss.accept(); // Accept the incoming request
			// obtain input and output streams
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			// displays message on the server's text area
			lblWord.append("Creating a new client: User " + clien + "\n");

			// Create a new handler object for handling this request.
			ClientHandler mtch = new ClientHandler(s, "\n" + "client" + clien, dis, dos, clien);

			// Create a new Thread with this object.
			Thread t = new Thread(mtch);
			// add this client to active clients list
			clientlist.add(mtch);
			// start the thread.
			t.start();

			// increment clien for new client.

			clien++;

		}
	}
}

// ClientHandler class
class ClientHandler implements Runnable {
	Scanner scn = new Scanner(System.in);

	private String name; // declaring variables to store client name
	final DataInputStream dis;
	final DataOutputStream dos;
	Socket s;
	int i;
	String filecreat;
	boolean isloggedin;
	public String word = null;
	// constructor
	public ClientHandler(Socket s, String name,DataInputStream dis, DataOutputStream dos, int i) {
		this.dis = dis;
		this.dos = dos;
		this.name = name;					//taking all the values needed by clienthandler constructor
		this.s = s;
		this.i = i;
		
	}

	@Override
	public void run() {

		String received;
		// long timestamp_fl = 0;
		try {
			while (true) {
				// receives the string from the client
				received = dis.readUTF();						//stores message after reading from input stream
				if (received.equals("File changed")) { // shows the logout functionality
					MultiServer.lblWord.append("From: Client" + i + received + "\n"); 	//appends word to text area
					//MultiServer.lblWord.append("Client" + i + "has logged out"); // where if client types logout it
					for (ClientHandler it : MultiServer.clientlist) { // and broadcasts the message to other active
						File source = new File("C:/Users/tejas/Desktop/Server/test.txt");
						File dest = new File("C:/Users/tejas/Desktop/Client1/test.txt");
						File dest1 = new File("C:/Users/tejas/Desktop/Client2/test.txt");
						File dest2 = new File("C:/Users/tejas/Desktop/Client3/test.txt");
						FileUtils.copyFile(source, dest);
						FileUtils.copyFile(source, dest1);
						FileUtils.copyFile(source, dest2);
						it.dos.writeUTF("Invalid data");
					}
					//break;
				}else{
					for (ClientHandler it : MultiServer.clientlist) { // and broadcasts the message to other active
						it.dos.writeUTF("No data");
					}
				}

			}

		} catch (IOException e) { // catches all the exceptions for the try block

			e.printStackTrace();
		}

		try {
			// closing resources
			this.dis.close();
			this.dos.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}