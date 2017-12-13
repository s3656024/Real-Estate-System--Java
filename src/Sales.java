// s3656024 assignment2
public class Sales {
	private String saleID;
	private String propertyAddress;
	private int currentOffer;
	private int reservePrice;
	private boolean acceptingOffers = true;

	public Sales(String saleID, String propertyAddress, int reservePrice)
	{
		//constructor to store the supplied information
		this.saleID = saleID;
		this.propertyAddress = propertyAddress;
		this.reservePrice = reservePrice;
	}
	// setting and getting the value for saleID
	public void setSaleID(String x){
		saleID = x;
	}
	public String getSaleID(){
		return saleID;
	}
	// setting and getting the value for propertyAddress
	public void setPropertyAddress(String x){
		propertyAddress = x;
	}
	public String getPropertyAddress(){
		return propertyAddress;
	}
	// setting and getting the value for currentOffer
	public void setCurrentOffer(int x){
		currentOffer = x;
	}
	public int getCurrentOffer(){
		return currentOffer;
	}
	//setting and getting the value for reserveOffer
	public void setReservePrice(int x){
		reservePrice = x;
	}
	public int getReservePrice(){
		return reservePrice;
	}
	//setting and getting the values for acceptingOffers
	public void setAcceptingOffers(boolean x){
		acceptingOffers =x;
	}
	public boolean getAcceptingOffers(){
		return acceptingOffers;
	}
	public void makeOffer(int offerPrice)throws OfferException{
		// throws the Exception for the Driver class to catch 
		//check if the offer given is accepted
		if(offerPrice <= currentOffer)
			throw new OfferException("Error - New offer must be higher"
					+ " than Current offer!\n Offer rejected!");
		else 
			currentOffer = offerPrice;
			// if accepted the current offer becomes the offer given

		if (acceptingOffers == false) // check if the user can bet on the property
			throw new OfferException("Error - The Property is not currently "
					+ "accepting offers");

		if (offerPrice >= reservePrice)
		{
			acceptingOffers = false;
		}
	}
	public String getPropertyStatus(){
		if (acceptingOffers == false)
			return "Sold";
		else
			return "On Sale";
	}
	public String getSaleDetails(){ // returns the sale properties
		String firstLine = String.format("%-20s %s\n","Sale ID:", saleID);
		String secondLine = String.format("%-20s %s\n", "Property Address;", propertyAddress);
		String thirdLine = String.format("%-20s %s\n","Current Offer:", currentOffer);
		String fourthLine = String.format("%-20s %s\n","Reserve Price:", reservePrice);
		String fifthLine = String.format("%-20s %s\n","Accepting offers:", acceptingOffers);
		String sixthLine = String.format("%-20s %s\n","Sale Status:", getPropertyStatus());
		return firstLine + secondLine + thirdLine + fourthLine + fifthLine + sixthLine;
	}
}
