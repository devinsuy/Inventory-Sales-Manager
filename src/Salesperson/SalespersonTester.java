package Salesperson;

import Inventory.Inventory;
import Customer.Customer;
import MainMenu.MainMenu;

import java.util.Scanner;

public class SalespersonTester {
    public static void main(String[] args) {
        Salesperson sp = new Salesperson("Chad", 0.06);
        Inventory inv = new Inventory();
        Customer cust = new Customer("Devin", "149 Harvard Ln.", "(209)123-4567", 0.08);
        final SalespersonController person = new SalespersonController(inv, sp, cust, new MainMenu());
//        person.makeSalesMenu(new Scanner(System.in));
    }
}