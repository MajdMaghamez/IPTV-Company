
public class customer {
	
	private String firstName; //Customer first name
	private String lastName; //Customer last name
	private String address; //Customer address
	private String city; //Customer city
	private String state; //Customer state
	private String zipcode; //Customer zip code
	private String telephone; //Customer telephone number.
	private String email; //Customer Email
	private int ID; //Customer ID
	
	public customer(){
		this.firstName = "";
		this.lastName = "";
		this.address = "";
		this.city = "";
		this.state = "";
		this.zipcode = "";
		this.telephone = "";
		this.email = "";
		this.ID = 0;
	}
	public customer(String firstName, String lastName, String address, String city, String state, String zipcode, String telephone, String email, int ID){
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.telephone = telephone;
		this.email = email;
		this.ID = ID;
	}
	public customer(int ID){
		this.ID = ID;
	}

	protected int getID() {
		return ID;
	}
	protected void setID(int iD) {
		ID = iD;
	}
	protected String getFirstName() {
		return firstName;
	}
	protected void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	protected String getLastName() {
		return lastName;
	}
	protected void setLastName(String lastName) {
		this.lastName = lastName;
	}
	protected String getAddress() {
		return address;
	}
	protected void setAddress(String address) {
		this.address = address;
	}
	protected String getCity() {
		return city;
	}
	protected void setCity(String city) {
		this.city = city;
	}
	protected String getState() {
		return state;
	}
	protected void setState(String state) {
		this.state = state;
	}
	protected String getZipcode() {
		return zipcode;
	}
	protected void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	protected String getTelephone() {
		return telephone;
	}
	protected void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	protected String getEmail() {
		return email;
	}
	protected void setEmail(String email) {
		this.email = email;
	}
}
