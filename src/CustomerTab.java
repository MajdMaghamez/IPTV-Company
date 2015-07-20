import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CustomerTab extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JButton SEARCH_TELEPHONE = new JButton("Phone Search");
	private JButton SEARCH_ID =	new JButton("ID Search");
	private JButton SEARCH_SERIAL = new JButton("Search Hardware");
	private JButton ADD = new JButton("Add");
	private JButton EDIT = new JButton("Edit");
	private JButton REMOVE = new JButton("Remove");
	private JButton ADD_CODE = new JButton("Add Subscription");
	private JButton SAVE = new JButton("Save");
	private JButton RESET = new JButton("Reset");
	private JButton PRINT_RECEIPT = new JButton("Print Invoice");
	private JButton EMAIL_RECEIPT = new JButton("Email Invoice");	
	
	private JLabel INFO = new JLabel();
	private JLabel FIRST = new JLabel("<html><font color='red'>*</font> First name: </html>");
	private JLabel LAST = new JLabel("<html><font color='red'>*</font> Last name: </html>");
	private JLabel ADDRESS = new JLabel("Address: ");
	private JLabel CITY = new JLabel("City: ");
	private JLabel STATE = new JLabel("State: ");
	private JLabel ZIP = new JLabel("Zip code: ");
	private JLabel PHONE = new JLabel("<html><font color='red'>*</font> Telephone: </html>");
	private JLabel EMAIL = new JLabel("<html><font color='red'>*</font> Email: </html>");
	private JLabel SERIAL = new JLabel("<html><font color='red'>*</font>  Hardware S/N: </html>");
	private JLabel MAC = new JLabel("  Hardware Mac: ");
	private JLabel PACKAGE = new JLabel("Package: ");
	private JLabel NOTE = new JLabel("Number of equipment(s) owned: ");
	private JLabel LENGTH = new JLabel("Length: ");
	private JLabel DAYS = new JLabel("Days ");
	
	private JTextField SEARCH_TEL_TEXT = new JTextField(20);
	private JTextField SEARCH_ID_TEXT = new JTextField(20);
	private JTextField FIRST_TEXT = new JTextField(8);
	private JTextField LAST_TEXT = new JTextField(8);
	private JTextField ADDRESS_TEXT = new JTextField(20);
	private JTextField CITY_TEXT = new JTextField(10);
	private JTextField STATE_TEXT = new JTextField(3);
	private JTextField ZIP_TEXT = new JTextField(5);
	private JTextField TEL_TEXT = new JTextField(10);
	private JTextField EMAIL_TEXT = new JTextField(10);
	private JTextField SERIAL_TEXT = new JTextField(10);
	private JTextField MAC_TEXT = new JTextField(10);
	private JTextField CODE_TEXT = new JTextField(10);
	private JTextField NUMOFHARDWARE = new JTextField(2);

	String [] TYPE_ARRAY = {"ATN Large", "ATN Small", "Show HD"};
	private JComboBox<String> PACKAGE_TYPE = new JComboBox<String>(TYPE_ARRAY);
	
	JSpinner CODE_LENGTH = new JSpinner();
	
	private String EMAIL_USER;
	private String EMAIL_PASSWORD;
	private String EMAIL_HOST;
	
	private Properties properties = new Properties();
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet RS = null;
	private static final String HOST = "jdbc:mysql://ATNDB.db.11581833.hostedresource.com:3306/ATNDB";
	private static final String USER = "ATNDB";
	private static final String PASSWORD = "Safaryy1987%";
	
	ArrayList<box> BOX_LIST = new ArrayList<box>();
	ArrayList<customer> CUSTOMER_LIST = new ArrayList<customer>();
	ArrayList<code> CODE_LIST = new ArrayList<code>();
	
	ArrayList<box> BOX_SALE = new ArrayList<box>();
	ArrayList<code> CODE_SALE = new ArrayList<code>();
	
	public boolean customer_T_search = false;
	public boolean customer_I_search = false;
	public boolean customer_add = false;
	public boolean customer_hard = false;
	public boolean customer_sub = false;
	
	public boolean EDIT_MODE = false;
	public boolean ADD_MODE = false;
	public boolean HARDWARE = false;
	public boolean SUBSCRIPTION = false;
	
	public CustomerTab(Connection c){
		getCredentials();
		properties.put("mail.smtp.host", EMAIL_HOST);
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL_USER, EMAIL_PASSWORD);				
			}
		});
		
		DisableCustomerTextFields();
		DisableHardware();
		DisableSubscription();
		
		JPanel P1 = new JPanel();
		P1.setBorder(BorderFactory.createTitledBorder("Search"));
		
		JPanel P2 = new JPanel();
		P2.setBorder(BorderFactory.createTitledBorder("Customer Information"));
		
		JPanel P3 = new JPanel();
		P3.setBorder(BorderFactory.createTitledBorder("Hardware"));
		
		JPanel P4 = new JPanel();
		P4.setBorder(BorderFactory.createTitledBorder("Subscription"));
		
		JPanel P5 = new JPanel();
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		GroupLayout Layout = new GroupLayout(P1); // contains the search part
		P1.setLayout(Layout);
		add(P1);
		Layout.setAutoCreateGaps(true);
		
		GroupLayout Layout1 = new GroupLayout(P2); // contains the customer information
		P2.setLayout(Layout1);
		add(P2);
		Layout1.setAutoCreateGaps(true);
		
		GroupLayout Layout2 = new GroupLayout(P3); // contains the hardware part
		P3.setLayout(Layout2);
		add(P3);
		Layout2.setAutoCreateGaps(true);
		
		GroupLayout Layout3 = new GroupLayout(P4); // contains the subscription part
		P4.setLayout(Layout3);
		add(P4);
		Layout3.setAutoCreateGaps(true);
		
		GroupLayout Layout4 = new GroupLayout(P5); // contains the buttons at last
		P5.setLayout(Layout4);
		add(P5);
		Layout4.setAutoCreateGaps(true);
		Layout.setAutoCreateContainerGaps(true);
		
		Layout.setHorizontalGroup(Layout.createSequentialGroup()
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(SEARCH_TELEPHONE)
						.addComponent(SEARCH_ID)
				)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(SEARCH_TEL_TEXT)
						.addComponent(SEARCH_ID_TEXT)
						.addComponent(INFO)
				)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(ADD)
						.addComponent(EDIT)
						.addComponent(REMOVE)
				)
		);
		
		Layout.linkSize(SwingConstants.HORIZONTAL, ADD, EDIT, REMOVE);
		
		Layout.setVerticalGroup(Layout.createSequentialGroup()
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(SEARCH_TELEPHONE)
						.addComponent(SEARCH_TEL_TEXT)
						.addComponent(ADD)
				)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(SEARCH_ID)
						.addComponent(SEARCH_ID_TEXT)
						.addComponent(EDIT)
				)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(INFO)
						.addComponent(REMOVE)
				)
		);
		
		Layout1.setHorizontalGroup(Layout1.createSequentialGroup()
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(FIRST)
						.addComponent(PHONE)
						.addComponent(ADDRESS)
						.addComponent(CITY)
				)
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(Layout1.createSequentialGroup()
							.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(FIRST_TEXT)
							)
							.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(LAST)
							)
						)
						.addGroup(Layout1.createSequentialGroup()
							.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(TEL_TEXT)
							)
							.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(EMAIL)
							)
						)
						.addComponent(ADDRESS_TEXT)
						.addGroup(Layout1.createSequentialGroup()
							.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(CITY_TEXT)
							)
							.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(STATE)
							)
						)
				)
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(LAST_TEXT)
						.addComponent(EMAIL_TEXT)
						.addGroup(Layout1.createSequentialGroup()
							.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(STATE_TEXT)
							)
							.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(ZIP)
							)
							.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(ZIP_TEXT)	
							)
						)
					)
				);
		
		Layout1.setVerticalGroup(Layout1.createSequentialGroup()
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(FIRST)
						.addComponent(FIRST_TEXT)
						.addComponent(LAST)
						.addComponent(LAST_TEXT)
				)	
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(PHONE)
						.addComponent(TEL_TEXT)
						.addComponent(EMAIL)
						.addComponent(EMAIL_TEXT)
				)
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(ADDRESS)
						.addComponent(ADDRESS_TEXT)
				)
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(CITY)
						.addComponent(CITY_TEXT)
						.addComponent(STATE)
						.addComponent(STATE_TEXT)
						.addComponent(ZIP)
						.addComponent(ZIP_TEXT)
				)
		);
		
		Layout2.setHorizontalGroup(Layout2.createSequentialGroup()
				
						.addGroup(Layout2.createSequentialGroup()
							.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(NOTE)
									.addComponent(SEARCH_SERIAL)
							)
							.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(NUMOFHARDWARE)
							)
							.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(SERIAL)
									.addComponent(SERIAL_TEXT)
							)
							.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(MAC)
									.addComponent(MAC_TEXT)
							)
						)	
		);
		
		Layout2.setVerticalGroup(Layout2.createSequentialGroup()
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(NOTE)
						.addComponent(NUMOFHARDWARE)
						.addComponent(SERIAL)
						.addComponent(MAC)
				)		
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(SEARCH_SERIAL)
						.addComponent(SERIAL_TEXT)
						.addComponent(MAC_TEXT)
				)
		);

		Layout3.setHorizontalGroup(Layout3.createSequentialGroup()
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(ADD_CODE)
					.addComponent(CODE_TEXT)
				)
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(PACKAGE)
					.addComponent(LENGTH)
				)
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(PACKAGE_TYPE)
					.addComponent(CODE_LENGTH)
				)
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(DAYS)
				)
		);
		
		Layout3.setVerticalGroup(Layout3.createSequentialGroup()
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(ADD_CODE)
					.addComponent(PACKAGE)
					.addComponent(PACKAGE_TYPE)
				)
				.addGroup(Layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(CODE_TEXT)
					.addComponent(LENGTH)
					.addComponent(CODE_LENGTH)
					.addComponent(DAYS)
				)
		);
		
		Layout4.setHorizontalGroup(Layout4.createSequentialGroup()
				.addGroup(Layout4.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(RESET)
				)
				.addGroup(Layout4.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(SAVE)
				)
				.addGroup(Layout4.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(PRINT_RECEIPT)
				)
				.addGroup(Layout4.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(EMAIL_RECEIPT)
				)
		);
		
		Layout4.setVerticalGroup(Layout4.createSequentialGroup()
				.addGroup(Layout4.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(RESET)
						.addComponent(SAVE)
						.addComponent(PRINT_RECEIPT)
						.addComponent(EMAIL_RECEIPT)
				)
		);
		
		Layout4.linkSize(SwingConstants.HORIZONTAL, RESET, SAVE, PRINT_RECEIPT, EMAIL_RECEIPT);	
		
