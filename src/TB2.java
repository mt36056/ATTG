import de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel;

import java.awt.*;   
import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class TB2 extends JFrame implements ActionListener,ItemListener
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
	int groupNumberForSql[]=new int[50];
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	static int flag=0,flag2;
	
	void check()
	{
		flag=0;
		for(int x=1;x<=Integer.parseInt(nmofsubch.getSelectedItem());x++)
		{
			if(subnametxt[x].getText().equals("") || subtchridch[x].getSelectedItem().equals("----"))
			{
				flag=1;
			}
		}
		
	}
	void checklab()
	{
		flag2=0;
		if(nmoflabch.getSelectedItem().equals("0"))
		{}
		else
		for(int x=1;x<=Integer.parseInt(nmoflabch.getSelectedItem());x++)
		{
			if(labnametxt[x].getText().equals("") || labtchridch[x].getSelectedItem().equals("----")||labroomch[x].getSelectedItem().equals("----"))
			{
				flag2=1;
			}
		}
		
	}
	 public void connection() throws ClassNotFoundException, SQLException
	{
		
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/timetable","root","manish9761759967");
		 stmt=con.createStatement();
		//stmtx=con.createStatement();
		
	}
	  
	  void addroom() throws ClassNotFoundException, SQLException
	{
		connection();String sql = null;
		if(clsnmbtxt.getText().equals(""))
		{}else
		sql="insert into room(roomno)"+"values('"+clsnmbtxt.getText()+"')";
		//ResultSet rs=
		stmt.executeUpdate(sql);
		roomnoch.add(clsnmbtxt.getText());
		con.close();
		
	}
	  void addcoursedb() throws ClassNotFoundException, SQLException
	  {
		  System.out.println("add1");
		connection();
		System.out.println("add2");
		 String sql="insert into addcourse(id,name,year,roomno,noofsub,nooflab)"+"values('"+crsidtxt.getText()
		+"',"+"'"+crnametxt.getText()+"',"+"'"+semList.getText()+"',"+"'"+roomnoch.getSelectedItem()+"',"+"'"+Integer.parseInt(nmofsubch.getSelectedItem())+"',"+"'"+Integer.parseInt(nmoflabch.getSelectedItem())+"')";
		System.out.println(sql);
		 stmt.executeUpdate(sql);
		con.close();
		insertsubject();
		insertlab();
		
	}
	  void insertsubject() throws ClassNotFoundException, SQLException
	  {
		connection();
		
		stmt.executeUpdate("CREATE TABLE "+crnametxt.getText()+"_"+crsidtxt.getText()+"  (subname VARCHAR(40),tchridname VARCHAR(40),creadit INT(3))");
		
		
		for(int x=1;x<=Integer.parseInt(nmofsubch.getSelectedItem());x++)
		{
			//System.out.println("insert into "+crnametxt.getText()+crsidtxt.getText()+"(subname ,tchridname ,creadit)"+" values ('"+subnametxt[x].getText()+"',"+"'"+subtchridch[x].getSelectedItem()+"',"+"'"+Integer.parseInt(creditch[x].getSelectedItem())+"')");
					
			String sql="insert into "+crnametxt.getText()+"_"+crsidtxt.getText()+ "(subname ,tchridname ,creadit)"+
			" values ('"+subnametxt[x].getText()+"',"+"'"+subtchridch[x].getSelectedItem()+"',"+"'"+Integer.parseInt(creditch[x].getSelectedItem())+"')";
			stmt.executeUpdate(sql);
		}
		con.close();
	 }
	 
	  void insertlab() throws ClassNotFoundException, SQLException
	  {
		connection();
		
		stmt.executeUpdate("CREATE TABLE "+crnametxt.getText()+"_"+crsidtxt.getText()+"lab"+"  (labname VARCHAR(30),tchridname VARCHAR(40),hours INT(3),room VARCHAR(10),grp int(3))");
		
		
		for(int x=1;x<=Integer.parseInt(nmoflabch.getSelectedItem());x++)
		{
			//System.out.println("insert into "+crnametxt.getText()+crsidtxt.getText()+"(subname ,tchridname ,creadit)"+" values ('"+subnametxt[x].getText()+"',"+"'"+subtchridch[x].getSelectedItem()+"',"+"'"+Integer.parseInt(creditch[x].getSelectedItem())+"')");
					
			String sql="insert into "+crnametxt.getText()+"_"+crsidtxt.getText()+"lab"+ "(labname ,tchridname ,hours,room,grp)"+
			" values ('"+labnametxt[x].getText()+"',"+"'"+labtchridch[x].getSelectedItem()+"',"+"'"+Integer.parseInt(hoursch[x].getSelectedItem())+"',"+"'"+labroomch[x].getSelectedItem()+"',"+groupNumberForSql[x]+")";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		}
		con.close();
		
	}
	  void addteacher() throws ClassNotFoundException, SQLException
	  {
		  connection();
		  String sql=null;
		  
			  try
		  {
			  sql="insert into addteacher(id,name)"+"values('"+tchridtxt.getText()
		+"',"+"'"+tchrnmtxt.getText()+"')";
		stmt.executeUpdate(sql);
		}
		  catch(Exception ex)
		  {
			  JOptionPane.showMessageDialog(null,"Something Wrong","Error",
  					JOptionPane.WARNING_MESSAGE);
		  }
		  con.close();
	  }
	ImageIcon img1;
	JLabel jl1;
	int a,a1;
	
	
	JPanel p1_1,p1_2,pnl2;
	
	
