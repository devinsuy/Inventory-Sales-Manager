package Customer;

import MainMenu.MainMenu;

import java.util.Scanner;

public class CustomerTester {
    public static void main(String [] args){
        CustomerController c = new CustomerController(new MainMenu());
        System.out.println("Customers List: ");
        c.enterCustomer(new Scanner(System.in), false);
    }
}
