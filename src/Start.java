import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel;

public class Start  
{{
	try {
	  UIManager.setLookAndFeel(new SyntheticaBlueIceLookAndFeel());
	} catch (Exception e) {
		
		e.printStackTrace();
	}	
}	
	 private static JProgressBar progressBar = new JProgressBar();
	    private static int count;
	    private static Timer timer1;
	    static JWindow w=new JWindow();
	Start()
	{
		Thread t1=new Thread()
		{
			public void run()
			{
				try{
				TB.connection();
				TB.stmt.executeUpdate("CREATE TABLE room  (roomno VARCHAR(20) UNIQUE);");
				TB.stmt.executeUpdate("CREATE TABLE addcourse (id VARCHAR(20) UNIQUE NOT NULL, name varchar(30) NOT NULL,year varchar(10) NOT NULL, roomno varchar(10) NOT NULL , noofsub int(10) NOT NULL, nooflab int(3) NOT NULL);");
				TB.stmt.executeUpdate("CREATE TABLE addteacher (id VARCHAR(20) UNIQUE NOT NULL, name varchar(30) NOT NULL);");
				TB.stmt.executeUpdate("CREATE TABLE adduser (uname VARCHAR(20) UNIQUE NOT NULL, pass varchar(30) NOT NULL);");
				stop();}catch(Exception e){stop();}
				}
		};
		t1.start();
		 Container container = w.getContentPane();
	        container.setLayout(null);
	        JPanel panel = new JPanel(null);
	        panel.setBounds(10,10,500,500);
	        panel.add( new ImageImplement(new ImageIcon("gehu.jpg").getImage()),"Center");//image
	        container.add(panel);
	        progressBar.setMaximum(6);
	        progressBar.setBounds(10, 150, 380, 150);
	        container.add(progressBar);
	        loadProgressBar();
	        w.setSize(400, 170);
	        w.setLocationRelativeTo(null);
	        w.setVisible(true);
	        try {
	    		new TB(); 
	        }
	    	 catch (Exception e) {e.printStackTrace();}
	        
	}

    private void loadProgressBar()
    {
        ActionListener al = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
            	
                count++;
                progressBar.setValue(count);
                if (count == 10)
                {
                	w.setVisible(false);
                    timer1.stop();	
                }
        }
    };
    timer1 = new Timer(50, al);
    timer1.start();
  }
public static void main(String []s)
{
	new Start();
}
}
