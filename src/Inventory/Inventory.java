package Inventory;

import java.io.*;
import java.util.regex.Matcher;

import Product.Stereo;
import java.util.Scanner;
import java.util.regex.Pattern;

import Product.Television;
import Product.Product;
import java.util.ArrayList;

public class Inventory
{
    private ArrayList<Product> inv;
    private ArrayList<Product> lowStock;
    private static int numItems;

    /**
     * Inventory constructor, loads items from .txt,
     * assigning each an item number
     */
    public Inventory() {
        this.inv = new ArrayList<Product>();
        this.lowStock = new ArrayList<Product>();
        this.loadInventory();
        for (final Product p : this.inv) {
            p.setItemNum(++Inventory.numItems);
        }
    }

    /**
     * Obtains the actual ArrayList products are stored in
     * @return A reference to the ArrayList of products
     */
    public ArrayList<Product> getInventory() {
        return this.inv;
    }

    /**
     * Adds an item to the inventory, assigning it an item #
     * @param p The item to add
     */
    void addItem(final Product p) {
        this.inv.add(p);
    }

    /**
     * Iterates through and displays each item in inventory
     */
    void displayInventory() {
        for (final Product p : this.inv) {
            p.calculateTotals();
            String productInfo;
            if (p.getItemType()) {
                productInfo = p.displayInfo(false);
            }
            else {
                final Television t = (Television)p;
                productInfo = t.displayInfo(false);
            }
            System.out.printf("\tItem #%03d%-7s%s\n", p.getItemNum(), ".", productInfo);
        }
    }

    static String createEntry(String itemNum, String manufacturer, String model, String cost, String selling, String quantity){
        return String.format(" %-15s%-20s%-36s%-20s%-17s%s", itemNum, manufacturer, model, cost, selling, quantity);
    }
    static String createEntry(String itemNum, String manufacturer, String display, String model, String cost, String selling, String quantity) {
        return String.format(" %-15s%-20s%-16s%-20s%-20s%-17s%s", itemNum, manufacturer, display, model, cost, selling, quantity);
    }

    void copyTxt(File src, File dst) throws IOException{
        BufferedWriter tempWriter = new BufferedWriter(new FileWriter(dst));
        Scanner input = new Scanner(src);
        while(input.hasNextLine()){
            tempWriter.write(input.nextLine());
            tempWriter.newLine();
        }
        input.close();
        tempWriter.close();
    }

