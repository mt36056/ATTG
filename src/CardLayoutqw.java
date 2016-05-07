import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CardLayoutqw
{
	JFrame result;
	JPanel tabsPanel;
	
	CardLayout cards; JPanel cardPanel;
	JButton []bTab=new JButton[100];
	JPanel []pTablePanel=new JPanel[100];
	CardLayoutqw()
	{
		result=new JFrame("ATTG");
		result.setLayout(new BorderLayout());
		cards=new CardLayout();
		tabsPanel = new JPanel();
		
		result.add(tabsPanel,BorderLayout.NORTH);
		cardPanel = new JPanel(); 
		cardPanel.setLayout(cards); cards.show(cardPanel, "Fruits"); 
		JPanel firstCard = new JPanel();
		JPanel secondCard = new JPanel();
		firstCard.setBackground(Color.GREEN);
		JButton but = new JButton("apples"); 
		cardPanel.add(firstCard,"apples");
		secondCard.setBackground(Color.blue);
		JButton but1 = new JButton("apples"); 
		cardPanel.add(secondCard,"Apples");
		tabsPanel.add(but);
		tabsPanel.add(but1);
		but.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cards.show(cardPanel,"apples");
			}
		});
		but1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cards.show(cardPanel,"Apples");
			}
		});
		result.add(tabsPanel,BorderLayout.NORTH);
		result.add(cardPanel,BorderLayout.CENTER);
				
		result.setLayout(new FlowLayout());
		result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		result.setSize(500,500);
		result.setLocationRelativeTo(null);
		result.setVisible(true);
	}
	
	public static void main(String []s)
	{
		new CardLayoutqw();
	}
}
