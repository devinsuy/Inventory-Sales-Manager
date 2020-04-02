package MainMenu;

import Invoice.Invoice;
import Invoice.InvoiceController;
import Inventory.Inventory;
import Inventory.InventoryController;
import Customer.Customer;
import Customer.CustomerController;
import Salesperson.Salesperson;
import Salesperson.SalespersonController;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private final static String password = "DACK";
    private Inventory inv;
    private ArrayList<Salesperson> salespeople;
    private InventoryController ic;
    private CustomerController cc;
    private SalespersonController sc;
    private InvoiceController invc;

    public MainMenu(){
        inv = new Inventory();
        ic = new InventoryController(inv, this);
        cc = new CustomerController(this);
        invc = new InvoiceController(cc, this);
        salespeople = new ArrayList<Salesperson>();
        salespeople.add(new Salesperson("Steve", 1350.39));
        salespeople.add(new Salesperson("John", 674.23));
    }

    /**
     * Takes a string and returns it underlined
     * @param text The string to underline
     * @return The underlined string
     */
    public static String underlineText(final String text) {
        String underLine = "";
        for (int i = text.length(); i > 0; --i) {
            underLine = underLine + "-";
        }
        return text + "\n" + underLine;
    }

    /**
     * Static input validation for menu selection
     * @param upperBound The last option on the menu
     * @param input System.in scanner object
     * @return The input validated choice
     */
    public static int menuValidation(final int upperBound, final Scanner input) {
        int userChoice;
        try {
            final String choiceString = input.next();
            userChoice = Integer.parseInt(choiceString);
            if (userChoice > upperBound || userChoice < 1) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {
            System.out.print("ERROR please make a valid selection: ");
            return menuValidation(upperBound, input);
        }
        return userChoice;
    }

    public static boolean confirm(Scanner input){
        System.out.println("   1. Confirm");
        System.out.println("   2. Cancel");
        System.out.print("Please enter your selection: ");
        int confirm = MainMenu.menuValidation(2, input);
        return confirm == 1;
    }

    public void mainMenu(Scanner input) {
        int selection;

        System.out.println(underlineText("Main Menu"));
        System.out.println("   1. Log a sale");
        System.out.println("   2. View/Modify Items In Inventory");
        System.out.println("   3. View/Update Invoices");
        System.out.println("   4. View/Modify Existing Customers");
        System.out.println("   5. View/Update Salesperson");
        System.out.println("   6. Exit");
        System.out.print("\nPlease enter your selection: ");
        selection = menuValidation(6, input);
        System.out.println();

        switch(selection){
            case 1:
                int counter = 1;
                int salespersonIndex, customerIndex;
                Customer c;
                System.out.println(underlineText("Log a Sale"));
                System.out.println("Salesperson who closed the sale: ");
                for(Salesperson s : salespeople){
                    System.out.println("   " + counter + ". " + s.getName());
                    counter++;
                }
                System.out.print("\nPlease enter your selection: ");
                salespersonIndex = menuValidation(counter - 1, input);
                System.out.println("\nCustomer associated with the sale: ");
                cc.viewCustomers();
                System.out.println("   " + (cc.getNumCustomers() + 1) + ". Enter a new customer");
                System.out.print("\nPlease enter your selection: ");
                customerIndex = menuValidation(cc.getNumCustomers() + 1, input);
                if(customerIndex > cc.getNumCustomers()){
                    System.out.println();
                    c = cc.enterCustomer(input, true);
                    System.out.println();
                }
                else{
                    c = cc.getCustomerArrayList().get(customerIndex - 1);
                }
                sc = new SalespersonController(inv, salespeople.get(salespersonIndex - 1), c, this);
                System.out.println();
                sc.makeSalesMenu(input, invc);
                System.out.println("\nReturning to main menu . . .\n");
                mainMenu(input);
                break;
            case 2:
                ic.mainInventoryMenu(input);
                break;
            case 3:
                // FIXME Implement Invoices
                invc.modifyMenu(input);
                System.out.println("\nReturning to main menu . . .\n");
                mainMenu(input);
                break;
            case 4:
                cc.customerMenu(input);
                break;
            case 5:
                try{
                    sc.commissionMenu(salespeople, input);
                }
                catch(NullPointerException e){
                    System.out.println("No sales have been made yet");
                    System.out.println("\nReturning to main menu . . .\n");
                    mainMenu(input);
                }
                break;
            case 6:
                System.out.println("Exiting program . . . Goodbye!");
                break;
        }
    }

    public static void passwordLogin(Scanner in) {
        System.out.print("Enter Password: ");
        String attempt = in.next();
        while (!attempt.equals(password)) {
            System.out.print("Incorrect. Try again: ");
            attempt = in.next();
        }
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        MainMenu m = new MainMenu();
        passwordLogin(in);
        m.mainMenu(in);
        in.close();
    }
}