package Product;

public class Stereo extends Product
{
    public Stereo(final String brand, final String model, final float costPrice, final float sellPrice) {
        super(brand, model, costPrice, sellPrice);
        super.setItemType(true);
        super.setQuantity(1);
    }

    public Stereo(final String brand, final String model, final float costPrice, final float sellPrice, final int quantity) {
        super(brand, model, costPrice, sellPrice, quantity);
        super.setItemType(true);
    }

    public Stereo(final int itemNum, final String brand, final String model, final float costPrice, final float sellPrice, final int quantity) {
        super(itemNum, brand, model, costPrice, sellPrice, quantity);
        super.setItemType(true);
    }

    public Stereo(final int itemNum, final String brand, final String model, final float costPrice, final float sellPrice, final int quantity, final int quantitySold) {
        super(itemNum, brand, model, costPrice, sellPrice, quantity, quantitySold);
        super.setItemType(true);
    }

    @Override
    public String displayInfo(final boolean print) {
        final String productInfo = String.format("%-13s%s%15s%-22sListed At: $%-15.2f%-15s%.2f%25s%d" +
                        "\n\t%-16s%.2f%%%-7s%s%d%22s%.2f%22s%.2f%22s%.2f\n",
                getBrand(), "Stereo", "Model: ", getModel(), getSellingPrice(),
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
        final String productInfo = String.format("%-13s%s%15s%-22sListed At: $%-15.2f",
                getBrand(), "Stereo", "Model: ", getModel(), getSellingPrice());
        if (print) {
            System.out.println(productInfo);
        }
        return productInfo;
    }

}
