package OSS;

public class Item {

    private double price;
    private String name;
    private int numberOf = 0;

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

    public int getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(int numberOf) {
        this.numberOf = numberOf;
    }

    public void addNumItems(int quantity){
        numberOf+=quantity;
    }
    public void subNumItems(int quantity){
        numberOf-=quantity;
    }

    public Item(double price, String name, int numberOf) {
        this.price = price;
        this.name = name;
        this.numberOf = numberOf;
    }



    @Override
    public String toString() {
        return (price + "," + name + "," + numberOf);
    }
}