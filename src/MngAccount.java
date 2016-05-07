
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MngAccount extends JFrame implements ActionListener
{
	
	static Connection con;
	static Statement stmt;
	 static ResultSet rs;
		static JFrame frame4;
	//for adaccnt...
		JButton add,bckA,clrAdd;
		JLabel usrnme,pwd,reenter;
		JTextField usrnmetxt,pwdtxt,reentertxt;
		static JPanel pnladaccnt,pnladaccnt1,pnldelaccnt,pnldelaccnt1,pnlupdateaccnt;
		
	
	//for delAccnt
		JButton del,clrD,bckD;
		Choice usrnmeDch;
		JTextField pwdDtxt;	
		JLabel usrnmeD,pwdD;
		
		
	//for updateAcc
		static JPanel inner,pnlupdateaccnt2;
		JButton update,clrU,bckU;
		Choice usrnmeUch;
		JTextField oldpwdUtxt,pwdUtxt,repwdUtxt;
		JLabel usrnmeU,oldpwdU,pwdU,repwdU;	
	

/*************************************************************************ADD****************************************************************/	
		 void connection() throws ClassNotFoundException, SQLException
			{
				
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/timetable","root","manish9761759967");
				stmt=con.createStatement();
				
			}
		  void adduser() throws ClassNotFoundException, SQLException
			{
				connection();
				String sql = null;
				if(pwdtxt.getText().equals(reentertxt.getText()))
				{
				try{
					sql="insert into adduser(uname, pass)"+"values('"+usrnmetxt.getText()+"','"+pwdtxt.getText()+"')";
					stmt.executeUpdate(sql);
					sql=usrnmetxt.getText();
					usrnmeDch.add(sql);
					usrnmeUch.add(sql);
					con.close();	
				}
				catch (Exception e)
				{
					 JOptionPane.showMessageDialog(null,"Already exixts","Error",
			  					JOptionPane.WARNING_MESSAGE);
				}}
				else
					JOptionPane.showMessageDialog(null,"mismatch","Error",
		  					JOptionPane.WARNING_MESSAGE);
				
			}
		  void deluser() throws ClassNotFoundException, SQLException
			{
				connection();
				String sql = null;
				int i=0;
				try{
					sql="select * from adduser where uname ='"+usrnmeDch.getSelectedItem()+"' and pass ='"+pwdDtxt.getText()+"'";
					rs=
							stmt.executeQuery(sql);
					while(rs.next())
					{i++;}
					 
				}
				catch (Exception e)
				{
					 JOptionPane.showMessageDialog(null,"Not exixts","Error",
			  					JOptionPane.WARNING_MESSAGE);
				}
				if(i==1)
				{
					sql="delete from adduser where uname ='"+usrnmeDch.getSelectedItem()+"' and pass ='"+pwdDtxt.getText()+"'";
					stmt.executeUpdate(sql);
					sql=usrnmeDch.getSelectedItem();
					usrnmeDch.remove(sql);
					usrnmeUch.remove(sql);
				}
				else
					  JOptionPane.showMessageDialog(null,"Not exixts","Error",
				  			JOptionPane.WARNING_MESSAGE);
						con.close();		
				
			}
		  void updateuser() throws ClassNotFoundException, SQLException
			{
			  
				connection();
				String sql = null;
				int i=0;
				try{
					sql="select * from adduser where uname ='"+usrnmeUch.getSelectedItem()+"' and pass ='"+oldpwdUtxt.getText()+"'";
					rs=stmt.executeQuery(sql);
					while(rs.next())
					{i++;}
					 
				}
				catch (Exception e)
				{
					 JOptionPane.showMessageDialog(null,"Not exixts","Error",
			  					JOptionPane.WARNING_MESSAGE);
				}
				if(i==1)
				{
					if(pwdUtxt.getText().equals(repwdUtxt.getText()))
					{
						sql="update adduser set pass ='"+pwdUtxt.getText()+"' where uname ='"+usrnmeUch.getSelectedItem()+"'";
						stmt.executeUpdate(sql);
						 
					}
					else
						JOptionPane.showMessageDialog(null,"Password Mismatch","Error",
					  			JOptionPane.WARNING_MESSAGE);
				}
				else
					  JOptionPane.showMessageDialog(null,"Not exixts","Error",
				  			JOptionPane.WARNING_MESSAGE);
						con.close();		
				
			}
	
		  public void account() throws SQLException, ClassNotFoundException
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
			usrnmeDch = new Choice();				
			usrnmeUch= new Choice();

			usrnmeDch.setBackground(Color.black);
			usrnmeDch.setForeground(Color.green);
			
			usrnmeUch.setBackground(Color.black);
			usrnmeUch.setForeground(Color.green);
			
			connection();
			rs=stmt.executeQuery("select * from adduser");
			 
			while(rs.next())				// adding users to list
				{
					usrnmeDch.add(rs.getString(1));
					usrnmeUch.add(rs.getString(1));
				 }
			 con.close();
			 frame4 = new JFrame("ATTG");
				frame4.setLayout(new FlowLayout());
				frame4.setResizable(false);
				
		addAccnt();
		delAccnt();
		updateAccnt();
	}
	public void addAccnt()
	{
		
	//setting frame4 icon on title bar.............
		inner=new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
		
		pnladaccnt = new JPanel();
		pnladaccnt.setLayout(new GridLayout(3,2,0,2));
		pnladaccnt1 = new JPanel(new FlowLayout());
		
		//pnladaccnt.setBackground(new Color(0,0,0,0));
		pnladaccnt.setOpaque(false);
		usrnme = new JLabel("UserName:");
		pwd = new JLabel("Password:");
		reenter = new JLabel("ReEnterPassword");
		add = new JButton("Add");
		bckA = new JButton("Back");
		clrAdd = new JButton("Clear");
		
		usrnmetxt = new JTextField(20);
		usrnmetxt.setToolTipText("Enter username");
		pwdtxt = new JPasswordField(20);
		pwdtxt.setToolTipText("Enter password");
		reentertxt = new JPasswordField(20);
		reentertxt.setToolTipText("RE-Enter password");
		
		//REGISTRATIONS......
		add.addActionListener(this);
		bckA.addActionListener(this);
		clrAdd.addActionListener(this);
		
		pnladaccnt.add(usrnme);
		pnladaccnt.add(usrnmetxt);
		pnladaccnt.add(pwd);
		pnladaccnt.add(pwdtxt);
		pnladaccnt.add(reenter);
		pnladaccnt.add(reentertxt);

		inner.add(pnladaccnt,gbc);
		
		pnladaccnt1.add(add);
		pnladaccnt1.add(clrAdd);
		pnladaccnt1.add(bckA);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
	      gbc.ipady = 20;   
	      gbc.gridx = 0;
	      gbc.gridy = 1;
	      inner.add(pnladaccnt1,gbc);
	    
		frame4.add(inner);
		pnladaccnt.setSize(500, 450);
		pnladaccnt.setVisible(false);
		frame4.setVisible(false);
	}
	
