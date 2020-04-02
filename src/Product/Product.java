package Product;

import java.util.function.Function;
import java.util.Comparator;

public class Product
{
    private String manufacturer;
    private String model;
    private float sellingPrice;
    private float costPrice;
    private float totalSales;
    private float totalCost;
    private float totalProfit;
    private float profitPct;
    private int quantity;
    private int itemNum;
    private int quantitySold;
    private boolean isStereo;
    public static Comparator<Product> byItemNum;
    public static Comparator<Product> byBrand;
    public static Comparator<Product> bySellPrice;
    public static Comparator<Product> byQuantity;
    public static Comparator<Product> byItemType;
    public static Comparator<Product> byProfitPct;

    public Product(final String brand, final String model, final float costPrice, final float sellingPrice) {
        this.manufacturer = brand;
        this.model = model;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantitySold = 0;
        totalSales = totalCost = totalProfit = profitPct = 0;
    }

    public Product(final String brand, final String model, final float costPrice, final float sellingPrice, final int quantity) {
        this.manufacturer = brand;
        this.model = model;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.setQuantity(quantity);
        this.quantitySold = 0;
        totalSales = totalCost = totalProfit = profitPct = 0;
    }
    public Product(final int itemNum, final String brand, final String model, final float costPrice, final float sellingPrice, final int quantity) {
        this.itemNum = itemNum;
        this.manufacturer = brand;
        this.model = model;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantitySold = 0;
        this.setQuantity(quantity);
        totalSales = totalCost = totalProfit = profitPct = 0;
    }

    public Product(final int itemNum, final String brand, final String model, final float costPrice, final float sellingPrice, final int quantity, final int quantitySold) {
        this.itemNum = itemNum;
        this.manufacturer = brand;
        this.model = model;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.setQuantity(quantity);
        this.quantitySold = quantitySold;
        totalSales = totalCost = totalProfit = profitPct = 0;
    }

    public String displayInfo(final boolean print) {
        return null;
    }

    public String getBrand() {
        return this.manufacturer;
    }

    public String getModel() {
        return this.model;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getItemNum() {
        return this.itemNum;
    }

    public float getSellingPrice() {
        return this.sellingPrice;
    }

    public float getCostPrice() {
        return this.costPrice;
    }

    public boolean getItemType() {
        return this.isStereo;
    }

    public void setSellingPrice(final float f) {
        this.sellingPrice = f;
    }

    public void setQuantity(final int i) {
        this.quantity = i;
    }

    public void setItemNum(final int i) {
        this.itemNum = i;
    }

    public void setItemType(final boolean i) {
        this.isStereo = i;
    }

    public void decrementItemNum() {
        --this.itemNum;
    }

    public float getTotalSales(){return totalSales;}

    public float getTotalCost(){return totalCost;}

    public float getTotalProfit(){return totalProfit;}

    public float getProfitPct(){return profitPct;}

    public String displayInfoCatalog(boolean b) {
        return null;
    }

    public void updateQuantitySold() {
        quantitySold++;
        quantity--;
        calculateTotals();
    }

    public void calculateTotals(){
        totalSales = sellingPrice * quantitySold;
        totalCost = costPrice * quantitySold;
        totalProfit = totalSales - totalCost;
        profitPct = totalProfit / totalSales;
    }

    public int getQuantitySold() {return quantitySold;}

    static {
        Product.byItemNum = Comparator.comparing((Function<? super Product, ? extends Comparable>)Product::getItemNum);
        Product.byBrand = Comparator.comparing((Function<? super Product, ? extends Comparable>)Product::getBrand);
        Product.bySellPrice = Comparator.comparing((Function<? super Product, ? extends Comparable>)Product::getSellingPrice);
        Product.byQuantity = Comparator.comparing((Function<? super Product, ? extends Comparable>)Product::getQuantity);
        Product.byItemType = Comparator.comparing((Function<? super Product, ? extends Comparable>)Product::getItemType);
        Product.byProfitPct = Comparator.comparing((Function<? super Product, ? extends Comparable>)Product::getProfitPct);
    }
}