////references for panel p5 = middle panel in frame 2 for buttons...................
	JPanel p5,p1;
	JButton course,tchr,clsrm,ttbl,logout,mngacc;
	
	
	//references for panel p6 = base panel in frame 2 containing other jpanels i.e. p7 p8 p9................
	
	JPanel p6,pnlcoursebtn,pnlcoursetop;      // p7 = jpanel for ADD COURSE..............
	JLabel adcorse,crname,nmofsub,nmoflab,sem,roomno,crsid;
	JTextField crnametxt, crsidtxt;
	JTextField semList;

	JButton submitac,cancelcourse;
	Choice nmofsubch,roomnoch,nmoflabch;
	
	JPanel p8;
	JButton submitat,canceltchr;
	JLabel adtchr,tchrid,tchrnm;
	JTextField tchridtxt,tchrnmtxt;
	
	//panels inside panel 8...
	JPanel pnltchrtop,pnltchrmiddle,pnltchrbottom,btmdiv1,btmdiv2;
	//for panel 8 add teacher...
	
	
	//for panel 9 add class

	
	
	JPanel p9,p9a,p9x;
	JLabel clsnmb,adcls;
	JTextField clsnmbtxt;
	JButton submitclsrm;
	//for panel 9 add classroom...
	
	//for panel p10 number of choice in ADD COURSE........
	JPanel p10,p11,p7;
	JButton ok,pnlcancel;
	JLabel border,border1;
	static JFrame f2;
	
	//panel for MANAGE ACCOUNT = pnlmngacc.........................
	JPanel pnlmngacc= new JPanel();						//REFERENCES.................
	JButton updateacc;
	 
	JFrame f3,f4;
	 
	static JLabel  subname[]=new JLabel[50],labname[]=new JLabel[50],labGroup[]=new JLabel[50];
	static JLabel  subtchr[]=new JLabel[50],  labtchr[]=new JLabel[50];
	static JLabel credit[]=new JLabel[50],hours[]=new JLabel[50],labroom[]=new JLabel[50];
	static JLabel subtchrid[]=new JLabel[50],labtchrid[]=new JLabel[50];
	ButtonGroup group[]=new ButtonGroup[50] ;
	JRadioButton []lGroup=new 	JRadioButton[50];
	JRadioButton[] lGroup1=new 	JRadioButton[50];
	static Choice creditch[]=new Choice[50],hoursch[]=new Choice[50],labroomch[]=new Choice[50];
	static Choice subtchridch[]=new Choice[50],labtchridch[]=new Choice[50];
	static JTextField subnametxt[]=new JTextField[50],labnametxt[]=new JTextField[50];
	
	JPanel enterChoice,pCheck,pSubmit,pMain;
	public static JRadioButton rbStudent=new JRadioButton("Student",true);
	public static JRadioButton rbTeacher=new JRadioButton("Teacher");
	ButtonGroup bTS=new ButtonGroup();
	ImageIcon img2;
	JButton bSub;
	void frame2() throws SQLException, ClassNotFoundException  
	{
		f2 = new JFrame("ATTG");	
		f2.setResizable(false);
		img1 = new ImageIcon("new.png");				
		f2.setIconImage(img1.getImage());
		 f2.setLayout(new GridLayout(2,1));
		
		 p1= new JPanel();							
		 p1.setLayout(new GridLayout(2,1,0,0));
		 p1.setOpaque(false);
		 p1.setVisible(true);
		 pnl2 = new JPanel();						
		 pnl2.setLayout(new FlowLayout());
		 pnl2.setOpaque(false);
		 p1_1 = new JPanel();						
		 p1_1.setLayout(new FlowLayout());
		 p1_1.setBackground(new Color(0,0,0,80));
		 p1_1.setVisible(true);
		 p1_2 = new JPanel();					
		 p1_2.setLayout(new FlowLayout());
		 p1_2.setOpaque(false);
		 p1_2.setVisible(true);
		 p1.add(p1_1);  //>>>>>
		 p1.add(p1_2);	//>>>>>
		 f2.add(p1);
		 
		 p1_1.add(new ImageImplement(new ImageIcon("new1.png").getImage()),"Center");
		
		 p1_1.setOpaque(false);
		p5 = new JPanel();		           
		p5.setLayout(new FlowLayout());
		p5.setOpaque(false);
		p5.setPreferredSize(new Dimension(500,500));
		
	    course = new JButton("AddCourse");
							
		tchr = new JButton("AddTeacher");
					
		clsrm = new JButton("AddClassRoom");
							
		ttbl = new JButton("CreateTimeTable");
		pMain=new JPanel(new GridLayout(3,1));				
		mngacc = new JButton("ManageAccount");
							
							logout= new JButton("Logout");
							 
							logout.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									f2.setVisible(false);
									p9.setVisible(false);
									p8.setVisible(false);
									p7.setVisible(false);
									pnlmngacc.setVisible(false);
									TB.fMain.setVisible(true);;
								}});
							course.addActionListener(this);
							tchr.addActionListener(this);
							clsrm.addActionListener(this);
							ttbl.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									
									p6.add(pMain);
									pMain.setVisible(true);
									p6.setVisible(true);
									p9.setVisible(false);
									p9a.setVisible(false);
									p9x.setVisible(false);
									p8.setVisible(false);
									p7.setVisible(false);
									pnlmngacc.setVisible(false);
									f2.revalidate();
									f2.repaint();
								}
							});
							logout.addActionListener(this);
							mngacc.addActionListener(this);
							ttbl.addActionListener(this);
							p5.add(clsrm);
							p5.add(tchr);
							p5.add(course);
							p5.add(ttbl);
							p5.add(mngacc);
							p5.add(logout);
							
							p1_2.add(p5); //>>>>>>>>>>>