/*********************************************************DELETE*****************************************************************/
	public void delAccnt()
	{

		inner=new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
		pnldelaccnt = new JPanel();							
		pnldelaccnt.setLayout(new GridLayout(2,2,0,2));
		pnldelaccnt.setOpaque(false);
		
		pnldelaccnt1=new JPanel(new FlowLayout());
		usrnmeD = new JLabel("UserName:");			
		pwdD = new JLabel("Password:");
		
		del = new JButton("Delete");	
		clrD = new JButton("Clear");
		bckD = new JButton("Back");
		
		
		pwdDtxt = new JPasswordField(20);
		pwdDtxt.setToolTipText("Enter password");
		
		
		JPanel pp=new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		final JButton show=new JButton();
		ImageIcon folderIcon = new ImageIcon( "eye.png" );
		    show .setIcon(folderIcon);
		pp.setBackground(new Color(58,58,58));
		show.setBorder(null);
		show.setBorderPainted(false);
		show.setMargin(new Insets(0,0,0,0));
		 Border emptyBorder = BorderFactory.createEmptyBorder();
		    show.setBorder(emptyBorder);
		    Border  raisedbevel=BorderFactory.createRaisedBevelBorder();
		    pp.setBorder(raisedbevel);
		    pwdDtxt .setBorder(javax.swing.BorderFactory.createEmptyBorder());
		    pwdDtxt.setPreferredSize(new Dimension(100, 25));
		    pwdDtxt.setForeground(Color.green);
		    show.setPreferredSize(new Dimension(25, 25));
			pp.add(pwdDtxt);
			pp.add(show);
			show.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent e) {
			        DefaultButtonModel model = (DefaultButtonModel) show.getModel();
			        if (model.isArmed())
			        	((JPasswordField)pwdDtxt).setEchoChar((char) 0);
			        else
			        	((JPasswordField)pwdDtxt).setEchoChar('*');
			          
			      }

			    });		
		 
		del.addActionListener(this);									//registrations
		clrD.addActionListener(this);
		bckD.addActionListener(this);
		
		pnldelaccnt.add(usrnmeD);						
		pnldelaccnt.add(usrnmeDch);
		pnldelaccnt.add(pwdD);
		pnldelaccnt.add(pp);
		 inner.add(pnldelaccnt,gbc);
		pnldelaccnt1.add(del);
		pnldelaccnt1.add(clrD);
		pnldelaccnt1.add(bckD);
		gbc.fill = GridBagConstraints.HORIZONTAL;
	      gbc.ipady = 20;   
	      gbc.gridx = 0;
	      gbc.gridy = 1;
	      inner.add(pnldelaccnt1,gbc);
	      
		frame4.add(inner);
		pnldelaccnt.setSize(500,450);
		pnldelaccnt.setVisible(false);
		frame4.setVisible(false);
	}
	
	
