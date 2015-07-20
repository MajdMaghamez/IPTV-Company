/*
 * Majd Maghamez Computer Science Developer.
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class CodeTab extends JPanel {
	private static final long serialVersionUID = 1L;

	private ArrayList<code> CODE_LIST = new ArrayList<code>();
	private ArrayList<customer> CUSTOMER_LIST = new ArrayList<customer>();
	
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet RS = null;
    private final String HOST = "jdbc:mysql://ATNDB.db.11581833.hostedresource.com:3306/ATNDB";
    private final String USER = "ATNDB";
    private final String PASSWORD = "Safaryy1987%";
	
    private JTextField SEARCH = new JTextField(20);
	private JButton SEARCH_BTN = new JButton("Search");
	private JButton ADD_BTN = new JButton("Add");
	private JButton REMOVE_BTN = new JButton("Remove");
	private JButton INVALID_BTN = new JButton("Invalid / Report");
	private JLabel STATUS = new JLabel(" Search by code number");
	private JLabel INFO = new JLabel();    
	
	private String [] Columns_Names = {"Code", "Length", "Type", "Price", "Reason"};
	private String [][] Replace_Values = {
			
	};
	
	private JTable REPLACEMENT = new JTable();
	DefaultTableModel REPLACEMENT_TABLE_DATA = (DefaultTableModel) REPLACEMENT.getModel(); 
	private JScrollPane REPLACEMENT_SCROLL = new JScrollPane(REPLACEMENT);
	private JLabel REASON_LABEL = new JLabel("Replacement reason: ");
	private JButton REPLACE = new JButton("Replace");
	private JButton DELETE = new JButton("Delete");
	private JLabel Replace_With = new JLabel("Replace with: ");
	private JTextField REPLACEMENT_TEXTFIELD = new JTextField(20);

	private JTextField REASON = new JTextField (20);
	
	public CodeTab(){
		
		REPLACEMENT_TABLE_DATA.setColumnIdentifiers(Columns_Names);
		REPLACEMENT.setSize(300, 100);
		
		REMOVE_BTN.setEnabled(false);
		INVALID_BTN.setEnabled(false);
		//-----------------------------
		ConnectToDataBase(HOST, USER, PASSWORD);
		String SQL = "SELECT `code_repair`.`code`, `code_repair`.`length`, `code_repair`.`type`, `code_repair`.`price`, `code_repair`.`note` FROM `ATNDB`.`code_repair`;";
		FillCode_list_DisplayOnRepairLabel(SQL);
		DisconnectFromDataBase();
		//-----------------------------
		JPanel P1 = new JPanel();
		P1.setBorder(BorderFactory.createTitledBorder("Search information"));
		JPanel P2 = new JPanel();
		P2.setBorder(BorderFactory.createTitledBorder("Replacement Center"));
		
		GroupLayout Layout = new GroupLayout(P1);
		P1.setLayout(Layout);
		add(P1);
		Layout.setAutoCreateGaps(true);
		Layout.setAutoCreateContainerGaps(true);
		
		GroupLayout Layout2 = new GroupLayout(P2);
		P2.setLayout(Layout2);
		add(P2);
		Layout2.setAutoCreateContainerGaps(true);
		Layout2.setAutoCreateGaps(true);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Layout.setHorizontalGroup(Layout.createSequentialGroup()
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(SEARCH_BTN)
						)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(SEARCH)
						.addComponent(STATUS)
						.addComponent(INFO)
						)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(ADD_BTN)
						.addComponent(REMOVE_BTN)
						.addComponent(INVALID_BTN)
						)
				);
		
		Layout.linkSize(SwingConstants.HORIZONTAL, ADD_BTN, REMOVE_BTN, INVALID_BTN);
		
		Layout.setVerticalGroup(Layout.createSequentialGroup()
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(SEARCH_BTN)
						.addComponent(SEARCH)
						.addComponent(ADD_BTN)
						)
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addGroup(Layout.createSequentialGroup()
								.addComponent(STATUS)
								.addComponent(INFO)
								)
						.addGroup(Layout.createSequentialGroup()
								.addComponent(REMOVE_BTN)
								.addComponent(INVALID_BTN)
								)
						)
				);
		
		Layout2.setHorizontalGroup(Layout2.createSequentialGroup()
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(Layout2.createSequentialGroup()
								.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(REASON_LABEL)
								)
								.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(REASON)
								)
						)
						.addGroup(Layout2.createSequentialGroup()
								.addComponent(REPLACEMENT_SCROLL)
						)
						.addGroup(Layout2.createSequentialGroup()
								.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(Replace_With)
								)
								.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(REPLACEMENT_TEXTFIELD)
								)
								.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(REPLACE)
								)
								.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(DELETE)
								)
						)
				)
		);
		
		Layout2.linkSize(SwingConstants.HORIZONTAL, REPLACE, DELETE);
		Layout2.setVerticalGroup(Layout2.createSequentialGroup()
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(REASON_LABEL)
						.addComponent(REASON)
				)
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(REPLACEMENT_SCROLL)
				)
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(Replace_With)
						.addComponent(REPLACEMENT_TEXTFIELD)
						.addComponent(REPLACE)
						.addComponent(DELETE)
				)
		);
		
		ADD_BTN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Add_code AD = new Add_code();
				AD.setVisible(true);
			}
		});
		
		SEARCH_BTN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(EmptyTextField(SEARCH)){
					DisplayRedInfo("Error! search box cannot be empty.");
				}
				else{
					ClearArrayLists();
					String SQL1 = "SELECT `code`.`ID`, `code`.`code`, `code`.`length`, `code`.`type`, `code`.`price`, `code`.`active`, `code`.`customer_ID` FROM `code` WHERE `code`.`code` = '" + SEARCH.getText() + "';";
					ConnectToDataBase(HOST, USER, PASSWORD);	
					getCodeAndCustomer(SQL1);
					ClearSearchBox();
					DisconnectFromDataBase();
				}				
			}
		});
		
		INVALID_BTN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!EmptyTextField(SEARCH) && !CODE_LIST.isEmpty()){
					try{
						String note = REASON.getText();
						String SQL = "INSERT INTO `ATNDB`.`code_repair` (`ID`, `code`, `length`, `type`, `price`, `note`) VALUES (" + CODE_LIST.get(0).getID() + ", '" + CODE_LIST.get(0).getCode() + "', " + CODE_LIST.get(0).getLength() + ", '" + CODE_LIST.get(0).getType() + "', '" + CODE_LIST.get(0).getPrice() + "', '" + note + "');";
						ConnectToDataBase(HOST, USER, PASSWORD);
						preparedStatement = connection.prepareStatement(SQL);
						preparedStatement.executeUpdate();
						String [] token = {String.valueOf(CODE_LIST.get(0).getCode()), String.valueOf(CODE_LIST.get(0).getLength()), CODE_LIST.get(0).getType(), String.valueOf(CODE_LIST.get(0).getPrice()),  note};
						REPLACEMENT_TABLE_DATA.addRow(token);
						ClearSearchBox();
						DisconnectFromDataBase();
					}catch(SQLException e1){
						DisplayRedInfo("Couldn't connect to Database!");
						e1.printStackTrace();
					}
				}
				else{
					DisplayRedInfo("Search box cannot be empty!");
				}
			}
		});	
		
		REMOVE_BTN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!EmptyTextField(SEARCH) && !CODE_LIST.isEmpty()){
					try{
						String SQL = "DELETE FROM `ATNDB`.`code` WHERE `code`.`ID` = '" + CODE_LIST.get(0).getID() + "' ;";
						ConnectToDataBase(HOST, USER, PASSWORD);
						preparedStatement = connection.prepareStatement(SQL);
						preparedStatement.executeUpdate();
						DisplayBlueInfo(CODE_LIST.get(0).getCode() + " Was removed.");
						ClearSearchBox();
						DisconnectFromDataBase();
					}catch(SQLException E1){
						DisplayRedInfo("Could not connect to Database!");
						E1.printStackTrace();
					}

				}
				else{
					DisplayRedInfo("The search box cannot be empty!");
				}
			}
		});
		DELETE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int selectedRowIndex = REPLACEMENT.getSelectedRow();
				if(selectedRowIndex >= 0){
					String codeFromTable = String.valueOf(REPLACEMENT_TABLE_DATA.getValueAt(selectedRowIndex, 0));
					ClearDisplayInfo();
					try{
						String SQL1 = "DELETE FROM `ATNDB`.`code_repair` WHERE `code_repair`.`code` = '" + codeFromTable + "' ;";
						ConnectToDataBase(HOST, USER, PASSWORD);
						preparedStatement = connection.prepareStatement(SQL1);
						preparedStatement.executeUpdate();
						ClearSearchBox();
						REPLACEMENT_TABLE_DATA.removeRow(selectedRowIndex);
						REPLACEMENT_TABLE_DATA.fireTableDataChanged();
						DisplayBlueInfo("Replacement code removed.");
						DisconnectFromDataBase();
					}catch(SQLException e1){
						DisplayRedInfo("Could not connect to Database!");
						e1.printStackTrace();
					}
				}
				else{
					DisplayRedInfo("Please Select a row");
				}
			}
		});
		REPLACE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int selectedRowIndex = REPLACEMENT.getSelectedRow();
				String codeFromTable = String.valueOf(REPLACEMENT_TABLE_DATA.getValueAt(selectedRowIndex, 0));
				int lengthFromTable = Integer.valueOf((String) REPLACEMENT_TABLE_DATA.getValueAt(selectedRowIndex, 1));
				String typeFromTable = String.valueOf(REPLACEMENT_TABLE_DATA.getValueAt(selectedRowIndex, 2));
				double priceFromTable = Double.valueOf((String) REPLACEMENT_TABLE_DATA.getValueAt(selectedRowIndex, 3));
				if(!codeFromTable.isEmpty() && !EmptyTextField(REPLACEMENT_TEXTFIELD)){
					try{
						String SQL1 = "DELETE FROM `ATNDB`.`code_repair` WHERE `code_repair`.`code` = '" + codeFromTable + "' ;";
						String SQL2 = "INSERT INTO `ATNDB`.`code` (`ID`, `code`, `length`, `type`, `purchase_date`, `expiration_date`, `price`, `active`, `customer_ID`) VALUES (NULL, '" +
								REPLACEMENT_TEXTFIELD.getText() + "', '" + lengthFromTable + "', '" + typeFromTable + "', '', '', '" + priceFromTable + "', '0', '0');";
						ConnectToDataBase(HOST, USER, PASSWORD);
						preparedStatement = connection.prepareStatement(SQL1);
						preparedStatement.executeUpdate();
						preparedStatement = connection.prepareStatement(SQL2);
						preparedStatement.executeUpdate();
						ClearSearchBox();
						REPLACEMENT_TABLE_DATA.removeRow(selectedRowIndex);
						REPLACEMENT_TABLE_DATA.fireTableDataChanged();
						DisplayBlueInfo("Replacement complete.");
						DisconnectFromDataBase();
					}catch(SQLException E1){
						DisplayRedInfo("Could not connect to Database!");
						E1.printStackTrace();
					}
				}
			}
		});
	}
	
	public void enableFeaturs (){
		REMOVE_BTN.setEnabled(true);
		INVALID_BTN.setEnabled(true);
	}
	public void disableFeatures (){
		REMOVE_BTN.setEnabled(false);
		INVALID_BTN.setEnabled(false);
	}
	
	public void DisplayRedInfo (String str){
		INFO.setForeground(Color.RED);
		INFO.setText(str);
	}
	
	public void DisplayBlueInfo (String str){
		INFO.setForeground(Color.BLUE);
		INFO.setText(str);
	}
	
	public void ClearDisplayInfo(){
		INFO.setText("");
	}
	
	public boolean EmptyTextField(JTextField TextField){
		if(TextField.getText().trim().isEmpty())
			return true;
		return false;
	}
	
	public void ConnectToDataBase (String host, String user, String pass){
		try{
			connection = DriverManager.getConnection(host, user, pass);
		}catch(SQLException e1){
			DisplayRedInfo("Could not connect to Database");
			e1.printStackTrace();
		}
	}
	
	public void DisconnectFromDataBase (){
		try{
			connection.close();
		}catch(SQLException e1){
			DisplayRedInfo("Could not disconnect from database");
			e1.printStackTrace();
		}
	}
	
	public void getCodeAndCustomer (String SQL1){
		try{
			preparedStatement = connection.prepareStatement(SQL1);
			RS = preparedStatement.executeQuery();
				if(RS.next()){
					ClearArrayLists();
					CODE_LIST.add(new code(RS.getInt(1), RS.getString(2), RS.getInt(3), RS.getString(4), RS.getDouble(5), ConvertToBoolean(RS.getInt(6))));
					CUSTOMER_LIST.add(new customer(RS.getInt(6)));
					RS.close();
				if(CUSTOMER_LIST.get(0).getID() != 0){
					String SQL2 = "SELECT `customer`.`fname`, `customer`.`lname`, `customer`.`telephone` FROM `customer` WHERE `customer`.`ID` ='" + CUSTOMER_LIST.get(0).getID() +"';";
					preparedStatement = connection.prepareStatement(SQL2);
					RS = preparedStatement.executeQuery();
					RS.next();
					CUSTOMER_LIST.get(0).setFirstName(RS.getString(1));
					CUSTOMER_LIST.get(0).setLastName(RS.getString(2));
					CUSTOMER_LIST.get(0).setTelephone(RS.getString(2));

					INFO.setText("<html><strong><font color='blue'>Code: </font>" + CODE_LIST.get(0).getCode() + "<br>" + 
							"<font color='blue'>Length: </font>" + CODE_LIST.get(0).getLength() + "<br>" +
							"<font color='blue'>Type: </font>" + CODE_LIST.get(0).getType() + "<br>" + 
							"<font color='blue'>Used: </font>" + CODE_LIST.get(0).isActive() + "<br>" + 
							"<font color='blue'>Customer Name: </font>" + CUSTOMER_LIST.get(0).getFirstName() + " " + CUSTOMER_LIST.get(0).getLastName() + "<br>" + 
							"<font color='blue'>Telephone: </font>" + CUSTOMER_LIST.get(0).getTelephone() + "<br>" + "</strong></html>");
				}
				else{
					INFO.setText("<html><strong><font color='blue'>Code: </font>" + CODE_LIST.get(0).getCode() + "<br>" + 
							"<font color='blue'>Length: </font>" + String.valueOf(CODE_LIST.get(0).getLength()) + " day(s)<br>" +
							"<font color='blue'>Type: </font>" + CODE_LIST.get(0).getType() + "<br>" + 
							"<font color='blue'>Used: </font>" + String.valueOf(CODE_LIST.get(0).isActive()) + "<br></strong></html>");					
				}
				RS.close();
				enableFeaturs();
			}
				else{
					DisplayRedInfo("could not find code!");
				}
			}catch(SQLException e1){
				DisplayRedInfo("Could not connect to database!");
				e1.printStackTrace();
			}
		}
	
	public boolean ConvertToBoolean (int A){
		if(A == 1)
			return true;
		return false;
	}
	
	public void ClearArrayLists(){
		CODE_LIST.clear();
		CUSTOMER_LIST.clear();
	}
	
	public void ClearSearchBox(){
		SEARCH.setText("");
	}
	
	public void FillCode_list_DisplayOnRepairLabel(String SQL){
		try{
			preparedStatement = connection.prepareStatement(SQL);
			RS = preparedStatement.executeQuery();
			while(RS.next()){
				CODE_LIST.add(new code(RS.getString(1), RS.getInt(2), RS.getString(3), RS.getDouble(4), RS.getString(5)));
			}
			Replace_Values = new String [CODE_LIST.size()][5];
			for(int i = 0; i < CODE_LIST.size(); i++){
				Replace_Values[i][0] = CODE_LIST.get(i).getCode();
				Replace_Values[i][1] = String.valueOf((CODE_LIST.get(i).getLength()));
				Replace_Values[i][2] = CODE_LIST.get(i).getType();
				Replace_Values[i][3] = String.valueOf(CODE_LIST.get(i).getPrice());
				Replace_Values[i][4] = CODE_LIST.get(i).getNote();
				REPLACEMENT_TABLE_DATA.addRow(Replace_Values[i]);
			}
			REPLACEMENT_TABLE_DATA.fireTableDataChanged();
			ClearArrayLists();
		RS.close();
		}catch(SQLException E1){
			DisplayRedInfo("Could not connect to Database!");
			E1.printStackTrace();
		}
		
	}
}
