package OSS;

public class Shipping {
    private boolean Apartment = false;
    private String streetAddress;
    private String roomNum;
    private String State;
    private String City;
    private String zipCode;

    public boolean isApartment() {
        return Apartment;
    }
    public void setApartment(boolean apartment) {
        Apartment = apartment;
    }

    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getRoomNum() {
        return roomNum;
    }
    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getState() {
        return State;
    }
    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return City;
    }
    public void setCity(String city) {
        City = city;
    }

    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String  zipCode) {
        this.zipCode = zipCode;
    }

    public String compileAddr(){
        //Form: H/A!(opt roomNum)!5327 Dank Run!Lubbock!Texas!75555
        if (!isApartment()){ //Not an apartment
            return("H!" + getStreetAddress() + "!" + getCity()+ "!"  + getState() + "!" + getZipCode());
        }else{//Is an apartment
            return(("A!" + getRoomNum() + "!" + getStreetAddress() + "!" + getCity()+ "!"  + getState() + "!" + getZipCode()));
        }
    }
}