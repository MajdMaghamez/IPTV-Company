/*
 * Majd Maghamez Computer Science Developer.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class code {
	private int ID;
	private String code;
	private int length; 
	private String type;
	private String purchaseDate;
	private String expirationDate;
	private boolean active;
	private double price;
	private String note;
	
	public code(){
		this.ID = 0;
		this.code = "";
		this.length = 0;
		this.type = "";
		this.purchaseDate = null;
		this.expirationDate = null;
		this.active = false;
		this.price = 0.0;
		this.note = "";
	}
	public code(int ID, String code, int length, String type, double price){
		this.ID = ID;
		this.code = code;
		this.length = length;
		this.type = type;
		this.price = price;
	}
	public code(int ID, String code, int length, String type, double price, boolean active){
		this.ID = ID;
		this.code = code;
		this.length = length;
		this.type = type;
		this.price = price;
		this.active = active;
	}
	public code(int ID, String code, double price, int length){
		this.ID = ID;
		this.code = code;
		this.price = price;
		this.length = length;
	}
	public code(String code, int length, String type, double price, String note){
		this.code = code;
		this.length = length;
		this.type = type;
		this.price = price;
		this.note = note;
	}
	protected int getID() {
		return ID;
	}
	protected void setID(int iD) {
		ID = iD;
	}
	protected String getCode() {
		return code;
	}
	protected void setCode(String code) {
		this.code = code;
	}
	protected int getLength() {
		return length;
	}
	protected void setLength(int length) {
		this.length = length;
	}
	protected String getType() {
		return type;
	}
	protected void setType(String type) {
		this.type = type;
	}
	protected String getPurchaseDate() {
		return purchaseDate;
	}
	protected void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	protected String getExpirationDate() {
		return expirationDate;
	}
	protected void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	protected boolean isActive() {
		return active;
	}
	protected void setActive(boolean active) {
		this.active = active;
	}
	protected double getPrice() {
		return price;
	}
	protected void setPrice(double price) {
		this.price = price;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	protected boolean ConvertToBoolean (int A){
		if(A == 1)
			return true;
		return false;
	}
	protected int ConvertToint(boolean B){
		if(B == true)
			return 1;
		return 0;
	}
	public static Date ConvertFromDatabase (String D){
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date d = DateFormat.parse(D);
			DateFormat.applyPattern("MM/dd/yyyy");
			return d;
		} catch (ParseException e) {
			System.out.println("There was a problem with parsing the date from database");
		}
		return new Date();
	}
}
