package Inventory;

import Product.Television;
import Product.Stereo;
import Product.Product;
import java.util.Scanner;
import MainMenu.MainMenu;

public class InventoryController
{
    private Inventory inv;
    private MainMenu m;

    public InventoryController(final Inventory inv, final MainMenu mm) {
        this.inv = inv;
        m = mm;
    }

    /**
     * Static input validation for entering an item price
     * @param input System.in scanner object
     * @return The input validated price
     */
    private static float priceValidation(final Scanner input) {
        float price;
        try {
            final String priceString = input.next();
            price = Float.parseFloat(priceString);
            if (price <= 0.0f) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {
            System.out.print("ERROR please enter a valid price: ");
            return priceValidation(input);
        }
        return price;
    }

    /**
     * UI submenu for selection the dimension to sort the
     * inventory according to
     * @param input System.in scanner object
     */
    private void sortMenu(final Scanner input) {
        System.out.println("\n" + MainMenu.underlineText("Sort the inventory by: "));
        System.out.println("   1. Item #");
        System.out.println("   2. Profit Percentage");
        System.out.println("   3. Selling Price");
        System.out.println("   4. Quantity On Hand");
        System.out.println("   5. Brand");
        System.out.println("   6. Return to main menu");
        System.out.print("\nPlease enter your selection: ");
        final int sortMenu = MainMenu.menuValidation(6, input);

        switch (sortMenu) {
            case 1: {
                this.inv.getInventory().sort(Product.byItemNum);
                System.out.println(MainMenu.underlineText("\nInventory Sorted By Item #"));
                break;
            }
            case 2: {
                this.inv.getInventory().sort(Product.byProfitPct.reversed());
                System.out.println(MainMenu.underlineText("\nInventory Sorted By Profit Percentage"));
                break;
            }
            case 3: {
                this.inv.getInventory().sort(Product.bySellPrice);
                System.out.println(MainMenu.underlineText("\nInventory Sorted By Selling Price"));
                break;
            }
            case 4: {
                this.inv.getInventory().sort(Product.byQuantity);
                System.out.println(MainMenu.underlineText("\nInventory Sorted By Quantity On Hand"));
                break;
            }
            case 5: {
                this.inv.getInventory().sort(Product.byBrand);
                System.out.println(MainMenu.underlineText("\nInventory Sorted By Brand"));
                break;
            }
            case 6: {
                System.out.println("\nReturning to Main Menu . . .\n");
                m.mainMenu(input);
            }
        }
        if(sortMenu < 6){
            this.inv.displayInventory();
            System.out.println("\nReturning to Main Menu . . .\n");
            m.mainMenu(input);
        }
    }

    /**
     * UI for selecting a product to modify
     * @param input System.in scanner object
     * @return The item selected
     */
    private Product selectProductToChange(final Scanner input) {
        this.inv.getInventory().sort(Product.byItemNum);
        this.inv.displayInventory();
        System.out.print("\nEnter the Item # of the product you wish to modify: ");
        final int itemIndex = MainMenu.menuValidation(this.inv.getNumItems(), input);
        final Product productToModify = this.inv.getInventory().get(itemIndex - 1);
        System.out.println("The product you have selected is: " + productToModify.displayInfo(false));
        return productToModify;
    }

    /**
     * UI for selecting a product to delete
     * @param input System.in scanner object
     * @return The index of the selected item
     */
    private int selectIndexToDelete(final Scanner input) {
        this.inv.getInventory().sort(Product.byItemNum);
        this.inv.displayInventory();
        System.out.print("\nEnter the Item # of the product you wish to delete: ");
        final int itemIndex = MainMenu.menuValidation(this.inv.getNumItems(), input);
        final Product productToDelete = this.inv.getInventory().get(itemIndex - 1);
        System.out.println("\nThe product you have selected is: " + productToDelete.displayInfo(false));
        return itemIndex - 1;
    }

    /**
     * UI submenu for item modification
     * @param input System.in scanner object
     */
    private void modifyMenu(final Scanner input) {
        Product productToModify = null;
        System.out.println("\n" + MainMenu.underlineText("Modify Item"));
        System.out.println("   1. Change the price of an item");
        System.out.println("   2. Update the quantity of an item");
        System.out.println("   3. Delete an item");
        System.out.println("   4. Main Menu");
        System.out.print("\nPlease enter your selection: ");
        final int modifyMenu = MainMenu.menuValidation(4, input);
        System.out.println();

        if (modifyMenu < 3) {
            productToModify = this.selectProductToChange(input);
        }

        switch (modifyMenu) {
            case 1: {
                System.out.println("Item #" + productToModify.getItemNum() + " is currently listed at: $" + productToModify.getSellingPrice() + "\n");
                System.out.print("Enter the new price of this item: ");
                final float newPrice = priceValidation(input);
                System.out.println("Price will be updated from $" + productToModify.getSellingPrice() + " to $" + newPrice);
                if (MainMenu.confirm(input)) {
                    inv.modifyTxt(1, productToModify, String.valueOf(newPrice));
                    productToModify.setSellingPrice(newPrice);
                    System.out.println("The price of Item #" + productToModify.getItemNum() + " has been updated to $" + newPrice);
                }
                else {
                    System.out.println("Price modification was cancelled");
                }
                System.out.println("\nReturning to menu . . .");
                this.inventoryMenu(input);
                break;
            }
            case 2: {
                System.out.println("Item #" + productToModify.getItemNum() + " has a current stock of: " + productToModify.getQuantity() + "\n");
                System.out.print("Enter the new quantity of the item: ");
                final int newQuantity = MainMenu.menuValidation(99999, input);
                System.out.println("Quantity will be updated from " + productToModify.getQuantity() + " to " + newQuantity);
                if (MainMenu.confirm(input)) {
                    inv.modifyTxt(2, productToModify, String.valueOf(newQuantity));
                    productToModify.setQuantity(newQuantity);
                    System.out.println("The quantity of Item # " + productToModify.getItemNum() + " has been updated to " + newQuantity);
                }
                else {
                    System.out.println("Quantity modification was cancelled");
                }
                System.out.println("\nReturning to menu . . .");
                this.inventoryMenu(input);
                break;
            }
            case 3: {
                final int deleteIndex = this.selectIndexToDelete(input);
                if (MainMenu.confirm(input)) {
                    inv.modifyTxt(3, this.inv.getInventory().get(deleteIndex), null);
                    this.inv.getInventory().remove(deleteIndex);
                    for (int i = deleteIndex; i < this.inv.getInventory().size(); ++i) {
                        this.inv.getInventory().get(i).decrementItemNum();
                    }
                    inv.decrementNumItems();
                    System.out.println("Item #" + (deleteIndex+1) + " has been deleted");
                }
                else {
                    System.out.println("Item deletion cancelled");
                }
                System.out.print("\nReturning to menu . . .\n");
                this.inventoryMenu(input);
                break;
            }
            case 4: {
                m.mainMenu(input);
                break;
            }
        }
    }

    /**
     * UI submenu for accessing the inventory
     * @param input System.in scanner object
     */
    private void inventoryMenu(final Scanner input) {
        System.out.println(MainMenu.underlineText("\nInventory"));
        System.out.println("   1. Display inventory");
        System.out.println("   2. Sort the inventory");
        System.out.println("   3. Modify an item");
        System.out.println("   4. Main Menu");
        System.out.print("\nPlease enter your selection: ");
        final int inventoryMenu = MainMenu.menuValidation(4, input);
        switch (inventoryMenu) {
            case 1: {
                System.out.println("\n" + MainMenu.underlineText("Displaying Inventory"));
                this.inv.displayInventory();
                System.out.println("\nReturning to Main Menu . . .\n");
                m.mainMenu(input);
                break;
            }
            case 2: {
                this.sortMenu(input);
                break;
            }
            case 3: {
                this.modifyMenu(input);
                break;
            }
            case 4: {
                System.out.println("\nReturning to Main Menu . . .\n");
                m.mainMenu(input);
                break;
            }
        }
    }

    /**
     * UI for starting screen
     * @param input System.in scanner object
     */
    public void mainInventoryMenu(final Scanner input) {
        System.out.println(MainMenu.underlineText("View/Modify Inventory"));
        System.out.println("   1. View items low in stock");
        System.out.println("   2. Enter a new item into inventory");
        System.out.println("   3. Modify an existing product");
        System.out.println("   4. View the inventory of products");
        System.out.println("   5. Return to main menu\n");
        System.out.print("Please enter your selection: ");
        final int menuChoice = MainMenu.menuValidation(5, input);
        switch (menuChoice) {
            case 1:
                System.out.println("\n" + MainMenu.underlineText("Items Low In Stock"));
                this.inv.checkLowStock(true);
                System.out.println("\nReturning to Main Menu . . .\n");
                m.mainMenu(input);
                break;
            case 2: {
                System.out.println(MainMenu.underlineText("\nEnter A New Item"));
                System.out.println("The product is a: ");
                System.out.println("   1. Television");
                System.out.println("   2. Stereo System");
                System.out.print("\nPlease enter your selection: ");
                final int productType = MainMenu.menuValidation(2, input);

                String itemType = (productType == 1) ? "Television" : "Stereo System";
                System.out.println("\n" + MainMenu.underlineText(itemType));
                System.out.print("   Enter the manufacturer of the product: ");
                final String productName = input.next();
                String productSpecs;
                if (productType == 1) {
                    System.out.print("   Enter the size(inches) of the display: ");
                    productSpecs = MainMenu.menuValidation(999, input) + "\"";
                }
                else {
                    productSpecs = "N/A";
                }
                System.out.print("   Enter the product's model number: ");
                final String productModel = input.next();
                System.out.print("   Enter the price it costs us from the manufacturer: ");
                final float costPrice = priceValidation(input);
                System.out.print("   Enter the selling price of the product: ");
                final float sellingPrice = priceValidation(input);
                System.out.print("   Enter the quantity of the product in stock: ");
                final int productQuantity = MainMenu.menuValidation(9999, input);
                Product p; String itemInfo;
                inv.incrementNumItems();
                String quantityZeros = "";
                String zeros = "";
                String itemNum = Integer.toString(inv.getNumItems());
                if(itemNum.length() < 2){
                    zeros = zeros + "00";
                }
                else if(itemNum.length() < 3){
                    zeros = zeros + "0";
                }
                if(productQuantity < 10){
                    quantityZeros = quantityZeros + "00";
                }
                else if(productQuantity < 100){
                    quantityZeros = quantityZeros + "0";
                }

                if(productType == 1){
                    itemInfo = Inventory.createEntry((zeros + itemNum + "."), productName, productSpecs, ("#" + productModel),
                            ("$" + costPrice), ("$" + sellingPrice), ("(" + quantityZeros + productQuantity + ")"));
                }
                else{
                    itemInfo = Inventory.createEntry((zeros + itemNum + "."), productName, ("#" + productModel),
                            ("$" + costPrice), ("$" + sellingPrice), ("(" + quantityZeros + productQuantity + ")"));
                }
                System.out.println(itemInfo);
                System.out.println("Enter a new " + itemType + " with the above information: ");
                if(MainMenu.confirm(input)){
                    if (productSpecs.equals("N/A")) {
                        p = new Stereo(inv.getNumItems(), productName, productModel, costPrice, sellingPrice, productQuantity);
                        inv.modifyTxt(4, null, itemInfo);
                    }
                    else {
                        p = new Television(inv.getNumItems(), productName, productSpecs, productModel, costPrice, sellingPrice, productQuantity);
                        inv.modifyTxt(4, p, itemInfo);

                    }
                    this.inv.addItem(p);
                    System.out.println("\n" + MainMenu.underlineText(p.displayInfo(false)));
                    System.out.println("The item has been added to your inventory of products");
                }
                else{
                    System.out.println("Entry cancelled, your item was not added to the inventory");
                    inv.decrementNumItems();
                }
                System.out.println("\nReturning to Main Menu . . .\n");
                m.mainMenu(input);
                break;
            }
            case 3: {
                this.modifyMenu(input);
                break;
            }
            case 4: {
                System.out.println("\n" + MainMenu.underlineText("Displaying Inventory"));
                this.inv.displayInventory();
                this.inventoryMenu(input);
                break;
            }
            case 5: {
                System.out.println("Returning to main menu . . .\n");
                m.mainMenu(input);
                break;
            }
        }
    }
}
