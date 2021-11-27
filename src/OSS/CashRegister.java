package OSS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CashRegister {
    ArrayList<Item> cart = new ArrayList<>();

    public boolean getProductByID(String ID, Stock stock) {
        Item itemMatch = stock.matchByID(ID);
        if (!itemMatch.getItemID().equals("ERROR") ) {
            cart.add(itemMatch);
            return true;
        } else {
            System.out.println("Item " + ID + " not found. Item was not added to checkout list.");
            return false;
        }
    }

    public ArrayList<Item> getCart() {
        return cart;
    }



}