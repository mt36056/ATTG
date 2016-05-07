import java.awt.*; 
import java.awt.font.TextAttribute;
import java.io.*; 
import java.sql.*; 
import java.util.*; 
import javax.swing.*;
import javax.swing.table.*; 
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

class Course
{
	String name;
	String subject[];
	int creadit[];
	int ctchr[];
	int room;
	int totsub;
	int allsub;
	int breaks;	
	int lbreak;
	String labname[];
	int labhr[];
	int labroom[];
	int labtchr[];
	int totlab;
	int labset[];
	int labrem[];
	int labgrp[];
	int busy[][];
	boolean lunch[][];
}
class Teach
{
	String name;
	int allsub;
	boolean busy[][];
	String room[][]; 
	String subj[][];
	String crs[][];
}
class Room
{
	String room;
	int allsub;
	boolean busy[][];
}
class TmpSub
{
	String tmpsub[][][];
}
class TmpTchr
{
	String tmptchr[][][];
}
class FiSub
{
	String fisub[][][];
}
class FiTch
{
	String fitch[][][];
}
class Result
{
	String result[][];
}
class Resultth
{
	String resultth[][];
}
public class Timetablepro
{
	JFrame f;
	static Connection conx;
	static Statement stmt;
	static Statement stmt1,stmt2 ;
	static ResultSet rs,rs1,rs2,rs3,rs4;
	static  int crs,tch,room,dys,lects,tmptch,tmproom,set,settmp,done,lxx;
	static Room []ro=new Room[100];
	static Teach []th=new Teach[100];
	static Course []co=new Course[100];
	static FiTch []fitch=new FiTch[100];
	static TmpTchr []tmptchr=new TmpTchr[100];
	static FiSub []fisub=new FiSub[100];
	static TmpSub []tmpsub=new TmpSub[100];
	static Result []result=new Result[100];
	static Resultth []resultth=new Resultth[100];
	
	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException **************************************************************/
	Timetablepro() throws ClassNotFoundException, IOException, SQLException
	{
		if(done==0){
			try{
			input();
		
		generate(0,0,0);}
		catch(Exception e)
		{e.printStackTrace();}
	}
		print();
		done=set=settmp=0;
	}
	 void connectionx() throws ClassNotFoundException, SQLException
		{
			Class.forName("com.mysql.jdbc.Driver");
			conx=DriverManager.getConnection("jdbc:mysql://localhost:3306/timetable","root","manish9761759967");
			stmt=conx.createStatement();
			stmt1=conx.createStatement();
			stmt2=conx.createStatement();
		}
	public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException, SQLException 
	{
		new Timetablepro();
	}
	public void input() throws IOException, ClassNotFoundException, SQLException
	 {	  
		int i,tmp1 = 0,tmp2=0;
		
			dys=5;lects=8;
			connectionx();
			 rs=stmt.executeQuery("select count(*) from addcourse");
			while(rs.next())
			crs=Integer.parseInt(rs.getString(1));
			
			rs=stmt.executeQuery("select count(*) from addteacher");
			while(rs.next())
			tch=Integer.parseInt(rs.getString(1));
			
			rs=stmt.executeQuery("select count(*) from room");
			while(rs.next())
			room=Integer.parseInt(rs.getString(1));
			 
			//rooms...
			 
			 rs1 =stmt.executeQuery("select * from room");
			i=0;
			while(rs1.next())
			{ 
				ro[i]=new Room();
				ro[i].room=rs1.getString(1); 
				
				ro[i].busy=new boolean[lects][dys];
				
				i++;
			}
			//tchrs
			
			rs1=stmt.executeQuery("select * from addteacher");
			i=0;
			while(rs1.next())
			{  
				th[i]=new Teach();
				//System.out.println("enter teachr name "+i);
				th[i].name=rs1.getString(2);
				
				th[i].busy=new boolean[lects][dys];
				th[i].room=new	String [lects][dys]; 
				th[i].subj=new	String [lects][dys];
				th[i].crs=new String [lects][dys];
				resultth[i]=new Resultth(); 
				resultth[i].resultth=new String[dys*3][lects+1];
				i++;
			}  
			//courses
			i=0;
			rs1=stmt.executeQuery("select * from addcourse");
			while(rs1.next())
			{
				co[i]=new Course();
				co[i].name=rs1.getString(2)+" "+rs1.getString(1);
				co[i].totsub=Integer.parseInt(rs1.getString(5));
				co[i].totlab=Integer.parseInt(rs1.getString(6));
				co[i].creadit=new int[co[i].totsub];
				co[i].subject=new String[co[i].totsub];
				co[i].labname=new String[co[i].totlab];
				co[i].ctchr=new int[co[i].totsub];
				co[i].labhr=new int[co[i].totlab];
				co[i].labroom=new int[co[i].totlab];
				co[i].labtchr=new int[co[i].totlab];
				co[i].labset=new int[co[i].totlab];
				co[i].labrem=new int[co[i].totlab];
				co[i].labgrp=new int[co[i].totlab];
				co[i].busy=new int[lects][dys];
				co[i].lunch=new boolean[lects][dys];
				
				connectionx();
				rs2=stmt2.executeQuery("select * from "+rs1.getString(2)+"_"+rs1.getString(1));
				int j=0;
				while(rs2.next())//for(j=0;j<co[i].totsub;j++)
				{
					co[i].subject[j]=rs2.getString(1);
					co[i].creadit[j]=Integer.parseInt(rs2.getString(3));
					co[i].allsub+=co[i].creadit[j];//less than m x m
					if(co[i].allsub>lects*dys-dys)
						{JOptionPane.showMessageDialog(null,"more than limited sub in a course","warning",JOptionPane.WARNING_MESSAGE);
						System.exit(0);}
					 
					String tmpstr=rs2.getString(2);
					String good=tmpstr.substring(0,tmpstr.indexOf("#"));
					
					 connectionx();
				    rs3=stmt1.executeQuery(" select id from addteacher");
					tmp2=0;tmp1=0;
					while(rs3.next())
					{
						if(good.equals(rs3.getString(1)))
						{
							tmp1=1;
						}
						if(tmp1==0)
						{tmp2++;
						 
					}}
					 
					//************//
					
					co[i].ctchr[j]=tmp2;
					th[tmp2].allsub+=co[i].creadit[j];
					
					if(th[tmp2].allsub>(lects*dys)-dys)//check total sub limit for a teacher
					{
						System.out.println("more than limited sub to a teacher"+th[tmptch].allsub+""+((lects*dys)-dys));
						JOptionPane.showMessageDialog(null,"more than limited sub to a teacher","warning",JOptionPane.WARNING_MESSAGE);
						System.exit(0);
						 
					}
					j++;
					conx.close();
					 
				}
				 String tmpstr=rs1.getString(4);
				 connectionx();
				    rs4=stmt2.executeQuery("select roomno from room");
					tmp2=0;tmp1=0; 
					while(rs4.next())
					{
						if(tmpstr.equals(rs4.getString(1)))
						{
							tmp1=1;
						}
						if(tmp1==0)
						tmp2++;
						
					} 
					
					co[i].room=tmp2;
					
				
				ro[tmp2].allsub+=co[i].allsub;
				System.out.println("more than limited sub in a room"+ro[tmp2].room+ro[tmp2].allsub);
				
				if(ro[tmp2].allsub>(lects*dys))//check total room sub limit
				{
					JOptionPane.showMessageDialog(null,"more than limited sub in a room","warning",JOptionPane.WARNING_MESSAGE);
					
					System.out.println("more than limited sub in a room"+ro[tmp2].room+ro[tmp2].allsub);
					System.exit(0);
					
				}
				
											//inputting labs************************
				if(Integer.parseInt(rs1.getString(6))>0)
				{
				 rs2=stmt2.executeQuery("select * from "+rs1.getString(2)+"_"+rs1.getString(1)+"lab");
				j=0;
				while(rs2.next()) 
				{
					 
					co[i].labname[j]=rs2.getString(1);
					
					co[i].labhr[j]=Integer.parseInt(rs2.getString(3));
					//lab group limit
					//co[i].labgrp[j]=Integer.parseInt(br.readline)%2;
					co[i].labgrp[j]=Integer.parseInt(rs2.getString(5));
					
					co[i].labrem[j]=co[i].labgrp[j];
					co[i].allsub+=co[i].labhr[j];
					if(co[i].allsub>lects*dys-dys)
					{JOptionPane.showMessageDialog(null,"more than limited sub in a course","warning",JOptionPane.WARNING_MESSAGE);
					System.exit(0);}
						//System.out.println("more than limitd sub in cource");
					tmpstr=rs2.getString(2);
					String good=tmpstr.substring(0,tmpstr.indexOf("#"));
					
					 connectionx();
					    rs3=stmt1.executeQuery("select id from addteacher");
						tmp2=0;tmp1=0;
						while(rs3.next())
						{
							if(good.equals(rs3.getString(1)))
							{
								tmp1=1;
							}
							if(tmp1==0)
							tmp2++;
						}
					
					co[i].labtchr[j]=tmp2;
					
					th[tmp2].allsub+=(co[i].labhr[j]*co[i].labgrp[j]);
					if(th[tmp2].allsub>lects*dys-dys)
					{System.out.println("more than limited subjects");
						JOptionPane.showMessageDialog(null,"more than limited sub  ","warning",JOptionPane.WARNING_MESSAGE);
						System.exit(0);}
					tmpstr=rs2.getString(4);
					connectionx();
					rs4=stmt2.executeQuery("select roomno from room");
						tmp2=0;tmp1=0; 
						while(rs4.next())
						{
							if(tmpstr.equals(rs4.getString(1)))
							{
								tmp1=1;
							}
							if(tmp1==0)
							tmp2++;
						
						}
						
						ro[tmproom].allsub+=(co[i].labhr[j]*co[i].labgrp[j]);
						if(ro[tmproom].allsub>lects*dys)//check total room sub limit
						{
							JOptionPane.showMessageDialog(null,"more than limited sub in a room","warning",JOptionPane.WARNING_MESSAGE);
							
							System.out.println("more than limited sub in a room");
							System.exit(0);
						}
						co[i].labroom[j]=tmp2;
						
						j++;
						conx.close(); conx.close();
				}
			}
				i++;
				conx.close();conx.close();
			}conx.close();
			
			//creating block for final teacher sub and tmp teacher sub
			for(int ii=0;ii<crs;ii++)
			{ 
				fitch[ii]=new FiTch();
				fitch[ii].fitch=new String[lects][dys][2];
				fisub[ii]=new FiSub();
				fisub[ii].fisub=new String[lects][dys][2];
				tmpsub[ii]=new TmpSub();
				tmpsub[ii].tmpsub=new String[lects][dys][2];
				tmptchr[ii]=new TmpTchr();
				 tmptchr[ii].tmptchr=new String[lects][dys][2];
				result[ii]=new Result(); 
				result[ii].result=new String[dys*4][lects+1];
				
			}
			
		}
	 
