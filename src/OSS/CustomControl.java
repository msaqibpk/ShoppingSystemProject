package OSS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Objects;

public class CustomControl{

    private final Image bezosLul = new Image("OSS/mynamejeff.png");

    //Main Display Sections

    @FXML
    ListView<String> MainListing,  CheckoutItemList;
    @FXML
    Label MainUserLabel, LoginFailure, RegisterTitle, RegisterAccountIsDup, CheckoutItemTitle,
            CheckoutTheSubTotal, CheckoutTheTotal, UpdateCardMessage, UpdateCardTheOr, UpdateShippingShipAddUp;
    @FXML
    Button MainLogout, MainUpdateCard, MainUpdateShipping, MainEnterCheckout, LoginButt, LoginCreateAccButt, RegisterButt,
            CheckoutPlaceOrder, UpdateCardUpdated, UpdateShippingShipUpdate, UpdateCardAccountSwitch;
    @FXML
    TextField LoginUsername, LoginPassword, RegisterNewUser, RegisterNewPass, RegisterCCNum, RegisterPhoneNum,
            RegisterName, RegisterAddress, RegisterCity, RegisterState, RegisterZip, RegisterRoomNum,
            UpdateWorkingCard, UpdateShippingStreet, UpdateShippingCity, UpdateShippingState, UpdateShippingZip,
            UpdateShippingRoomNum;
    @FXML
    CheckBox RegisterApartment, RegisterPremium, CheckoutDeliveryBox, CheckoutAddPremiumBox;
    @FXML
    Rectangle LoginBack, RegisterBack, CheckoutBack, UpdateCardBack, UpdateShippingBack;


    Stock inventory = new Stock();
    ShoppingCart userCart = new ShoppingCart();
    ReadInventory storeMaster = new ReadInventory();
    CustomerLog dummyLog = new CustomerLog();
    Customer user;
    Shipping ship = new Shipping();
    Order orderLog = new Order();
    messageResponseBuffer mrb = new messageResponseBuffer();
    bankerThread bank = new bankerThread(mrb);
    int bankCode;


