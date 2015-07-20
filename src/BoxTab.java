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

public class BoxTab extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<box> BOX_LIST = new ArrayList<box>();
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
	private JLabel STATUS = new JLabel("Search by serial number");
	private JLabel INFO = new JLabel();
	
	private String [] Columns_Names = {"Serial", "Mac", "Model", "Price", "Reason"};
	private String [][] Replace_Values = {
			
	};
	
	private JTable REPLACEMENT = new JTable();
	DefaultTableModel REPLACEMENT_TABLE_DATA = (DefaultTableModel) REPLACEMENT.getModel(); 
	private JScrollPane REPLACEMENT_SCROLL = new JScrollPane(REPLACEMENT);
	private JLabel REASON_LABEL = new JLabel("Replacement reason: ");
	private JButton REPLACE = new JButton("Replace");
	private JButton DELETE = new JButton("Delete");
	private JLabel Replace_With = new JLabel("Replace with Serial: ");
	private JLabel Replace_With2 = new JLabel("Mac: ");
	private JTextField REPLACEMENT_TEXTFIELD = new JTextField(20);
	private JTextField REPLACEMENT_TEXTFIELD2 = new JTextField(20);

	private JTextField REASON = new JTextField (20);
	
	public BoxTab(){
		
		REPLACEMENT_TABLE_DATA.setColumnIdentifiers(Columns_Names);
		REPLACEMENT.setSize(300, 100);
		
		REMOVE_BTN.setEnabled(false);
		INVALID_BTN.setEnabled(false);

		//-----------------------------
		ConnectToDataBase(HOST, USER, PASSWORD);
		String SQL = "SELECT `box_repair`.`serial`, `box_repair`.`mac`, `box_repair`.`model`, `box_repair`.`price`, `box_repair`.`note` FROM `ATNDB`.`box_repair`;";
		FillBox_list_DisplayOnRepairLabel(SQL);
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
		
		Layout.linkSize(SwingConstants.HORIZONTAL, ADD_BTN, REMOVE_BTN, INVALID_BTN);
		
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
		
		Layout.setVerticalGroup(Layout.createSequentialGroup()
				.addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(SEARCH_BTN)
						.addComponent(SEARCH)
						.addComponent(ADD_BTN))
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
										.addComponent(Replace_With2)
								)
								.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(REPLACEMENT_TEXTFIELD2)
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
						.addComponent(Replace_With2)
						.addComponent(REPLACEMENT_TEXTFIELD2)
						.addComponent(REPLACE)
						.addComponent(DELETE)
				)
		);
		
		SEARCH_BTN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(EmptyTextField(SEARCH)){
					DisplayRedInfo("Error! search box cannot be empty.");
				}
				else{
					ClearArrayLists();
					String SQL1 = "SELECT `box`.`ID`, `box`.`serial`, `box`.`mac`, `box`.`model`, `box`.`price`, `box`.`customer_ID` FROM `box` WHERE `box`.`serial` = '" + SEARCH.getText() + "';";
					ConnectToDataBase(HOST, USER, PASSWORD);	
					getBoxAndCustomer(SQL1);
					DisconnectFromDataBase();
				}				
			}
		});
		
		ADD_BTN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Add_box AB = new Add_box();
				AB.setVisible(true);
			}
		});

		INVALID_BTN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!EmptyTextField(SEARCH) && !BOX_LIST.isEmpty()){
					try{
						String note = REASON.getText();
						String SQL = "INSERT INTO `ATNDB`.`box_repair` (`ID`, `serial`, `mac`, `model`, `price`, `note`) VALUES (" + BOX_LIST.get(0).getID() + ", '" + BOX_LIST.get(0).getSerial() + "', '" + BOX_LIST.get(0).getMac() + "', '" + BOX_LIST.get(0).getModel() + "', '" + BOX_LIST.get(0).getPrice() + "', '" + note + "');";
						ConnectToDataBase(HOST, USER, PASSWORD);
						preparedStatement = connection.prepareStatement(SQL);
						preparedStatement.executeUpdate();
						String [] token = {BOX_LIST.get(0).getSerial(), BOX_LIST.get(0).getMac(), BOX_LIST.get(0).getModel(), String.valueOf(BOX_LIST.get(0).getPrice()),  note};
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
				if(!EmptyTextField(SEARCH) && !BOX_LIST.isEmpty()){
					try{
						String SQL = "DELETE FROM `ATNDB`.`box` WHERE `box`.`ID` = '" + BOX_LIST.get(0).getID() + "' ;";
						ConnectToDataBase(HOST, USER, PASSWORD);
						preparedStatement = connection.prepareStatement(SQL);
						preparedStatement.executeUpdate();
						DisplayBlueInfo(BOX_LIST.get(0).getSerial() + " Was removed.");
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
					String SerialFromTable = String.valueOf(REPLACEMENT_TABLE_DATA.getValueAt(selectedRowIndex, 0));
					ClearDisplayInfo();
					try{
						String SQL1 = "DELETE FROM `ATNDB`.`box_repair` WHERE `box_repair`.`serial` = '" + SerialFromTable + "' ;";
						ConnectToDataBase(HOST, USER, PASSWORD);
						preparedStatement = connection.prepareStatement(SQL1);
						preparedStatement.executeUpdate();
						ClearSearchBox();
						DisplayBlueInfo("Replacement Hardware removed.");
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
				if((selectedRowIndex >= 0) && !EmptyTextField(REPLACEMENT_TEXTFIELD) && !EmptyTextField(REPLACEMENT_TEXTFIELD2)){
					String SerialFromTable = String.valueOf(REPLACEMENT_TABLE_DATA.getValueAt(selectedRowIndex, 0));
					String ModelFromTable = String.valueOf(REPLACEMENT_TABLE_DATA.getValueAt(selectedRowIndex, 2));
					double priceFromTable = Double.valueOf((String) REPLACEMENT_TABLE_DATA.getValueAt(selectedRowIndex, 3));
					try{
						String SQL1 = "DELETE FROM `ATNDB`.`box_repair` WHERE `box_repair`.`serial` = '" + SerialFromTable + "' ;";
						String SQL2 = "INSERT INTO `ATNDB`.`box` (`ID`, `serial`, `mac`, `model`, `purchase_date`, `price`, `customer_ID`) VALUES (NULL, '" +
								REPLACEMENT_TEXTFIELD.getText() + "', '" + REPLACEMENT_TEXTFIELD2.getText() + "', '" + ModelFromTable + "', '', '" + priceFromTable + "', '0');";
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
				else{
					DisplayRedInfo("Please Select a row and fill serial & mac boxes");
				}
			}
		});
	}
	
	public void getBoxAndCustomer (String SQL1){
		try{
			preparedStatement = connection.prepareStatement(SQL1);
			RS = preparedStatement.executeQuery();
				if(RS.next()){
					ClearArrayLists();
					BOX_LIST.add(new box(RS.getInt(1), RS.getString(4), RS.getString(2), RS.getString(3), RS.getDouble(5)));
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
					ClearSearchBox();
					INFO.setText("<html><strong><font color='blue'>Serial: </font>" + BOX_LIST.get(0).getSerial() + "<br>" + 
							"<font color='blue'>Mac: </font>" + BOX_LIST.get(0).getMac() + "<br>" +
							"<font color='blue'>Model: </font>" + BOX_LIST.get(0).getModel() + "<br>" + 
							"<font color='blue'>Customer Name: </font>" + CUSTOMER_LIST.get(0).getFirstName() + " " + CUSTOMER_LIST.get(0).getLastName() + "<br>" + 
							"<font color='blue'>Telephone: </font>" + CUSTOMER_LIST.get(0).getTelephone() + "<br>" + "</strong></html>");
				}
				else{
					INFO.setText("<html><strong><font color='blue'>Serial: </font>" + BOX_LIST.get(0).getSerial() + "<br>" + 
							"<font color='blue'>Mac: </font>" + BOX_LIST.get(0).getMac() + "<br>" +
							"<font color='blue'>Model: </font>" + BOX_LIST.get(0).getModel() + "<br></strong></html>");		
					ClearSearchBox();
				}
				RS.close();
				enableFeaturs();
			}
				else{
					DisplayRedInfo("could not find Hardware!");
				}
			}catch(SQLException e1){
				DisplayRedInfo("Could not connect to database!");
				e1.printStackTrace();
			}
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
	
	public void ClearArrayLists(){
		BOX_LIST.clear();
		CUSTOMER_LIST.clear();
	}
	
	public void ClearSearchBox(){
		SEARCH.setText("");
	}
	
	public void FillBox_list_DisplayOnRepairLabel(String SQL){
		try{
			preparedStatement = connection.prepareStatement(SQL);
			RS = preparedStatement.executeQuery();
			while(RS.next()){
				BOX_LIST.add(new box(RS.getString(3), RS.getString(1), RS.getString(2), RS.getDouble(4), RS.getString(5)));
			}
			Replace_Values = new String [BOX_LIST.size()][5];
			for(int i = 0; i < BOX_LIST.size(); i++){
				Replace_Values[i][0] = BOX_LIST.get(i).getSerial();
				Replace_Values[i][1] = BOX_LIST.get(i).getMac();
				Replace_Values[i][2] = BOX_LIST.get(i).getModel();
				Replace_Values[i][3] = String.valueOf(BOX_LIST.get(i).getPrice());
				Replace_Values[i][4] = BOX_LIST.get(i).getNote();
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