 void save(int x)
	 {
	 	int i,j,k;
	 	for(i=0;i<=x;i++)
	 	for(j=0;j<lects;j++)
	 	for(k=0;k<dys;k++)
	 	{ 
	 		fitch[i].fitch[j][k][0]=tmptchr[i].tmptchr[j][k][0];
	 		fisub[i].fisub[j][k][0]=tmpsub[i].tmpsub[j][k][0];
	 		fitch[i].fitch[j][k][1]=tmptchr[i].tmptchr[j][k][1];
	 		fisub[i].fisub[j][k][1]=tmpsub[i].tmpsub[j][k][1];
	 	}
	 }
 boolean freelab(int x,int i,int j,int k) throws IOException
 {
 	int z;boolean c=true;
 	
 	for(z=0;z<co[i].labhr[x];z++)
 	{
 		 
 		if( (th[co[i].labtchr[x]].busy[j+z][k]!=false) 
 			
 			|| (ro[co[i].labroom[x]].busy[j+z][k]!=false) ||  
 			   co[i].busy[j+z][k]!=0)
 	c=false;//return false;
 	}
 	if(lects/2<j+co[i].labhr[x] && (lects/2)-1>=j)
 	c=false;//return false;
 	if(co[i].labrem[x]==0)
 	c=false;//return false;
 	
	if(co[i].busy[j][k]==2 && co[i].labgrp[x]==1)
	c=false;//return false;
	 
	 	
	if(co[i].labset[x]==1 && tmpsub[i].tmpsub[j][k][1]!=null)
	c=false; 
	 	
	if(co[i].labset[x]==2 && tmpsub[i].tmpsub[j][k][0]!=null)
	c=false;//return false;
	
 	return c; 
 }
   void generate(int i,int j,int k)throws Exception
  {
	int x; 
	
	
	if((i<crs)&&(set<crs))
	{
		if(((j==(lects/2)-1 && (ro[co[i].room].busy[j+1][k]==false)) 		
				|| ((j==(lects/2) && (co[i].lunch[0][k]==false ) )))&& 
				(co[i].allsub<((dys*lects)-co[i].breaks) ))									//for lunch
		{
			tmptchr[i].tmptchr[j][k][0]="---";
			tmpsub[i].tmpsub[j][k][0]="lnch";
			if(j==(lects/2)-1)
			co[i].lunch[0][k]=true;
			else
			co[i].lunch[1][k]=true;
			co[i].breaks++;
			co[i].lbreak++;
			co[i].busy[j][k]=1;//for busy of course
			for(lxx=1;((i+(j+(k+1)/dys)/lects)<crs&&((j+(k+1)/dys)%lects)<lects&&((k+1)%dys)<dys)&& co [ i+(j+(k+lxx)/dys)/lects].busy[ (j+(k+lxx)/dys)%lects][(k+lxx)%dys]!=0 ; lxx++)
			{}
			//save(i);print();
			generate(i+(j+(k+lxx)/dys)/lects,(j+(k+lxx)/dys)%lects,(k+lxx)%dys);
			if(done==0)
			{
				tmptchr[i].tmptchr[j][k][0]=null;
				tmpsub[i].tmpsub[j][k][0]=null;
				co[i].busy[j][k]=0;
			if(j==(lects/2)-1)
				co[i].lunch[0][k]=false;
			else
				co[i].lunch[1][k]=false;
			co[i].breaks--;
			co[i].lbreak--;
			}
		}
																		//		**	subjects
		for(x=0;x<co[i].totsub;x++)
		{
			if(j!=(lects/2) || co[i].lunch[0][k]!=false)
			if((th[co[i].ctchr[x]].busy[j][k]==false)
					&& (ro[co[i].room].busy[j][k]==false) 
					&& (co[i].creadit[x]>0) &&
					(co[i].allsub<=((dys*lects)-co[i].breaks))
					&& co[i].busy[j][k]==0)
			{
				co[i].busy[j][k]=1;
				tmptchr[i].tmptchr[j][k][0]=th[co[i].ctchr[x]].name;
				tmpsub[i].tmpsub[j][k][0]=co[i].subject[x];	
				co[i].creadit[x]--;
				th[co[i].ctchr[x]].busy[j][k]=true;
				ro[co[i].room].busy[j][k]=true;
				
				th[co[i].ctchr[x]].crs[j][k]=co[i].name;
				th[co[i].ctchr[x]].subj[j][k]=co[i].subject[x];
				th[co[i].ctchr[x]].room[j][k]=ro[co[i].room].room;
				
				if(j==(lects-1) && k==(dys-1))
					set++;
				if(settmp<set)
				{
					settmp=set;
					save(i); 
				}
				for(lxx=1;((i+(j+(k+1)/dys)/lects)<crs&&((j+(k+1)/dys)%lects)<lects&&
						((k+1)%dys)<dys)&& co [ i+(j+(k+lxx)/dys)/lects].busy[ (j+(k+lxx)/dys)%lects][(k+lxx)%dys]!=0
						 ; lxx++)
						{}
				 generate(i+(j+(k+lxx)/dys)/lects,(j+(k+lxx)/dys)%lects,(k+lxx)%dys);
					
				if(done==0)
				{
				
					co[i].busy[j][k]=0;
					
					if(j==(lects-1) && k==(dys-1))				//backtrack start
					set--;	
				
				co[i].creadit[x]++;
				tmptchr[i].tmptchr[j][k][0]=null;
				tmpsub[i].tmpsub[j][k][0]=null;

				th[co[i].ctchr[x]].busy[j][k]=false;
				ro[co[i].room].busy[j][k]=false;
				
				th[co[i].ctchr[x]].crs[j][k]=null;
				th[co[i].ctchr[x]].subj[j][k]=null;
				th[co[i].ctchr[x]].room[j][k]=null;
				
				}
				
			}
		}
		//labs*********************************
		for(x=0;x<co[i].totlab;x++)
		 {
		 	 
			if(j!=(lects/2) || co[i].lunch[0][k]!=false)
				 
			if( (j+co[i].labhr[x]<=lects) && co[i].labhr[x]>0 && freelab(x,i,j,k))
			  { int z,lh=co[i].labhr[x],strrow=0,initv;
			 
				for(z=0;z<lh;z++)
				{
					if(co[i].busy[j+z][k]==0 && co[i].labgrp[x]==2)
					co[i].busy[j+z][k]=2;
					else
					co[i].busy[j+z][k]=1;	//  for busy of course
					if(co[i].labset[x]==1 || (co[i].labset[x]==0 && tmpsub[i].tmpsub[j+z][k][0]!=null))
					{
						 
						tmptchr[i].tmptchr[j+z][k][1]=th[co[i].labtchr[x]].name;
						tmpsub[i].tmpsub[j+z][k][1]=co[i].labname[x];
						strrow=1;
					}
					else
					{
						tmptchr[i].tmptchr[j+z][k][0]=th[co[i].labtchr[x]].name;
						tmpsub[i].tmpsub[j+z][k][0]=co[i].labname[x];
					}
					th[co[i].labtchr[x]].busy[j+z][k]=true;
					ro[co[i].labroom[x]].busy[j+z][k]=true;	
					
					th[co[i].labtchr[x]].crs[j+z][k]=co[i].name;
					th[co[i].labtchr[x]].subj[j+z][k]=co[i].labname[x];
					th[co[i].labtchr[x]].room[j+z][k]=ro[co[i].labroom[x]].room;
				
					if(co[i].labrem[x]==1)
					co[i].labhr[x]--;	
				}
				initv=co[i].labset[x];
				if(co[i].labset[x]==0 && co[i].labgrp[x]==2)
				if(strrow==1)
				co[i].labset[x]=2;
				else
				co[i].labset[x]=1;
				
				co[i].labrem[x]--;
				
				if(j==(lects-1) && k==(dys-1))
					set++;
				
				if(settmp<set)
				{
					settmp=set;
					save(i); 
				}
				if(	co[i].busy[j][k]==2 && x<co[i].totlab-1)
					generate(i,j,k);
					
				for(lxx=1;((i+(j+(k+1)/dys)/lects)<crs&&((j+(k+1)/dys)%lects)<lects&&((k+1)%dys)<dys)&& co [ i+(j+(k+lxx)/dys)/lects].busy[ (j+(k+lxx)/dys)%lects][(k+lxx)%dys]!=0
						 ; lxx++)
						{}
				generate(i+(j+(k+lxx)/dys)/lects,(j+(k+lxx)/dys)%lects,(k+lxx)%dys);
			   	
				if(done==0)
				{
				 
				for(z=0;z<lh;z++)
				{
					if(co[i].busy[j+z][k]==1)
					if( co[i].labgrp[x]==1)
					co[i].busy[j+z][k]=0;	//  for busy of course
					else
					co[i].busy[j+z][k]=2;	//  for busy of course
					else
					co[i].busy[j+z][k]=0;	//  for busy of course
					
					th[co[i].labtchr[x]].crs[j+z][k]=null;
					th[co[i].labtchr[x]].subj[j+z][k]=null;
					th[co[i].labtchr[x]].room[j+z][k]=null;
				
					if(strrow==1)
					{
						tmptchr[i].tmptchr[j+z][k][1]=null;
						tmpsub[i].tmpsub[j+z][k][1]=null;
					}
					else
					{
						tmptchr[i].tmptchr[j+z][k][0]=null;
						tmpsub[i].tmpsub[j+z][k][0]=null;
					}
					/***********************/
					
					 
					th[co[i].labtchr[x]].busy[j+z][k]=false;
					ro[co[i].labroom[x]].busy[j+z][k]=false;	
					if(co[i].labrem[x]==0)
					co[i].labhr[x]++;
						
				}
				co[i].labset[x]=initv;
				
				co[i].labrem[x]++;
				
				if(j==(lects-1) && k==(dys-1))		
					set--;	
				}
			  }
		 	}
		
																							//breaks************
		 																
		if(j!=(lects/2) || co[i].lunch[0][k]!=false)
				
		if(co[i].allsub<((lects*dys)-(dys-co[i].lbreak)-(co[i].breaks))&& co[i].busy[j][k]==0)
		{
			
			co[i].breaks++;
				tmptchr[i].tmptchr[j][k][0]="---";
				tmpsub[i].tmpsub[j][k][0]="brk";
			
			if(j==(lects-1) && k==(dys-1))
				set++;
				
			if(settmp<set)
			{
				settmp=set;
				save(i);
			}
			for(lxx=1;((i+(j+(k+1)/dys)/lects)<crs&&((j+(k+1)/dys)%lects)<lects&&((k+1)%dys)<dys)&& co [ i+(j+(k+lxx)/dys)/lects].busy[ (j+(k+lxx)/dys)%lects][(k+lxx)%dys]!=0
					 ; lxx++)
					{}
		 	 generate(i+(j+(k+lxx)/dys)/lects,(j+(k+lxx)/dys)%lects,(k+lxx)%dys);
		 	if(done==0)
			{
				tmptchr[i].tmptchr[j][k][0]=null;
				tmpsub[i].tmpsub[j][k][0]=null;
			 
				if(j==(lects-1) && k==(dys-1))				//backtrack start
				set--;
				co[i].breaks--;	
			}
		}
	}
	else
	{ 
		done=1;
	}	
  }  
   
@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
void show()
{
	{
		try
	    {
	      UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
	    } 
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	    }}
	   int i;JPanel inner[]=new JPanel[crs];
	   f=new JFrame("xxxxxxxxxxx");
	   ImageIcon img1 = new ImageIcon("C:\\Users\\UNDEAD\\VinayNegi\\timetablegenerator\\src\\Time_table\\icons\\45.jpg");				//setting frame 2 icon on title bar
		f. setIconImage(img1.getImage());
		
