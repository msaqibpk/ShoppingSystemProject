package OSS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Reads the inventory file and fills the inventory arraylist with the information
class ReadInventory{
    public ArrayList<Item> Read() throws FileNotFoundException {
        String[] itemTemp;
        ArrayList<Item> TempArrl = new ArrayList<>();
        int count = 0;
        double dTemp;
        int iTemp;
        File Inventory = new File("Inventory.txt");
        Scanner scInventory = new Scanner(Inventory);
        while(scInventory.hasNextLine()){
            itemTemp = scInventory.nextLine().split(",");
            dTemp = Double.parseDouble(itemTemp[0]);
            iTemp = Integer.parseInt(itemTemp[2]);
            TempArrl.add(count,new Item(dTemp,itemTemp[1],iTemp));
            count++;
        }
        scInventory.close();
        return (TempArrl);
    }
}