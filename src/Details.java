import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Details extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JLabel INFO = new JLabel();
	private JButton CLOSE = new JButton("Close");
	
	public Details(){
		
		GroupLayout Layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(Layout);
		Layout.setAutoCreateGaps(true);
		Layout.setAutoCreateContainerGaps(true);
		
		String info = "<html><body><p>Use the following references to add the desired details to your template:</p><br>" +
		"<strong>[F_NAME] = </strong>Customer first name.<br>" + "<strong>[L_NAME] = </strong>Customer last name.<br>" +
		"<strong>[ADDRESS] = </strong>Customer street address.<br>" + "<strong>[CITY] = </strong>Customer city.<br>" + "<strong>[STATE] = </strong>Customer State.<br>" +
		"<strong>[ZIPCODE] = </strong>Customer Zipcode.<br>" + "<strong>[TELEPHONE] = </strong>Customer telephone number.<br>" +
		"<strong>[EMAIL] = </strong>Customer Email address.<br><br>" + "<strong>[SERIAL] = </strong>Hardware serial number.<br>" + 
		"<strong>[MAC] = </strong>Hardware mac address.<br>" + "<strong>[MODEL] = </strong>Hardware model number.<br>" + "<strong>[B_PRICE] = </strong>Hardware price.<br><br>" + 
		"<strong>[CODE] = </strong>Subscription code number.<br>" + "<strong>[LENGTH] = </strong>Subscription length in days.<br>" +
		"<strong>[PACKAGE] = </strong>Subscription package type.<br>" + "<strong>[C_PRICE] = </strong>Subscription price.<br>";
		
		AddInstructions(info);
		Layout.setHorizontalGroup(Layout.createSequentialGroup()
				.addComponent(INFO)
				.addComponent(CLOSE)
		);
		
		Layout.setVerticalGroup(Layout.createSequentialGroup()
				.addComponent(INFO)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(CLOSE)		
				)
		);
		
		CLOSE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
		pack();
		setTitle("Templates detail");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void AddInstructions(String str){
		INFO.setText(str);
	}
}
