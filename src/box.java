

public class box {
	
	private int ID; //ID for database use.
	private String model; //Holds the value of box model number it is used in printing receipt and helpful when trouble shooting equipment
	private String serial; //Hold the value of box serial number, it is used in searching.
	private String mac; //Holds the value of the box mac address, it is used in searching.
	private double price; //Holds the value of the price, with or without tax.
	private String note;

	public box(){
		this.model = "";
		this.serial = "";
		this.mac = "";
		this.price = 0.0;
	}
	public box(int ID, String model, String serial, String mac, double price){
		this.ID = ID;
		this.model = model;
		this.serial = serial;
		this.mac = mac;
		this.price = price;
	}
	
	public box(String serial,  String mac, double price){
		this.serial = serial;
		this.mac = mac;
		this.price = price;
	}
	
	public box(String model, String serial, String mac, double price, String note){
		this.model = model;
		this.serial = serial;
		this.mac = mac;
		this.price = price;
		this.note = note;
	}

	protected String getModel() {
		return model;
	}
	protected void setModel(String model) {
		this.model = model;
	}
	protected String getSerial() {
		return serial;
	}
	protected void setSerial(String serial) {
		this.serial = serial;
	}
	protected String getMac() {
		return mac;
	}
	protected void setMac(String mac) {
		this.mac = mac;
	}
	protected double getPrice() {
		return price;
	}
	protected void setPrice(double price) {
		this.price = price;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