// ------------------------- * Telephone Search Button * ------------------------- //
		SEARCH_TELEPHONE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(EmptyTextField(SEARCH_TEL_TEXT)){
					DisplayRedInfo("Search bar cannot be empty.");
				}
				else{
					ClearAllArrayLists();
					ClearCustomerFields();
					ClearHardwareFields();
					ClearSubscriptionFields();
					ClearINFOLabel();
					
					ConnectToDataBase(HOST, USER, PASSWORD);
					String SQL = "SELECT `customer`.`ID`, `customer`.`fname`, `customer`.`lname`, `customer`.`address`, `customer`.`city`, `customer`.`state`, `customer`.`zipcode`, `customer`.`telephone`, `customer`.`email` FROM `customer` WHERE `customer`.`telephone` = '" + TrimTelephone(SEARCH_TEL_TEXT.getText()) + "';";
					AddNewCustomer(SQL);
					
					if(!CUSTOMER_LIST.isEmpty()){
						DisplayCustomerInfoFromCustomerList();
						EnableFeatures();
						DisplayBlueInfo("Customer ID: " + CUSTOMER_LIST.get(0).getID() + " Found.");
						String SQL2 = "SELECT `box`.`ID`, `box`.`serial`, `box`.`mac`, `box`.`model`, `box`.`price` FROM `box` WHERE `box`.`customer_ID` = " + CUSTOMER_LIST.get(0).getID();
						AddNewHardware(SQL2);
						
						if(!BOX_LIST.isEmpty()){
							MAC_TEXT.setText(BOX_LIST.get(0).getMac());
							NUMOFHARDWARE.setText(String.valueOf(BOX_LIST.size()));
							customer_T_search = true;
						}
						else{
							MAC_TEXT.setText("");
							NUMOFHARDWARE.setText(String.valueOf(BOX_LIST.size()));
						}
					}
					else{
						DisplayRedInfo("Could not locate any info.");
					}
					DisconnectFromDataBase();
				}
			}
		});
