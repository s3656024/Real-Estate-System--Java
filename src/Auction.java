// s3656024 assignment2
import java.util.Scanner;
public class Auction extends Sales{
	static Scanner kb = new Scanner(System.in);
	private String highestBidder = "NO BIDS PLACED"; 
	public Auction(String saleID, String propertyAddress, int reservePrice)
	{
		super(saleID,propertyAddress,reservePrice);

	}
	public String getPropertyStatus(){
		//override Sales class
		if (getAcceptingOffers() == true )
			return "ACCEPTING BIDS";
		else if (getCurrentOffer() >= getReservePrice())
			return "SOLD";
		else 
			return "PASSED IN";
	}
	public String closeAuction(){
		//closes the auction
		if (getAcceptingOffers() == false)
			return "False";
		else 
		{
			setAcceptingOffers(false);
			return "True";
		}
	}
	public void makeOffer(int offerPrice) throws OfferException{
		// overrides the Sales class
		//includes the highest bidder
		// has auction specific exception throws 
		if (getAcceptingOffers() == false)
			throw new OfferException("Error - This auction has been closed until further notice");
		if (offerPrice <= getCurrentOffer())
			throw new OfferException("Error - New offer for the auction must be higher"
					+ " than Current offer!\n Offer rejected!");
		if (offerPrice > getReservePrice())
			setAcceptingOffers(false);
		setCurrentOffer(offerPrice);
		System.out.format("%-25s", "Enter Bidder Name:");
		highestBidder = kb.nextLine();
	}
	public String getHighestBidder(){
		return highestBidder;
	}
	public void setHighestBidder(String s){
		highestBidder = s;
	}
	public String getSaleDetails(){
		super.getSaleDetails();
		String seventhLine = String.format("%-20s %s\n","Highest Bidder:",highestBidder );

		return 	super.getSaleDetails() + seventhLine;
	}
}
