package Inventory;

import MainMenu.MainMenu;
import java.util.Scanner;

public class InventoryTester
{
    public static void main(final String[] args) {
        final InventoryController ic = new InventoryController(new Inventory(), new MainMenu());
        final Scanner input = new Scanner(System.in);
        ic.mainInventoryMenu(input);
        input.close();
    }
}