// ------------------------------------------------------------------------------- //
		
// ------------------------- * ID SEARCH BUTTON * ------------------------- //		
		SEARCH_ID.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(EmptyTextField(SEARCH_ID_TEXT)){
					DisplayRedInfo("Search bar cannot be empty.");
				}
				else{
					ClearAllArrayLists();
					ClearCustomerFields();
					ClearHardwareFields();
					ClearSubscriptionFields();
					ClearINFOLabel();
					
					ConnectToDataBase(HOST, USER, PASSWORD);
					String SQL = "SELECT `customer`.`ID`, `customer`.`fname`, `customer`.`lname`, `customer`.`address`, `customer`.`city`, `customer`.`state`, `customer`.`zipcode`, `customer`.`telephone`, `customer`.`email` FROM `customer` WHERE `customer`.`ID` = '" + SEARCH_ID_TEXT.getText() + "';";
					AddNewCustomer(SQL);
					
					if(!CUSTOMER_LIST.isEmpty()){
						DisplayCustomerInfoFromCustomerList();
						EnableFeatures();
						String SQL2 = "SELECT `box`.`ID`, `box`.`serial`, `box`.`mac`, `box`.`model`, `box`.`price` FROM `box` WHERE `box`.`customer_ID` = " + CUSTOMER_LIST.get(0).getID();
						AddNewHardware(SQL2);
						
						if(!BOX_LIST.isEmpty()){
							MAC_TEXT.setText(BOX_LIST.get(0).getMac());
							NUMOFHARDWARE.setText(String.valueOf(BOX_LIST.size()));
							customer_I_search = true;
						}
						else{
							MAC_TEXT.setText("");
							NUMOFHARDWARE.setText(String.valueOf(BOX_LIST.size()));
						}
					}
					else{
						DisplayRedInfo("Could not locate any info.");
					}
					DisconnectFromDataBase();
				}
			}
		});
// ------------------------------------------------------------------------ //	

