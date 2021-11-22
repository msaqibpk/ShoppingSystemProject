package OSS;

public class Employee { 
    String eID;
    String dob;
    String name;
    String username;
    String passcode;
    double salary;
    Shipping address;

    public Employee setUp(String eID, String dob, String name, double salary, String address,
                          String username, String passcode){
        Employee intern = new Employee();
        intern.eID = eID;
        intern.dob = dob;
        intern.name = name;
        intern.salary = salary;
        intern.address = setUp(address);
        intern.username = username;
        intern.passcode = passcode;
        return intern;
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
        if(password.equals(this.passcode))
            return true;
        else
            return false;
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

    public String writeInfo()
    {
        return this.eID+","+this.dob+","+this.name+","+Double.toString(this.salary)+","+this.address.compileAddr()+","+this.username+","+this.passcode;
    }

}
