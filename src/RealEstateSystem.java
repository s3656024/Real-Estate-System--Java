// s3656024 assignment2
import java.util.Scanner;
import java.util.ArrayList;

public class RealEstateSystem{

	static ArrayList<Sales> Sale = new ArrayList<>(); 
	ArrayList<String> bidder = new ArrayList<String>(); 
	static int counter = 0;
	static Scanner kb = new Scanner(System.in);
	static RealEstateSystem move = new RealEstateSystem();
	static int checkSaleLocation = 0;
	static boolean checkSaleID = false;
	static String saleID;

	static ReadingFile file = new ReadingFile();

	static  OfferException OfferException = new OfferException();
	public static void main(String[] args){
		file.readFromFile(counter, Sale);
		move.mainMenu();
	}
	public void mainMenu(){
		System.out.println("*** Real Estate System Menu ***");
		System.out.printf("%-25s %s\n", "Sale ID:", "A");
		System.out.printf("%-25s %s\n", "Submit Offer:", "B");
		System.out.printf("%-25s %s\n", "Display Sales Summary:", "C");
		System.out.printf("%-25s %s\n", "Add New Auction", "D");
		System.out.printf("%-25s %s\n", "Close Auction", "E");
		System.out.printf("%-25s %s\n", "Exit Program", "X");
		//each have their own Exception catches
		switch(kb.nextLine()){ // checks what the user inputs and moves to the right method
		case "a":
		case "A":
			try {
				move.choiceA();
			} catch (OfferException e) {
				System.out.println(e.getMessage());
				move.mainMenu();
			}
			break;
		case "b":
		case "B":
			try {
				move.choiceB();
			} catch (OfferException e) {
				System.out.println(e.getMessage());
				move.mainMenu();
			}
			break;
		case "c":
		case "C":
			try {
				move.choiceC();
			} catch (OfferException e) {
				System.out.println(e.getMessage());
				move.mainMenu();
			}
			break;
		case "d":
		case "D":
			try {
				move.choiceD();
			} catch (OfferException e) {
				System.out.println(e.getMessage());
				move.mainMenu();
			}
			break;
		case "e":
		case "E":
			try {
				move.choiceE();
			} catch (OfferException ex)
			{
				System.out.println(ex.getMessage());
				move.mainMenu();
			}
			break;
		case "X":
		case "x":
			move.choiceX();
			break;
		default:
			System.out.println("Error - Please enter a valid character");
			move.mainMenu();

		}
	}

	public void choiceA() throws OfferException {
		String propertyAddress;
		int reservePrice = 0;
		System.out.print("Enter Sale ID for new PropertySale:\t\t");	
			String tempSaleID = kb.nextLine(); // temporary saleID holder
			if (tempSaleID.trim().equals(""))
				throw new OfferException("Sale ID can't be empty"); // check if the user entered any value


			if (!move.findSaleID(tempSaleID) == true)
			throw new OfferException("Error - Sale ID \"" + tempSaleID +
					"\" already exists in the system!"); //if it does send an error and move to menu

		System.out.print("Enter Property Address for new PropertySale:\t");
		propertyAddress = kb.nextLine();
		
		if (propertyAddress.trim().equals("")) // check if the user entered any value
			throw new OfferException("The property Address can't be empty");
		System.out.print("Enter Reserve Price for new PropertySale:\t");
		// Exceptions to ensure that the user isnt breaking the code
		try {
			reservePrice = Integer.parseInt(kb.nextLine());
			if (reservePrice <= 0) 
				throw new OfferException("The Value has to be greater than 0");;
		}	catch (NumberFormatException ex){
			System.out.println("The Value entered must be an integer");
			move.mainMenu();
		}

		Sale.add(counter, new Sales(saleID, propertyAddress,reservePrice)); //create a new sale and set the current offer to 0
		Sale.get(counter).setCurrentOffer(0);
		System.out.println("New Property Sale added successfully for property at " + Sale.get(counter).getPropertyAddress());
		++counter;
		move.mainMenu();
	}
		

