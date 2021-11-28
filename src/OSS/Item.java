package OSS;

import java.util.Date;

public class Item {

    private String itemID;
    private double price;
    private String name;
    private double numberOf = 0;
    private boolean bulk;
    private String expDate;


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(double numberOf) {
        this.numberOf = numberOf;
    }

    public void remove(double quantity){
        this.numberOf -= quantity;
    }

    public Item(String itemID, double price, String name, double numberOf, boolean bulk, String expDate) {
        this.itemID = itemID;
        this.expDate = expDate;
        this.price = price;
        this.name = name;
        this.bulk = bulk;
        this.numberOf = numberOf;
    }

    public String getItemID() {
        return itemID;
    }

    public boolean getBulk() {
        return bulk;
    }

    public String getExpDate() {
        return expDate;
    }

    @Override
    public String toString() {
        String isBulk = "No";
        if(bulk)
            isBulk = "Yes";
        return (itemID+","+price + "," + name + "," + numberOf+","+isBulk+","+expDate);
    }
}