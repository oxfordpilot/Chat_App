package clientside;



import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client implements ActionListener{
	JOptionPane jop;
	String strip;
	JFrame jf;
	JTextArea jta;
	JScrollPane jsp;
	JTextField jtf;
	Socket s;
	OutputStream os;
	DataOutputStream dos;
	String sjtf;
	InputStream is;
	DataInputStream dis;
	String rstr;
	
	//----------------------Thread_Starts--------------------------
	Thread t=new Thread() 
	{
		public void run() 
		{
			while(true)
			{
				rvcmesg();	
			}
		}
	};
	
	//----------------------Thread_Ends-----------------------------
	
	Client()  //--------------------Client_Side_JFrame_Starts Here------ 
	{
		jop=new JOptionPane();
		strip=jop.showInputDialog("Enter IP Address");
		
		if(strip !=null)
		{
			if(!strip.isEmpty())
			{
			
			jf=new JFrame("Client Side Chating App");
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
			}
			else
			{
				while(strip !=null)
				{
					jop.showMessageDialog(jf, "IP Address is Empty.");
					strip=jop.showInputDialog("Enter IP Address");
				}
			}
		}
		else
		{
			System.exit(0);
		}
		
		
		
		
	} //--------------------Client_Side_JFrame_Ends------ 
	
	
	public void csocket() //-----Socket_Message_Starts_Here------
	{
		try 
		{
			s=new Socket(strip,2222);
			jta.append("Welcome, You are connected now. \n");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}//-----------------------Socket_Message_Ends--------------------
	
	public void iostart()
	{
		
		try 
		{
			os= s.getOutputStream();
			dos=new DataOutputStream(os);
			is= s.getInputStream();
			dis=new DataInputStream(is);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		t.start();
	}
	
	public void sndmsg(String str) //---------------Send_Message_Starts----------
	{
		try 
		{
			
			
			dos.writeUTF(str);
			dos.flush();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	} //--------------------------Sends_Message_Ends---------------------
	
	public void rvcmesg()  //---------------Message_Receiver_Starts----------
	{
		try
		{
			
			rstr=dis.readUTF();
			show(rstr);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	} //---------------Message_Receiver_Ends----------
	
	
	public void show(String str)
	{
		jta.append("Server Sent: "+str+"\n");
		chatsound();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jtf )
		{
			String str= jtf.getText();
			
			if(str.trim().length()==0)
			{
				jop.showMessageDialog(jf, "Please type something.");
			}
			else {
				sndmsg(jtf.getText());
				jta.append(jtf.getText()+"\n");
				jtf.setText("");
			}
			
			
			
		}
		
	}

	
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
