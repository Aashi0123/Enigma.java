import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Main {

	static ArrayList<Integer> temprary = new ArrayList<>();
	static char [] alphabat = new char[100];

	final static String IMG_LINK="./src/grey.jpg";
	static ArrayList<Integer> random_serial = new ArrayList<>();
	static ArrayList<Integer> upperCaseIndex = new ArrayList<>();
	static JTextField textbox_serial;
	static Hashtable<String,Node> table = new Hashtable<>(); 
	static Hashtable<Integer,String> User_code_info = new Hashtable<>();
	
	final static int frameWidth=500;
	final static int frameHeight=330;
	static JFrame frame;
	static Dimension dim;
	static String input;
	static int serial;
	
	
	public static void main(String[] args) {
		initializeUI();
		
	}
	private static void initializeUI() {
		 dim = Toolkit.getDefaultToolkit().getScreenSize();
		 JFrame frame = new JFrame();
		  try {
frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(IMG_LINK)))));
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		 frame.setSize(frameWidth, frameHeight);
		 frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		 frame.setLayout(null);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setTitle(" E N I G M A");
		 JPanel panel= new JPanel();
		 panel.setOpaque(false);
		panel.setBounds(3,3,frameWidth-23,frameHeight-47);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Enigma"));
		 TitledBorder titledBorder = (TitledBorder)panel.getBorder();
		    titledBorder.setTitleColor(Color.WHITE);
		panel.setLayout(null);
		frame.add(panel);
		
		JTextField textbox  = new JTextField();
		textbox.setVisible(true);
		textbox.setBounds(frameWidth/4, frameHeight/4,115,20);
		panel.add(textbox);
		
		
		 textbox_serial  = new JTextField();
		textbox_serial.setVisible(true);
		textbox_serial.setBounds(frameWidth/4+160, frameHeight/4-3,115,20);
		panel.add(textbox_serial);
		
		JTextField textbox_result  = new JTextField();
		textbox_result.setVisible(true);
		textbox_result.setBounds(frameWidth/4+160, frameHeight/2+34,115,20);
		panel.add(textbox_result);
		
		frame.add(textbox);
		
		JLabel lbl = new JLabel();
		lbl.setBounds(frameWidth/4+10, frameHeight/4-30,100,20);
		lbl.setText("Enter input");
		panel.add(lbl);
		

		JLabel lbl_serial = new JLabel();
		lbl_serial.setBounds(frameWidth/2+36, frameHeight/4-30,100,20);
		lbl_serial.setText("Key:");
		panel.add(lbl_serial);

		JLabel lbl_result = new JLabel();
		lbl_result.setBounds(frameWidth/2+36, frameHeight/3+60,100,20);
		lbl_result.setText("Result:");
		panel.add(lbl_result);
		
		
		
		
		JButton button = new JButton();
		
		button.setBounds(frameWidth/4+17, frameHeight/4+28,80,20);
		button.setText("Encode");
		ArrayList<Character> Code = new ArrayList<>();//Take code characters
		Slinkedlist save = new Slinkedlist();

		panel.add(button);
		
		JTextField textbox_decode  = new JTextField();
		textbox_decode.setVisible(true);
		textbox_decode.setBounds(frameWidth/4, frameHeight/3+90,115,20);
		panel.add(textbox_decode);
		
		JLabel lbl_decode = new JLabel();
		lbl_decode.setBounds(frameWidth/4+10, frameHeight/3+60,100,20);
		lbl_decode.setText("Enter Key");
		panel.add(lbl_decode);
		
		JButton button_decode = new JButton();
		
		button_decode.setBounds(frameWidth/4+20, frameHeight/3+120,80,20);
		button_decode.setText("Decode");
	
	
		panel.add(button_decode);
		
		
		JRadioButton radio_btn_encode = new JRadioButton();
		radio_btn_encode.setBounds(frameWidth/6, frameHeight/12,80,20);
		radio_btn_encode.setText("Encode");
		radio_btn_encode.setOpaque(false);
		
		JRadioButton radio_btn_decode = new JRadioButton();
		radio_btn_decode.setBounds(frameWidth/6, frameHeight/2-20,80,20);
		radio_btn_decode.setText("Decode");
		radio_btn_decode.setOpaque(false);
		
		
		radio_btn_encode.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if(radio_btn_decode.isSelected()) {
		    		radio_btn_decode.setSelected(false);
		    		radio_btn_encode.setSelected(true);
		    	}

	    		textbox.setEnabled(true);
	    		button.setEnabled(true);
	    		textbox_decode.setEnabled(false);
	    		button_decode.setEnabled(false);
		    }
		});
		radio_btn_decode.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if(radio_btn_encode.isSelected()) {
		    		radio_btn_encode.setSelected(false);
		    		radio_btn_decode.setSelected(true);
		    	}
	    		textbox.setEnabled(false);
	    		button.setEnabled(false);
	    		textbox_decode.setEnabled(true);
	    		button_decode.setEnabled(true);
		    }
		});
		
		panel.add(radio_btn_encode);
		panel.add(radio_btn_decode);
		
	    textbox_result.setEnabled(false);
	    textbox_decode.setEnabled(false);
	    radio_btn_decode.setSelected(false);
	    radio_btn_encode.setSelected(true);
	    button_decode.setEnabled(false);
	    textbox_serial.setEnabled(false);
	    
		button.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	textbox_result.setText("");
		    	textbox_decode.setText("");
		    	upperCaseIndex = new ArrayList<>();
		    	String in = getInputWithoutUpperCase();
		    	String out = getInputWithUpperCase(in);

		    	//System.out.println(in);
		    	//System.out.println(out);
		    	String temp2 = null ;
				Node Address = null;
		    	String word = in;
		    	
				if(save.Available(word) == true)
				{
					Same_Case sc= new Same_Case();
					//Encoding Process...
					temp2 = sc.ENCODING(word);
					Address = save.Addfirst(word);
					table.put(temp2, Address);
					word =null;
				}
				if(save.Available(word) == false && word != null)
				{
					//Encoding Process...
					temp2 = ENCODING(word);
					Address = save.Addfirst(word);
					table.put(temp2, Address);
					word =null;
				}
		    	
			    textbox_serial.setText(""+serial);
			   
			    textbox.setEnabled(false);
			    radio_btn_encode.setSelected(false);
			    button.setEnabled(false);
			    textbox_serial.setEnabled(true);

			    textbox_result.setEnabled(true);
			    textbox_decode.setEnabled(true);
			    radio_btn_decode.setSelected(true);
			    button_decode.setEnabled(true);
			    textbox.setText("");
		    }

			private String getInputWithUpperCase(String in) {
				StringBuilder input = new StringBuilder(in);
				char[] arr = in.toCharArray();
				
	
			for(int i=0;i< upperCaseIndex.size();i++) {
					int val = upperCaseIndex.get(i);
					arr[val]=Character.toUpperCase(arr[val]);
					
				}

				return new String(arr);
				
			}

			private String getInputWithoutUpperCase() {
				for(int i=0;i< textbox.getText().length();i++) {
					if(Character.isUpperCase(textbox.getText().charAt(i)))
			    	upperCaseIndex.add(i);
				}
		    	
				return textbox.getText().toLowerCase();
			}
		});
		
		button_decode.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	int Serial = Integer.parseInt(textbox_decode.getText());
				if(User_code_info.containsKey(Serial))
				{
					String temp = User_code_info.get(Serial);
	
				String Result = Decoding(Code);
textbox_result.setText( getInputWithUpperCase(""+Result));
				
				Code.removeAll(Code);

				}if(User_code_info.containsKey(Serial) == false)
				{
					System.out.println("Invalid Key.");
				}
			    textbox_serial.setEnabled(true);
			    textbox.setEnabled(true);
			    radio_btn_encode.setSelected(true);
			    button_decode.setEnabled(false);

			    textbox_decode.setEnabled(false);
			    radio_btn_decode.setSelected(false);
			    textbox_serial.setEnabled(false);
			    button.setEnabled(true);
			  
		    }

			private String getInputWithUpperCase(String string) {
				char[] arr = string.toCharArray();
				
				for(int i=0;i< upperCaseIndex.size();i++) {
					int val = upperCaseIndex.get(i);
					arr[val]=Character.toUpperCase(arr[val]);
				}

				return new String(arr);
			}
		});
		
		frame.setVisible(true);
		
	}
	
	public static String ENCODING(String word)
	{
		String Temp = " ";
		int initial_temp  = temprary.size();
		
		
		for(int loop = 0 ; loop< word.length() ; loop++)
		{
			alphabat [loop] = word.charAt(loop);
		}
		int check = 0 ;
		while(check != word.length())
		{
			char alpha = alphabat [check];
			int value = alpha ;
			for(int i = 65; i<91 ; i++)
			{
				if(i == value)
				{
					if(value>=65 && value<84)
					{
						int Val = 0;
						
						if(value %2 == 0)
						{
							for(int loop = 0 ; loop <2 ; loop++)
							{
								Val = value+(loop+3);
								if(temprary.size() == 0)
								{
									temprary.add(Val);
								}
								else
								{
									if(temprary.contains(Val))
									{
					int lastadd = temprary.size()-1;
					int lastvalue = 5+temprary.get(lastadd);
					if(lastvalue>=65 && lastvalue<91)
	{					temprary.add(lastvalue-1);
	}else
	{
		temprary.add(lastvalue-8);
		}
				
	}
	else
		{
			temprary.add(Val);
		}
		}
								
		}
		}
			if(value %2 != 0)
			{
			for(int loop = 0 ; loop <2 ; loop++)
			{
			Val = value+(loop+3);
		if(temprary.size() == 0)
			{
		temprary.add(Val);
		}
			else
		{
		if(temprary.contains(Val))
			{
		int lastadd = temprary.size()-1;
		int lastvalue = 5+temprary.get(lastadd);
		if(lastvalue>=65 && lastvalue<91)
		{
											temprary.add(lastvalue+3);
		}else
		{
											temprary.add(lastvalue-7);
										}
										
		}
		else
		{
		temprary.add(Val);
		}
			}
			}
			}
						
			}
					
				if(value >= 84 && value < 91)
			{	int Val = 0;
						if(value %2 == 0)
						{
						
						for(int loop = 10 ; loop >8; loop--)
						{
							Val = value - (loop-4);
							if(temprary.size() == 0)
							{
								temprary.add(Val);
							}
							else
							{
			if(temprary.contains(Val))
								{
			int lastadd = temprary.size()-1;
			int lastvalue = 5+temprary.get(lastadd);
				if(lastvalue>=65 && lastvalue<91)
				{
				temprary.add(lastvalue-1);
			} else
				{
				temprary.add(lastvalue-8);
						}
								
					}
	else
	{
	temprary.add(Val);
		}
		}
			}
						}
				if(value %2 != 0)
						{
						for(int loop = 10 ; loop >8; loop--)
						{
							Val = value - (loop-7);
							if(temprary.size() == 0)
							{
								temprary.add(Val);
							}
							else
							{
								if(temprary.contains(Val))
								{
									int lastadd = temprary.size()-1;
									int lastvalue = 4+temprary.get(lastadd);
									if(lastvalue>=65 && lastvalue<91)
									{
										temprary.add(lastvalue-1);
									}if(lastvalue>=91)
									{
										temprary.add(lastvalue-10);
					}
			
			}
			else
			{
			temprary.add(Val);
		}
				}
				}
						}
						
					}
									
				}	
			}
			check +=1;
	
		}
		
		//Creating Code ...
		for(int i = initial_temp ; i < temprary.size()  ; i++)
		{
			int temp = temprary.get(i);
			Temp +=(char)temp;
		}
		
		//Adding Serial number to identify number of code.
		int Serial = Rand();
		
		serial=Serial;
		User_code_info.put(Serial, Temp);
		

		return Temp;	
	}

	public  static int Rand()
	{
		Random serial = new Random();
		int num;
		num = serial.nextInt(100);
		if(num == 0)
		{
			num = num+1;
		}
		if(random_serial.size() == 0)
		{
			random_serial.add(num);
		}
		if(random_serial.size() != 0)
		{
			if(random_serial.contains(num))
			{
				num = num+1;
				random_serial.add(num);
			}	
			else
			{
				random_serial.add(num);
			}
		}
		
		return num;
	}
	public static String Decoding(ArrayList<Character> c)
	{
		Slinkedlist info = new Slinkedlist();
		String temp = " ";
		for(int i = 0 ; i < c.size() ; i++)
		{
			char placer = c.get(i);
			
			temp +=placer;
		}
		Node search = null;
		if(table.containsKey(temp))
		{
			search = table.get(temp);	
			String temp2 =info.Search(search);
			temp = null;
			return temp2;
		}
		else
		{
			temp = null;
			return "Invalid Code.";
		}
		
	}

}

