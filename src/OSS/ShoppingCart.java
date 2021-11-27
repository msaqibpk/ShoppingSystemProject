package OSS;

import java.util.ArrayList;

public class ShoppingCart {
    //Attributes
    private ArrayList<Item> Cart = new ArrayList<>();
    private int bankCode = 0;

    //Methods
    public ArrayList<Item> getCart() {
        return Cart;
    }

    public void setCode(int code) {
        bankCode = code;
    }

    public void addToCart(Item matching){
        boolean existing = false;
        for(Item items : this.Cart) {
            if(matching.getName() == items.getName()) {
                items.setNumberOf(items.getNumberOf() + 1);
                existing = true;
            }
        }
        if(!existing) {
            Item addTo = new Item(matching.getItemID(), matching.getPrice(), matching.getName(), 1, matching.getBulk(), matching.getExpDate());
            this.Cart.add(addTo);
        }
    }

    public boolean removeFromCart(Item matching){
        boolean existing = false;
        for(Item items : this.Cart) {
            if(matching.getName().equals(items.getName())) {
                existing = true;
                if(items.getNumberOf() > 0)
                    items.setNumberOf(items.getNumberOf()-1);
                else
                    this.Cart.remove(items);
            }
        }
        if(!existing)
            System.out.println("WARNING, INVALID CART REMOVAL");
        return existing;
    }

    public Item matchedItem(Item matching){
        for(Item items : this.Cart) {
            if(matching.getName().equals(items.getName()))
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
            total += item.getPrice();
        }
        return total;
    }

    public String placeOrder(Customer user) {
        return String.format("%s,%s,%s", user.custPIN, user.bankID, cartTotal());
    }

    public int getBankCode(){
        return this.bankCode;
    }

}