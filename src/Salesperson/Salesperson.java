package Salesperson;
import Inventory.Inventory;
import Product.*;
import Customer.Customer;

public class Salesperson {
    String name;
    double commissionPct;
    float totalRevenue;
    double totalCommission;
    int numItemsSold;

    public Salesperson(String name, double commission){
        this.name = name;
        this.commissionPct = commission;
        this.totalRevenue = 0;
        this.totalCommission = 0;
        this.numItemsSold = 0;
    }

    public String getName(){
        return name;
    }

    public double getCommissionPct(){
        return commissionPct;
    }

    public void updateCommissionPct(double commission){
        this.commissionPct = commission;
    }

    public void displayCatalog(Inventory inv) {
        for (final Product p : inv.getInventory()) {
            String productInfo;
            if (p.getItemType()) {
                productInfo = p.displayInfoCatalog(false);
            }
            else {
                final Television t = (Television)p;
                productInfo = t.displayInfoCatalog(false);
            }

            System.out.printf("\tItem #%03d%-7s%s\n", p.getItemNum(), ".", productInfo);
        }
    }

    public void makeSale(Product product, Customer customer) {
            customer.addItem(product);
            totalRevenue += product.getSellingPrice();
            numItemsSold++;
            product.updateQuantitySold();
    }

    public double getTotalCommission(){
        totalCommission = totalRevenue * commissionPct;
        return totalCommission;
    }

    public int getNumSales() {
        return numItemsSold;
    }

    public double getTotalRevenue(){
        return totalRevenue;
    }

    public void setCommissionPct(double commissionPct){
        this.commissionPct = commissionPct;
    }

    public void displaySalespersonSales() {
        System.out.printf("   Number of Items Sold: %d\n   Generated Sales Revenue: $%.2f", numItemsSold, totalRevenue);
    }

}