// ------------------------- * Hardware Search Button * ------------------------- //
		SEARCH_SERIAL.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DisableCustomerTextFields();
				if(EmptyTextField(SERIAL_TEXT)){
					DisplayRedInfo("Warning! serial number cannot be empty");
				}
				else if(CUSTOMER_LIST.isEmpty()){
					DisplayRedInfo("Warning! no customer is selected.");
				}
				else{
					ClearINFOLabel();
					BOX_LIST.clear();
					ConnectToDataBase(HOST, USER, PASSWORD);
					String SQL = "SELECT `box`.`ID`, `box`.`serial`, `box`.`mac`, `box`.`model`, `box`.`price` FROM `box` WHERE `box`.`serial` = '" + SERIAL_TEXT.getText() + "';";
					AddNewHardware(SQL);
					
					if(!BOX_LIST.isEmpty()){
						MAC_TEXT.setText(BOX_LIST.get(0).getMac());
						DisplayBlueInfo("Hardware found, model: " + BOX_LIST.get(0).getModel());
						HARDWARE = true;
						ADD_MODE = false;
						EDIT_MODE = false;
						SUBSCRIPTION = false;
					}
					else{
						DisplayRedInfo("Hardware wasn't found.");
					}
					DisconnectFromDataBase();
				}
			}
		});
// --------------------------------------------------------------------------- //		
		
		ADD_CODE.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				if(CUSTOMER_LIST.isEmpty()){
					DisplayRedInfo("You need to select a customer first.");
				}
				else if(BOX_LIST.isEmpty()){
					DisplayRedInfo("You need to select a hardware first.");
				}
				else{
				ClearINFOLabel();
				String SQL = "SELECT `code`.`ID`, `code`.`code`, `code`.`price`, `code`.`length` FROM `code` WHERE `code`.`active` = '0' AND `code`.`length` ='" + CODE_LENGTH.getValue() + "' AND `code`.`type` = '" 
						+ TYPE_ARRAY[PACKAGE_TYPE.getSelectedIndex()] + "';";
	
					ConnectToDataBase(HOST, USER, PASSWORD);
					AddNewSubscription(SQL);
					
					if(!CODE_LIST.isEmpty()){
						CODE_TEXT.setText(CODE_LIST.get(0).getCode());
						CODE_TEXT.setEnabled(false);
						CODE_LENGTH.setEnabled(false);
						DisplayBlueInfo("Found Subscription, " + (CODE_LIST.size()-1) + " more left.");
						
						SUBSCRIPTION = true;
						ADD_MODE = false;
						EDIT_MODE = false;
						HARDWARE = false;
					}
					else{
						DisplayRedInfo("Could not find any subscription.");
					}
					DisconnectFromDataBase();
				}
			}
		});
		
// ------------------------- * EDIT Button * ------------------------- //
		EDIT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				EnableCustomerTextFields();
				DisableHardware();

				if(!CustomerRequiedFields_Empty() && CUSTOMER_LIST.size() > 0){
					EDIT_MODE = true; 
					ADD_MODE = false; 
					HARDWARE = false;
					SUBSCRIPTION = false;
					DisplayBlueInfo("Edit mode.");
				}
				else{
					EDIT_MODE = false;
					DisplayRedInfo("You cannot edit if no information is provided!");
				}
			}
		});
