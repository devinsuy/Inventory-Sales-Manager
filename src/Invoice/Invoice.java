package Invoice;

import Customer.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;

public class Invoice {

    private Customer customerInvoice;
    private LocalDateTime invoiceDate; // updates an invoice date and time
    private double initialInvoiceAmount; // total amount that's due initially
    private double remainingBalanace; // remaining portion to be paid
    private boolean invoiceStatus; // status determined for open/closed invoice
    private final DateTimeFormatter invoiceDateFormat =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final double deliveryCharge = 0.08; // delivery percentage
    private final double discount = 0.10; // 10% deduction if invoice paid within 10 days
    private final double financeCharge = 0.02; // 2% charge if invoice paid after 30 days
    private int payDays = 0; // number of days it takes to pay the invoice
    public static Comparator<Invoice> byInvoiceDate;
    public static Comparator<Invoice> byInvoiceAmount;

    public Invoice(Customer customer, LocalDateTime invoiceDate, double invoiceTotal, double amountPaid, boolean productDelivered) {
        if(productDelivered){
            this.initialInvoiceAmount = invoiceTotal + (invoiceTotal * deliveryCharge); // apply a delivery charge to the total invoice amount
        } else {
            this.initialInvoiceAmount = invoiceTotal;
        }
        this.customerInvoice = customer;
        this.invoiceDate = invoiceDate;
        this.remainingBalanace = this.initialInvoiceAmount - amountPaid;
        this.invoiceStatus = true; // defualt value of invoice object is true (i.e. invoice is open)
    }

    public Customer getCustomerInvoice() {
        return this.customerInvoice;
    }

    public void setCustomerInvoice(Customer customer) {
        this.customerInvoice = customer;
    }

    public LocalDateTime getInvoiceDate() {
        return this.invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime date){
        this.invoiceDate = date;
    }

    public double getInitialInvoiceAmount() {
        return this.initialInvoiceAmount;
    }

    public void setInitialInvoiceAmount(double invoiceAmount) {
        this.initialInvoiceAmount = invoiceAmount;
    }

    public double getRemainingBalanace() {
        return this.remainingBalanace;
    }

    public void setRemainingBalanace(double totalDue) {
        this.remainingBalanace = totalDue;
    }

    public double getDeliveryCharge() {
        return this.deliveryCharge;
    }

    public void setInvoiceStatus(boolean status){
        this.invoiceStatus = status;
    }

    public boolean getInvoiceStatus(){
        return this.invoiceStatus;
    }

    public void updateRemainingBalance(double updateAmount){
        this.remainingBalanace -= updateAmount;
        if(this.getRemainingBalanace() <= 0){
            this.setInvoiceStatus(false); // invoice has been paid
        }
    }

    public void writeInvoice() throws IOException {
        FileWriter writer = new FileWriter(getCustomerInvoice().getName() + " Invoice.txt");

        writer.write("Customer Name: " + getCustomerInvoice().getName() + "\n" +
                "Invoice Date: " + this.getInvoiceDate() + "\n" +
                "Invoice Amount: " + this.getInitialInvoiceAmount() + "\n" +
                "Delivery Charge: " + this.getDeliveryCharge() + "\n" +
                "Total Due: " + this.getRemainingBalanace() + "\n");

        writer.flush();
        writer.close();
    }
    public void displayInvoice() throws IOException{
        writeInvoice();

        BufferedReader reader = new BufferedReader(new FileReader(
                getCustomerInvoice().getName() + " Invoice.txt"));

        String line = reader.readLine();
        ArrayList<String> invoice = new ArrayList<String>();

        while (line != null) {
            invoice.add(line);
            line = reader.readLine();
        }
        reader.close();


        for (int i = 0; i < invoice.size(); i++){
            if (i == 0) {
                System.out.print("|" + invoice.get(i));
                continue;
            }
            else if (i == 1) {
                System.out.printf("%81s|", invoice.get(i));
            }
            else if (i == invoice.size() - 1) {
                System.out.printf("|%100s|", invoice.get(i));
            }
            else {
                if (i == 4){
                    System.out.printf("|%-100s|%n", " ");
                    System.out.printf("|%-100s|%n", "Purchased Items: ");
                    System.out.printf("|%-100s|", invoice.get(i));
                } else {
                    System.out.printf("|%-100s|", invoice.get(i));

                }
            }
            System.out.println();
        }
    }

    public boolean discountApplied() {
        if(this.payDays < 10){
//            this.initialInvoiceAmount = this.initialInvoiceAmount - (this.initialInvoiceAmount * this.discount);
            if(this.getRemainingBalanace() <= 0){
                double refund = this.getRemainingBalanace()*-1 + this.getInitialInvoiceAmount()*this.discount;
                String refundFormat = String.format("%.2f", refund);
                System.out.println( "Discount applied. Refund Amount: $" + refundFormat);
                return true;
            }
        }
        return false;
    }

    public void applyDiscount(){
        if(this.getRemainingBalanace() <= 0){
            double refund = this.getRemainingBalanace()*-1 + this.getInitialInvoiceAmount()*this.discount;
            String refundFormat = String.format("%.2f", refund);
            System.out.println( "Discount applied. Refund Amount: $" + refundFormat);
            this.setInvoiceStatus(false);
        }
    }

    public void applyFinanceCharge(){
        if(this.getRemainingBalanace() > 0){
            double totalWithFinanceCharge = this.getRemainingBalanace() + this.getInitialInvoiceAmount()*this.financeCharge;
            String financeChargeFormat = String.format("%.2f", totalWithFinanceCharge);
            System.out.println("Finance charge applied. Total Due: $" + financeChargeFormat);
        }
    }

    public void incrementPayDay(int n){
        this.payDays += n;
    }

    static {
        Invoice.byInvoiceDate = Comparator.comparing((Function<? super Invoice, ? extends Comparable>)Invoice::getInvoiceDate);
        Invoice.byInvoiceAmount = Comparator.comparing((Function<? super Invoice, ? extends Comparable>)Invoice::getInitialInvoiceAmount);

    }
}