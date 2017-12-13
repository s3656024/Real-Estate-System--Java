// s3656024 assignment2
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadingFile {
	static String delim = "/";
	static String fileName = "SaleDetails.txt";
	static Scanner inputStream = null;
	boolean input = true;
	public void readFromFile(int counter, ArrayList<Sales> Sale){
		String classType;
		char choice;
		try {
			// check if the original file is there
			inputStream = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			try {
				// if not access the backup file
				inputStream = new Scanner(new File("SaleDetailsbackup.txt"));
				System.out.println("The file was not found, A backup file has been loaded automatically");

			} catch (FileNotFoundException e1) {
				try {
					System.out.println("The original file and the backup file wasn't found");
					System.out.println("Would you like to load the program with sample data? Y/N");
					// if neither files are found have preset values
					userInput();
					inputStream = new Scanner(new File("PresetSales.txt"));
					
				} catch (FileNotFoundException e2){
					
				}
			}
		}
		if (input == true){
		for ( counter = 0;inputStream.hasNextLine();++counter){

			
			String line = inputStream.nextLine();
			String[] vars = line.split(delim); // splits up all the values and stores them in an array

			classType = vars[0];
			//stores all the values back into the arraylist
			// what class type it is,is determined by the first class type
			if (classType.equals("Sales"))
			{
				Sale.add(counter, new Sales(vars[1], vars[2], Integer.parseInt(vars[3])));
				Sale.get(counter).setCurrentOffer(Integer.parseInt(vars[4]));
				Sale.get(counter).setAcceptingOffers(Boolean.parseBoolean(vars[5]));
			}
			else if (classType.equals("Auction"))
			{
				Sale.add(counter, new Auction(vars[1], vars[2], Integer.parseInt(vars[3])));
				Sale.get(counter).setCurrentOffer(Integer.parseInt(vars[4]));
				Sale.get(counter).setAcceptingOffers(Boolean.parseBoolean(vars[5]));
				((Auction)Sale.get(counter)).setHighestBidder(vars[6]);

			}
		}
		}
	}
	public void userInput(){
		Scanner kb = new Scanner(System.in);
		switch(kb.nextLine()){ // checks what the user inputs and moves to the right method
		case "y":
		case "Y":
			input = true;
			break;
		case "n":
		case "N":
			input = false;
			break;
		default:
			System.out.println("Please enter Y or N");
			userInput();
		}
	}
	public void writeToFile(ArrayList<Sales> Sale)
	{
		// writing the Sales Array into the file
		try{
			for (int j = 0; j <2; ++j)
			{
				if (j == 0 )
					fileName = "SaleDetails.txt"; // changes what the text file is to create a backup
				else 
					fileName = "SaleDetailsBackup.txt";
				PrintWriter outputStream = new PrintWriter(new FileOutputStream(fileName),true);

				for (int i = 0; i < Sale.size();++i)
				{		
					if (Sale.get(i) instanceof Auction)
					{
						outputStream.write("Auction" +delim); // use to tell what class it is a part of 
						outputStream.write(Sale.get(i).getSaleID() + delim 
								+ Sale.get(i).getPropertyAddress() + delim
								+ Sale.get(i).getReservePrice() + delim
								+ Sale.get(i).getCurrentOffer() + delim 
								+ Sale.get(i).getAcceptingOffers()+ delim);
						outputStream.write(((Auction)Sale.get(i)).getHighestBidder() + "\n"); // delim to split up each values 
					} // make it easier to read the file 
					else if (Sale.get(i) instanceof Sales)
					{
						outputStream.write("Sales" + delim); 
						outputStream.write(Sale.get(i).getSaleID() + delim 
								+ Sale.get(i).getPropertyAddress() + delim
								+ Sale.get(i).getReservePrice() + delim
								+ Sale.get(i).getCurrentOffer() + delim
								+ Sale.get(i).getAcceptingOffers()+ "\n");
					}
				}
				outputStream.close();
			}
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("The file was not found"); 
		}


	}
}
