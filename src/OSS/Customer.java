package OSS;
class Customer
{
    Boolean isPremium;
    String custPIN;
    int custPoints;
    String name;
    String phoneNum;
    String bankID;
    Shipping shipTo;

    public Customer setUp(Boolean isPremium, String phoneNum, String custPIN, int custPoints, String name, String bID, String sID)
    {
        Customer newCustomer = new Customer();
        newCustomer.isPremium = isPremium;
        newCustomer.custPIN = custPIN;
        newCustomer.custPoints = custPoints;
        newCustomer.name = name;
        newCustomer.phoneNum = phoneNum;
        newCustomer.bankID = bID;
        newCustomer.shipTo = setUp(sID);

        return newCustomer;
    }

    public Boolean phoneMatch(String phone){
        if(phone.equals(this.phoneNum))
            return true;
        else
            return false;
    }

    public Boolean pinMatch(String username)
    {
        if(username.equals(this.custPIN))
            return true;
        else
            return false;
    }

    public void addPoints(int add)
    {
        custPoints += add;
    }

    public void updateCard(String cardNumber)
    {
        this.bankID = cardNumber;
    }

    public String writeInfo()
    {
        char iP = 'N';
        if(isPremium)
            iP = 'P';
        return this.custPIN + "," + this.custPoints + "," + this.name + "," + this.phoneNum + "," + iP + ",BANK:," + this.bankID + ",SHIP:," + this.shipTo.compileAddr();
    }

    public Shipping setUp(String input){
        String[] in = input.split("!");
        Shipping newShip = new Shipping();
        newShip.setApartment(false);
        int index = 0;
        if(in[0].equals("A")){
            index++;
            newShip.setApartment(true);
            newShip.setRoomNum(in[index]);
        }
        index++;
        if(in[0].equals("H") || in[0].equals("A")){
            newShip.setStreetAddress(in[index]);
            index++;
            newShip.setCity(in[index]);
            index++;
            newShip.setState(in[index]);
            index++;
            newShip.setZipCode(in[index]);
        }
        else
            System.out.println("Unexpected character in shipping setup.");
        return newShip;
    }

    public void updateShipping(String input){
        this.shipTo = setUp(input);
    }

}
