package commandLineInterface;

import model.StockReader;
import model.Product;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class AdminCLI {
	public final static String INVALID = "Invalid input";

    public static void run(Scanner consoleInput) {
    	System.out.println("ADMIN VIEW");
        

        
        while (true) {
        	Product[] products = StockReader.readStock(); //list of product objects
        	
        	printAdminMenu();
        	
        	int selection = Integer.parseInt(consoleInput.nextLine().trim());
        	
        	switch (selection) {
        		case 1:
        			for (Product product : products) { //loops all products
        				System.out.println(product.toString());//returns all info in the products
        			}
        			break;
        		
        		case 2:
        			String toAdd = ""; //will hold line to add
        			for (int i=1;i<8;i++) {
        				System.out.println("input parameter" + i);
        				toAdd = toAdd + consoleInput.nextLine().trim() + "; "; //add new input to the line
        				
        			}
        			// for the last one we don't want to add ;
        			System.out.println("input parameter" + 8);
    				toAdd = toAdd + consoleInput.nextLine().trim();
    			

    		        try {
    		        	
    		            FileWriter writer = new FileWriter("files/Stock.txt", true); 
    		            writer.write(toAdd + "\n"); //write new line in
    		            writer.close();

    		        } catch (IOException e) {
    		            e.printStackTrace();
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
    
    
    private static void printAdminMenu() {

        System.out.println("PLEASE SELECT ACTION BY INPUTTING THE CORRESPONDING NUMBER (or 0 for logout)");
        System.out.println("1) View all products");
        System.out.println("2) Add new product");
        

        System.out.println("0) Log out");
    }
    
    
    
}