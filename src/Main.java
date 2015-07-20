import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Main{
	
	public static void main (String [] args) throws ClassNotFoundException, SQLException{
		Connection connection = null;
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("MySQL driver has been loaded");
		
		JFrame frame = new JFrame();
		JTabbedPane pane = new JTabbedPane();
		CustomerTab customerTab = new CustomerTab(connection);
		BoxTab boxTab = new BoxTab();
		CodeTab codeTab = new CodeTab();
		Inventory inventory = new Inventory();
		Setting setting = new Setting();
		pane.addTab("Customers", customerTab);
		pane.addTab("Hardware", boxTab);
		pane.addTab("Subscription", codeTab);
		pane.addTab("Inventory", inventory);
		pane.addTab("Email setting", setting);
		
		frame.getContentPane().add(pane);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(595, 557);
		frame.setTitle("IPTV Manager");
		
	}
}
