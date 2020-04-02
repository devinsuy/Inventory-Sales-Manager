package Salesperson;

import java.util.Scanner;

/** 
 * Static functions used to check console input for validity.
 *
 * Use:	Place Salesperson.CheckInput class in the same project folder as your code.
 *	Call Salesperson.CheckInput functions from your code using "Salesperson.CheckInput."
 *
 * Example:  int num = Salesperson.CheckInput.getInt();
 *
 * @author Shannon Cleary 2018
 */
public class CheckInput {
	
	/**
	 * Checks if the inputted value is an integer.
	 * @return the valid input.
	 */
	public static int getInt() {
		Scanner in = new Scanner( System.in );
		int input = 0;
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextInt() ) {
				input = in.nextInt();
				valid = true;
			} else {
				in.next(); //clear invalid string
				System.out.println( "Invalid Input." );
			}
		}
		return input;
	}
	
	/**
	 * Checks if the inputted value is an integer and 
	 * within the specified range (ex: 1-10)
	 * @param low lower bound of the range.
	 * @param high upper bound of the range.
	 * @return the valid input.
	 */
	public static int getIntRange( int low, int high ) {
		Scanner in = new Scanner( System.in );
		int input = 0;
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextInt() ) {
				input = in.nextInt();
				if( input <= high && input >= low ) {
					valid = true;
				} else {
					System.out.println( "Invalid Range." );
				}
			} else {
				in.next(); //clear invalid string
				System.out.println( "Invalid Input." );
			}
		}
		return input;
	}
	
	public static String getValidString( String x, String y, String z ) {
		Scanner in = new Scanner( System.in );
		String input = "";
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextLine() ) { // Tests if user input is a String object
				input = in.nextLine(); // Assigns string variable
				if( input.equals(x) || input.equals(y) || input.equals(z) ) {
					valid = true;
				} else {
					System.out.println( "Invalid option." );
				}
			} else {
				in.next(); //clear invalid string
				System.out.println( "Invalid Input." );
			}
		}
		return input;
	}
	
	public static String getValidString( String a, String b, String c, String d, String e ) {
		Scanner in = new Scanner( System.in );
		String input = "";
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextLine() ) { // Tests if user input is a String object
				input = in.nextLine(); // Assigns string variable
				if( input.equals(a) || input.equals(b) || input.equals(c) || input.equals(d) || input.equals(e) ) {
					valid = true;
				} else {
					System.out.println( "Invalid option." );
				}
			} else {
				in.next(); //clear invalid string
				System.out.println( "Invalid Input." );
			}
		}
		return input;
	}
	
	public static String getValidString( String a, String b ) {
		Scanner in = new Scanner( System.in );
		String input = "";
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextLine() ) { // Tests if user input is a String object
				input = in.nextLine(); // Assigns string variable
				if( input.equals(a) || input.equals(b) ) {
					valid = true;
				} else {
					System.out.println( "Invalid option." );
				}
			} else {
				in.next(); //clear invalid string
				System.out.println( "Invalid Input." );
			}
		}
		return input;
	}
	
	
	/**
	 * Checks if the inputted value is a non-negative integer.
	 * @return the valid input.
	 */
	public static int getPositiveInt( ) {
		Scanner in = new Scanner( System.in );
		int input = 0;
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextInt() ) {
				input = in.nextInt();
				if( input >= 0 ) {
					valid = true;
				} else {
					System.out.println( "Invalid Range." );
				}
			} else {
				in.next(); //clear invalid string
				System.out.println( "Invalid Input." );
			}
		}
		return input;
	}

	/**
	 * Checks if the inputted value is a double.
	 * @return the valid input.
	 */
	public static double getPositiveDouble() {
		Scanner in = new Scanner( System.in );
		double input = 0;
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextDouble() ) {
				input = in.nextDouble();
				if( input >= 0 ) {
					valid = true;
				} else {
					System.out.println( "Invalid Range." );
				}
			} else {
				in.next(); //clear invalid string
				System.out.println( "Invalid Input." );
			}
		}
		return input;
	}
	
	/**
	 * Takes in a string from the user.
	 * @return the inputted String.
	 */
	public static String getString() {
		Scanner in = new Scanner( System.in );
		String input = in.nextLine();
		return input;
	}

	/**
	 * Takes in a yes/no from the user.
	 * @return 1 if yes, 0 if no.
	 */
	public static int getYesNo(){
		boolean valid = false;
		while( !valid ) {
			String s = getString();
			if( s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y") ) {
				return 1;
			} else if( s.equalsIgnoreCase("no") || s.equalsIgnoreCase("n") ) {
				return 0;
			} else {
				System.out.println( "Invalid Input" );
			}
		}
		return 0;
	}	
}