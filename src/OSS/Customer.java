package OSS;
class Customer
{
    String username;
    String password;
    String name;
    String phoneNum;
    Boolean isPremium;
    String bankID;
    Shipping shipTo;

    public Customer setUp(String username, String password, String name, String phoneNum, Boolean isPremium, String bID, String sID)
    {
        Customer newCustomer = new Customer();
        newCustomer.username = username;
        newCustomer.password = password;
        newCustomer.name = name;
        newCustomer.phoneNum = phoneNum;
        newCustomer.isPremium = isPremium;
        newCustomer.bankID = bID;
        newCustomer.shipTo = setUp(sID);

        return newCustomer;
    }

    public Boolean nameMatch(String username)
    {
        if(username.equals(this.username))
            return true;
        else
            return false;
    }

    public Boolean passMatch(String password)
    {
        if(password.equals(this.password))
            return true;
        else
            return false;
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
        return this.username + "," + this.password + "," + this.name + "," + this.phoneNum + "," + iP + ",BANK:," + this.bankID + ",SHIP:," + this.shipTo.compileAddr();
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
