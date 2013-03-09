package model;

public class Guest 
{	
	//Class variables
	private int guestID;
	private String guestAddress;
	private String guestAffiliation;
	private String guestName;
	
	public Guest()
	{
		
	}
	
	public Guest(int guestID, String guestAddress, String guestAffiliation,
			String guestName) 
	{	
		this.guestID = guestID;
		this.guestAddress = guestAddress;
		this.guestAffiliation = guestAffiliation;
		this.guestName = guestName;
	}

	public Guest(String guestAddress, String guestAffiliation,
			String guestName) 
	{	
		this.guestAddress = guestAddress;
		this.guestAffiliation = guestAffiliation;
		this.guestName = guestName;
	}
	
	public int getGuestID() {
		return guestID;
	}

	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}

	public String getGuestAddress() {
		return guestAddress;
	}

	public void setGuestAddress(String guestAddress) {
		this.guestAddress = guestAddress;
	}

	public String getguestAffiliation() {
		return guestAffiliation;
	}

	public void setGuestAffiliation(String guestAffiliation) {
		this.guestAffiliation = guestAffiliation;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
}
