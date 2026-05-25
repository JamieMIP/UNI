package commandLineInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {
    	System.out.println(System.getProperty("user.dir"));
    	

        Scanner consoleInput = new Scanner(System.in);

        System.out.println("WELCOME");
        
        while (true) {
            String[][] data = printWelcomeMenu(); //2d array as you can't return two arrays like python
            //unpacking 2d array
            String[] userIDs = data[0]; 
            String[] permissions = data[1];
            
            String line = consoleInput.nextLine().trim();

            int selection = Integer.parseInt(line.trim());
            
            if (selection == 0) {
            	System.out.println("Goodbye");
        		System.out.println("Closing program...");
        		System.out.println();
        		return;
        		
            } else  {
            	if (permissions[selection - 1].equals("admin")) { //if they picked an admin run adminCLI.java
            		AdminCLI.run(consoleInput);
            	} else if (permissions[selection - 1].equals("customer")){ //otherwise customerCLI.java
            		CustomerCLI.run(consoleInput, userIDs[selection-1]);
            	}
            }
        }
    }

    private static String[][] printWelcomeMenu() {

        // Replace placeholders with enumeration of existing users (1..n)
        System.out.println("PLEASE SELECT USER BY INPUTTING THE CORRESPONDING NUMBER (or 0 for exit)");
        
        BufferedReader br = null;
        String[][] placeHolder = new String[0][0]; //initilising the 2d array mentioned earlier
        try {
            //System.out.println(System.getProperty("user.dir")); //debugging
            br = new BufferedReader(new FileReader("files/UserAccounts.txt")); //set file location
            
            int count = 0;
            // find out how many lines there are
            while (br.readLine() != null) {
                count++;
            }
            br.close(); //reset line position by closing and reopening (I do this a few times)
            
            String[] userIDs = new String[count];
            String[] permissions = new String[count];
            //two above arrays go in the 2d array eventually
            
            br = new BufferedReader(new FileReader("files/UserAccounts.txt"));
            String line = null;
            int i = 0;

            while ((line = br.readLine()) != null) { //loop through while also defining variable line (quite neat)
         
                String[] values = line.split(";\\s*"); //format specified 
                System.out.println((i + 1) + ") "
                        + values[0] + " | "
                        + values[1] + " | "
                        + values[5]);
                
                userIDs[i] = values[0]; //adding userIDs to list
                permissions[i] = values[5]; //adding permissions to list
                // we need these to actually know what account we are in and what permissions we should have
                // also to run the correct CLI
                i++;
            }

            br.close();
            System.out.println("0) Exit");
            return new String[][] {userIDs, permissions}; //pack arrays into 2d array

        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return placeHolder;
        }
        
    }

    private static String readLine(Scanner consoleInput) {
        if (!consoleInput.hasNextLine()) {
            return null;
        }
        return consoleInput.nextLine();
    }
}