import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;

public class Client2 extends JFrame
{
	final static int ServerPort = 3214;								//Port to connect to server
	static Socket s;												//Socket for communicating messages
	static String msg;												//To recieve message
	static Timer timer = new Timer();								//object for timer
	static JTextArea txtFromClient;									//to type messages on client
	static JTextArea txtdisp;										//to display messages on client
	JButton sendText;												//to send message
	
	
	public Client2(){										//it runs the constructor and enables all gui properties														
		this.setTitle("Client2");																								
		this.setSize(1366, 768);										//Creating a Frame for client														
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);																			
		getContentPane().setLayout(null);																						

		txtFromClient = new JTextArea();							// creates a TextArea for client to type messages
		txtFromClient.setBounds(1200, 200, 650, 100);				//This determines size of the TextArea
		add(txtFromClient);											//adds the TextArea to the container
		
		txtdisp = new JTextArea();								// creates TextArea to display messages broadcasted by server
		txtdisp.setBounds(350, 15, 700, 950);					//This determines size of the TextArea
		txtdisp.setEditable(false);							//Makes the TextArea non editable
		add(txtdisp);											//adds the TextArea to the container

		this.setVisible(true);	// makes GUI visible
	}

	
	public static void sendMsg()  throws UnknownHostException, IOException {
		File clientfile = new File("C:/Users/tejas/Desktop/Client2/test.txt");
		while(true) {
			long time1 = clientfile.lastModified();
			String word = "";
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long time2= clientfile.lastModified();
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());		//sends data across the network and InputStream can accept it on other end
			
			if(time1 != time2) {
				File source = new File("C:/Users/tejas/Desktop/Client2/test.txt");
		    	File dest = new File("C:/Users/tejas/Desktop/Server/test.txt");
		    	FileUtils.copyFile(source, dest);
				txtFromClient.setText("File changed");
				word = txtFromClient.getText().trim();							//gets the data from the text area and assigns to word variable
				dos.writeUTF(word);														// read word from the client and write to server
			}else{
				dos.writeUTF("No change");
			}
				DataInputStream dis = new DataInputStream(s.getInputStream());		//accepts the data from the OutputStream
				msg = dis.readUTF();													//reads the messages from inputstream
				//txtdisp.append(msg);				//appends the message to the TextArea 
				
				if(msg.contains("Invalid data")) {
					
					String httpMsg="Invalid Data \n"; 
					txtdisp.append(httpMsg);
					String httpMsg1="File is updated \n"; 
					txtdisp.append(httpMsg1);
				
				}else if(msg.contains("No data")){
					continue;
				}
		}
	}


	public static void main(String args[]) throws UnknownHostException, IOException 		//executes the main function
	{	
		new Client2();//Object is called on 
																		//creates new file
		InetAddress ip = InetAddress.getByName("localhost");										//gets the ip address
		s = new Socket(ip, ServerPort);																//allocates the ip address
		txtdisp.append("Client Connected \n");
		sendMsg();
	}
}
