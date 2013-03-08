package model;

public class Guest 
{	
	//Class variables
	private String guestID;
	private String guestAddress;
	private String guestAffliation;
	private String guestName;
	
	public Guest()
	{
		
	}
	
	public Guest(String guestID, String guestAddress, String guestAffliation,
			String guestName) 
	{
		super();
		this.guestID = guestID;
		this.guestAddress = guestAddress;
		this.guestAffliation = guestAffliation;
		this.guestName = guestName;
	}

	public String getGuestID() {
		return guestID;
	}

	public void setGuestID(String guestID) {
		this.guestID = guestID;
	}

	public String getGuestAddress() {
		return guestAddress;
	}

	public void setGuestAddress(String guestAddress) {
		this.guestAddress = guestAddress;
	}

	public String getGuestAffliation() {
		return guestAffliation;
	}

	public void setGuestAffliation(String guestAffliation) {
		this.guestAffliation = guestAffliation;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
}
