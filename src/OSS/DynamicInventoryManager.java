
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DynamicInventoryManager {

    // Automatically logs low inventory items to the supplier log every 24 hours for items = 200 - current number of items, to ensure 200 quantity is available for each item.
    public static void AutomatedOrder() throws FileNotFoundException {

        Stock stock = new Stock();
        ReadInventory ri = new ReadInventory();
        stock.setInventory(ri.Read());

        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                try {
                    PrintWriter outFile = new PrintWriter(new FileWriter("AutomatedOrdersSupplierLog.txt",true));

                    String output = "";
                    output += new Date() + "\n\n";
                    output += "ItemID | ItemName | QuantityOrdered\n\n";

                    for(int i = 0; i < stock.getInventory().size(); i++) {
                        if (stock.getInventory().get(i).getNumberOf() <= 100) {
                            output += stock.getInventory().get(i).getItemID() + " | " + stock.getInventory().get(i).getName() + " | " + (200 - stock.getInventory().get(i).getNumberOf()) + "\n";
                        }
                    }

                    output += "--------------------------------------------------------------------------------------------------\n\n\n";

                    outFile.append(output);
                    outFile.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            };
        };

        //Runs once every 24 hours of launch
        t.schedule(tt, new Date(), 24 * 60 * 60 * 1000);

    }
}