    public void displayMainListing() throws IOException {
        inventory.setInventory(storeMaster.Read());
        ObservableList<String> itemListing = FXCollections.observableArrayList();
        for(Item items : inventory.getInventory()){
            itemListing.add(items.getName());
        }
        MainListing.setItems(itemListing);
        MainListing.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new itemDisplayCard(inventory, userCart);
            }
        });

    } //TODO Update with Buttons

    public void displayCheckoutListing() {
        ObservableList<String> checkoutListing = FXCollections.observableArrayList();
        for(Item items: userCart.getCart()){
            checkoutListing.add(String.format("%s: %s Quantity: %s Total: %s", items.getName(), items.getPrice(),
                    items.getNumberOf(), (items.getPrice() * items.getNumberOf())));
        }
        CheckoutItemList.setItems(checkoutListing);
        CheckoutItemList.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
    }

    //Conversion to Unified Methods
    public void openUnified() throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("unified.fxml")));
        Stage unified = new Stage();
        unified.setScene(new Scene(root, 800, 400));
        //should open login by default.
        unifiedLogin();
    }

    public void unifiedLogin() {
        clearAll();
        user = null;
        userCart = new ShoppingCart();
        LoginBack.setVisible(true);
        LoginPassword.setVisible(true);
        LoginUsername.setVisible(true);
        LoginButt.setVisible(true);
        LoginCreateAccButt.setVisible(true);
        LoginPassword.setMouseTransparent(false);
        LoginUsername.setMouseTransparent(false);
        LoginButt.setMouseTransparent(false);
        LoginCreateAccButt.setMouseTransparent(false);
    }

    public void unifiedRegister() {
        clearAll();
        RegisterBack.setVisible(true);
        RegisterTitle.setVisible(true);
        RegisterNewUser.setVisible(true);
        RegisterNewPass.setVisible(true);
        RegisterName.setVisible(true);
        RegisterAddress.setVisible(true);
        RegisterCity.setVisible(true);
        RegisterState.setVisible(true);
        RegisterZip.setVisible(true);
        RegisterApartment.setVisible(true);
        RegisterCCNum.setVisible(true);
        RegisterButt.setVisible(true);
        RegisterPhoneNum.setVisible(true);
        RegisterPremium.setVisible(true);
        RegisterPremium.setMouseTransparent(false);
        RegisterAddress.setMouseTransparent(false);
        RegisterNewUser.setMouseTransparent(false);
        RegisterNewPass.setMouseTransparent(false);
        RegisterName.setMouseTransparent(false);
        RegisterAddress.setMouseTransparent(false);
        RegisterCity.setMouseTransparent(false);
        RegisterState.setMouseTransparent(false);
        RegisterZip.setMouseTransparent(false);
        RegisterApartment.setMouseTransparent(false);
        RegisterButt.setMouseTransparent(false);
        RegisterCCNum.setMouseTransparent(false);
        RegisterPhoneNum.setMouseTransparent(false);
    }

    public void unifiedMainDisplay() throws IOException {
        clearAll();
        MainListing.setVisible(true);
        displayMainListing();
        MainUserLabel.setVisible(true);
        //MainUserLabel.setText("Welcome, " + user.username+"!");
        MainLogout.setVisible(true);
        MainEnterCheckout.setVisible(true);
        MainUpdateCard.setVisible(true);
        MainUpdateShipping.setVisible(true);
        MainListing.setMouseTransparent(false);
        MainUserLabel.setMouseTransparent(false);
        MainLogout.setMouseTransparent(false);
        MainEnterCheckout.setMouseTransparent(false);
        MainUpdateCard.setMouseTransparent(false);
        MainUpdateShipping.setMouseTransparent(false);
    }

    public void unifiedUpdateCard() {
        clearAll();
        UpdateCardBack.setVisible(true);
        UpdateCardAccountSwitch.setVisible(true);
        UpdateCardMessage.setVisible(true);
        UpdateCardTheOr.setVisible(true);
        UpdateCardUpdated.setVisible(true);
        UpdateCardAccountSwitch.setVisible(true);
        UpdateWorkingCard.setVisible(true);
        UpdateCardUpdated.setMouseTransparent(false);
        UpdateCardAccountSwitch.setMouseTransparent(false);
        UpdateWorkingCard.setMouseTransparent(false);
    }

    public void unifiedUpdateShipping() {
        clearAll();
        UpdateShippingBack.setVisible(true);
        UpdateShippingStreet.setVisible(true);
        UpdateShippingCity.setVisible(true);
        UpdateShippingState.setVisible(true);
        UpdateShippingZip.setVisible(true);
        UpdateShippingRoomNum.setVisible(true);
        UpdateShippingRoomNum.setDisable(false);
        UpdateShippingShipAddUp.setVisible(true);
        UpdateShippingShipUpdate.setVisible(true);
        UpdateShippingShipUpdate.setMouseTransparent(false);
        UpdateShippingStreet.setMouseTransparent(false);
        UpdateShippingCity.setMouseTransparent(false);
        UpdateShippingState.setMouseTransparent(false);
        UpdateShippingZip.setMouseTransparent(false);
        UpdateShippingRoomNum.setMouseTransparent(false);
    }

    public void unifiedCheckout() {
        clearAll();
        displayCheckoutListing();
        CheckoutBack.setVisible(true);
        CheckoutItemList.setVisible(true);
        CheckoutTheSubTotal.setVisible(true);
        double charge = userCart.cartTotal();
        CheckoutTheSubTotal.setText("SUBTOTAL: " + charge);
        CheckoutTheTotal.setVisible(true);
        CheckoutTheTotal.setText("TOTAL: " + charge);
        CheckoutTheSubTotal.setVisible(true);
        CheckoutPlaceOrder.setVisible(true);
        CheckoutDeliveryBox.setVisible(true);
        CheckoutAddPremiumBox.setVisible(true);
        CheckoutDeliveryBox.setMouseTransparent(false);
        CheckoutAddPremiumBox.setMouseTransparent(false);
        CheckoutPlaceOrder.setMouseTransparent(false);
    }

    public void clearAll(){
        LoginBack.setVisible(false);
        LoginPassword.setVisible(false);
        LoginUsername.setVisible(false);
        LoginButt.setVisible(false);
        LoginCreateAccButt.setVisible(false);
        LoginFailure.setVisible(false);
        RegisterBack.setVisible(false);
        RegisterTitle.setVisible(false);
        RegisterNewUser.setVisible(false);
        RegisterNewPass.setVisible(false);
        RegisterName.setVisible(false);
        RegisterAddress.setVisible(false);
        RegisterCity.setVisible(false);
        RegisterState.setVisible(false);
        RegisterZip.setVisible(false);
        RegisterApartment.setVisible(false);
        RegisterCCNum.setVisible(false);
        RegisterRoomNum.setVisible(false);
        RegisterPhoneNum.setVisible(false);
        RegisterButt.setVisible(false);
        RegisterPremium.setVisible(false);
        MainListing.setVisible(false);
        MainUserLabel.setVisible(false);
        MainLogout.setVisible(false);
        MainEnterCheckout.setVisible(false);
        MainUpdateCard.setVisible(false);
        MainUpdateShipping.setVisible(false);
        UpdateCardBack.setVisible(false);
        UpdateCardAccountSwitch.setVisible(false);
        UpdateCardMessage.setVisible(false);
        UpdateCardTheOr.setVisible(false);
        UpdateCardUpdated.setVisible(false);
        UpdateCardAccountSwitch.setVisible(false);
        UpdateWorkingCard.setVisible(false);
        UpdateShippingBack.setVisible(false);
        UpdateShippingStreet.setVisible(false);
        UpdateShippingCity.setVisible(false);
        UpdateShippingState.setVisible(false);
        UpdateShippingZip.setVisible(false);
        UpdateShippingRoomNum.setVisible(false);
        UpdateShippingShipAddUp.setVisible(false);
        UpdateShippingShipUpdate.setVisible(false);
        CheckoutBack.setVisible(false);
        CheckoutItemList.setVisible(false);
        CheckoutTheSubTotal.setVisible(false);
        CheckoutTheTotal.setVisible(false);
        CheckoutTheSubTotal.setVisible(false);
        CheckoutPlaceOrder.setVisible(false);
        CheckoutAddPremiumBox.setVisible(false);
        CheckoutDeliveryBox.setVisible(false);
        CheckoutDeliveryBox.setMouseTransparent(true);
        CheckoutPlaceOrder.setMouseTransparent(true);
        CheckoutAddPremiumBox.setMouseTransparent(true);
        UpdateShippingShipUpdate.setMouseTransparent(true);
        UpdateShippingStreet.setMouseTransparent(true);
        UpdateShippingCity.setMouseTransparent(true);
        UpdateShippingState.setMouseTransparent(true);
        UpdateShippingZip.setMouseTransparent(true);
        UpdateShippingRoomNum.setMouseTransparent(true);
        UpdateCardUpdated.setMouseTransparent(true);
        UpdateCardAccountSwitch.setMouseTransparent(true);
        UpdateWorkingCard.setMouseTransparent(true);
        MainListing.setMouseTransparent(true);
        MainUserLabel.setMouseTransparent(true);
        MainLogout.setMouseTransparent(true);
        MainEnterCheckout.setMouseTransparent(true);
        MainUpdateCard.setMouseTransparent(true);
        MainUpdateShipping.setMouseTransparent(true);
        RegisterAddress.setMouseTransparent(true);
        RegisterNewUser.setMouseTransparent(true);
        RegisterNewPass.setMouseTransparent(true);
        RegisterName.setMouseTransparent(true);
        RegisterAddress.setMouseTransparent(true);
        RegisterCity.setMouseTransparent(true);
        RegisterState.setMouseTransparent(true);
        RegisterZip.setMouseTransparent(true);
        RegisterApartment.setMouseTransparent(true);
        RegisterRoomNum.setMouseTransparent(true);
        RegisterCCNum.setMouseTransparent(true);
        RegisterPhoneNum.setMouseTransparent(true);
        RegisterButt.setMouseTransparent(true);
        LoginPassword.setMouseTransparent(true);
        LoginUsername.setMouseTransparent(true);
        LoginButt.setMouseTransparent(true);
        LoginCreateAccButt.setMouseTransparent(true);
    }

    //Unified Logic Methods
    public void login() throws IOException {
        if (dummyLog.verifyLogIn(LoginUsername.getText(), LoginPassword.getText())) {
            user = dummyLog.associateUser(LoginUsername.getText(), LoginPassword.getText());
            unifiedMainDisplay();

        } else {
            LoginFailure.setText("Either your username or password is incorrect.");
            LoginFailure.setDisable(false);
        }
    }

    public void RegisterAcc() throws Exception {//on Action for registering a new customer.
        if (!dummyLog.noDupUser(RegisterNewUser.getText())) {// If the username has is already in the system.
            //Assign data to its respective variables
            if (RegisterApartment.isSelected()) {
                ship.setApartment(true);
                ship.setRoomNum(RegisterRoomNum.getText());
            }
            if (RegisterPremium.isSelected()){
                CheckoutAddPremiumBox.setSelected(true);
            }
            ship.setStreetAddress(RegisterAddress.getText());//Fill the user data into the variables.
            ship.setCity(RegisterCity.getText());
            ship.setState(RegisterState.getText());
            ship.setZipCode(RegisterZip.getText());
            user = new Customer();
            user = user.setUp(false, RegisterNewUser.getText(),RegisterNewPass.getText(),
                    0, RegisterName.getText(), RegisterCCNum.getText(),ship.compileAddr());
            dummyLog.getCustomerLog().add(user);
            dummyLog.saveToFile(dummyLog.getCustomerLog());
            unifiedMainDisplay();
        }
        else{
            RegisterAccountIsDup.setText("This username already exists please enter a new one");
            RegisterAccountIsDup.setVisible(true);
        }
    }

    public void updateCard() throws IOException {
        user.updateCard(UpdateWorkingCard.getText());
        dummyLog.saveToFile(dummyLog.getCustomerLog());
        unifiedMainDisplay();
    }

    public void updateShipping() throws IOException { //TODO Investigate List Duplication
        if(UpdateShippingRoomNum.getText().isBlank()) {
            dummyLog.associateUser(user.phoneNum,user.custPIN).updateShipping(String.format("H!%s!%s!%s!%s", UpdateShippingStreet.getText(), UpdateShippingCity.getText(),
                    UpdateShippingState.getText(), UpdateShippingZip.getText()));
        }
        else{
            dummyLog.associateUser(user.phoneNum,user.custPIN).updateShipping(String.format("A!%s!%s!%s!%s!%s", UpdateShippingRoomNum.getText(), UpdateShippingStreet.getText(),
                    UpdateShippingCity.getText(), UpdateShippingState.getText(), UpdateShippingZip.getText()));
        }
        dummyLog.saveToFile(dummyLog.getCustomerLog());
        unifiedMainDisplay();
    }

    public void activateRoomNum(){
        RegisterRoomNum.setVisible(RegisterApartment.isSelected());
        RegisterRoomNum.setMouseTransparent(!RegisterApartment.isSelected());
    }

    public void updateCheckoutTotal(){
        double charge = userCart.cartTotal();
        if(CheckoutDeliveryBox.isSelected())
            charge += 3;
        if(CheckoutAddPremiumBox.isSelected() || user.isPremium)
            charge *= .9;
        if(CheckoutAddPremiumBox.isSelected())
            charge += 40;
        CheckoutTheTotal.setText("TOTAL: " + charge);
    }

    public void placeOrder() throws Exception {
        resolveOrder(mrb);
        if(bankCode == -1){
            UpdateCardMessage.setText("The bank didn't find that card, try a new one!");
            unifiedUpdateCard();
        }
        else if(bankCode == -2){
            UpdateCardMessage.setText("It Bounced, Homie");
            unifiedUpdateCard();
        }
        else if(bankCode > 0){
            inventory.writeToFile();
            CheckoutTheTotal.setText("ORDER PLACED! Bank Code: " + bankCode);
            orderLog.noteOrder(user, userCart);
        }
    }

    public void resolveOrder(messageResponseBuffer m) {
        String ossOutput;
        ossOutput = userCart.placeOrder(user);
        System.out.println(ossOutput);
        bankCode = m.send(ossOutput);
        userCart.setCode(bankCode);
    }
}