    /* ITEMCODES
    1 : modify price
    2 : modify quantity
    3 : delete item
    4 : add item*/
    void modifyTxt(int itemCode, Product p, String change){
        File inventoryList = new File("C:\\Users\\Devin\\IdeaProjects\\DACK 343 LOCAL\\src\\Inventory\\SampleInventory.txt");
        Scanner invReader, tempReader;
        int modifyIndex = 0;
        int stringLength = 0;
        String modifiedItem, currentItem, currentLine;
        currentItem = modifiedItem = "";

        try{
            // Copy the original file
            File tempFile = new File("C:\\Users\\Devin\\IdeaProjects\\DACK 343 LOCAL\\src\\Inventory\\temp.txt");
            copyTxt(inventoryList, tempFile);
            invReader = new Scanner(inventoryList);
            tempReader = new Scanner(tempFile);
            if(itemCode < 4){
                while(invReader.hasNextLine()){
                    currentItem = invReader.nextLine();
                    if(currentItem.contains(p.getModel())){
                        break;
                    }
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(inventoryList));
            String zeros, leading;
            zeros = leading = "";

            switch(itemCode){
                case 1:
                    modifyIndex = currentItem.indexOf(Float.toString(p.getSellingPrice()));
                    stringLength = Float.toString(p.getSellingPrice()).length();
                    modifiedItem = currentItem.substring(0, modifyIndex) + change
                            + currentItem.substring((modifyIndex + stringLength));
                    while(tempReader.hasNextLine()){
                        currentLine = tempReader.nextLine();
                        if(currentLine.contains(p.getModel())){
                            bw.write(modifiedItem);
                        }
                        else{
                            bw.write(currentLine);
                        }
                        bw.newLine();
                    }
                    break;
                case 2:
                    modifyIndex = currentItem.indexOf("(");
                    stringLength = change.length();
                    if(stringLength < 2){
                        zeros = "00";
                    }
                    else if (stringLength < 3){
                        zeros = "0";
                    }
                    leading = "(" + zeros + change + ")";
                    modifiedItem = currentItem.substring(0, modifyIndex) + leading;
                    while(tempReader.hasNextLine()){
                        currentLine = tempReader.nextLine();
                        if(currentLine.contains(p.getModel())){
                            bw.write(modifiedItem);
                        }
                        else{
                            bw.write(currentLine);
                        }
                        bw.newLine();
                    }
                    break;
                case 3:
                    boolean lineFound = false;
                    int currentItemNum;
                    while(tempReader.hasNextLine()){
                        currentLine = tempReader.nextLine();
                        if(currentLine.contains(p.getModel())){
                            lineFound = true;
                            continue;
                        }
                        // Copies items above the deleted item
                        if(!lineFound || !(currentLine.contains(String.valueOf(0)))){
                            bw.write(currentLine);
                            bw.newLine();
                        }
                        // Decrements item numbers of items below deleted item
                        else{
                            modifyIndex = currentLine.indexOf(".") - 3;
                            currentItemNum = Integer.valueOf(currentLine.substring(modifyIndex, (modifyIndex + 3)));
                            currentItemNum -= 1;
                            stringLength = Integer.toString(currentItemNum).length();
                            if(stringLength < 2){
                                zeros = "00";
                            }
                            else if (stringLength < 3){
                                zeros = "0";
                            }
                            leading = zeros + currentItemNum;
                            modifiedItem = currentLine.substring(0, modifyIndex) + leading
                                    + currentLine.substring((modifyIndex + 3));
                            bw.write(modifiedItem);
                            bw.newLine();
                        }
                    }
                    break;
                case 4:
                    if(p == null){ // Signifies item is a Stereo, has no display size
                        while(tempReader.hasNextLine()){
                            currentLine = tempReader.nextLine();
                            bw.write(currentLine);
                            bw.newLine();
                        }
                        bw.write(change);
                        bw.newLine();
                    }
                    else{
                        Product lastTV = p;
                        inv.sort(Product.byItemNum);
                        for(int i = 0; i < inv.size(); i++){
                            if(!(inv.get(i).getItemType())){
                                lastTV = inv.get(i);
                            }
                            if(inv.get(i).getItemType()){
                                break;
                            }
                        }
                        while(tempReader.hasNextLine()){
                            currentLine = tempReader.nextLine();
                            if(currentLine.contains(lastTV.getModel())){
                                bw.write(currentLine);
                                bw.newLine();
                                bw.write(change);
                                bw.newLine();
                            }
                            else{
                                bw.write(currentLine);
                                bw.newLine();
                            }
                        }
                    }
            }

            invReader.close();
            bw.close();
            tempReader.close();
            tempFile.delete();
        }
        catch (IOException i){
            System.out.println("ERROR: FILE COULD NOT BE READ");
        }

    }

    /**
     * Opens SampleInventory.txt and instantiates Products
     */
    void loadInventory() {
        final File inventoryList = new File("C:\\Users\\kevin\\IntelliJ IDEA Projects\\343-Project-2\\src\\Inventory\\SampleInventory.txt");
        final Pattern tv = Pattern.compile("(\\d+)\\.\\s+(\\w+)\\s+(\\d+)\\\"\\s+\\#(\\S+)\\s+\\$(\\d+\\.\\d+)\\s+\\$(\\d+\\.\\d+)\\s+\\((\\d+)\\)\\s+\\((\\d+)\\)");
        final Pattern stereo = Pattern.compile("(\\d+)\\.\\s+(\\w+)\\s+\\s+\\#(\\S+)\\s+\\$(\\d+\\.\\d+)\\s+\\$(\\d+\\.\\d+)\\s+\\((\\d+)\\)\\s+\\((\\d+)\\)");
        final String stopperLine = "STEREO";
        boolean loadTV = true;
        try {
            final Scanner input = new Scanner(inventoryList);
            String currentLine;
            while (input.hasNextLine()) {
                currentLine = input.nextLine();
                if (currentLine.contains(stopperLine)) {
                    loadTV = false;
                }
                if (loadTV) {
                    final Matcher m = tv.matcher(currentLine);
                    while (m.find()) {
                        Product temp = new Television(Integer.valueOf(m.group(1)), m.group(2), m.group(3), m.group(4),
                                Float.valueOf(m.group(5)), Float.valueOf(m.group(6)), Integer.valueOf(m.group(7)), Integer.valueOf(m.group(8)));
                        temp.calculateTotals();
                        this.inv.add(temp);
                    }
                }
                else {
                    final Matcher m = stereo.matcher(currentLine);
                    while (m.find()) {
                        Product temp = new Stereo(Integer.valueOf(m.group(1)), m.group(2), m.group(3), Float.valueOf(m.group(4)),
                                Float.valueOf(m.group(5)), Integer.valueOf(m.group(6)), Integer.valueOf(m.group(7)));
                        temp.calculateTotals();
                        this.inv.add(temp);
                    }
                }
            }
            input.close();
        }
        catch (FileNotFoundException fnf) {
            System.out.println("INVALID DIRECTORY SPECIFIED, failed to load existing items");
        }
    }

    /**
     * Checks and updates which items are low in stock (quantity <= 5)
     * @param print Prints the items low in stock if true
     */
    public void checkLowStock(final boolean print) {
        lowStock.clear();
        ArrayList<Product> byStock = this.inv;
        byStock.sort(Product.byQuantity);

        // Adds items that are in low stock
        for (final Product p : byStock) {
            if(p.getQuantity() > 5){
                break;
            }
            lowStock.add(p);
        }
        this.lowStock.sort(Product.byQuantity);
        if (print) {
            for (final Product p : this.lowStock) {
                System.out.printf("\tItem #%03d%-7s%s\n", p.getItemNum(), ".", p.displayInfo(false));
            }
        }
    }

    public int getNumItems() {
        return Inventory.numItems;
    }

    public ArrayList<Product> getLowStock(){
        return lowStock;
    }

    public void decrementNumItems() {
        --Inventory.numItems;
    }
    public void incrementNumItems(){
        Inventory.numItems++;
    }

    static { // Initialization
        Inventory.numItems = 0;
    }
}
