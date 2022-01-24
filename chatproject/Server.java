package chatproject;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Server implements ActionListener{
	ServerSocket ss;
	Socket s;
	JFrame jf;
	JTextArea jta;
	JScrollPane jsp;
	JTextField jtf;
	InetAddress ins;
	String sip;
	//String sm;
	OutputStream os;
	DataOutputStream dos;
	InputStream is;
	DataInputStream dis;
	String str;
	JOptionPane jop;
	
	//---------------Thread_Created--------------------
	Thread t=new Thread() 
	{
		public void run() 
		{
			while(true)
			{
				rcvmsg();	
			}
		}
	};
	//------------------Thread_Ends-------------------
	
	
	Server() //--------------------Server_Side_JFrame_Starts Here------ 
	{
		jf=new JFrame("Server Side Chating App");
		jf.setSize(450, 400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		jta=new JTextArea();
		Font font=new Font("poppins",1,12);
		jta.setFont(font);
		jta.setEditable(false);
		jsp=new JScrollPane(jta);
		//jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jf.add(jsp);
		
		jtf=new JTextField();
		jtf.addActionListener(this);
		jf.add(jtf,BorderLayout.SOUTH);
		
		
		jf.setVisible(true);
		
	} //------------JFrame_Ends_Here--------------
	
	
	public void clientc() //---------------To_Get_IPAddress-----------------
	{
		try
		{
			ins=InetAddress.getLocalHost();
			sip=ins.getHostAddress();
			
		}
		catch(Exception e) 
		{
			System.out.println(e);
		}
	} //-------------------To_Get_IPAddress_Ends--------------------
	
	
	public void sockets() //--------------ServerSocket---------------
	{
		try
		{	
			ss=new ServerSocket(2222);
			jta.append("To connect provide this IP: "+sip+"\n");
			s=ss.accept();
			jta.append("Client Connected \n");
			jta.append("--------------------------------------------------- \n");
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	} //--------------ServerSocket_Ends---------------
	
	
	public void iostart() 
	{
		
		try
		{
			os= s.getOutputStream();
			dos=new DataOutputStream(os);
			is= s.getInputStream();
			dis=new DataInputStream(is);
		}
		catch (Exception e) {
		System.out.println(e);
		}
		t.start();
	}
	
	public void sendmsg( String msg)  //---------Send_Message-------
	{
		try {
			
			//sm=jtf.getText();
			dos.writeUTF(msg);
			dos.flush();
		//Extra Added
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	} //-----------Send_Message_Ends----------
	
	public void rcvmsg() //--------Receive_Message-------
	{
		try 
		{
			
			str=dis.readUTF();
//			jta.append(str+"Client Sent: \n");
			show(str);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	} //----------Receive_Message_Ends-------------------
	
	public void show(String str) //-------Adding_JTF_to_JTA_and_Resetting_JTA-----------
	{
		
		jta.append("Client Sent: "+str+"\n");
		chatsound();
		
	} //-------Adding_JTF_to_JTA_and_Resetting_JTA_Ends-----------------------


	@Override
	public void actionPerformed(ActionEvent e) { //---ActionListner_Overrided-----
		if(e.getSource()==jtf)
		{
				String str= jtf.getText();
							
					if(str.trim().length()==0)
					{
							jop.showMessageDialog(jf, "Please type something.");
					}
						else {
						sendmsg(jtf.getText());
						jta.append(jtf.getText()+"\n");
						jtf.setText("");
					}
			
			
			
			
		}
		
	}//---ActionListner_Overrided_Ends----------------------------------------------
	
	public void chatsound()
	{
		try {


			File f=new File ("chatsound.wav");
			AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(f.getAbsoluteFile());
			Clip clip=AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
