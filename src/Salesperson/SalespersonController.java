package Salesperson;
import Inventory.Inventory;
import Invoice.Invoice;
import Invoice.InvoiceController;
import Product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import Customer.Customer;
import MainMenu.MainMenu;


public class SalespersonController {
    Salesperson salesperson;
    Inventory inventory;
    Customer customer;
    MainMenu m;

    public SalespersonController(Inventory inventory, Salesperson person, Customer customer, MainMenu m) {
        this.salesperson = person;
        this.inventory = inventory;
        this.customer = customer;
        this.m = m;
    }

    public void makeSalesMenu(Scanner scan, InvoiceController invc) {
        double subTotal = 0;
        salesperson.displayCatalog(this.inventory);
        System.out.println(MainMenu.underlineText("\nItem Purchase"));
        System.out.println("Select items purchased by " + customer.getName());
        while (true) {
            System.out.print("   Enter an item number: ");
            int item = scan.nextInt();
            while (item <= 0 || item > inventory.getNumItems()) {
                System.out.print("   Enter a valid item: ");
                item = scan.nextInt();
            }
            Product temp = inventory.getInventory().get(item - 1);
            // make the sale
            if(temp.getQuantity() == 0){
                System.out.println("Item out of stock, unable to purchase item #" + temp.getItemNum());
            }
            else{
                salesperson.makeSale(temp, customer);
                subTotal += inventory.getInventory().get(item - 1).getSellingPrice();
            }
            System.out.print("Do you want to buy another item? (y/n): ");
            String endLoop = scan.next();
            if (endLoop.toLowerCase().equals("n")) {
                break;
            }
        }
        System.out.println("Is the order going to be delivered? (y/n) ");
        String deliver = scan.next();
        boolean deliverFee = false;
        if (deliver.toLowerCase().equals("y")) {
            deliverFee = true;
        }

        System.out.printf("\nSubtotal: $%.2f   Tax Rate @ %.0f%s\n", subTotal, (customer.getTaxPercentage() * 100),"%");
        System.out.printf("Total: $%.2f\n", subTotal * (1+customer.getTaxPercentage()));
        // FIXME Add function call to generate invoice here
        System.out.println("Enter Payment: " );
        double customerPayment = CheckInput.getPositiveDouble();
        LocalDateTime currentSaleTime = LocalDateTime.now();
        double total = subTotal * (1+customer.getTaxPercentage());
        invc.addInvoice(new Invoice(customer, currentSaleTime, total, customerPayment, deliverFee));
    }

    public void commissionMenu(ArrayList<Salesperson> salespeople, Scanner input){
        int selection;
        System.out.println(MainMenu.underlineText("Commission Menu"));
        System.out.println("   1. Update commission percentage");
        System.out.println("   2. View Total Sales/Commission ");
        System.out.println("   3. Return to main menu");
        System.out.print("\nPlease enter your selection: ");
        selection = MainMenu.menuValidation(3, input);

        if(selection == 1){
            int counter = 1;
            int salespersonIndex;
            double newCommission;
            Salesperson sp;
            System.out.println(MainMenu.underlineText("Update Commission"));
            System.out.println("Salesperson who closed the sale: ");
            for(Salesperson s : salespeople){
                System.out.println("   " + counter + ". " + s.getName());
                counter++;
            }
            System.out.print("\nPlease enter your selection: ");
            salespersonIndex = MainMenu.menuValidation(counter - 1, input);
            sp = salespeople.get(salespersonIndex);
            System.out.println("\nUpdating " + sp.getName() + "'s commission");
            System.out.print("   Enter the new commission percentage: ");
            newCommission = commissionValidation(input);
            System.out.println(sp.getName() + "'s commission will be updated from " +
                    sp.getCommissionPct() + "% to " + newCommission + "%");
            if(MainMenu.confirm(input)){
                sp.setCommissionPct(newCommission);
                System.out.println(sp.getName() + "'s commission percentage has been updated");
            }
            else{
                System.out.println("Commission was not updated");
            }
        }
        else if(selection == 2){
            int totalNumSold = 0;
            double totalSalesIncome = 0;
            System.out.println(MainMenu.underlineText("Total Sales and Commission"));
            for(Salesperson s : salespeople){
                System.out.println("Salesperson: " + s.getName());
                s.displaySalespersonSales();
                System.out.println();
                totalNumSold += s.getNumSales();
                totalSalesIncome += s.getTotalRevenue();
                System.out.println();
            }
            System.out.println("Total Number Items Sold: " + totalNumSold);
            System.out.printf("Total Sales Income: $%.2f\n", totalSalesIncome);
        }
        System.out.println("\nReturning to main menu . . .\n");
        m.mainMenu(input);
    }

    private static double commissionValidation(final Scanner input) {
        double commisionPct;
        try {
            final String priceString = input.next();
            commisionPct = Float.parseFloat(priceString);
            if (commisionPct <= 0) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {
            System.out.print("ERROR please enter a valid commission percentage: ");
            return commissionValidation(input);
        }
        return commisionPct / 100;
    }
}