package OSS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

//Reads the inventory file and fills the inventory arraylist with the information
class ReadInventory{
    public ArrayList<Item> Read() throws FileNotFoundException {
        String[] itemTemp;
        ArrayList<Item> TempArrl = new ArrayList<>();
        int count = 0;
        double priceTemp;
        double quantityTemp;
        String idTemp;
        String expDate;
        String name;
        boolean bulk;
        File Inventory = new File("Inventory.txt");
        Scanner scInventory = new Scanner(Inventory);
        while(scInventory.hasNextLine()){
            bulk = true;
            itemTemp = scInventory.nextLine().split(",");
            idTemp = itemTemp[0];
            priceTemp = Double.parseDouble(itemTemp[1]);
            name = itemTemp[2];
            quantityTemp = Double.parseDouble(itemTemp[3]);
            if(itemTemp[4].equals("No"))
                bulk = false;
            expDate = itemTemp[5];
            TempArrl.add(count,new Item(idTemp,priceTemp,name,quantityTemp, bulk, expDate));
            count++;
        }
        scInventory.close();
        return (TempArrl);
    }
}