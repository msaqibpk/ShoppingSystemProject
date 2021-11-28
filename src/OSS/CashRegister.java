package OSS;

import java.util.ArrayList;

public class CashRegister {
    //Attributes
    private ArrayList<Item> Cart = new ArrayList<>();
    private int bankCode = 0;
    Item lastAdded;

    //Methods
    public ArrayList<Item> getCart() {
        return Cart;
    }

    public void setCode(int code) {
        bankCode = code;
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
        return total;
    }

    public String placeOrder(String bankID) {
        return String.format("%s,%s",bankID, cartTotal());
    }

    public int getBankCode(){
        return this.bankCode;
    }

}