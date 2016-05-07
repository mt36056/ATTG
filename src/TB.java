
import java.awt.*;   
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel;

import java.sql.*;

public class TB extends JFrame implements ActionListener
{
	{
		try 
	    {
		  UIManager.setLookAndFeel(new SyntheticaBlueIceLookAndFeel());
	    } 
	    catch (Exception e) 
		
	    {
	      e.printStackTrace();
	    }}
	public static JFrame fMain=new JFrame("ATTG");
	public static Connection con;
	public static Statement stmt;
	ResultSet rs;
		JPanel cardPanel= new JPanel();;
	    private CardLayout cl; 

		final JPanel pBottom1 = new JPanel();
		final JPanel pBottom2 = new JPanel();
	    final JPanel pBottom3 = new JPanel();
	public static void connection() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/timetable","root","manish9761759967");
		stmt=con.createStatement();
	}
	public void login()
	{
		lUser.setForeground(Color.RED);
		lPass.setForeground(Color.RED);
		final JTextField tfUser=new JTextField(20);
		final JPasswordField tpPass=new JPasswordField(17);
		final JButton show=new JButton();
		ImageIcon folderIcon = new ImageIcon("eye.png");
		JPanel pp=new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		show .setIcon(folderIcon);
		show.setBorder(null);
		show.setBorderPainted(false);
		show.setMargin(new Insets(0,0,0,0));
		Border emptyBorder = BorderFactory.createEmptyBorder();
		show.setBorder(emptyBorder);
		Border  raisedbevel=BorderFactory.createRaisedBevelBorder();  
		 pp.setBorder(raisedbevel);
		tpPass .setBorder(javax.swing.BorderFactory.createEmptyBorder());
		tpPass.setPreferredSize(new Dimension(100, 25));
		tpPass.setForeground(Color.green);
		pp.add(tpPass);
		pp.add(show);
		show.setPreferredSize(new Dimension(25, 25));		
		show.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
				{
					DefaultButtonModel model = (DefaultButtonModel) show.getModel();
					if (model.isArmed())
						((JPasswordField) tpPass).setEchoChar((char) 0);
					else
					    ((JPasswordField) tpPass).setEchoChar('*');  
				}
		});		
		bClear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tfUser.setText("");
				tpPass.setText("");
			}
			
		});
		bLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {connection();} 
				 catch (Exception e2) {JOptionPane.showInternalMessageDialog(fMain,"Problem With SQL","ERROR",JOptionPane.ERROR_MESSAGE);}
				String sql = null;
				int i=0;
				try{
					sql="select * from adduser where uname ='"+tfUser.getText()+"' and pass ='"+tpPass.getText()+"'";
					rs=stmt.executeQuery(sql);
					while(rs.next())
					{i=1;}				 
				}
				catch (Exception e1)
				{
					 JOptionPane.showMessageDialog(null,"User Name or Password Not Valid..","Error",JOptionPane.WARNING_MESSAGE);
				}
				if(i==1)
				{		
						fMain.dispose();
						TB2.f2.setSize(600, 500);
						TB2.f2.setLocationRelativeTo(null);
						TB2.f2.setVisible(true);
						tfUser.setText("");
						tpPass.setText("");
				}
				else
					JOptionPane.showMessageDialog(null,"SORRY!!! Wrong USERNAME or PASSWORD","Failed...",JOptionPane.WARNING_MESSAGE);
						tpPass.setText("");
						tfUser.setText("");
						try {
							con.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}			 
			}
		});
		JPanel pUser=new JPanel(new FlowLayout());
		JPanel pPass=new JPanel(new FlowLayout());
		JPanel pButt=new JPanel(new FlowLayout());
		JPanel pFinal=new JPanel(new GridLayout(3,1));
		pUser.add(lUser);
		pUser.add(tfUser);	
		pPass.add(lPass);
		pPass.add(pp);
		pPass.add(show);
		pButt.add(bLogin);
		pButt.add(bClear);
		pFinal.add(pUser);
		pFinal.add(pPass);
		pFinal.add(pButt);
		pBottom3.add(pFinal);
	}
	public void about()
	{
		String s="we are a small group of devlopers\n"
				+ "adasdasdasda\n"
				+ "asdasdasdasd\n"
				+ "asdasdasdasd\n"
				+ "asdassdasdasd\n"
				+ "asdasdasdasdasd\n"
				+ "asdasdasdasd\n"
				+ "asdasdas\n"
				+ "dasd\n"
				+ "as\n"
				+ "das\n"
				+ "sd\n"
				+ "asd\n"
				+ "a\n"
				+ "sd\n"
				+ "as\n"
				+ "da\n"
				+ "sd\n"
				+ "a\n"
				+ "sd\n";
		TextArea ta=new TextArea();
		ta.setText(s);
		ta.setEditable(false);
		pBottom2.add(ta);
	}
	TB() throws ClassNotFoundException, SQLException
	{
		frame1();
		
		TB2 obj=new TB2();
		obj.frame2();
		MngAccount obj2=new MngAccount();
		obj2.account();
	}
	
	JPanel pTop,pMiddle;
	JButton bHome,bAbout,bSign,bLogin,bClear;
	JLabel lUser,lPass;
	JTextField tfUser;
	JPasswordField tpPass; 
 
	private void frame1()
	{ 
		cl = new CardLayout();
		cardPanel.setLayout(cl);
		cl.show(cardPanel, "login"); 
		pTop=new JPanel(new FlowLayout());
		pMiddle=new JPanel(new FlowLayout());
		
		bHome=new JButton("Home");
		bAbout=new JButton("About Us");
		bSign=new JButton("Sign In");
		bLogin=new JButton("Submit");
		bClear=new JButton("Clear");
		lUser=new JLabel("User Name:");
		lPass=new JLabel("Password:");
		cardPanel.add(pBottom1, "home");
        cardPanel.add(pBottom2, "about");
        cardPanel.add(pBottom3, "login");
        
		
		
		pTop.add(new ImageImplement(new ImageIcon("new.png").getImage()),"Center");
		fMain.add(pTop);
		ImageIcon img1 = new ImageIcon("new.png");				
		fMain.setIconImage(img1.getImage());
		bHome.setPreferredSize(new Dimension(100, 25));
		bAbout.setPreferredSize(new Dimension(100, 25));
		bSign.setPreferredSize(new Dimension(100, 25));
		pMiddle.add(bHome);
		pMiddle.add(bAbout);
		pMiddle.add(bSign);
		fMain.add(pMiddle);
		
		fMain.add(cardPanel);
		bHome.addActionListener(this);
		bHome.setActionCommand("H");
		bAbout.addActionListener(this);
		bAbout.setActionCommand("A");
		bSign.addActionListener(this);
		bSign.setActionCommand("L");
		about();
		login();
		home();
		fMain.setLayout(new FlowLayout());
		fMain.setSize(500,500);
		fMain.setLocationRelativeTo(null);
		fMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fMain.setVisible(true);
	}
	private void home() 
	{
		pBottom1.setLayout(new BorderLayout());
		JPanel pHow=new JPanel(new FlowLayout());
		pHow.add(new JLabel("How To Use:"));
		JButton bHow=new JButton("Check");
		pHow.add(bHow);
		pBottom1.add(pHow,BorderLayout.NORTH);
		bHow.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent eqq)
			{
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "HowToUse.txt");
				try {
					pb.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		JPanel pReq=new JPanel(new FlowLayout());
		pReq.add(new JLabel("Requirements:"));
		JButton breq=new JButton("Check");
		pReq.add(breq);
		pBottom1.add(pReq,BorderLayout.CENTER);
		
		breq.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent eqq)
			{
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Requirements.txt");
				try {
					pb.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("H"))
		{
			cl.show(cardPanel,"home");	
		}
		else if(e.getActionCommand().equals("A"))
		{
			cl.show(cardPanel,"about");
			
		}
		else if(e.getActionCommand().equals("L"))
		{
			cl.show(cardPanel,"login");
				
		}
		
	}

}
	