// ----------------------------------------------------------------- //		
		
		ADD.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(CustomerRequiedFields_Empty()){
					ADD_MODE = true;
					EDIT_MODE = false;
					HARDWARE = false; 
					SUBSCRIPTION = false; 
					
					EnableCustomerTextFields();
					SERIAL_TEXT.setEnabled(true);
					DisplayBlueInfo("Add mode.");
				}
				else{
					DisplayRedInfo("You must clear current customer information first!");
				}
			}
		});
		
		SAVE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				if(EDIT_MODE){
					String SQL = "UPDATE `ATNDB`.`customer` SET `customer`.`fname` = '" + FIRST_TEXT.getText() + "', `customer`.`lname` = '" +
						LAST_TEXT.getText() + "', `customer`.`address` = '" + ADDRESS_TEXT.getText() + "', `customer`.`city` = '" + CITY_TEXT.getText() + 
						"', `customer`.`state` = '" + STATE_TEXT.getText() + "', `customer`.`zipcode` = '" + ZIP_TEXT.getText() + 
						"', `customer`.`telephone` = '" + TrimTelephone(TEL_TEXT.getText()) + "', `customer`.`email` = '" + EMAIL_TEXT.getText() + "'WHERE `customer`.`ID` =" + 
						CUSTOMER_LIST.get(0).getID() + ";";
					try{
						ConnectToDataBase(HOST, USER, PASSWORD);
						
						preparedStatement = connection.prepareStatement(SQL);
						preparedStatement.executeUpdate();
						DisplayBlueInfo("Customer info has been modified.");
						
						DisconnectFromDataBase();
						
					}catch(SQLException e1){
						DisplayRedInfo("Could not save the new information!");
					}
					HARDWARE = false;
					SUBSCRIPTION = false;
					ADD_MODE = false;
					EDIT_MODE = false;
					DisableCustomerTextFields();
				}
					
				if(ADD_MODE){
					
					String SQL = "INSERT INTO `ATNDB`.`customer`(`ID`, `fname`, `lname`, `address`, `city`, `state`, `zipcode`, `telephone`, `email`) VALUES ( NULL, '" +
							FIRST_TEXT.getText() + "', '" + LAST_TEXT.getText() + "', '" + ADDRESS_TEXT.getText() + "', '" + CITY_TEXT.getText() + "', '" +
							STATE_TEXT.getText() + "', '" + ZIP_TEXT.getText() + "', '" + TrimTelephone(TEL_TEXT.getText()) + "', '" + EMAIL_TEXT.getText() + "');";
					
					String SQL2 = "SELECT `customer`.`ID` FROM `customer` WHERE `customer`.`telephone` = '" + TrimTelephone(TEL_TEXT.getText()) + "';";
				
						try{
							ConnectToDataBase(HOST, USER, PASSWORD);

							DisableCustomerTextFields();
							if(!CustomerRequiedFields_Empty()){
							
							preparedStatement = connection.prepareStatement(SQL);
							preparedStatement.executeUpdate();
							
							CUSTOMER_LIST.clear();

							preparedStatement = connection.prepareStatement(SQL2);
							RS = preparedStatement.executeQuery();
							
								if(RS.next()){
									CUSTOMER_LIST.add(new customer(FIRST_TEXT.getText(), LAST_TEXT.getText(), ADDRESS_TEXT.getText(), CITY_TEXT.getText(), STATE_TEXT.getText(), ZIP_TEXT.getText(), TEL_TEXT.getText(), EMAIL_TEXT.getText(), RS.getInt(1)));
									DisplayBlueInfo("New customer has been added, ID = " + CUSTOMER_LIST.get(0).getID());
									RS.close();
									EnableFeatures();
									customer_add = true;
									RS.close();
								}
								else{
									DisplayRedInfo("Could not add this customer!");
								}
						
							}else{
								DisplayRedInfo("Please fill all required fields");
							}
							DisconnectFromDataBase();
						}catch(SQLException e1){
							DisplayRedInfo("There was a problem in adding customer");
						}
						HARDWARE = false;
						SUBSCRIPTION = false;
						ADD_MODE = false;
						EDIT_MODE = false;
					}
				
				if(HARDWARE){
					String SQL = "UPDATE `ATNDB`.`box` SET `box`.`customer_ID` = " + CUSTOMER_LIST.get(0).getID() + ", `box`.`purchase_date` = '" + new SimpleDateFormat("MM-dd-yyyy").format(new Date()) + "' WHERE `box`.`ID` = " + BOX_LIST.get(0).getID() + ";";
					try{
						ConnectToDataBase(HOST, USER, PASSWORD);
						
						preparedStatement = connection.prepareStatement(SQL);
						preparedStatement.executeUpdate();
						SERIAL_TEXT.setEnabled(false);
						DisplayBlueInfo("Hardware has been assigned to customer");
						
						BOX_SALE.add(BOX_LIST.get(0));
						connection.close();
						customer_hard = true;
					}catch(SQLException e1){
						DisplayRedInfo("Hardware couldn't be assigned to customer!");
					}
					HARDWARE = false;
					SUBSCRIPTION = false;
					ADD_MODE = false;
					EDIT_MODE = false;
				}
				
				if(SUBSCRIPTION){
					String SQL = "UPDATE `ATNDB`.`code` SET `code`.`active` = 1, `code`.`expiration_date` = '" + setExpirationDate(CODE_LIST.get(0).getLength()) + "' ,`code`.`purchase_date` = '" + new SimpleDateFormat("MM-dd-yyyy").format(new Date())  + "',`code`.`customer_ID` = " + CUSTOMER_LIST.get(0).getID() + " WHERE `code`.`ID` = " + CODE_LIST.get(0).getID() + ";";
					try{
						ConnectToDataBase(HOST, USER, PASSWORD);
						
						preparedStatement = connection.prepareStatement(SQL);
						preparedStatement.executeUpdate();
						DisplayBlueInfo("Subscription added to customer ID: " + CUSTOMER_LIST.get(0).getID());
						CODE_SALE.add(CODE_LIST.get(0));
						connection.close();
						customer_sub = true;
					}catch(SQLException e1){
						DisplayRedInfo("Subscription couldn't be assigned to customer!");
					}
				}
			}
		});
		
		RESET.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ClearAllArrayLists();
				ClearCustomerFields();
				ClearHardwareFields();
				ClearSubscriptionFields();
				ClearINFOLabel();
				DisableCustomerTextFields();
				DisableHardware();
				DisableSubscription();
				SEARCH_TEL_TEXT.setText("");
				SEARCH_ID_TEXT.setText("");
				DisplayBlueInfo("Reset complete.");
			}
		});
		
		EMAIL_RECEIPT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(customer_add && customer_hard && customer_sub){
					try {
						File file1 = new File("template1.rtf");
						File subject1 = new File("subject1.rtf");
						Scanner input_temp = new Scanner(file1);
						Scanner input_sub = new Scanner(subject1);
						String subject = input_sub.nextLine();
						String template = "";
						while(input_temp.hasNextLine()){
							template = template + input_temp.nextLine() + "\n";
						}
						input_temp.close();
						input_sub.close();
						String afterSub1 = Substitute(template);
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(Email_FROM()));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(CUSTOMER_LIST.get(0).getEmail()));
					message.setSubject(subject);
					message.setContent(afterSub1 + template, "text/html; charset=utf-8");
					System.out.println(afterSub1);
					Transport.send(message);
		 
					DisplayBlueInfo("Receipt has been emailed.");
		 
				}catch (MessagingException e3) {
					throw new RuntimeException(e3);
				}catch(IOException ioe){
					DisplayRedInfo("could not load tempalte1.");
					ioe.printStackTrace();
				}
					BOX_SALE.clear();
					CODE_SALE.clear();
					customer_add = false;
					customer_hard = false;
					customer_sub = false;
			}
				if((customer_T_search || customer_I_search) && customer_sub){
					try {
						File file2 = new File("template2.rtf");
						File subject2 = new File("subject2.rtf");
						Scanner input_temp2 = new Scanner(file2);
						Scanner input_sub2 = new Scanner(subject2);
						String subject = input_sub2.nextLine();
						String template2 = "";
						while(input_temp2.hasNextLine()){
							template2 = template2 + input_temp2.nextLine() + "\n";
						}
						input_temp2.close();
						input_sub2.close();
					String afterSub2 = Substitute(template2);
					
					Message message2 = new MimeMessage(session);
					message2.setFrom(new InternetAddress(Email_FROM()));
					message2.setRecipients(Message.RecipientType.TO, InternetAddress.parse(CUSTOMER_LIST.get(0).getEmail()));
					message2.setSubject(subject);
					message2.setContent(afterSub2, "text/html; charset=utf-8");
					System.out.println(afterSub2);
					Transport.send(message2);
		 
					DisplayBlueInfo("Receipt has been emailed.");
		 
				}catch (MessagingException e3) {
					throw new RuntimeException(e3);
				}catch(IOException ioe){
					DisplayRedInfo("could not load tempalte1.");
					ioe.printStackTrace();
				}
					BOX_SALE.clear();
					CODE_SALE.clear();
					customer_T_search = false;
					customer_I_search = false;
			}
			}
		});
		
		PRINT_RECEIPT.addActionListener(new PrintAction());
	}
	
	public class PrintAction implements ActionListener, Printable{

		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex){
			
			if(pageIndex > 0){
				return NO_SUCH_PAGE;
			}
			double Total = 0.0;
			
			Graphics2D G = (Graphics2D)(graphics);
			G.setStroke(new BasicStroke(.5f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,new float[]{2.5f},0f));
			G.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			G.drawString("Nouri Brothers Shopping Center", 50, 25);
			G.drawString("999 Main st. Paterson NJ 07503", 50, 45);
			G.drawString("Toll Free: 1-800-356-6874", 50, 65);
			G.drawString("Bill to: ", 300, 10);
			G.drawString(CUSTOMER_LIST.get(0).getFirstName() + " " + CUSTOMER_LIST.get(0).getLastName(), 300, 25);
			G.drawString(CUSTOMER_LIST.get(0).getAddress() + " " + CUSTOMER_LIST.get(0).getCity() + " " + CUSTOMER_LIST.get(0).getState() + " " + CUSTOMER_LIST.get(0).getZipcode(), 300, 45);
			G.drawString(CUSTOMER_LIST.get(0).getTelephone(), 300, 65);
			G.drawString("Client ID: ", 50, 105);
			G.drawString(String.valueOf(CUSTOMER_LIST.get(0).getID()), 115, 105);
			G.drawString("Date: ", 190, 105);
			G.drawString(DateToStr(new Date()), 230, 105);
			G.drawLine(0, 118, 612, 118);
			G.drawString("Item ID", 0, 130);
			G.drawString("Quantity", 75, 130);
			G.drawString("Item Description", 150, 130);
			G.drawString("Unit Price", 400, 130);
			G.drawString("Line Total", 500, 130);
			G.drawLine(0, 140, 612, 140);
			if(!BOX_LIST.isEmpty()){
			G.drawString(String.valueOf(BOX_LIST.get(0).getID()),0 ,155);
			G.drawString("1", 75, 155);
			G.drawString(BOX_LIST.get(0).getModel() + " S/N: " + BOX_LIST.get(0).getSerial(), 150, 155);
			G.drawString(String.valueOf(BOX_LIST.get(0).getPrice()), 400, 155);
			G.drawString(String.valueOf(BOX_LIST.get(0).getPrice()), 500, 155);
			G.drawLine(0, 200, 612, 200);
			Total = Total + BOX_LIST.get(0).getPrice();
			}
			if(!CODE_LIST.isEmpty()){
			G.drawString(String.valueOf(CODE_LIST.get(0).getID()), 0, 170);
			G.drawString("1", 75, 170);
			G.drawString(CODE_LIST.get(0).getCode() + " valid for: " + String.valueOf(CODE_LIST.get(0).getLength()) + " days", 150, 170);
			G.drawString(String.valueOf(CODE_LIST.get(0).getPrice()), 400, 170);
			G.drawString(String.valueOf(CODE_LIST.get(0).getPrice()), 500, 170);
			Total = Total + CODE_LIST.get(0).getPrice();
			}
			G.drawString("Total: " + String.valueOf(Total), 465, 215);
			return 0;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			PrinterJob job = PrinterJob.getPrinterJob();
			
			job.setPrintable(this);
			
			if(job.printDialog() == true){
				try{
					job.print();
				}catch(PrinterException PE){
					DisplayRedInfo("Could not print!");
					PE.printStackTrace();
				}
			}
			
		}
		
	}
	
	public String DateToStr (Date date){
		DateFormat DF = DateFormat.getDateTimeInstance();
		StringBuffer str = DF.format(date, new StringBuffer(), new FieldPosition(DateFormat.AM_PM_FIELD));
		return str.toString();
	}
	public String TrimTelephone(String str){
		String output = "";
		output = str.replaceAll("[\\s\\-()]", "");
		return output;
	}
	public String setExpirationDate (int days){
		SimpleDateFormat simple = new SimpleDateFormat("MM-dd-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, days);
		String output = simple.format(cal.getTime());
		return output;
	}
	public void ConnectToDataBase(String host, String user, String password){
		try{
		connection = DriverManager.getConnection(host, user, password);
		}catch(SQLException e1){
			DisplayRedInfo("Could not connect to database");
			e1.printStackTrace();
		}
	}
	public void AddNewCustomer (String SQL){
		try{
			preparedStatement = connection.prepareStatement(SQL);
			RS = preparedStatement.executeQuery();
			while(RS.next()){
				CUSTOMER_LIST.add(new customer(RS.getString(2), RS.getString(3), RS.getString(4), RS.getString(5), RS.getString(6), RS.getString(7), RS.getString(8), RS.getString(9), RS.getInt(1)));
			}
			RS.close();
		}catch(SQLException e1){
			DisplayRedInfo("COULD NOT CONNECT TO DATABASE");
			e1.printStackTrace();
		}
	}
	
	public void AddNewHardware (String SQL){
		try{
			preparedStatement = connection.prepareStatement(SQL);
			RS = preparedStatement.executeQuery();
			while(RS.next()){
				BOX_LIST.add(new box(RS.getInt(1), RS.getString(4), RS.getString(2), RS.getString(3), RS.getDouble(5)));
			}
		}catch(SQLException e1){
			DisplayRedInfo("COULD NOT CONNECT TO DATABASE");
			e1.printStackTrace();
		}
	}
	
	public void AddNewSubscription (String SQL){
		try{
			preparedStatement = connection.prepareStatement(SQL);
			RS = preparedStatement.executeQuery();
			while(RS.next()){
				CODE_LIST.add(new code(RS.getInt(1), RS.getString(2), RS.getDouble(3), RS.getInt(4)));
			}
		}catch(SQLException e1){
			DisplayRedInfo("COULD NOT CONNECT TO DATABASE");
			e1.printStackTrace();
		}
	}
	
	public void DisconnectFromDataBase(){
		try{
			connection.close();
		}catch(SQLException e1){
			DisplayRedInfo("Couldn't disconnect from database");
			e1.printStackTrace();
		}
	}
	
	public void ClearAllArrayLists(){
		CUSTOMER_LIST.clear();
		BOX_LIST.clear();
		CODE_LIST.clear();
	}
	
	public void DisplayRedInfo (String str){
		INFO.setForeground(Color.RED);
		INFO.setText(str);
	}
	
	public void DisplayBlueInfo (String str){
		INFO.setForeground(Color.BLUE);
		INFO.setText(str);
	}
	public void ClearINFOLabel (){
		INFO.setText("");
	}
	public void DisplayCustomerInfoFromCustomerList(){
		FIRST_TEXT.setText(CUSTOMER_LIST.get(0).getFirstName());
		LAST_TEXT.setText(CUSTOMER_LIST.get(0).getLastName());
		ADDRESS_TEXT.setText(CUSTOMER_LIST.get(0).getAddress());
		CITY_TEXT.setText(CUSTOMER_LIST.get(0).getCity());
		STATE_TEXT.setText(CUSTOMER_LIST.get(0).getState());
		ZIP_TEXT.setText(CUSTOMER_LIST.get(0).getZipcode());
		TEL_TEXT.setText(CUSTOMER_LIST.get(0).getTelephone());
		EMAIL_TEXT.setText(CUSTOMER_LIST.get(0).getEmail());
	}
	
	public boolean EmptyTextField(JTextField TextField){
		if(TextField.getText().trim().isEmpty())
			return true;
		return false;
	}
	
	public void EnableFeatures (){
		SERIAL_TEXT.setEnabled(true);
		SEARCH_SERIAL.setEnabled(true);
		CODE_TEXT.setEnabled(true);
		CODE_LENGTH.setEnabled(true);
		ADD_CODE.setEnabled(true);
	}
	public void DisableCustomerTextFields(){
		FIRST_TEXT.setEnabled(false);
		LAST_TEXT.setEnabled(false);
		ADDRESS_TEXT.setEnabled(false);
		CITY_TEXT.setEnabled(false);
		STATE_TEXT.setEnabled(false);
		ZIP_TEXT.setEnabled(false);
		TEL_TEXT.setEnabled(false);
		EMAIL_TEXT.setEnabled(false);
	}
	
	public void EnableCustomerTextFields(){
		FIRST_TEXT.setEnabled(true);
		LAST_TEXT.setEnabled(true);
		ADDRESS_TEXT.setEnabled(true);
		CITY_TEXT.setEnabled(true);
		STATE_TEXT.setEnabled(true);
		ZIP_TEXT.setEnabled(true);
		TEL_TEXT.setEnabled(true);
		EMAIL_TEXT.setEnabled(true);		
	}
	
	public void DisableHardware(){
		SERIAL_TEXT.setEnabled(false);
		MAC_TEXT.setEnabled(false);
		SEARCH_SERIAL.setEnabled(false);
		NUMOFHARDWARE.setEnabled(false);
	}
	
	public void DisableSubscription(){
		CODE_TEXT.setEnabled(false);
		CODE_LENGTH.setEnabled(false);
		ADD_CODE.setEnabled(false);
	}
	
	public void ClearHardwareFields(){
		MAC_TEXT.setText("");
		SERIAL_TEXT.setText("");
	}
	
	public void ClearSubscriptionFields(){
		CODE_TEXT.setText("");
		CODE_LENGTH.setValue(0);
		PACKAGE_TYPE.setSelectedIndex(0);
	}
	public void ClearCustomerFields(){
		FIRST_TEXT.setText("");
		LAST_TEXT.setText("");
		ADDRESS_TEXT.setText("");
		CITY_TEXT.setText("");
		STATE_TEXT.setText("");
		ZIP_TEXT.setText("");
		TEL_TEXT.setText("");
		EMAIL_TEXT.setText("");		
		NUMOFHARDWARE.setText("");
	}

	public boolean CustomerRequiedFields_Empty(){
		if(FIRST_TEXT.getText().isEmpty() && LAST_TEXT.getText().isEmpty() && TEL_TEXT.getText().isEmpty() && EMAIL_TEXT.getText().isEmpty()){
			return true;
		}
		return false;
	}
	
	public void getCredentials(){
		try{
			File file = new File("settings.dat");
			Scanner input = new Scanner(file);
			while(input.hasNext()){
				String token = input.next();
				if(token.equals("G")){
					EMAIL_HOST = "smtp.gmail.com";
				}
				if(token.equals("H")){
					EMAIL_HOST = "smtp.live.com";
				}
				if(token.equals("Y")){
					EMAIL_HOST = "smtp.mail.yahoo.comf";
				}
				if(token.equals("user")){
					EMAIL_USER = input.next();
				}
				if(token.equals("password")){
					EMAIL_PASSWORD = input.next();
				}
			}
			input.close();
		}catch(IOException e1){
			DisplayRedInfo("Could not find email credentials.");
			e1.printStackTrace();
		}
	}
	public String Substitute (String str){
		String [][] replacements = {
				{"[CODE]", CODE_LIST.get(0).getCode()},
				{"[LENGTH]", String.valueOf(CODE_LIST.get(0).getLength())},
				{"[C_PRICE]", String.valueOf(CODE_LIST.get(0).getPrice())},
				{"[PACKAGE]", CODE_LIST.get(0).getType()},
				{"[SERIAL]", BOX_LIST.get(0).getSerial()},
				{"[MAC]", BOX_LIST.get(0).getMac()},
				{"[MODEL]", BOX_LIST.get(0).getModel()},
				{"[B_PRICE]", String.valueOf(BOX_LIST.get(0).getPrice())},
				{"[F_NAME]", CUSTOMER_LIST.get(0).getFirstName()},
				{"[L_NAME]", CUSTOMER_LIST.get(0).getLastName()},
				{"[ADDRESS]", CUSTOMER_LIST.get(0).getAddress()},
				{"[CITY]", CUSTOMER_LIST.get(0).getCity()},
				{"[STATE]", CUSTOMER_LIST.get(0).getState()},
				{"[ZIPCODE]", CUSTOMER_LIST.get(0).getZipcode()},
				{"[EMAIL]", CUSTOMER_LIST.get(0).getEmail()},
				{"[TELEPHONE]", CUSTOMER_LIST.get(0).getTelephone()},
		};
		String temp = str;
		for(String [] replacement : replacements){
			temp = temp.replace(replacement[0], replacement[1]);
		}
		return temp;
	}
	public String Email_FROM(){
		String email = EMAIL_USER + "@";
		if(EMAIL_HOST.equals("smtp.gmail.com")){
			email = email + "gmail.com";
		}
		if(EMAIL_HOST.equals("smtp.live.com")){
			email = email + "hotmail.com";
		}
		if(EMAIL_HOST.equals("smtp.mail.yahoo.com")){
			email = email + "yahoo.com";
		}
		return email;
	}
}
