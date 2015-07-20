import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Inventory extends JPanel{

	private static final long serialVersionUID = 1L;
	
	String [] CODE_INVENTORY_COLUMNS_NAMES = {"Code number", "Package", "Length", "Price"};
	String [] BOX_INVENTORY_COLUMNS_NAMES = {"Serial number", "Mac address", "Model number", "Price"};
	String [][] Code_Values = {
			
	};
	public String [][] Box_Values = {
			
	};
	
	private JTable BOX_INVENTORY = new JTable();
	DefaultTableModel BOX_TableData = (DefaultTableModel) BOX_INVENTORY.getModel();
	private JScrollPane BOX_SCROLL = new JScrollPane(BOX_INVENTORY);
	
	private JTable CODE_INVENTORY = new JTable();
	DefaultTableModel CODE_TableData = (DefaultTableModel) CODE_INVENTORY.getModel();
	private JScrollPane CODE_SCROLL = new JScrollPane(CODE_INVENTORY);

	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet RS = null;
	private static final String HOST = "jdbc:mysql://ATNDB.db.11581833.hostedresource.com:3306/ATNDB";
	private static final String USER = "ATNDB";
	private static final String PASSWORD = "Safaryy1987%";
	
	ArrayList<code> CODE_LIST = new ArrayList<code>();
	ArrayList<box> BOX_LIST = new ArrayList<box>();
	
	public Inventory(){
		
		BOX_TableData.setColumnIdentifiers(BOX_INVENTORY_COLUMNS_NAMES);
		CODE_TableData.setColumnIdentifiers(CODE_INVENTORY_COLUMNS_NAMES);
		
		CODE_INVENTORY.setSize(300, 200);
		BOX_INVENTORY.setSize(300, 200);
		JPanel P2 = new JPanel();
		
		P2.setBorder(BorderFactory.createTitledBorder("Available Codes"));
		JPanel P3 = new JPanel();
		P3.setBorder(BorderFactory.createTitledBorder("Available Hardware"));
		
		GroupLayout Layout1 = new GroupLayout(P2);
		P2.setLayout(Layout1);
		add(P2);
		Layout1.setAutoCreateContainerGaps(true);
		Layout1.setAutoCreateGaps(true);
		
		GroupLayout Layout2 = new GroupLayout(P3);
		P3.setLayout(Layout2);
		add(P3);
		Layout2.setAutoCreateContainerGaps(true);
		Layout2.setAutoCreateGaps(true);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Layout1.setHorizontalGroup(Layout1.createSequentialGroup()
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(CODE_SCROLL)
				)
		);
		
		Layout1.setVerticalGroup(Layout1.createSequentialGroup()
				.addGroup(Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(CODE_SCROLL)
				)
		);
		
		Layout2.setHorizontalGroup(Layout2.createSequentialGroup()
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(BOX_SCROLL)
				)
		);
		
		Layout2.setVerticalGroup(Layout2.createSequentialGroup()
				.addGroup(Layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(BOX_SCROLL)
				)
		);
		
		ConnectToDataBase(HOST, USER, PASSWORD);
		String SQL = "SELECT `box`.`ID`, `box`.`model`, `box`.`serial`, `box`.`mac`, `box`.`price` FROM `box` WHERE `box`.`customer_ID` ='0';";
		String SQL2 = "SELECT `code`.`ID`, `code`.`code`, `code`.`length`, `code`.`type`, `code`.`price` FROM `code` WHERE `code`.`active` = '0';";
		FillBoxList(SQL);
		FillCodeList(SQL2);
		if(!BOX_LIST.isEmpty()){
			Box_Values = new String [BOX_LIST.size()][4];
			for(int i = 0; i < BOX_LIST.size(); i++){
				Box_Values[i][0] = BOX_LIST.get(i).getSerial();
				Box_Values[i][1] = BOX_LIST.get(i).getMac();
				Box_Values[i][2] = BOX_LIST.get(i).getModel();
				Box_Values[i][3] = String.valueOf(BOX_LIST.get(i).getPrice());
				BOX_TableData.addRow(Box_Values[i]);
			}
			BOX_TableData.fireTableDataChanged();
		}
		if(!CODE_LIST.isEmpty()){
			Code_Values = new String [CODE_LIST.size()][4];
			for(int i = 0; i < CODE_LIST.size(); i++){
				Code_Values[i][0] = CODE_LIST.get(i).getCode();
				Code_Values[i][1] = CODE_LIST.get(i).getType();
				Code_Values[i][2] = String.valueOf(CODE_LIST.get(i).getLength());
				Code_Values[i][3] = String.valueOf(CODE_LIST.get(i).getPrice());
				CODE_TableData.addRow(Code_Values[i]);
			}
			CODE_TableData.fireTableDataChanged();
		}
		DisconnectFromDataBase();
		BOX_LIST.clear();
		CODE_LIST.clear();
	}
	
	public void ConnectToDataBase(String host, String user, String password){
		try{
			connection = DriverManager.getConnection(host, user, password);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	public void DisconnectFromDataBase(){
		try{
			connection.close();
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	public void FillBoxList (String SQL){
		try{
			preparedStatement = connection.prepareStatement(SQL);
			RS = preparedStatement.executeQuery();
			while(RS.next()){
				BOX_LIST.add(new box(RS.getInt(1), RS.getString(2), RS.getString(3), RS.getString(4), RS.getDouble(5)));
			}
			RS.close();
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	public void FillCodeList (String SQL){
		try{
			preparedStatement = connection.prepareStatement(SQL);
			RS = preparedStatement.executeQuery();
			while(RS.next()){
				CODE_LIST.add(new code(RS.getInt(1), RS.getString(2), RS.getInt(3), RS.getString(4), RS.getDouble(5)));
			}
			RS.close();
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
}