	  	JPanel pt = new JPanel(); 
	  	 pt.setLayout(new GridLayout(crs,1));
	  	 String column[]={"DAYS","lect 1","lect 2","lect 3","lect 4","lect 5","lect 6","lect 7","lect 8"};
	  	
	  	JTable jtt[]=new JTable[crs];
	  	JScrollPane sp[]=new JScrollPane [crs],spx=new JScrollPane (pt);
	  	for(i=0;i<crs;i++)
	  	{
	  		inner[i]=new JPanel(new GridBagLayout());
	  		GridBagConstraints gbc = new GridBagConstraints();

	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        gbc.gridx = 0;
	        gbc.ipady = 20;   
	        gbc.gridy = 0;
	        
	        jtt[i]=new JTable(result[i].result,column);
	        Random rand=new Random();
	        
	        
	  	  	JLabel lblHeading = new JLabel("  "+co[i].name.toUpperCase()),
	  	  			lb2= new JLabel(co[i].name.toUpperCase());
	  	  	lblHeading.setFont(new Font("Garamond",Font.BOLD,130));
	  	  	lblHeading.setForeground(new Color(255,255,255,40));
	  	   
	  	  	jtt[i].setFont(new Font("Times New Roman",Font.TRUETYPE_FONT,14));
	  	  	
	  	  Font font = new Font("Garamond",Font.BOLD,25);
	  	Map attributes = font.getAttributes();
	  	attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	  	lb2.setFont(font.deriveFont(attributes));
	  	  
	  	  	inner[i].add(lb2,gbc);
	  	  gbc.fill = GridBagConstraints.HORIZONTAL;
	      gbc.ipady = 20;   
	      gbc.gridx = 0;
	      gbc.gridy = 1;
	  	inner[i].add(lblHeading,gbc);
	  	jtt[i].setRowHeight(40);
	  	jtt[i].setBackground(new Color((Math.abs(rand.nextInt()))%255,(Math.abs(rand.nextInt()))%255,(Math.abs(rand.nextInt()))%255));
	  	jtt[i].setGridColor(Color.green);//black
	  	sp[i]=new JScrollPane(jtt[i]);  
	  	Dimension d=new Dimension();
	  	d.width=924;
	  	d.height=810;
	  	
	  	sp[i].setPreferredSize(d);
	  	jtt[i].setRowMargin(10);
	  	 JTableHeader header = jtt[i].getTableHeader();
	      header.setBackground(Color.gray);
	      header.setForeground(Color.black);
	      header.setFont(new Font("Garamond",Font.TRUETYPE_FONT,18));
	  	jtt[i].getColumnModel().getColumn(0).setMaxWidth(100);
	  	jtt[i].getColumnModel().getColumn(0).setMinWidth(100);
	  	
		for(int x=1;x<=lects;x++)
		{
	  	jtt[i].getColumnModel().getColumn(x).setMaxWidth(100);
	  	jtt[i].getColumnModel().getColumn(x).setMinWidth(100);
	  	}
		
		jtt[i].setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

	        @Override
	        public Component getTableCellRendererComponent(JTable table, 
	                Object value, boolean isSelected, boolean hasFocus,
	                int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, 
	                value, isSelected, hasFocus, row, column);
	            c.setBackground(row%2==0 ? Color.black : Color.gray ); 
	            c.setForeground(row%2==0 ? Color.green : Color.black );
	            return c;
	        };
	    }); 
		TableColumn tm ;
		tm = jtt[i].getColumnModel().getColumn(0);
     tm.setCellRenderer(new ColorColumnRenderer( new Color(30,30,30), Color.green));
      jtt[i].setEnabled(false);//for editing of cells
	  	jtt[i].getTableHeader().setReorderingAllowed(false);//stop draging of collumns
	  	gbc.fill = GridBagConstraints.HORIZONTAL;
	      gbc.ipady = 20;   
	      gbc.gridx = 0;
	      gbc.gridy = 1;
	  	inner[i].add(sp[i],gbc);
	  	inner[i].setBorder(BorderFactory.createLineBorder(new Color(0,255,0,100)));
	  	 pt.add(inner[i]);
	  	}
	  	f.add(spx);  
	  	f.setSize(1000,900);  
	    f.setVisible(true); 
}
@SuppressWarnings("unchecked")
void showth()
{
   int i;JPanel inner[]=new JPanel[tch];
   f=new JFrame("xxxxxxxxxxx");
   ImageIcon img1 = new ImageIcon("C:\\Users\\UNDEAD\\VinayNegi\\timetablegenerator\\src\\Time_table\\icons\\45.jpg");				//setting frame 2 icon on title bar
	f. setIconImage(img1.getImage());
	
  	JPanel pt = new JPanel(); 
  	 pt.setLayout(new GridLayout(tch,1));
  	 String column[]={"DAYS","lect 1","lect 2","lect 3","lect 4","lect 5","lect 6","lect 7","lect 8"};
  	
  	JTable jtt[]=new JTable[tch];
  	JScrollPane sp[]=new JScrollPane [tch],spx=new JScrollPane (pt);
  	for(i=0;i<tch;i++)
  	{
  		inner[i]=new JPanel(new GridBagLayout());
  		GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.ipady = 20;   
        gbc.gridy = 0;
        
        jtt[i]=new JTable(resultth[i].resultth,column);
        Random rand=new Random();
        
        
  	  	JLabel lblHeading = new JLabel("  "+th[i].name.toUpperCase()),
  	  			lb2= new JLabel(th[i].name.toUpperCase());
  	  	lblHeading.setFont(new Font("Garamond",Font.BOLD,130));
  	  	lblHeading.setForeground(new Color(255,255,255,40));
  	   
  	  	jtt[i].setFont(new Font("Times New Roman",Font.TRUETYPE_FONT,14));
  	  	
  	  Font font = new Font("Garamond",Font.BOLD,25);
  	Map attributes = font.getAttributes();
  	attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
  	lb2.setFont(font.deriveFont(attributes));
  	  
  	  	inner[i].add(lb2,gbc);
  	  gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.ipady = 20;   
      gbc.gridx = 0;
      gbc.gridy = 1;
  	inner[i].add(lblHeading,gbc);
  	jtt[i].setRowHeight(40);
  	jtt[i].setBackground(new Color((Math.abs(rand.nextInt()))%255,(Math.abs(rand.nextInt()))%255,(Math.abs(rand.nextInt()))%255));
  	jtt[i].setGridColor(Color.green);//black
  	sp[i]=new JScrollPane(jtt[i]);  
  	Dimension d=new Dimension();
  	d.width=924;
  	d.height=610;
  	
  	sp[i].setPreferredSize(d);
  	jtt[i].setRowMargin(10);
  	 JTableHeader header = jtt[i].getTableHeader();
      header.setBackground(Color.gray);
      header.setForeground(Color.black);
      header.setFont(new Font("Garamond",Font.TRUETYPE_FONT,18));
  	jtt[i].getColumnModel().getColumn(0).setMaxWidth(100);
  	jtt[i].getColumnModel().getColumn(0).setMinWidth(100);
  	
	for(int x=1;x<=lects;x++)
	{
  	jtt[i].getColumnModel().getColumn(x).setMaxWidth(100);
  	jtt[i].getColumnModel().getColumn(x).setMinWidth(100);
  	}
	
	jtt[i].setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

        @Override
        public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component c = super.getTableCellRendererComponent(table, 
                value, isSelected, hasFocus, row, column);
            c.setBackground(row%2==0 ? Color.black : Color.gray ); 
            c.setForeground(row%2==0 ? Color.green : Color.black );
            return c;
        };
    }); 
	TableColumn tm ;
	tm = jtt[i].getColumnModel().getColumn(0);
 tm.setCellRenderer(new ColorColumnRenderer( new Color(30,30,30), Color.green));
  jtt[i].setEnabled(false);//for editing of cells
  	jtt[i].getTableHeader().setReorderingAllowed(false);//stop draging of collumns
  	gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.ipady = 20;   
      gbc.gridx = 0;
      gbc.gridy = 1;
  	inner[i].add(sp[i],gbc);
  	inner[i].setBorder(BorderFactory.createLineBorder(new Color(0,255,0,100)));
  	 pt.add(inner[i]);
  	}
  	f.add(spx);  
  	f.setSize(1000,800);  
    f.setVisible(true);  
}
   void print()
  {
  	int i,j,k,xx = 0;
  	 
  	 for(i=0;i<crs;i++)
  	{
  		 
  		System.out.print("\t"+co[i].name);
  		for(j=0;j<dys;j++)
  		System.out.format("\t day %d \t",(j+1));
  		System.out.println("\n............................................................................\n");
  		for(j=0;j<=lects;j++)
  		{
  			System.out.format("\n lecture  %2d ",(j+1));
  			for(k=0;k<dys;k++)
  			{
  				if(j==0)
  				{
  					if(k==0)
  	  					result[i].result[k*4][xx]="Monday";
  					if(k==1)
  	  					result[i].result[k*4][xx]="Tuesday";
  					if(k==2)
  	  					result[i].result[k*4][xx]="Wednesday";
  					if(k==3)
  	  					result[i].result[k*4][xx]="Thrusday";
  					if(k==4)
  	  					result[i].result[k*4][xx]="Friday";
  				}
  					
  				else
  				{
  				result[i].result[k*4][xx]=fisub[i].fisub[j-1][k][0];
  				result[i].result[k*4+2][xx]=fisub[i].fisub[j-1][k][1];
  				System.out.format("%15s",fisub[i].fisub[j-1][k][0]);//
  				System.out.format("%15s",fisub[i].fisub[j-1][k][1]);//
  				//if(fisub[i].fisub[j][k][1]!=null)
  			//	System.out.format("%15s",fisub[i].fisub[j-1][k][1]);//
  				//	else
  				System.out.format(" \t");
  				
  				}
  			} 
  			System.out.print("\n              ");
  			for(k=0;k<dys;k++)
  			{
  				if(j==0)
  					result[i].result[k*4+1][xx]=null;
  				else
  				 {
  					result[i].result[k*4+1][xx]=fitch[i].fitch[j-1][k][0];
  					result[i].result[k*4+3][xx]=fitch[i].fitch[j-1][k][1];
  					System.out.format("%15s",fitch[i].fitch[j-1][k][0]);
  					System.out.format("%15s",fitch[i].fitch[j-1][k][1]);//tmptchr[i].tmptchr[j].tmptchr[k].tmptchr);
  					//if(fitch[i].fitch[j][k][1]!=null)
  					//System.out.format("%15s",fitch[i].fitch[j][k][1]);//
  						//else
  					System.out.format(" \t");
  	  				
  				 }
  			}
  			xx=(xx+1)%(lects+1);
  			System.out.println();
  		}System.out.println();
  	}
  	 printtchr();
  	show();
  	showth();
  }
   void printtchr()
   {
   	int i,j,k,xx=0 ;
   	 
    for(i=0;i<tch;i++)
  	{
  		 
  		System.out.print("\t"+th[i].name);
  		for(j=0;j<dys;j++)
  		System.out.format("\t day %d \t",(j+1));
  		System.out.println("\n............................................................................\n");
  		for(j=0;j<=lects;j++)
  		{
  			System.out.format("\n lecture  %2d ",(j+1));
  			for(k=0;k<dys;k++)
  			{
  				if(j==0)
  				{
  					if(k==0)
  	  					resultth[i].resultth[k*3][xx]="Monday";
  					if(k==1)
  	  					resultth[i].resultth[k*3][xx]="Tuesday";
  					if(k==2)
  	  					resultth[i].resultth[k*3][xx]="Wednesday";
  					if(k==3)
  	  					resultth[i].resultth[k*3][xx]="Thrusday";
  					if(k==4)
  	  					resultth[i].resultth[k*3][xx]="Friday";
  				}
  					
  				else
  				{
  				resultth[i].resultth[k*3][xx]=th[i].crs[j-1][k];
  				System.out.format("%15s",th[i].crs[j-1][k]);//
  				//if(fisub[i].fisub[j][k][1]!=null)
  			//	System.out.format("%15s",fisub[i].fisub[j-1][k][1]);//
  				//	else
  				System.out.format(" \t");
  				
  				}
  			} 
  			System.out.print("\n              ");
  			for(k=0;k<dys;k++)
  			{
  				if(j==0)
  					resultth[i].resultth[k*3+1][xx]=null;
  				else
  				 {
  					resultth[i].resultth[k*3+1][xx]=th[i].subj[j-1][k];
  	  				
  					System.out.format("%15s",th[i].subj[j-1][k]);//

  					System.out.format(" \t");
  	  				 
  	  				
  				 }
  			}
  			System.out.print("\n              ");
  			for(k=0;k<dys;k++)
  			{
  				if(j==0)
  					resultth[i].resultth[k*3+2][xx]=null;
  				else
  				 {
  					resultth[i].resultth[k*3+2][xx]=th[i].room[j-1][k];
  	  				
  					System.out.format("%15s",th[i].room[j-1][k]);//

  					System.out.format(" \t");
  	  				 
  	  				
  				 }
  			}
  			xx=(xx+1)%(lects+1);
  			System.out.println();
  		}System.out.println();
  	}
    
   } 
}

@SuppressWarnings("serial")
class ColorColumnRenderer extends DefaultTableCellRenderer
{
   Color bkgndColor, fgndColor;
     
   public ColorColumnRenderer(Color bkgnd, Color foregnd) {
      super();
      bkgndColor = bkgnd;
      fgndColor = foregnd;
   }
     
   public Component getTableCellRendererComponent
        (JTable table, Object value, boolean isSelected,
         boolean hasFocus, int row, int column)
   {
      Component cell = super.getTableCellRendererComponent
         (table, value, isSelected, hasFocus, row, column);
  
      cell.setBackground( bkgndColor );
      cell.setForeground( fgndColor );
      
      return cell;
   }
} 

 