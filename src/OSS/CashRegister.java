package OSS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CashRegister {
    //Attributes
    private ArrayList<Item> Cart = new ArrayList<>();
    private int bankCode = 0;
    Item lastAdded;
    private double charge;
    Employee user;

    //Methods
    public ArrayList<Item> getCart() {
        return Cart;
    }

    public short addProductByID(String ID, double weight, Stock stock) { //Returns 1 for success, -1 for exceeding stock limit, -2 for not finding
        Item matching = stock.matchByID(ID);
        if (!matching.getItemID().equals("ERROR") ) {
            boolean existing = false;
            for(Item items : this.Cart) {
                if(matching.getName().equals(items.getName())) {
                    existing = true;
                    if(items.getBulk())
                        items.setNumberOf(items.getNumberOf() + weight);
                    else if(stock.matchedItem(items).getNumberOf() > items.getNumberOf()) {
                        items.setNumberOf(items.getNumberOf() + 1);
                        lastAdded = items;
                    }
                    else {
                        System.out.println("Stock limit reached. Item not added to checkout list.");
                        return -1;
                    }
                }
            }
            if(!existing) {
                Item addTo = new Item(matching.getItemID(), matching.getPrice(), matching.getName(), 1, matching.getBulk(), matching.getExpDate());
                lastAdded = addTo;
                this.Cart.add(addTo);
            }
            return 1;
        } else {
            System.out.println("Item " + ID + " not found. Item was not added to checkout list.");
            return -2;
        }
    }

    public Item matchedItem(String matchThis){
        for(Item items : this.Cart) {
            if(matchThis.equals(items.getName()))
                return items;
        }
        return new Item("ERROR", 0.00, "ERROR", 0, false,"ERROR");
    }

    public void emptyCart(){
        Cart.clear();
    }

    public double cartTotal(){
        double total = 0;
        for (Item item : Cart) {
            total += (item.getPrice() * item.getNumberOf());
        }
        charge = total;
        return total;
    }

    public double getCharge()
    {
        return charge;
    }

    public String placeOrder(String bankID) {
        return String.format("%s,%s",bankID, cartTotal());
    }

    public void setCode(int code) {
        bankCode = code;
    }

    public int getBankCode(){
        return this.bankCode;
    }

    public Employee getUser(){
        return user;
    }

    public void setUser(Employee user){
        this.user = user;
    }

    public void printPaymentInfo(String bankID, boolean isCheck) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        File orderLog = new File("OrderLog.txt");
        FileWriter clerk = new FileWriter(orderLog);
        String orderLine = formatter.format(date) + ", STORE: IMACON Lubbock, TX" + ", EMPLOYEE: " + user.name + " ";
        if(bankID.equals(""))
            orderLine += "CASH: " + charge;
        else if(isCheck)
            orderLine += "CHECK: " + charge;
        else
            orderLine += "CARD: " + charge;
        clerk.write(orderLine);
        clerk.close();
    }
}