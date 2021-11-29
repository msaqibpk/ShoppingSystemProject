package OSS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    ListView<String> CheckoutItemList, FinalItemList;
    @FXML
    Label LoginFailure, CheckoutItemData, TotalLabel, PaymentFailureMessage, PaymentRewardsData;
    @FXML
    Button LoginButt, ListCheckoutButton, ListAddButton, FinalCheckoutButton, FinalCustomerButton, ConfirmButton;
    @FXML
    TextField LoginUsername, LoginPassword, ItemIDEntry, ItemWeightEntry, CustomerInfoPIN, CustomerInfoPhone, PaymentCardInfo, PaymentCashInfo;
    @FXML
    AnchorPane LoginGroup, ItemListGroup, CheckoutGroup, CustomerInfoGroup, ConfirmGroup;
    @FXML
    CheckBox isCheck;


    Stock inventory = new Stock();
    ReadInventory storeMaster = new ReadInventory();
    CashRegister terminal = new CashRegister();
    EmployeeLog userLog = new EmployeeLog();
    CustomerLog customerLog = new CustomerLog();
    Customer client;
    messageResponseBuffer mrb = new messageResponseBuffer();
    bankerThread bank = new bankerThread(mrb);
    int bankCode;
    double charge;


    public void displayMainListing() {
        ObservableList<String> itemListing = FXCollections.observableArrayList();
        for(Item items : terminal.getCart()){
            itemListing.add(items.getName());
        }
        CheckoutItemList.setItems(itemListing);
        CheckoutItemList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new itemDisplayCard(inventory, terminal);
            }
        });

    } //TODO Update with Buttons

    public void displayCheckoutListing() {
        ObservableList<String> checkoutListing = FXCollections.observableArrayList();
        for(Item items: terminal.getCart()){
            checkoutListing.add(String.format("%s: $%s Quantity: %s Total: $%s", items.getName(), items.getPrice(),
                    items.getNumberOf(), (items.getPrice() * items.getNumberOf())));
        }
        FinalItemList.setItems(checkoutListing);
        FinalItemList.setCellFactory(param -> new ListCell<String>(){
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

    public void unifiedLogin() {
        clearAll();
        LoginGroup.setVisible(true);
        LoginGroup.setMouseTransparent(false);
    }

    public void unifiedItemListing() throws IOException{
        clearAll();
        inventory.setInventory(storeMaster.Read());
        ItemListGroup.setVisible(true);
        ItemListGroup.setMouseTransparent(false);
        displayMainListing();
    }

    public void unifiedCheckout() {
        clearAll();
        CheckoutGroup.setVisible(true);
        CheckoutGroup.setMouseTransparent(false);
        CustomerInfoGroup.setVisible(true);
        CustomerInfoGroup.setMouseTransparent(false);
        displayCheckoutListing();
        charge = terminal.cartTotal();
        TotalLabel.setText("TOTAL: " + charge);
    }

    public void unifiedConfirm() {
        clearAll();
        ConfirmGroup.setVisible(true);
        ConfirmGroup.setMouseTransparent(false);
    }

    public void clearAll(){
        LoginGroup.setVisible(false);
        LoginGroup.setMouseTransparent(true);
        ItemListGroup.setVisible(false);
        ItemListGroup.setMouseTransparent(true);
        CheckoutGroup.setVisible(false);
        CheckoutGroup.setMouseTransparent(true);
        ConfirmGroup.setVisible(false);
        ConfirmGroup.setMouseTransparent(true);
    }

    //Unified Logic Methods
    public void login() throws IOException {
        if (userLog.verifyLogIn(LoginUsername.getText(), LoginPassword.getText())) {
            terminal.setUser(userLog.associateUser(LoginUsername.getText(), LoginPassword.getText()));
            unifiedItemListing();

        } else {
            LoginFailure.setText("Invalid Credentials");
        }
    }

    public void addToCart() { //Errorcode returns 1 if item can be added, -1 if over limit, -2 if not found
        if(ItemWeightEntry.getText().equals(""))
            ItemWeightEntry.setText("0.0");
        short errorCode = terminal.addProductByID(ItemIDEntry.getText(), Double.parseDouble(ItemWeightEntry.getText()), inventory);
        if (errorCode > 0) {
            CheckoutItemData.setText(String.format("ID:%s %s \nPRICE: %s BULK: %s",terminal.lastAdded.getItemID(), terminal.lastAdded.getName(),
                    terminal.lastAdded.getPrice(),terminal.lastAdded.getBulk()));
            displayMainListing();
        }
        else if(errorCode == -1)
            CheckoutItemData.setText("Stock limit reached\n for selected item.");
        else
            CheckoutItemData.setText("Item not found.");
        terminal.setCode(0);
    }

    public void findCustomer() throws IOException {
        if (customerLog.verifyLogIn(CustomerInfoPIN.getText(), CustomerInfoPhone.getText())) {
            client = customerLog.associateUser(CustomerInfoPIN.getText(), CustomerInfoPhone.getText());
            CustomerInfoGroup.setVisible(false);
            CustomerInfoGroup.setMouseTransparent(true);
            PaymentRewardsData.setText("REWARDS DATA: \nNAME: " + client.name + "\nPOINTS: " + client.custPoints);
            PaymentCardInfo.setText(client.bankID);

        } else {
            FinalCustomerButton.setText("Not Found");
        }
    }

    public void placeOrder() throws Exception {
        if(PaymentCardInfo.getText().equals("")) {
            if(PaymentCashInfo.getText().equals(""))
            charge -= Double.parseDouble(PaymentCashInfo.getText());
            if (charge < 0) {
                charge *= -1;
                System.out.println("Cash Confirmed, Till Open. CHANGE: " + charge);
                inventory.writeToFile(); //Todo register should send call to inventory.
                terminal.printPaymentInfo(PaymentCardInfo.getText(), isCheck.isSelected());
                if(client!=null)
                    client.addPoints((int)terminal.cartTotal());
                unifiedConfirm();
            } else {
                System.out.println("Cash Confirmed. Remaining Charge: " + charge);
            }
        }
        else{
            resolveOrder(mrb);
            if(bankCode == -1){
                PaymentFailureMessage.setText("The bank didn't find that card.");
            }
            else if(bankCode == -2){
                PaymentFailureMessage.setText("Card Declined.");
            }
            else if(bankCode > 0){
                inventory.writeToFile(); //Todo register should send call to inventory.
                terminal.printPaymentInfo(PaymentCardInfo.getText(), isCheck.isSelected());
                if(client!=null)
                    client.addPoints((int)terminal.cartTotal());
                unifiedConfirm();
            }
        }
        charge = 0;
    }

    public void resolveOrder(messageResponseBuffer m) {
        String ossOutput;
        ossOutput = terminal.placeOrder(PaymentCardInfo.getText());
        System.out.println(ossOutput);
        bankCode = m.send(ossOutput);
        terminal.setCode(bankCode);
    }

    public void orderConfirmed() throws IOException {
        inventory.removeOrderFromStock(terminal);
        inventory.writeToFile();
        terminal.emptyCart();
        customerLog.saveToFile(customerLog.getCustomerLog());
        client = null;
        unifiedItemListing();
        displayMainListing();

    }

}
