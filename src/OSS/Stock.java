package OSS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Stock {
    //Attributes
    private ArrayList<Item> inventory = new ArrayList<>();
    private  int Amount;
    int restockDate;

    //Methods
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> items) {
        inventory = items;
    }
    public int getAmount() {
        return Amount;
    }
    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getRestockDate() {
        return restockDate;
    }
    public void setRestockDate(int restockDate) {
        this.restockDate = restockDate;
    }

    public int countItems(){
        return getInventory().size();
    }

    public Item matchedItem(Item matching){
        for(Item items : this.inventory) {
            if(matching.getName() == items.getName())
                return items;
        }
        return new Item("ERROR",0.00, "ERROR", 0, "ERROR");
    }

    public boolean removeFromStock(Item matching){
        for(Item items : this.inventory) {
            if(matching.getName() == items.getName() && items.getNumberOf() > 0){
                items.setNumberOf(items.getNumberOf() - 1);
                return true;
            }
        }
        return false;
    }

    public Item matchByID(String name){
        for(Item items : this.inventory) {
            if(name.equals(items.getItemID()))
                return items;
        }
        return new Item("ERROR",0.00, "ERROR", 0, "ERROR");
    }

    public void fillInventory() throws FileNotFoundException {
        Stock stock = new Stock();
        ReadInventory ri = new ReadInventory();
        stock.setInventory(ri.Read());
        //commented out to print the inventory to screen
        /*for(int i = 0; i < stock.getInventory().size(); i++){
            System.out.println(stock.getInventory().get(i).toString());
        }*/
    }
    public void writeToFile() throws IOException {
        String output = "";
        PrintWriter clerk = new PrintWriter("Inventory.txt");
        for (Item current : inventory) {
            output += current.toString() + "\n";
        }
        clerk.write(output.trim());
        clerk.close();
    }

}

