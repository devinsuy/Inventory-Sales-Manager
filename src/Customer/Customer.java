package Customer;

import Product.Product;

import java.util.ArrayList;
public class Customer {
    private String name;
    private String address;
    private String number;
    private Double taxPercentage;
    private ArrayList<Product> itemsBought;


    public Customer(String name, String address, String custNumber, Double taxPercentage) {
        this.name = name;
        this.address = address;
        this.number = custNumber;
        this.taxPercentage = taxPercentage;
        this.itemsBought = new ArrayList<Product>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setNumber(String custNumber) {
        this.number = custNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Double getTaxPercentage() {
        return taxPercentage;
    }

    public String getItemsBought(){
        String temp = "";
        for(Product p : itemsBought){
            temp += ("Item #" + p.getItemNum() + ": " + p.getBrand() + " " + p.getModel() + "\n");
        }
        return temp;
    }

    public String toString(){
        return this.getName() + ", " + this.getAddress() + ", " +
            this.getNumber() + ", " + this.getTaxPercentage();
    }

    public void addItem(Product p){
        itemsBought.add(p);
    }

}