/*****************************************************************UPDATE***************************************************************/
	public void updateAccnt()
	{
		
		
		inner=new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        pnlupdateaccnt = new JPanel();
		pnlupdateaccnt.setLayout(new GridLayout(4,2));
		//pnlupdateaccnt.setBackground(new Color(0,0,0,0));
		pnlupdateaccnt.setOpaque(false);
		pnlupdateaccnt2 = new JPanel(new FlowLayout());
		usrnmeU = new JLabel("UserName:");	
		oldpwdU = new JLabel("Old Password:");
		pwdU = new JLabel("New Password:");
		repwdU = new JLabel("Re-enter Password:");
		update = new JButton("Update");										
		clrU = new JButton("Clear");
		bckU = new JButton("Back");
		
		 	
		oldpwdUtxt = new JPasswordField(20);
		oldpwdUtxt.setToolTipText("Enter old Password");
		
		pwdUtxt = new JPasswordField(20);
		pwdUtxt.setToolTipText("Enter new password");
		repwdUtxt = new JPasswordField(20);
		repwdUtxt.setToolTipText("Re-enter password");
		
		

		JPanel pp=new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		final JButton show=new JButton();
		ImageIcon folderIcon = new ImageIcon( "eye.png" );
		    show .setIcon(folderIcon);
		pp.setBackground(new Color(58,58,58));
		show.setBorder(null);
		show.setBorderPainted(false);
		show.setMargin(new Insets(0,0,0,0));
		 Border emptyBorder = BorderFactory.createEmptyBorder();
		    show.setBorder(emptyBorder);
		    Border  raisedbevel=BorderFactory.createRaisedBevelBorder();
		    pp.setBorder(raisedbevel);
		    oldpwdUtxt .setBorder(javax.swing.BorderFactory.createEmptyBorder());
		    oldpwdUtxt.setPreferredSize(new Dimension(100, 25));
		    oldpwdUtxt.setForeground(Color.green);
		    show.setPreferredSize(new Dimension(25, 25));
			pp.add(oldpwdUtxt);
			pp.add(show);
			show.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent e) {
			        DefaultButtonModel model = (DefaultButtonModel) show.getModel();
			        if (model.isArmed())
			        	((JPasswordField)oldpwdUtxt).setEchoChar((char) 0);
			        else
			        	((JPasswordField)oldpwdUtxt).setEchoChar('*');
			          
			      }

			    });
			JPanel pp1=new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			final JButton show1=new JButton();
			show1 .setIcon(folderIcon);
			pp1.setBackground(new Color(58,58,58));
			show1.setBorder(null);
			show1.setBorderPainted(false);
			show1.setMargin(new Insets(0,0,0,0));
			     show1.setBorder(emptyBorder);
			     pp1.setBorder(raisedbevel);
			  pwdUtxt .setBorder(javax.swing.BorderFactory.createEmptyBorder());
			    pwdUtxt.setPreferredSize(new Dimension(100, 25));
			    pwdUtxt.setForeground(Color.green);
			    show1.setPreferredSize(new Dimension(25, 25));
				pp1.add(pwdUtxt);
				pp1.add(show1);
				show1.addChangeListener(new ChangeListener() {
				      public void stateChanged(ChangeEvent e) {
				        DefaultButtonModel model = (DefaultButtonModel) show1.getModel();
				        if (model.isArmed())
				        	((JPasswordField)pwdUtxt).setEchoChar((char) 0);
				        else
				        	((JPasswordField)pwdUtxt).setEchoChar('*');
				          
				      }

				    });
				JPanel pp2=new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
				final JButton show2=new JButton();
				show2 .setIcon(folderIcon);
				pp2.setBackground(new Color(58,58,58));
				show2.setBorder(null);
				show2.setBorderPainted(false);
				show2.setMargin(new Insets(0,0,0,0));
				show2.setBorder(emptyBorder);
				pp2.setBorder(raisedbevel);
				repwdUtxt .setBorder(javax.swing.BorderFactory.createEmptyBorder());
				repwdUtxt.setPreferredSize(new Dimension(100, 25));
				repwdUtxt.setForeground(Color.green);
				show2.setPreferredSize(new Dimension(25, 25));
				pp2.add(repwdUtxt);
				pp2.add(show2);
				show2.addChangeListener(new ChangeListener() {
					 public void stateChanged(ChangeEvent e) {
					        DefaultButtonModel model = (DefaultButtonModel) show2.getModel();
					        if (model.isArmed())
					        	((JPasswordField)repwdUtxt).setEchoChar((char) 0);
					        else
					        	((JPasswordField)repwdUtxt).setEchoChar('*');
					          
					      }

					    });
				
		
		update.addActionListener(this);							//registration
		bckU.addActionListener(this);
		clrU.addActionListener(this);
		
		pnlupdateaccnt.add(usrnmeU);
		pnlupdateaccnt.add(usrnmeUch);
		
		pnlupdateaccnt.add(oldpwdU);
		pnlupdateaccnt.add(pp);
		
		pnlupdateaccnt.add(pwdU);
		pnlupdateaccnt.add(pp1);
		
		pnlupdateaccnt.add(repwdU);
		pnlupdateaccnt.add(pp2);
		
		inner.add(pnlupdateaccnt,gbc);
		
		pnlupdateaccnt2.add(update);
		pnlupdateaccnt2.add(clrU);
		pnlupdateaccnt2.add(bckU);
		
		//frame4.setSize(500,250);
		gbc.fill = GridBagConstraints.HORIZONTAL;
	      gbc.ipady = 20;   
	      gbc.gridx = 0;
	      gbc.gridy = 1;
	      inner.add(pnlupdateaccnt2,gbc);
	      
		frame4.add(inner);
		//frame4.pack();
		pnlupdateaccnt.setVisible(false);
		frame4.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmp = e.getActionCommand();
		Object src = e.getSource();
		
		
		if(cmp == "Add")
		{
			try {
				adduser();
				usrnmetxt.setText("");
				pwdtxt.setText("");
				reentertxt.setText("");
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(cmp == "Back")
		{
			frame4.setVisible(false);
		}
		if(cmp == "Clear")
		{
			usrnmetxt.setText("");
			pwdtxt.setText("");
			reentertxt.setText("");
		}
		/******************************************ACTION LISTENER FOR "ADD" ENDS********************************************/
		
		if(cmp == "Delete")
		{
			try {
				deluser();
				usrnmeDch.select(0);
				pwdDtxt.setText("");
				
				} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(src.equals(clrD))
		{
			usrnmeDch.select(0);
			pwdDtxt.setText("");
			
		}
		if(src.equals(bckD))
		{
			frame4.setVisible(false);
		}
		
		
		/******************************************ACTION LISTENER FOR "DELETE" ENDS********************************************/
		if(cmp == "Update")
		{
			try {
				updateuser();
				usrnmeUch.select(0);
				pwdUtxt.setText("");
				oldpwdUtxt.setText("");
				repwdUtxt.setText("");
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(src.equals(clrU))
		{
			usrnmeUch.select(0);
			pwdUtxt.setText("");
			oldpwdUtxt.setText("");
			repwdUtxt.setText("");
		}
		if(src.equals(bckU))
		{
			frame4.setVisible(false);
		}
		/******************************************ACTION LISTENER FOR "UPDATE" ENDS********************************************/
	}
}
