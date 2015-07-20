import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Setting extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JLabel INFO = new JLabel ();
	 
	private JLabel HOST_LABEL = new JLabel ("Choose Host");
	private JRadioButton Radio_YAHOO = new JRadioButton("Yahoo!");
	private JRadioButton Radio_GMAIL = new JRadioButton("Gmail");
	private JRadioButton Radio_HOTMAIL = new JRadioButton("Hotmail");
	
	private JLabel USERNAME_LABEL = new JLabel ("User ID: ");
	private JLabel PASSWORD_LABEL = new JLabel ("Password: ");
	private JTextField USERNAME = new JTextField(10);
	private JPasswordField PASSWORD = new JPasswordField(10);
	
	private JTextArea EMAIL_TEMPLATE = new JTextArea(50, 50);
	private JScrollPane TEMPLATE_SCROLL = new JScrollPane(EMAIL_TEMPLATE);
	private JTextField TEMPLATE_SUBJECT = new JTextField(10);
	private JLabel Subject_label = new JLabel("Email subject: ");
	private JRadioButton Template1 = new JRadioButton("Template 1");
	private JRadioButton Template2 = new JRadioButton("Template 2");
	private JRadioButton Template3 = new JRadioButton("Template 3");
	private JLabel Template_Info = new JLabel();
	private JButton SAVE_TEMPLATE = new JButton("Save");
	private JButton DETAILS = new JButton("Details");
	private JButton SAVE = new JButton ("Save");
	
	public Setting (){
		
		JPanel P1 = new JPanel();
		JPanel P3 = new JPanel();
		JPanel P2 = new JPanel();
		JPanel P4 = new JPanel();
		P4.setBorder(BorderFactory.createTitledBorder("Edit Email Templates"));
		GroupLayout Layout = new GroupLayout(P1);
		P1.setLayout(Layout);
		add(P1);
		add(P2);
		Layout.setAutoCreateContainerGaps(true);
		Layout.setAutoCreateGaps(true);
		
		GroupLayout Layout2 = new GroupLayout(P3);
		P3.setLayout(Layout2);
		add(P3);
		Layout2.setAutoCreateContainerGaps(true);
		Layout2.setAutoCreateGaps(true);
		
		GroupLayout Layout1 = new GroupLayout(P2);
		P2.setLayout(Layout1);
		add(P2);
		Layout1.setAutoCreateContainerGaps(true);
		Layout1.setAutoCreateGaps(true);
		
		GroupLayout Layout3 = new GroupLayout(P4);
		P4.setLayout(Layout3);
		add(P4);
		Layout3.setAutoCreateContainerGaps(true);
		Layout3.setAutoCreateGaps(true);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		ButtonGroup HostRadioButton = new ButtonGroup();
		HostRadioButton.add(Radio_GMAIL);
		HostRadioButton.add(Radio_HOTMAIL);
		HostRadioButton.add(Radio_YAHOO);
		
		getSettings();
		
		ButtonGroup Templates = new ButtonGroup();
		Templates.add(Template1);
		Templates.add(Template2);
		Templates.add(Template3);
		
		Layout3.setHorizontalGroup(Layout3.createSequentialGroup()
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(Template_Info)
						.addGroup(Layout3.createSequentialGroup()
								.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(Subject_label)
								)
								.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(TEMPLATE_SUBJECT)
								)
								.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(DETAILS)
								)
						)
						.addComponent(TEMPLATE_SCROLL)
				)
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(TEMPLATE_SUBJECT)
				)
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(Template1)
						.addComponent(Template2)
						.addComponent(Template3)
						.addComponent(SAVE_TEMPLATE)
				)
		);
		
		Layout3.setVerticalGroup(Layout3.createSequentialGroup()
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(Template_Info)
				)
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(Subject_label)
						.addComponent(TEMPLATE_SUBJECT)
						.addComponent(DETAILS)
				)
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(TEMPLATE_SCROLL)
						.addGroup(Layout3.createSequentialGroup()
								.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(Template1)
								)
								.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(Template2)
								)
								.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(Template3)
								)
								.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(SAVE_TEMPLATE)
								)
						)
				)
		);
		Layout.setHorizontalGroup(Layout.createSequentialGroup()
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(HOST_LABEL)
				)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(Radio_GMAIL)
				)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(Radio_HOTMAIL)
				)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(Radio_YAHOO)
				)
		);
		
		Layout.setVerticalGroup(Layout.createSequentialGroup()
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(HOST_LABEL)
						.addComponent(Radio_GMAIL)
						.addComponent(Radio_HOTMAIL)
						.addComponent(Radio_YAHOO)
				)
		);
		
		Layout2.setHorizontalGroup(Layout2.createSequentialGroup()
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(INFO)
				)
		);
		
		Layout2.setVerticalGroup(Layout.createSequentialGroup()
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(INFO)
				)
		);
		
		Layout1.setHorizontalGroup(Layout1.createSequentialGroup()
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(USERNAME_LABEL)
						.addComponent(PASSWORD_LABEL)
				)
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(USERNAME)
						.addComponent(PASSWORD)
				)
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(SAVE)
				)
		);
		
		Layout1.setVerticalGroup(Layout1.createSequentialGroup()
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(USERNAME_LABEL)
						.addComponent(USERNAME)
						.addComponent(SAVE)
				)
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(PASSWORD_LABEL)
						.addComponent(PASSWORD)
				)
		);
		
		DETAILS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Details D = new Details();
				D.setVisible(true);
			}
		});
		
		SAVE.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				ClearInfo();
				try{
					File file = new File("settings.dat");
					PrintWriter output = new PrintWriter(file);
					if (Radio_GMAIL.isSelected()){
						output.write("G"  + "\n");
					}
					else if (Radio_HOTMAIL.isSelected()){
						output.write("H" + "\n");
					}
					else if (Radio_YAHOO.isSelected()){
						output.write("Y" + "\n");
					}
					if (!USERNAME.getText().isEmpty()){
						output.write("user " + USERNAME.getText() + "\n");
					}
					else{
						DisplayRedInfo("User name cannot be empty!");
					}
					if (PASSWORD.getPassword().length != 0){
						output.write("password ");
						output.write(PASSWORD.getPassword());
						output.write("\n");
					}
					else{
						DisplayRedInfo("Password cannot be empty!");
					}
					DisplayBlueInfo("Credentials has been saved.");
					output.close();
				}catch(FileNotFoundException e1){
					DisplayRedInfo("Could not find any data.");
					e1.printStackTrace();
				}
			}
		});
		
		Template1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ClearInfo();
				try{
					File file = new File("template1.rtf");
					File subject = new File("subject1.rtf");
					Scanner output = new Scanner(file);
					Scanner output1 = new Scanner(subject);
					String token = "";
					TEMPLATE_SUBJECT.setText(output1.nextLine());
					while(output.hasNextLine()){
						token = token + output.nextLine() + "\n";
					}
					EMAIL_TEMPLATE.setText(token);
					output.close();
					output1.close();
				}catch(IOException e1){
					DisplayRedInfo("Warning! could not load template 1");
					e1.printStackTrace();
				}
			}
		});
		
		Template2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ClearInfo();
				try{
					File file = new File("template2.rtf");
					File subject = new File("subject2.rtf");
					Scanner output = new Scanner(file);
					Scanner output1 = new Scanner(subject);
					String token = "";
					TEMPLATE_SUBJECT.setText(output1.nextLine());
					while(output.hasNextLine()){
						token = token + output.nextLine() + "\n";
					}
					EMAIL_TEMPLATE.setText(token);
					output.close();
					output1.close();
				}catch(IOException e1){
					DisplayRedInfo("Warning! could not load template 2");
					e1.printStackTrace();
				}
			}
		});
		
		Template3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ClearInfo();
				try{
					File file = new File("template3.rtf");
					File subject = new File("subject3.rtf");
					Scanner output = new Scanner(file);
					Scanner output1 = new Scanner(subject);
					String token = "";
					TEMPLATE_SUBJECT.setText(output1.nextLine());
					while(output.hasNextLine()){
						token = token + output.nextLine() + "\n";
					}
					EMAIL_TEMPLATE.setText(token);
					output.close();
					output1.close();
				}catch(IOException e1){
					DisplayRedInfo("Warning! could not load template 3");
					e1.printStackTrace();
				}
			}
		});
		
		SAVE_TEMPLATE.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				ClearInfo();
				if(Template1.isSelected()){
					try{
						File file1 = new File("template1.rtf");
						File subject1 = new File("subject1.rtf");
						PrintWriter input1 = new PrintWriter(file1);
						PrintWriter sub_input1 = new PrintWriter(subject1);
						String str1 = EMAIL_TEMPLATE.getText();
						sub_input1.write(TEMPLATE_SUBJECT.getText());
						input1.write(str1);
						input1.close();
						sub_input1.close();
						DisplayBlueInfo("Template 1 has been saved.");
					}catch(IOException e1){
						DisplayRedInfo("Warning! could not save template 1.");
						e1.printStackTrace();
					}
				}
				else if(Template2.isSelected()){
					try{
						File file2 = new File("template2.rtf");
						File subject2 = new File("subject2.rtf");
						PrintWriter input2 = new PrintWriter(file2);
						PrintWriter sub_input2 = new PrintWriter(subject2);
						String str2 = EMAIL_TEMPLATE.getText();
						sub_input2.write(TEMPLATE_SUBJECT.getText());
						input2.write(str2);
						input2.close();
						sub_input2.close();
						DisplayBlueInfo("Template 2 has been saved.");
					}catch(IOException e2){
						DisplayRedInfo("Warning! could not save template 2.");
						e2.printStackTrace();
					}
				}
				else if(Template3.isSelected()){
					try{
						File file3 = new File("template3.rtf");
						File subject3 = new File("subject3.rtf");
						PrintWriter input3 = new PrintWriter(file3);
						PrintWriter sub_input3 = new PrintWriter(subject3);
						String str3 = EMAIL_TEMPLATE.getText();
						sub_input3.write(TEMPLATE_SUBJECT.getText());
						input3.write(str3);
						input3.close();
						sub_input3.close();
						DisplayBlueInfo("Template 3 has been saved.");
					}catch(IOException e3){
						DisplayRedInfo("Warning! could not save template 3.");
						e3.printStackTrace();
					}
				}
				else{
					DisplayRedInfo("No template was selected to be saved!");
				}
			}
		});
	}
	
	public void getSettings(){
		try{
			File file = new File("settings.dat");
			Scanner input = new Scanner(file);
			
			while(input.hasNext()){
				String token = input.next();
				if(token.equals("G") || token.equals("Y") || token.equals("H")){
					getHost(token);
				}
				if(token.equals("user")){
					USERNAME.setText(input.next());
				}
				if(token.equals("password")){
					PASSWORD.setText(input.next());
				}
			}
			
			input.close();
		}catch(IOException e1){
			DisplayRedInfo("Could not find any data to retrieve");
			System.out.println("could not locate the file");
			e1.printStackTrace();
		}
	}
	
	public void getHost(String ch){
		switch(ch){
		case "G":
			Radio_GMAIL.setSelected(true);
			Radio_HOTMAIL.setSelected(false);
			Radio_YAHOO.setSelected(false);
			return;
		case "Y":
			Radio_GMAIL.setSelected(false);
			Radio_HOTMAIL.setSelected(false);
			Radio_YAHOO.setSelected(true);
			return;
		case "H":
			Radio_GMAIL.setSelected(false);
			Radio_HOTMAIL.setSelected(true);
			Radio_YAHOO.setSelected(false);
			return;
		default:
			Radio_GMAIL.setSelected(false);
			Radio_HOTMAIL.setSelected(false);
			Radio_YAHOO.setSelected(false);
			return;
		}
	}
	
	public void DisplayRedInfo(String str){
		INFO.setForeground(Color.RED);
		INFO.setText(str);
	}
	
	public void DisplayBlueInfo(String str){
		INFO.setForeground(Color.blue);
		INFO.setText(str);
	}
	public void ClearInfo(){
		INFO.setText("");
	}
}
