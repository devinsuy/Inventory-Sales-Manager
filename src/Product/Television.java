package Product;

public class Television extends Product
{
    private String screenSize;

    public Television(final String brand, final String spec, final String model, final float costPrice, final float sellPrice) {
        super(brand, model, costPrice, sellPrice);
        this.screenSize = spec + "\"";
        super.setItemType(false);
        super.setQuantity(1);
    }

    public Television(final String brand, final String spec, final String model, final float costPrice, final float sellPrice, final int quantity) {
        super(brand, model, costPrice, sellPrice, quantity);
        this.screenSize = spec + "\"";
        super.setItemType(false);
    }

    public Television(final int itemNum, final String brand, final String spec, final String model, final float costPrice, final float sellPrice, final int quantity) {
        super(itemNum, brand, model, costPrice, sellPrice, quantity);
        this.screenSize = spec + "\"";
        super.setItemType(false);
    }

    public Television(final int itemNum, final String brand, final String spec, final String model, final float costPrice, final float sellPrice, final int quantity, final int quantitySold) {
        super(itemNum, brand, model, costPrice, sellPrice, quantity, quantitySold);
        this.screenSize = spec + "\"";
        super.setItemType(false);
    }

    @Override
    public String displayInfo(final boolean print) {
        final String productInfo = String.format("%-13s%s TV%15s%-22sListed At: $%-15.2f%-15s%.2f%25s%d" +
                        "\n\t%-16s%.2f%%%-7s%s%d%22s%.2f%22s%.2f%22s%.2f\n",
                getBrand(), getSize(), "Model: ", getModel(), getSellingPrice(),
                "Purchased For: $", getCostPrice(), "Quantity On Hand: ", getQuantity(),
                "Total Profit: ", getProfitPct()*100, "", "Quantity Sold: ", getQuantitySold(),
                "Total Sales: $", getTotalSales(), "Total Cost: $", getTotalCost(),
                "Total Profit: $", getTotalProfit());
        if (print) {
            System.out.println(productInfo);
        }
        return productInfo;
    }

    public String displayInfoCatalog(final boolean print) {
        final String productInfo = String.format("%-13s%s TV%15s%-22sListed At: $%-15.2f",
                getBrand(), getSize(), "Model: ", getModel(), getSellingPrice());
        if (print) {
            System.out.println(productInfo);
        }
        return productInfo;
    }

    public String getSize() {
        return this.screenSize;
    }
}
