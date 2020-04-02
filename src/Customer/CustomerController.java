package Customer;

import MainMenu.MainMenu;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerController {
    private ArrayList<Customer> customerArrayList;
    private MainMenu m;

    public CustomerController(MainMenu mm){
        this.customerArrayList = new ArrayList<Customer>();
        this.loadCustomers();
        m = mm;
    }

    private void loadCustomers(){
        final File customersFile = new File("C:\\Users\\kevin\\IntelliJ IDEA Projects\\343-Project-2\\src\\Customer\\Customers.txt");
        final Pattern customerPattern = Pattern.compile("(\\w+\\s\\w+)\\.\\s+\\\"(.+)\\\"\\s+(.+)\\\"\\s+(\\d+\\.\\d+)\\%");
        try{
            Scanner scanCustomer = new Scanner(customersFile);
            while(scanCustomer.hasNextLine()){
                final String currentLine = scanCustomer.nextLine();
                final Matcher matchGroups = customerPattern.matcher(currentLine);
                while(matchGroups.find()){
                    DecimalFormat decimalFormat = new DecimalFormat("##.00");
                    Double taxPercentage = Double.parseDouble(decimalFormat.format(Double.parseDouble(matchGroups.group(4))));
                    this.customerArrayList.add(new Customer(matchGroups.group(1), matchGroups.group(2), matchGroups.group(3), taxPercentage));
                }
            }

        } catch (FileNotFoundException fnf) {
            System.out.println("File DNE");
        }
    }

    private static double taxValidation(final Scanner input) {
        double taxPct;
        try {
            final String taxString = input.next();
            taxPct = Double.parseDouble(taxString);
            if (taxPct <= 0) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {
            System.out.print("ERROR please enter a valid tax percentage: ");
            return taxValidation(input);
        }
        return taxPct / 100;
    }

    public void modifyMenu(Customer c, final Scanner input){
        int selection;
        System.out.println("You have selected: " + c.toString() + "\n");
        System.out.println("Update the customer's:");
        System.out.println("   1. Name");
        System.out.println("   2. Address");
        System.out.println("   3. Phone Number");
        System.out.println("   4. Tax Percentage");
        System.out.println("   5. Main Menu");
        System.out.print("\nEnter your selection: ");
        selection = MainMenu.menuValidation(5, input);

        switch(selection){
            case 1:
                String name;
                System.out.print("\nEnter the updated name: ");
                name = input.next() + input.nextLine();
                System.out.println("Customer name will be updated from " + c.getName() + " to " + name);
                if(MainMenu.confirm(input)){
                    c.setName(name);
                    System.out.println("Customer name has been updated");
                }
                else{
                    System.out.println("Name change cancelled");
                }
                System.out.println("\nReturning to main menu . . .\n");
                m.mainMenu(input);
                break;
            case 2:
                String address;
                System.out.print("\nEnter the updated address: ");
                address = input.next() + input.nextLine();
                System.out.println("Customer name will be updated from " + c.getAddress() + " to " + address);
                if(MainMenu.confirm(input)){
                    c.setAddress(address);
                    System.out.println("Customer address has been updated");
                }
                else{
                    System.out.println("Address change cancelled");
                }
                System.out.println("\nReturning to main menu . . .\n");
                m.mainMenu(input);
                break;
            case 3:
                String phoneNum;
                System.out.print("\nEnter the updated phone number: ");
                phoneNum = validateNum(input);
                System.out.println("Customer name will be updated from " + c.getNumber() + " to " + phoneNum);
                if(MainMenu.confirm(input)){
                    c.setNumber(phoneNum);
                    System.out.println("Customer phone number has been updated");
                }
                else{
                    System.out.println("Phone number change cancelled");
                }
                System.out.println("\nReturning to main menu . . .\n");
                m.mainMenu(input);
                break;
            case 4:
                Double taxPct;
                System.out.print("\nEnter the updated tax percentage: ");
                taxPct = taxValidation(input);
                System.out.println("Customer name will be updated from " + c.getTaxPercentage() + " to " + taxPct);
                if(MainMenu.confirm(input)){
                    c.setTaxPercentage(taxPct);
                    System.out.println("Customer tax percentage has been updated");
                }
                else{
                    System.out.println("Tax percentage change cancelled");
                }
                System.out.println("\nReturning to main menu . . .\n");
                m.mainMenu(input);
                break;
            case 5:
                m.mainMenu(input);
                break;
        }
    }

    public void deleteCustomer(Customer c){
        customerArrayList.remove(c);
    }

    public void customerMenu(final Scanner input){
        int selection;
        System.out.println(MainMenu.underlineText("Customer Menu"));
        System.out.println("   1. Enter a new customer");
        System.out.println("   2. View existing customers");
        System.out.println("   3. Update a customer's info");
        System.out.println("   4. Delete a customer from the system");
        System.out.println("   5. Main Menu");
        System.out.print("\nPlease enter your selection: ");
        selection = MainMenu.menuValidation(5, input);
        System.out.println();
        switch(selection){
            case 1:
                enterCustomer(input, false);
                break;
            case 2:
                System.out.println(MainMenu.underlineText("View Customers"));
                viewCustomers();
                System.out.println("\nReturning to main menu . . .\n");
                m.mainMenu(input);
                break;
            case 3:
                viewCustomers();
                System.out.println(MainMenu.underlineText("\nModify a Customer"));
                int customerIndex;
                System.out.print("Select a customer to modify: ");
                customerIndex = MainMenu.menuValidation(customerArrayList.size(), input);
                modifyMenu(customerArrayList.get(customerIndex-1), input);
                break;
            case 4:
                viewCustomers();
                System.out.println(MainMenu.underlineText("\nDelete a Customer"));
                int index;
                System.out.print("Select a customer to delete: ");
                index = MainMenu.menuValidation(customerArrayList.size(), input);
                Customer temp = customerArrayList.get(index-1);
                System.out.println("Customer selected for deletion: " + temp.toString());
                if(MainMenu.confirm(input)){
                    deleteCustomer(temp);
                    System.out.println("Customer has successfully been deleted");
                }
                else{
                    System.out.println("Customer deletion cancelled");
                }
                System.out.println("\nReturning to main menu . . .\n");
                m.mainMenu(input);
                break;
            case 5:
                System.out.println("\nReturning to main menu . . .\n");
                m.mainMenu(input);
                break;
        }
    }

    public void viewCustomers(){
        int counter = 1;
        for(Customer c : customerArrayList){
            System.out.println("   " + counter + ". " + c.toString());
            counter++;
        }
    }

    public Customer enterCustomer(final Scanner input, boolean returnCustomer){
        Customer temp;
        String name, address, number;
        Double taxPct;

        System.out.println(MainMenu.underlineText("Add A New Customer"));
        System.out.print("   Enter the customer's name: ");
        name = input.next() + input.nextLine();
        System.out.print("   Enter the customer's address: ");
        address = input.next() + input.nextLine();
        System.out.print("   Enter the customer's phone number: ");
        number = validateNum(input);
        System.out.print("   Enter the customer's tax percentage: ");
        taxPct = taxValidation(input);
        temp = new Customer(name, address, number, taxPct);
        System.out.println("\n" + temp.toString());
        System.out.println("Create a customer with the following information: ");

        if(MainMenu.confirm(input)){
            addCustomer(temp);
            writeCustomer(temp);
            System.out.println("Customer has successfully been added to the system");
            if(returnCustomer){
                return temp;
            }
        }
        else{
            System.out.println("Canceled, customer was not added");
        }
        System.out.println("\nReturning to main menu . . .\n");
        m.mainMenu(input);
        return null;
    }

    void copyTxt(File src, File dst) throws IOException {
        BufferedWriter tempWriter = new BufferedWriter(new FileWriter(dst));
        Scanner input = new Scanner(src);
        while(input.hasNextLine()){
            tempWriter.write(input.nextLine());
            tempWriter.newLine();
        }
        input.close();
        tempWriter.close();
    }

    String createEntry(Customer c){
        String name, address, number, tax;
        name = c.getName() + ".";
        address = "\"" + c.getAddress() + "\"";
        number = c.getNumber();
        tax = c.getTaxPercentage() + "%";
        return String.format("%-28s%-52s%-26s%s", name, address, number, tax);
    }

    void writeCustomer(Customer c){
        String entry = createEntry(c);
        File customers = new File("C:\\Users\\Devin\\IdeaProjects\\DACK 343 LOCAL\\src\\customer\\Customers.txt");
        File temp = new File("C:\\Users\\Devin\\IdeaProjects\\DACK 343 LOCAL\\src\\customer\\temp.txt");
        try{
            copyTxt(customers, temp);
            Scanner tempReader = new Scanner(temp);
            BufferedWriter bw = new BufferedWriter(new FileWriter(customers));
            while(tempReader.hasNextLine()){
                bw.write(tempReader.nextLine());
                bw.newLine();
            }
            bw.write(entry);
            bw.newLine();
            temp.delete();
            bw.close();
            tempReader.close();
        }
        catch(IOException e){
            System.out.println("FILE ERROR");
        }
    }

    public void addCustomer(Customer newCustomer){
        this.customerArrayList.add(newCustomer);
    }
    public ArrayList<Customer> getCustomerArrayList() {
        return this.customerArrayList;
    }

    /**
     * Input validation for entering a 10 digit
     * phone number unformatted
     * @param input System.in scanner
     * @return The validated phone number
     */
    public String validateNum(Scanner input){
        String number, formattedNumber;
        try {
            number = input.next();
            if(number.length() != 10){
                throw new NumberFormatException();
            }
        }
        catch(NumberFormatException n){
            System.out.print("   ERROR please enter a valid phone number: ");
            return validateNum(input);
        }
        formattedNumber = "(" + number.substring(0,3) + ")" + number.substring(3,6)
                + "-" + number.substring(6,10) + "\"";
        return formattedNumber;
    }

    public int getNumCustomers(){
        return customerArrayList.size();
    }
}
