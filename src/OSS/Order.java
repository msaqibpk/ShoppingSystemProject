package OSS;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    String clientName;
    String itemListing;
    File orderLog;

    public void noteOrder(Customer client, CashRegister order) throws IOException {
        clientName = client.custPIN;
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

    public void printPaymentInfo(CashRegister terminal, String bankID, boolean isCheck) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        File orderLog = new File("OrderLog.txt");
        FileWriter clerk = new FileWriter(orderLog);
        String orderLine = formatter.format(date) + ", STORE: IMACON Lubbock, TX" + ", EMPLOYEE: " + terminal.getUser().name + ": ";
        if(bankID.equals(""))
            orderLine += "CASH: " + terminal.getCharge();
        else if(isCheck)
            orderLine += "CHECK: " + terminal.getCharge();
        else
            orderLine += "CARD: " + terminal.getCharge();
        clerk.write(orderLine);
        clerk.close();
    }
}