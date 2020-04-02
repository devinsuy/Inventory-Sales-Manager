package Invoice;

import Customer.Customer;
import Customer.CustomerController;
import MainMenu.MainMenu;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/* The invoice controller class modifies
 * the data for an invoice
 */
public class InvoiceController
{
    private MainMenu mm;
    private Invoice invoice;
    private ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();
    private ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
    private int numberOfPostPaymentDays = 0; // counter for post payments

    public InvoiceController(CustomerController cc, final MainMenu mm) {
        //before an invoice can be created, a customer must exist in database
        //check to see if customer exists in database
        this.mm = mm;
        //this.listOfCustomers = customers;
        this.listOfCustomers = cc.getCustomerArrayList();
    }

    public void modifyMenu(final Scanner input) {
        System.out.println("\n" + MainMenu.underlineText("Invoice"));
        System.out.println("   1. Post payment");
        System.out.println("   2. Display open invoices");
        System.out.println("   3. Display closed invoices");
        System.out.println("   4. Main Menu");
        System.out.print("\nPlease enter your selection: ");
        final int modifyMenu = MainMenu.menuValidation(4, input);
        System.out.println();

        if (modifyMenu == 1) {
            int counter = 1;
            for (Invoice i : invoiceList) {
                if (i.getInvoiceStatus()) {
                    System.out.println("   " + counter + ". " + i.getCustomerInvoice().toString());
                }
                counter++;
            }
            System.out.print("\nSelect an invoice to post a payment to: ");
            int choice = input.nextInt() - 1;
            Invoice selectedInv = invoiceList.get(choice);
            System.out.print("Enter amount to pay: ");
            double payment = input.nextDouble();
            LocalDateTime postPaymentDate = LocalDateTime.now();
            LocalDateTime initialInvoiceDate = selectedInv.getInvoiceDate();
            int postPaymentDayOfYear = postPaymentDate.getDayOfYear();
            int initialInvoiceDateOfYear = initialInvoiceDate.getDayOfYear();
            numberOfPostPaymentDays += (postPaymentDayOfYear - initialInvoiceDateOfYear);
            postPayment(selectedInv, payment, numberOfPostPaymentDays);
        }
        else if (modifyMenu == 2) {
            try {
                displayOpen();
            } catch (IOException e) {
                System.out.println("IOException Encountered");
            }
        }
        else if (modifyMenu == 3) {
            try {
                displayClose();
            } catch (IOException e) {
                System.out.println("IOException Encountered");
            }
        }
    }


    public boolean verifyCustomerExists(String name, String number){
        String customerName, customerNumber;
        for(int i = 0; i < this.listOfCustomers.size(); i++){
            customerName = this.listOfCustomers.get(i).getName();
            customerNumber = this.listOfCustomers.get(i).getNumber();
            if(name.equals(customerName) && number.equals(customerNumber)){
                return true;
            }
        }
        return false;
    }
    public void addInvoice(Invoice customerInvoice){
        if(customerInvoice.discountApplied()){
            customerInvoice.setInvoiceStatus(false); // invoice status set to false (invoice has been paid)
        }
        this.invoiceList.add(customerInvoice);
    }

    public void displayOpen() throws IOException {
        invoiceList.sort(Invoice.byInvoiceDate);
        for (Invoice i : invoiceList){
            if (i.getInvoiceStatus()) {
                i.displayInvoice();
            }
        }
    }

    public void displayClose() throws IOException {
        invoiceList.sort(Invoice.byInvoiceAmount.reversed());
        for (Invoice i : invoiceList){
            if (!i.getInvoiceStatus()) {
                i.displayInvoice();
            }
        }
    }

    public void postPayment(Invoice i, double updateAmount, int postPaymentDays) {
        i.updateRemainingBalance(updateAmount);
        if(postPaymentDays <= 10){
            i.applyDiscount(); // apply discount
        }
        if(postPaymentDays > 30){
            i.applyFinanceCharge(); // apply finance charge
        }
        System.out.println( "---Post payment has been applied---" );
    }

}