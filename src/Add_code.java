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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Add_code extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	JTextArea LIST = new JTextArea(20, 20);
	JScrollPane SCROLL = new JScrollPane(LIST);
	
	JButton SUBMIT = new JButton("Submit");
	JButton CLOSE = new JButton("Close");
	
	String [] TYPE_ARRAY = {"ATN Large", "ATN Small", "Show HD"};
	JComboBox<String> TYPE = new JComboBox<String>(TYPE_ARRAY);
	
	JSpinner LENGTH = new JSpinner();
	JTextField PRICE = new JTextField(5);
	
	JLabel STATUS = new JLabel();
	JLabel JTYPE = new JLabel("Package: ");
	JLabel JLENGTH = new JLabel("Day #: ");
	JLabel JPRICE = new JLabel("Price: ");
	
	Border TEXTAREA_BORDER = BorderFactory.createTitledBorder("Add Codes");

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    
	public Add_code(){

	SCROLL.setBorder(TEXTAREA_BORDER);
	
	CLOSE.addActionListener(new ActionListener(){
		public void actionPerformed (ActionEvent e){
			dispose();
		}
	});
	
	SUBMIT.addActionListener(new ActionListener(){
		public void actionPerformed (ActionEvent e){
			// 1) check if the code list is empty.
			if((!LIST.getText().isEmpty()) && (!PRICE.getText().isEmpty())){
				try{
					connection = DriverManager.getConnection("jdbc:mysql://ATNDB.db.11581833.hostedresource.com:3306/ATNDB", "ATNDB", "Safaryy1987%");
					// read text from LIST
				@SuppressWarnings("resource")
				Scanner input = new Scanner(LIST.getText());
				int count = 0; // keep a counter just to display how many items has been added later.
				while(input.hasNext()){
					String SQL = "INSERT INTO `ATNDB`.`code` (`ID`, `code`, `length`, `type`, `purchase_date`, `expiration_date`, `price`, `active`, `customer_ID`) VALUES (NULL, '" +
				input.nextLine() + "', '" + ((Integer) LENGTH.getValue()) + "', '" + TYPE_ARRAY[TYPE.getSelectedIndex()] + "', '', '', '" + PRICE.getText() + "', '0', '0');";
					preparedStatement = connection.prepareStatement(SQL);
					preparedStatement.executeUpdate();
					count++;
				}
				clearFields();
				STATUS.setForeground(Color.BLUE);
				STATUS.setText(count + " subscription code(s) has been added.");
				connection.close();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			}
			else{
				STATUS.setForeground(Color.red);
				STATUS.setText("Subscription codes or price box cannot be empty!");
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
							.addComponent(SCROLL))
					.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(JTYPE)
							.addComponent(JLENGTH)
							.addComponent(JPRICE))
					.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
							.addComponent(TYPE)
							.addComponent(LENGTH)
							.addComponent(PRICE))
			)
			.addGroup(Layout.createSequentialGroup()
					.addGroup(Layout.createSequentialGroup()
							.addComponent(CLOSE))
					.addGroup(Layout.createSequentialGroup()
							.addComponent(SUBMIT))
			)
		)
	);
	
	Layout.linkSize(SwingConstants.HORIZONTAL, CLOSE, SUBMIT);
	
	Layout.setVerticalGroup(Layout.createSequentialGroup()
			.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(STATUS))
			.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addGroup(Layout.createSequentialGroup()
							.addComponent(SCROLL)
					)

					.addGroup(Layout.createSequentialGroup()
							.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(JTYPE)
									.addComponent(TYPE)
							)
							.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(JLENGTH)
									.addComponent(LENGTH)
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
	setTitle("Adding Codes");
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void clearFields(){
		LIST.setText("");
		PRICE.setText("");
		LENGTH.setValue(0);
		TYPE.setSelectedIndex(0);
	}
}