//**************************************for student and teacher selection*****************************8							
							enterChoice=new JPanel(new FlowLayout());
							enterChoice.add(new JLabel("Please Select One"));
							pCheck=new JPanel(new GridLayout(2,1));
							bTS.add(rbStudent);
							bTS.add(rbTeacher);
							pCheck.add(rbStudent);
							pCheck.add(rbTeacher);
							bSub=new JButton("Next");
							
							pSubmit=new JPanel(new FlowLayout());
							pSubmit.add(bSub);
							
							pMain.add(enterChoice);
							pMain.add(pCheck);
							pMain.add(bSub);
							
							bSub.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									try{
									if(rbStudent.isSelected())
										new Timetablepro();
									else if(rbTeacher.isSelected())
										new Timetablepro();
									}catch(Exception e76)
									{
										JOptionPane.showMessageDialog(null,"Something Wrong","error",JOptionPane.ERROR_MESSAGE);
									}
								}
							});
//*******************************************************************************************************							
							//f2.add(p5);
							
							p6 = new JPanel(new FlowLayout());
							p6.setOpaque(false);
							pnl2.add(p6);
							p7 = new JPanel(new GridLayout(3,1));
							
							//p7.setBackground(new Color(0,0,0,60));
							p7.setOpaque(false);
							p8 = new JPanel();
							p8.setLayout(new GridLayout(3,1,0,0));
							pnlcoursebtn = new JPanel();
							pnlcoursebtn.setLayout(new FlowLayout());
							//pnlcoursebtn.setBackground(new Color(0,0,0,0));
							pnlcoursebtn.setOpaque(false);
							pnlcoursetop = new JPanel();

							pnlcoursetop.setLayout(new GridLayout(6,2));
							
							//pnlcoursetop.setOpaque(false);
							adcorse = new JLabel("Add Course",JLabel.CENTER);
							adcorse.setForeground(Color.black);
							
							crname = new JLabel("CourseName:");
							crname.setForeground(Color.black);
							
							crsid=new JLabel("CourseID:");
							crsid.setForeground(Color.black);
							
							crsidtxt = new JTextField(20);
							//crsidtxt.setDocument(new JTextFieldLimit(10));
							crsidtxt.setToolTipText("Enter course id");
							
							crnametxt = new JTextField(20);
							//crnametxt.setDocument(new JTextFieldLimit(20));
							crnametxt.setToolTipText("Enter course name");
							
							nmofsub = new JLabel("Number of Subjects:");
							nmofsub.setForeground(Color.black);
							
							nmoflab = new JLabel("Number of Labs:");
							nmoflab.setForeground(Color.black);
							
							sem = new JLabel("Semester:");
							sem.setForeground(Color.black);
							
							semList=new JTextField();
							

							//yeartxt.setDocument(new JTextFieldLimit(9));
							
							roomno = new JLabel("RoomNo:");
							roomno.setForeground(Color.BLACK);
							
							roomnoch = new Choice();

							roomnoch.setBackground(Color.black);
							roomnoch.setForeground(Color.green);
							
							
							nmofsubch = new Choice();
							nmofsubch.addItemListener(new ItemListener() {
						         public void itemStateChanged(ItemEvent e)
						         {
						        	 a = Integer.parseInt(nmofsubch.getSelectedItem());
						     		
						        		p10.removeAll();
						        		if(crsidtxt.getText().equals("")||roomnoch.getSelectedItem().equals("------")||crnametxt.getText().equals("") || semList.getText().equals("")||nmofsubch.getSelectedItem().equals("0"))  
						        		{	
						        			//nmofsubch.select(0);
						        			 f3.setVisible(false);
						        			JOptionPane.showMessageDialog(null,"Please fill all the entries to proceed","Error",
						        					JOptionPane.WARNING_MESSAGE);
						        			
						        			nmofsubch.select(0);
						        		}
						        		else
						        		{
						        			f3.setVisible(true);
						        			
						        				try {
													adCorse(a);
												} catch (ClassNotFoundException | SQLException e1) {
													// TODO Auto-generated catch block
													JOptionPane.showMessageDialog(null,"error in sql","Error",
								        					JOptionPane.WARNING_MESSAGE);
												}
						        		}
						        		 f3.pack();
						          }
						       });
							
							nmoflabch = new Choice();
							nmoflabch.addItemListener(new ItemListener() {
						         public void itemStateChanged(ItemEvent e)
						         {
						        	  a1 = Integer.parseInt(nmoflabch.getSelectedItem());
						     		
						        		p11.removeAll();
						
						        		if(crsidtxt.getText().equals("")||roomnoch.getSelectedItem().equals("------")||crnametxt.getText().equals("") || semList.getText().equals("")||nmofsubch.getSelectedItem().equals("0"))  
						        		{	
						        			//nmofsubch.select(0);
						        			 f4.setVisible(false);
						        			JOptionPane.showMessageDialog(null,"Please fill all the entries to proceed","Error",
						        					JOptionPane.WARNING_MESSAGE);

						        			nmoflabch.select(0);
						        		}
						        		else
						        		{
						        			f4.setVisible(true);
						        			
						        				try {
													adlab(a1);
												} catch (ClassNotFoundException | SQLException e1) {
													// TODO Auto-generated catch block
													JOptionPane.showMessageDialog(null,"error in sql","Error",
								        					JOptionPane.WARNING_MESSAGE);
												}	
						        		}
						        			
						        		 f4.pack();
						          }
						       });//registration

							submitac = new JButton("Submit");
							submitac.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									
									nmofsubch.getSelectedItem().equals("+");
										if(nmofsubch.getSelectedItem().equals("0"))
											f3.setVisible(false);
										
										check(); checklab();
										if(  flag==1 ||flag2==1 || crnametxt.getText().equals("")||crsidtxt.getText().equals("") || semList.getText().equals("")|| nmofsubch.getSelectedItem().equals("0") || (roomnoch.getSelectedItem().equals("------")))
										{	
											
											JOptionPane.showMessageDialog(null,"Please fill all the entries to proceed","Error",
													JOptionPane.WARNING_MESSAGE);
											
											
										}
										else
										{
											try {
												addcoursedb();
												
												crsidtxt.setText("");
												crnametxt.setText("");
												semList.setText("");
												roomnoch.select(0);
												nmofsubch.select(0);
												nmoflabch.select(0);
												
											} catch (ClassNotFoundException | SQLException e1) {
												JOptionPane.showMessageDialog(null,"Record already exist in course","off...",JOptionPane.WARNING_MESSAGE);
											}
											
											
										}}
							});
							
							cancelcourse = new JButton("  Clear  ");
							cancelcourse.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent E)
								{
									crsidtxt.setText("");
									crnametxt.setText("");
									semList.setText("");
									roomnoch.select(0);
									nmofsubch.select(0);
									nmoflabch.select(0);
								}
							});
							roomnoch.add("------");
							
							nmofsubch.add("0");
							nmofsubch.add("4");
							nmofsubch.add("5");
							nmofsubch.add("6");
							nmofsubch.add("7");
							nmofsubch.add("8");
							nmofsubch.setBackground(Color.black);
							nmofsubch.setForeground(Color.green);
							
							nmoflabch.add("0");
							nmoflabch.add("1");
							nmoflabch.add("2");
							nmoflabch.add("3");
							nmoflabch.add("4");

							nmoflabch.setBackground(Color.black);
							nmoflabch.setForeground(Color.green); 
							
							pnlcoursetop.add(crsid);
							pnlcoursetop.add(crsidtxt);
							pnlcoursetop.add(crname);    
							pnlcoursetop.add(crnametxt); 
							pnlcoursetop.add(sem);
							pnlcoursetop.add(semList);
							pnlcoursetop.add(roomno);
							pnlcoursetop.add(roomnoch);
							pnlcoursetop.add(nmofsub);    
							pnlcoursetop.add(nmofsubch);   
							pnlcoursetop.add(nmoflab);    
							pnlcoursetop.add(nmoflabch);
							pnlcoursebtn.add(cancelcourse,JButton.CENTER);
							pnlcoursebtn.add(submitac,JButton.CENTER); 
							connection();
							
							rs=stmt.executeQuery("select * from room");
							
							while(rs.next())				
							  roomnoch.add(rs.getString(1));
							 con.close();
							JPanel pTemp1=new JPanel(new BorderLayout());
							
							
							
							
							pTemp1.add(pnlcoursetop,BorderLayout.NORTH);
							pTemp1.add(pnlcoursebtn,BorderLayout.CENTER);
							
							p7.add(pTemp1,BorderLayout.CENTER);
						
							p6.setVisible(false);
							pnlcoursetop.setVisible(true);
							pnlcoursebtn.setVisible(true);
							
						
							p6.add(p7);
							p7.setVisible(true);
							
							p8.setOpaque(false);
							pnltchrtop = new JPanel();
							pnltchrtop.setLayout(new FlowLayout());
							pnltchrtop.setBackground(new Color(0,0,0,80));
							//pnltchrtop.setOpaque(false);
							pnltchrmiddle = new JPanel();
							pnltchrmiddle.setLayout(new GridLayout(2,2));
							pnltchrmiddle.setBackground(new Color(0,0,0,80));
							//pnltchrmiddle.setOpaque(false);
							pnltchrbottom = new JPanel();
							pnltchrbottom.setLayout(new FlowLayout());
							//pnltchrbottom.setBackground(new Color(0,0,0,0));
							pnltchrbottom.setOpaque(false);
							btmdiv1 = new JPanel();								
							btmdiv1.setLayout(new BorderLayout());
							btmdiv1.setBackground(new Color(0,0,0,80));
							//btmdiv1.setOpaque(false);
							btmdiv2 = new JPanel();								//sub division 2 of panel= pnltchrbottom
							btmdiv2.setLayout(new FlowLayout());
							
							btmdiv2.setOpaque(false);
							adtchr =  new JLabel("Add Teacher",JLabel.CENTER);
							
							tchrid = new JLabel("TeacherId:");
							tchridtxt = new JTextField(20);
							
							tchridtxt.setToolTipText("Enter teacher id IN NUMBERS ONLY!!!");
							
							tchrnm = new JLabel("Name:");
							tchrnmtxt = new JTextField(20);
							
							tchrnmtxt.setToolTipText("Enter faculty name");
							
							
							
							
							submitat = new JButton("Submit");
							submitat.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									try {
										addteacher();
										tchridtxt.setText("");
										tchrnmtxt.setText("");
										
										
									} catch (ClassNotFoundException | SQLException e1) {
																				JOptionPane.showMessageDialog(null,"Record already exist","off",JOptionPane.WARNING_MESSAGE);
									}
									
								}
							});				//Registration for submit add teacher button
							
							canceltchr = new JButton("  Clear  ");
							canceltchr.addActionListener(this);
							
							
							adtchr.setForeground(Color.black);
							tchrid.setForeground(Color.black);
							tchrnm.setForeground(Color.black);
							
							
							
							pnltchrtop.add(adtchr);
							pnltchrtop.setOpaque(false);
							pnltchrmiddle.add(tchrid);
							pnltchrmiddle.add(tchridtxt);
							pnltchrmiddle.add(tchrnm);
							pnltchrmiddle.add(tchrnmtxt);
							pnltchrmiddle.setOpaque(false);
							
							btmdiv2.add(submitat);
							btmdiv2.add(canceltchr);
							
							
							pnltchrbottom.add(btmdiv1);
							pnltchrbottom.add(btmdiv2);
							
							btmdiv1.setVisible(true);
							btmdiv2.setVisible(true);
							
							p8.add(pnltchrtop);
							p8.add(pnltchrmiddle);
							p8.add(pnltchrbottom);
							p6.add(p8);
							p8.setOpaque(false);
							p6.setOpaque(false);
							
							pnltchrtop.setVisible(true);
							pnltchrmiddle.setVisible(true);
							pnltchrbottom.setVisible(true);
							p8.setVisible(false);
							
						//panel 8 for add teacher ENDS..........
							
							
						//panel 9 for add classroom STARTS.........
							  p9x=new JPanel(new GridLayout(2,1));
							 p9a = new JPanel(new FlowLayout());;
							p9 = new JPanel();
							p9.setLayout(new GridLayout(2,1));
							p9.setBackground(new Color(0,0,0,100));
							//p9.setOpaque(false);
							p9x.setOpaque(false);
							p9a.setOpaque(false);
							
							adcls = new JLabel("Add Class Room",JLabel.CENTER);
							
							clsnmb = new JLabel("ClassNo:");
							clsnmbtxt = new JTextField(20);
							//clsnmbtxt.setDocument(new JTextFieldLimit(10));
							clsnmbtxt.setToolTipText("Enter class number");
							
							submitclsrm = new JButton("Submit");
							submitclsrm.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									try {
										addroom();
										clsnmbtxt.setText("");
									} catch (ClassNotFoundException | SQLException e1) {
										// TODO Auto-generated catch block
										JOptionPane.showMessageDialog(null,"Record already exist","off,,,",JOptionPane.WARNING_MESSAGE);
									}
									
								}
							});		//registration for submit class room button
							
							adcls.setForeground(Color.black);
							clsnmb.setForeground(Color.black);
							JPanel pTemp=new JPanel(new FlowLayout());
							
							p9.add(adcls);
							p9.setOpaque(false);
							pTemp.add(clsnmb);
							pTemp.add(clsnmbtxt);
							p9.add(pTemp);
							p9a.add(submitclsrm);
							
							p9x.add(p9);
							p9x.add(p9a);
							p6.add(p9x);
							
						//panel 9 for add classroom ENDS..........
	
							//pnlngacc = panel for manage account............ 
						    pnlmngacc = new JPanel();
							pnlmngacc.setLayout(new FlowLayout());
							//pnlmngacc.setBackground(new Color(0,0,0,0));
							pnlmngacc.setOpaque(false);
							
							updateacc = new JButton("Update Account");
							
							
							updateacc.addActionListener(this);
							
							pnlmngacc.add(new JLabel("Update You Details..(Like Password)"));
							pnlmngacc.add(updateacc);
							
							p6.add(pnlmngacc);
							pnlmngacc.setVisible(false);
						 f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
						 f2.setVisible(false);
							f2.add(pnl2);
							
	//frame 3***********************************************************
		f3 = new JFrame("ATTG");
							
		f3.setLayout(new FlowLayout());
		f3.setSize(500, 500);
		
		//setting frame3 ( f3 ) icon on title bar.............
				img2 = new ImageIcon("new.png");				
				f3.setIconImage(img2.getImage());
				
		f3.setVisible(false);		
		
		//panel 10........................
		 p10 =new JPanel();
		 p10.setLayout(new GridLayout(a,2));
		  
		 //frame4********************************************************/
		 
		 f4 = new JFrame("ATTG");
		 
			f4.setLayout(new FlowLayout());
			f4.setSize(500, 500);
			
			//setting frame3 ( f3 ) icon on title bar.............
									
					f4.setIconImage(img2.getImage());
					
			f4.setVisible(false);
			//panel 10........................
			 
			 p11=new JPanel();
			 p11.setLayout(new GridLayout(a1,2));
	
	}

	public void itemStateChanged(ItemEvent arg0) {
		
		repaint();
		
		
	}

	
	@Override
	public void actionPerformed(ActionEvent ea) {
		 
		String fr = ea.getActionCommand();
		
		if(fr == "AddTeacher")
		{
			p6.setVisible(true);
			p9.setVisible(false);
			p9a.setVisible(false);
			p9x.setVisible(false);
			//repaint();
			p7.setVisible(false);
			p8.setVisible(true);
			pnltchrtop.setVisible(true);
			pnltchrmiddle.setVisible(true);
			pnltchrbottom.setVisible(true);
			pnlmngacc.setVisible(false);
			pMain.setVisible(false);
			f2.revalidate();
			f2.repaint();
			p8.revalidate();
			p8.repaint();
		}
		if(fr == "AddCourse")
		{
			pMain.setVisible(false);
			p6.setVisible(true);
			p9.setVisible(false);
			p9a.setVisible(false);
			p9x.setVisible(false);
			p8.setVisible(false);
			p7.setVisible(true);
			pnlmngacc.setVisible(false);
			f2.revalidate();
			f2.repaint();			 
		}
		if(fr == "AddClassRoom")
		{
			
			//repaint();
			pMain.setVisible(false);
			p6.setVisible(true);

			p9a.setVisible(true);
			p9x.setVisible(true);
			p8.setVisible(false);
			p7.setVisible(false);
			p9.setVisible(true);
			pnlmngacc.setVisible(false);
			f2.revalidate();
			f2.repaint();
		}
		
		if(fr == "ManageAccount")
		{

			p9a.setVisible(false);
			p9x.setVisible(false);
			p6.setVisible(true);
			pnlmngacc.setVisible(true);
			p8.setVisible(false);
			p7.setVisible(false);
			p9.setVisible(false);
			f2.revalidate();
			pMain.setVisible(false);
			f2.repaint();
		}
		 
		Object cncl = ea.getSource();
		
		if(cncl.equals(canceltchr))
		{
			tchridtxt.setText("");
			tchrnmtxt.setText("");
			
			
		}
		
		if ( fr == "Update Account")
		{
			//MngAccount.frame4.removeAll();
			MngAccount.pnldelaccnt1.setVisible(false);
			MngAccount.pnladaccnt1.setVisible(false);
			MngAccount.inner.setVisible(true);
			MngAccount.pnlupdateaccnt2.setVisible(true);
			MngAccount.pnldelaccnt.setVisible(false);
			MngAccount.pnladaccnt.setVisible(false);
			MngAccount.pnlupdateaccnt.setVisible(true);
			MngAccount.frame4.setVisible(true);
			MngAccount.frame4.pack();
			MngAccount.frame4.revalidate();
			MngAccount.frame4.repaint();
		}
	}
	void adCorse(final int a) throws ClassNotFoundException, SQLException
	{
		
		ok = new JButton("ok");
		pnlcancel = new JButton("clear");
		
		for(int i=1; i<=a;i++)
		{
			
			subname[i] = new JLabel("Subject "+i+" Name:");
			subnametxt[i] = new JTextField(20);
			//subnametxt[i].setDocument(new JTextFieldLimit(20));
			subnametxt[i].setToolTipText("Enter name of "+i+" subject");
			
			subtchrid[i] = new JLabel("Subject "+i+" Teacher(ID)+name:");
			subtchridch[i]=new Choice();
			subtchridch[i].setBackground(Color.black);
			subtchridch[i].setForeground(Color.green);
		 	
			credit[i] = new JLabel("Credit: ");
			creditch[i] = new Choice();
			creditch[i].setBackground(Color.black);
			creditch[i].setForeground(Color.green);
		 	
			border = new JLabel("********************************************");
			border1 = new JLabel("*******************************************");
			
			subtchridch[i].add("----");
			
			connection();
			ResultSet rs1=stmt.executeQuery("select * from addteacher");
			
			while(rs1.next())				// adding rooms to list
			 subtchridch[i].add(rs1.getString(1)+"#"+rs1.getString(2));
			 con.close();
			
			
			
		
			creditch[i].add("1");
			creditch[i].add("2");
			creditch[i].add("3");
			creditch[i].add("4");
			creditch[i].add("5");
			creditch[i].add("6");
			
			
			p10.add(subname[i]);
			p10.add(subnametxt[i]);
			p10.add(subtchrid[i]);
			p10.add(subtchridch[i]);
			//p10.add(subtchr[i]);
			//p10.add(subtchrtxt[i]);
			p10.add(credit[i]);
			p10.add(creditch[i]);
			p10.add(border);
			p10.add(border1);
			
			
		}
		p10.add(ok);
		p10.add(pnlcancel);
		p10.setSize(500,800);
		f3.add(p10);
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				check();
				if(flag==0)
				f3.setVisible(false);
				else
					JOptionPane.showMessageDialog(null,"fill all records","off",JOptionPane.WARNING_MESSAGE);
					
				 
				
			}
		});	
		pnlcancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				for(int i=1; i<=a;i++)
				{
					subnametxt[i].setText("");
					subtchridch[i].select(0);
					creditch[i].select(0);
				
				}
				
			}
		});
		f3.setSize(500,800);
	}

	void adlab(final int a) throws ClassNotFoundException, SQLException
	{
		ok = new JButton("ok");
		pnlcancel = new JButton("clear");
		
		for(int i=1; i<=a;i++)
		{
			labname[i] = new JLabel("Lab "+i+" Name:");
			labnametxt[i] = new JTextField(20);
		//	labnametxt[i].setDocument(new JTextFieldLimit(20));
			labnametxt[i].setToolTipText("Enter name of "+i+" lab");
			
			labtchrid[i] = new JLabel("Subject "+i+" Teacher(ID)+name:");
			labtchridch[i]=new Choice();
			labtchridch[i].setBackground(Color.black);
			labtchridch[i].setForeground(Color.green);
			hours[i] = new JLabel("hours: ");
			hoursch[i] = new Choice();

			hoursch[i].setBackground(Color.black);
			hoursch[i].setForeground(Color.green);
			labroom[i] = new JLabel("labroom: ");
			labroomch[i] = new Choice();

			labroomch[i].setBackground(Color.black);
			labroomch[i].setForeground(Color.green);
			
			labGroup[i]=new JLabel("Number Of Group:");
			lGroup[i]=new JRadioButton("1",true);
			lGroup1[i]=new JRadioButton("2");
			groupNumberForSql[i]=1;
			if(lGroup[i].isSelected())
				groupNumberForSql[i]=1;
			else if(lGroup1[i].isSelected())
				groupNumberForSql[i]=2;
			group[i]  = new ButtonGroup();
			group[i].add(lGroup[i]);
			group[i].add(lGroup1[i]);
			border = new JLabel("********************************************");
			border1 = new JLabel("*******************************************");
			
			labtchridch[i].add("----");
			
			connection();
			ResultSet rs1=stmt.executeQuery("select * from addteacher");
			
			while(rs1.next())				// adding rooms to list
			 labtchridch[i].add(rs1.getString(1)+"#"+rs1.getString(2));
			  
			labroomch[i].add("----");
			rs1=stmt.executeQuery("select * from room");
			while(rs1.next())				// adding rooms to list
				 labroomch[i].add(rs1.getString(1));
				 con.close();
			
		
			hoursch[i].add("1");
			hoursch[i].add("2");
			hoursch[i].add("3");
			hoursch[i].add("4");
			
			
			
			p11.add(labname[i]);
			p11.add(labnametxt[i]);
			p11.add(labtchrid[i]);
			p11.add(labtchridch[i]);
			p11.add(hours[i]);
			p11.add(hoursch[i]);
			p11.add(labroom[i]);
			p11.add(labroomch[i]);
			JPanel temp=new JPanel(new FlowLayout());
			p11.add(labGroup[i]);
			temp.add(lGroup[i]);
			temp.add(lGroup1[i]);
			p11.add(temp);
			p11.add(border);
			p11.add(border1);
			
			f4.add(p11);
			
		}
		p11.add(ok);
		p11.add(pnlcancel);
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				checklab();
				if(flag2==0)
				f4.setVisible(false);
				else
					JOptionPane.showMessageDialog(null,"fill all records","off..",JOptionPane.WARNING_MESSAGE);
					
				
			}
		});	
		pnlcancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				for(int i=1; i<=a;i++)
				{
					labnametxt[i].setText("");
					labtchridch[i].select(0);
					hoursch[i].select(0);
					labroomch[i].select(0);
				}
				
			}
		});	
	}
							
}