	public void choiceB() throws OfferException {
		System.out.format("%-25s", "Enter Sale ID:");

			String tempSaleID = kb.nextLine();
		if (tempSaleID.equals(""))
			throw new OfferException("The SaleID can't be blank"); // check if the user entered any value
		
		int tempCurrentOffer = 0;
		
		if (!move.findSaleID(tempSaleID)) // check if the SaleID exists and finds its position
		{
			System.out.format("Current Offer: %11d\n", Sale.get(checkSaleLocation).getCurrentOffer());
			System.out.format("Enter new offer:\t ");
			// to prevent the user form breaking the code
			try {
			tempCurrentOffer = Integer.parseInt(kb.nextLine()); 
			} catch (NumberFormatException ex)
			{
			System.out.println("The Value entered must be an integer");
			}
			
			Sale.get(checkSaleLocation).makeOffer(tempCurrentOffer);

			// to print out whether the reserve price has been met or not
			move.makeOffer(tempCurrentOffer, checkSaleLocation); 
			move.mainMenu();
		}
		else 
		{
			System.out.println("Error - Sale ID \""+ tempSaleID + "\"doesnt exist");
			move.mainMenu();
		}
	}
	public void makeOffer(int x, int location){
		// check if the offer given meets the requirements given
		 // the new currentOffer will be x
		if(x >= Sale.get(location).getReservePrice())
		{
			System.out.println("Offer accepted! (reserve price has been met/exceeded)");
			Sale.get(location).setCurrentOffer(x);
		}
		else 
		{
			System.out.println("Offer accepted! (offer is below reserve price)");
			Sale.get(location).setCurrentOffer(x);
		}

	}
	public void choiceC() throws OfferException{
		if (Sale.size() == 0)
			throw new OfferException("No sales or auctions exist"); // if there are no Sales or 
		// auctions created yet, send an exception
		for(int i = 0; i < Sale.size(); ++i) // print out all the details for every position in the arraylist
			System.out.println(Sale.get(i).getSaleDetails());
		move.mainMenu();
	}
	public void choiceD() throws OfferException{
		// create a new Auction class
		
		System.out.format("%-40s", "Enter Sale ID for new Auctionsale:");
		String tempSaleID = kb.nextLine();
		if (tempSaleID.equals("")) // makes sure the user doesnt leave it blank
			throw new OfferException("The SaleID can't be blank");
		//check if the sale id already exists
		if (!move.findSaleID(tempSaleID))
		{
			System.out.println("Error - Sale ID \"" + tempSaleID + "\" already exists in the system");
			move.mainMenu();
		}
		else 
		{ // collects the property address and the reserveprice
			System.out.format("%-40s", "Enter Property Address for Auction Sale: ");
			String tempPropertyAddress = kb.nextLine();
			if (tempPropertyAddress.trim().equals("")) // check if the user entered any value
				throw new OfferException("The property Address can't be empty");
			System.out.format("%-40s", "Enter Reserve Price for Auction Sale:");
			int tempReservePrice = 0;
			//prevent the user from breaking the code by using number format exception
			try {
				 tempReservePrice = Integer.parseInt(kb.nextLine());
			} catch(NumberFormatException ex)
			{
				System.out.println("The Value entered must be an integer");
				move.mainMenu();
			}
			if (tempReservePrice < 0)
				throw new OfferException("The value entered must be greater than 0");
			Sale.add(counter, new Auction(tempSaleID, tempPropertyAddress, tempReservePrice)); // creates a new Auction class 
			Sale.get(counter).setCurrentOffer(0);
			++counter;
			System.out.println("New Auction Sale added successfully for property at " +tempPropertyAddress);
			move.mainMenu();
		}
	}
	public boolean findSaleID(String x){ // find i the Sale id being called exist in the array
		// if it exists find the location of the Sale id
		for(int i =0; i <Sale.size();++i)
		{
			if((Sale.get(i).getSaleID()).equals(x))
			{
				checkSaleLocation = i;
				return false;
			}

		}
		return true;
	}
	public void choiceE()throws OfferException{
		// close an auction 
		System.out.format("%-35s", "Enter ID of Auction to be closed: ");
		String tempSaleID = kb.nextLine();
		if (move.findSaleID(tempSaleID))
			throw new OfferException("The Sale ID "+ tempSaleID + " wasn't found");
		if (tempSaleID.trim().equals(""))
			throw new OfferException("The SaleID can't be blank");
		// check if the saleID was used to make an auction
		if(!(Sale.get(checkSaleLocation) instanceof Auction)) // checks if the sale is part of the auction class
		{
			System.out.println("Error - Property Sale ID" + tempSaleID + " is not an auction!");
		}
		else 
		{
			Sale.get(checkSaleLocation).setAcceptingOffers(false);
			System.out.println("Auction " + tempSaleID + " has ended - property has been: "
					+ "" + Sale.get(checkSaleLocation).getPropertyStatus());
		}	
		move.mainMenu();
	}
	public void choiceX(){
		// write all the values from the Sales arraylist to a textfile
		file.writeToFile(Sale);
		System.out.println("Closing the program");
		System.exit(0);
	}
}