CLASS NODE: 

public class Node {
private String data;
private Node Address;

Node(String data,Node ref)
{
	this.data = data;
	Address = ref;
}

public void setdata(String data)
{
	this.data = data;
}

public String getdata()
{
	return data;
}

public void setAddress(Node ref)
{
	Address= ref;
}
public Node getAddress()
{
	return Address;
}
}

Class Same Case: 

import java.util.Random;

public class Same_Case {

	public static String ENCODING(String word)
	{
		String Temp = " ";
		int initial_temp  = Main.temprary.size();
		for(int loop = 0 ; loop< word.length() ; loop++)
		{
			Main.alphabat [loop] = word.charAt(loop);
		}
		int check = 0 ;
		while(check != word.length())
		{
			char alpha = Main.alphabat [check];
			int value = alpha ;
			for(int i = 65; i<91 ; i++)
			{
				if(i == value)
				{
					if(value>=65 && value<84)
					{
						int Val = 0;
						
						if(value %2 == 0)
						{
							for(int loop = 0 ; loop <2 ; loop++)
							{
								Val = value+(loop+2);
								if(Main.temprary.size() == 0)
								{
									Main.temprary.add(Val);
			}
			else
		{
									if(Main.temprary.contains(Val))
			{
		int lastadd = Main.temprary.size()-1;
		int lastvalue = 3+Main.temprary.get(lastadd);
		if(lastvalue>=65 && lastvalue<91)
			{
		Main.temprary.add(lastvalue-3);
		}
else
		{
	Main.temprary.add(lastvalue-5);
		}
				
		}
	else
	{										Main.temprary.add(Val);
			}
				}
								
		}
			}
		if(value %2 != 0)
		{
	for(int loop = 0 ; loop <2 ; loop++)
			{
				Val = value+(loop+8);
			if(Main.temprary.size() == 0)
			{
		Main.temprary.add(Val);
				}
			else
			{
									if(Main.temprary.contains(Val))
									{
		int lastadd = Main.temprary.size()-8;
		int lastvalue = 1+Main.temprary.get(lastadd);
	if(lastvalue>=65 && lastvalue<91)
		{
			Main.temprary.add(lastvalue+1);
			}else
			{
											Main.temprary.add(lastvalue-4);
			}
		
			}
			else
		{
										Main.temprary.add(Val);
		}
		}
			}
			}
						
			}
				
			if(value >= 84 && value < 91)
			{	int Val = 0;
				if(value %2 == 0)
					{
						
				for(int loop = 10 ; loop >8; loop--)
				{
				Val = value - (loop-1);
				if(Main.temprary.size() == 0)
					{
				Main.temprary.add(Val);
						}
			else
					{
				if(Main.temprary.contains(Val))
				{
		int lastadd = Main.temprary.size()-1;
		int lastvalue = 9+Main.temprary.get(lastadd);
		if(lastvalue>=65 && lastvalue<91)
		{
									Main.temprary.add(lastvalue-2);
			}	else
			{
									Main.temprary.add(lastvalue-6);
				}
					
			}
			else
				{
			Main.temprary.add(Val);
				}
				}
			}
			}
			if(value %2 != 0)
		{
			for(int loop = 10 ; loop >8; loop--)
			{
			Val = value - (loop-6);
		if(Main.temprary.size() == 0)
				{
			Main.temprary.add(Val);
				}
			else
				{
				if(Main.temprary.contains(Val))
				{
			int lastadd = Main.temprary.size()-1;
			int lastvalue = 3+Main.temprary.get(lastadd);
			if(lastvalue>=65 && lastvalue<91)
				{
			Main.temprary.add(lastvalue-8);
			} if(lastvalue>=91)
				{
										Main.temprary.add(lastvalue-12);
					}
						
				}
					else
				{
				Main.temprary.add(Val);
								}
							}
						}
						}
						
					}
									
				}	
			}
			check +=1;
	
		}
		
		//Creating Code ...
		for(int i = initial_temp ; i < Main.temprary.size()  ; i++)
		{
			int temp = Main.temprary.get(i);
			Temp +=(char)temp;
		}
		
		//Adding Serial number to identify number of code.
		int Serial = Rand();
		Main.textbox_serial.setText(""+Serial);
		
		Main.User_code_info.put(Serial, Temp);
		

		return Temp;	
	}

