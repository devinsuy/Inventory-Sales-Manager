package Invoice;

import Customer.Customer;
import Product.Product;
import java.io.*;
import java.util.ArrayList;
import java.lang.String;

public class Invoice2 {

    private String customerName;
    private String invoiceDate;
    private double invoiceAmount;
    private double totalDue;
    private double deliveryCharge;
    private boolean invoiceStatus;
    private Product purchasedProduct;

    public Invoice2(String custName, String invoiceDate, double invoiceAmount, double totalDue, double delivery) {
        this.customerName = custName;
        this.invoiceDate = invoiceDate;
        this.invoiceAmount = invoiceAmount;
        this.totalDue = invoiceAmount + delivery;
        this.deliveryCharge = delivery;
        this.invoiceStatus = true; // defualt value of invoice object is true
    }

    public Invoice2(Customer customer, Product purchasedProduct){
        this.customerName = customer.getName();

    }

    public String getCustomerName() {
        return customerName;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setCustomerName(String name){
        this.customerName = name;
    }

    public void setInvoiceDate(String date){
        this.invoiceDate = date;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public void setTotalDue(double totalDue) {
        this.totalDue = totalDue;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public void setInvoiceStatus(boolean status){ this.invoiceStatus = status; }

    public boolean getInvoiceStatus(){ return this.invoiceStatus; }

    public void updateInvoiceAmount(double updateAmount){
        this.invoiceAmount -= updateAmount;
    }

    public void writeInvoice() throws IOException {
        // File file = new file(customerName + " Invoice.txt");
        // file.createNewFile();

        FileWriter writer = new FileWriter(customerName + " Invoice.txt");

        writer.write("Customer Name: " + customerName + "\n" +
            "Invoice Date: " + invoiceDate + "\n" + 
            "Invoice Amount: " + invoiceAmount + "\n" + 
            "Delivery Charge: " + deliveryCharge + "\n" + 
            "Big Toy\nSmall Toy\n" +
            "Total Due: " + totalDue + "\n");
            
        writer.flush();
        writer.close();

    }

    public void displayInvoice() throws IOException{
        writeInvoice();

        BufferedReader reader = new BufferedReader(new FileReader(customerName + " Invoice.txt"));

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
}