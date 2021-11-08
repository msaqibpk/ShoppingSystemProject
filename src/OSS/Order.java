package OSS;
import java.io.*;
public class Order {

    String clientName;
    String itemListing;
    File orderLog;

    public void noteOrder(Customer client, ShoppingCart order) throws IOException {
        clientName = client.username;
        File orderLog = new File("OrderLog.txt");
        if (orderLog.createNewFile())
        {
            System.out.println("Log Generated; Populating...");
        }
        FileWriter clerk = new FileWriter(orderLog);
        for(Item items: order.getCart()) {
            itemListing += items.getName() + ": " + items.getNumberOf() + "\n";
            clerk.write(clientName + ":\n" + itemListing + "\n");
        }
        clerk.close();
    }
}