	public  static int Rand()
	{
		Random serial = new Random();
		int num;
		num = serial.nextInt(100);
		if(num == 0)
		{
			num = num+1;
		}
		if(Main.random_serial.size() == 0)
		{
			Main.random_serial.add(num);
		}
		if(Main.random_serial.size() != 0)
		{
			if(Main.random_serial.contains(num))
			{
				num = num+1;
				Main.random_serial.add(num);
			}	
			else
			{
				Main.random_serial.add(num);
			}
		}
		
		return num;
	}
}

CLASS Slinked list: 


public class Slinkedlist {
	private Node head,tail;
	private int size;

	Slinkedlist()
	{
		head =tail =  null;
		size = 0;
	}

	public Node Addfirst(String value)
	{

		Node n = new Node(value , null);
		n.setAddress(head);
		head = n;
		size++;
		if(tail == null)
		{
			tail = n;
		}
		return head;
		
	}
	
	public String Search(Node address)
	{
		String data = address.getdata();
		String error = "Result not found.";
		if(data != null)
		{
			return data;
		}
		else
			return error;
	}
	public boolean Available(String value)
	{
		Node n = head;
		while(n != null)
		{
			String match = n.getdata();
			if(match.equals(value))
			{
				return true;
			}
			n = n.getAddress();
		}
		return false;
	}

	public void Delete_Data(Node address)
	{
		String data = address.getdata();
		if(data != null)
		{
			address = null;
			System.out.println("Data deleted.");
		}
		else
		{
			System.out.println("Data is not available.");
		}
	}
	}
