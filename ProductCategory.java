package commandLineInterface;

import model.StockReader;
import model.Product;
import model.ProductCategory;
import model.Receipt;
import model.Address;
import model.Basket;
import model.Accessory;

import java.util.Scanner;


public class CustomerCLI {
	public final static String INVALID = "Invalid input";

	public static void run(Scanner consoleInput, String accountID) {
    	
    	
    	Basket basket = new Basket();
    	
        System.out.println("CUSTOMER VIEW");
        System.out.println("Logged in as account: " + accountID);
        System.out.println("");
        
        while (true) {
        	Product[] products = StockReader.readStock();
        	
        	printCustomerMenu();
        	
        	int selection = Integer.parseInt(consoleInput.nextLine().trim());
        	
        	switch (selection) {
        		case 1: // print everything (same as in adminCLI.java)
        			for (Product product : products) {
        				System.out.println(product.toString());
        			}
        				
        			break;
        		
        		case 2:
        			System.out.println("ENTER ITEM ID (or 0 to go back) ");
        			System.out.println();
        			String line2 = consoleInput.nextLine().trim(); //input
        			
        			if (line2.equals("0")) { //alows returning to main menu
        				break;
        			}
        			
        			basket.addItem(line2);//add item to basket
        			
        			break;
        		
        		case 3:
        			String items[] = basket.getItems();//arr of items in basket
        			for (int i = 0; i < items.length; i++) {
        			    System.out.println("item " + (i+1) + ": " + items[i]);//output contents line by line
        			}
        			break;	
        		
        		case 4:
        			System.out.println("SELECT PAYMENT METHOD BY INPUTTING THE CORRESPONDING NUMBER (or 0 to go back)");
        			System.out.println("1) Credit cart");
        			System.out.println("2) PayPal");
        			
        			String line4 = consoleInput.nextLine().trim();
        			
        			if (line4.equals("1")) { //chose card payment
        				System.out.println("ENTER CARD NUM");
        				String cardNum = consoleInput.nextLine().trim(); //colect card num
        				System.out.println("ENTER SECURITY CODE");
        				String securityCode = consoleInput.nextLine().trim(); //collect code
        			
        				double total = basket.calculatePrice(products);
        				
        				Address address = new Address(accountID); //instanciate object adress based on accountID
        				String fullAddress = address.getFullAddress();//get full address line
        				
        				Receipt receipt = new Receipt(total, fullAddress); //instanciate receipts object
        				String receiptOut = receipt.generateCardReceipt(cardNum, securityCode);//get full receipt
        				
        				System.out.println(receiptOut);//print receipt
        				
        			} else if (line4.equals("2")){ //use paypal
        				System.out.println("ENTER EMAIL");
        				String email = consoleInput.nextLine().trim(); //same logic as before this time collecting email
        			
        				double total = basket.calculatePrice(products);
        				
        				Address address = new Address(accountID);
        				String fullAddress = address.getFullAddress();
        				
        				Receipt receipt = new Receipt(total, fullAddress);
        				String receiptOut = receipt.generatePayPalReceipt(email); //and using a different method here
        				
        				System.out.println(receiptOut);
        				
        			} else if (line4.equals("0")) {
        				break;
        				
        			}
        			
        			break;	
        	
        		case 5:
        			basket.clearBasket(); //pretty simple basket clear
        			System.out.println("BASKET CLEARED");
        			System.out.println();
        			break;	
        	
        		case 6:
        			System.out.println();
        			System.out.println("ENTER PRODUCT ID: ");
        			System.out.println();
        			
        			String temp = consoleInput.nextLine().trim();
        			int productID = Integer.parseInt(temp.trim()); 
        			
        			for (Product product : products) {        		 //loops through all products		
        				if (product.getProductId() == productID){
        					System.out.println(product.toString()); //if it finds one where the IDs match output all the info
        					System.out.println();
        				}
        			}
        			break;	
        			
        		case 7:
        			System.out.println();
        			System.out.println("ENTER COMPATABILITY: ");
        			System.out.println();
        			
        			String compatability = consoleInput.nextLine().trim();
        			
        			for (Product product : products) { //again looping through all products

        			    if (product.getProductCategory().equals(ProductCategory.ACCESSORY)) { 
        			    	//filtering accessories cus boardgames don't have compatability issues

        			        Accessory accessory = (Accessory) product; //convert to subclass 

        			        if (accessory.getCompatibility().equals(compatability)) { 
        			        	//if they match output all the info same as in searching

        			            System.out.println(accessory.toString());
        			            System.out.println();
        			        }
        			    }
        			}		
        			
        			break;	
        		case 0:
        			return;
        			
        		default:
        			System.out.println(INVALID);
        			System.out.println();
        	}
        }
    }
    
    private static void printCustomerMenu() {
        System.out.println("PLEASE SELECT ACTION BY INPUTTING THE CORRESPONDING NUMBER (or 0 for logout)");
        System.out.println("1) View all products");
        System.out.println("2) Add product to shopping basket");
        System.out.println("3) View contents of shopping basket");
        System.out.println("4) Purchase items in the basket");
        System.out.println("5) Cancel shopping basket");
        System.out.println("6) Lookup with product ID");
        System.out.println("7) Search/filter based on compatilbility");

        System.out.println("0) Log out");
        System.out.println();
    }
}