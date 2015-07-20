import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Add_box extends JFrame{

	private static final long serialVersionUID = 1L;
	
	JTextArea SERIAL = new JTextArea(20, 20);
	JTextArea MAC = new JTextArea(20,20);
	
	JScrollPane SCROLL_MAC = new JScrollPane(MAC);
	JScrollPane SCROLL_SERIAL = new JScrollPane(SERIAL);
	
	JButton SUBMIT = new JButton("Submit");
	JButton CLOSE = new JButton("Close");
	
	JTextField PRICE = new JTextField(5);
	JTextField MODEL = new JTextField(7);
	
	JLabel JPRICE = new JLabel("Price: ");
	JLabel JMODEL = new JLabel("Model: ");
	JLabel STATUS = new JLabel();
	
	Border TEXTAREA_BORDER = BorderFactory.createTitledBorder("Serial number ");
	Border TEXTAREA_BORDER2 = BorderFactory.createTitledBorder("Mac address");
    
	private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    
	public Add_box() {
		
		SCROLL_SERIAL.setBorder(TEXTAREA_BORDER);
		SCROLL_MAC.setBorder(TEXTAREA_BORDER2);
		
		CLOSE.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				dispose();
			}
		});
		
		SUBMIT.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				// 1) check if the box serial and mac fields are empty and they have equal lines.
				if(!SERIAL.getText().isEmpty() && !MAC.getText().isEmpty() && (SERIAL.getLineCount() == MAC.getLineCount()) && !PRICE.getText().isEmpty() && !MODEL.getText().isEmpty()){
					try{
						connection = DriverManager.getConnection("jdbc:mysql://ATNDB.db.11581833.hostedresource.com:3306/ATNDB", "ATNDB", "Safaryy1987%");
						
						@SuppressWarnings("resource")
						Scanner Serial_input = new Scanner(SERIAL.getText());
						@SuppressWarnings("resource")
						Scanner Mac_input = new Scanner(MAC.getText());
						int count = 0;
						while(Serial_input.hasNext() && Mac_input.hasNext()){
							String SQL = "INSERT INTO `ATNDB`.`box` (`model`, `serial`, `mac`, `price`, `purchase_date`, `customer_ID`) VALUES ('" +
									MODEL.getText() + "', '" + Serial_input.nextLine() + "', '" + Mac_input.nextLine() + "', '" + PRICE.getText() + "','', '0');";
							preparedStatement = connection.prepareStatement(SQL);
							preparedStatement.executeUpdate();
							count++;
						}
						clearFields();
						STATUS.setForeground(Color.blue);
						STATUS.setText(count + " Hardware boxe(s) has been added.");
						
						connection.close();
					}catch(SQLException e1){
						e1.printStackTrace();
					}
				}
				else{
					STATUS.setText("<html><font color='red'>Text fields cannot be empty or un-even!</font></html>");
				}
			}
		});
		
		GroupLayout Layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(Layout);
		Layout.setAutoCreateGaps(true);
		Layout.setAutoCreateContainerGaps(true);
		
		Layout.setHorizontalGroup(Layout.createSequentialGroup()
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(STATUS)
					.addGroup(Layout.createSequentialGroup()
							.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(SCROLL_SERIAL)
							)
							.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(SCROLL_MAC)
							)
							.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(JMODEL)
									.addComponent(JPRICE)
							)
							.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(MODEL)
									.addComponent(PRICE)
							)
					)
					.addGroup(Layout.createSequentialGroup()
							.addGroup(Layout.createSequentialGroup()
									.addComponent(CLOSE)
							)
							.addGroup(Layout.createSequentialGroup()
									.addComponent(SUBMIT)
							)
					)
				)
		);
		
		Layout.linkSize(SwingConstants.HORIZONTAL, CLOSE, SUBMIT);
		
		Layout.setVerticalGroup(Layout.createSequentialGroup()		
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(STATUS))
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(SCROLL_SERIAL)
						.addComponent(SCROLL_MAC)
						.addGroup(Layout.createSequentialGroup()
								.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(JMODEL)
										.addComponent(MODEL)
								)
								.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(JPRICE)
										.addComponent(PRICE)
								)
						)
				)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addGroup(Layout.createSequentialGroup()
								.addComponent(CLOSE))
						.addGroup(Layout.createSequentialGroup()
								.addComponent(SUBMIT))
				)
		);
		
		pack();
		setTitle("Adding Boxes to Database");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void clearFields(){
		SERIAL.setText("");
		MAC.setText("");
		PRICE.setText("");
		MODEL.setText("");
	}
}
