package OSS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.nio.Buffer;
import java.util.Objects;

public class Main extends Application {

    private TextField searchBar;
    private TextField customerName;
    private TextField cardInfo;
    private TextField cardExpiry;
    private TextField cardCVV;
    private TextField shippingStreet;
    private TextField shippingCity;
    private ChoiceBox shippingState;
    private TextField shippingCountry;
    private Label imaconLogo;
    Stage window;
    Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("unified.fxml")));
        primaryStage.setTitle("IMACON.com");
        mainScene = new Scene(root, 800.0, 500.0);
        primaryStage.setScene(mainScene);
        // Show the window.
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
//        messageResponseBuffer m = new messageResponseBuffer();
//        ShoppingCart cart = new ShoppingCart();
//        ossThread ossThreadDummy = new ossThread(m);
//        bankerThread btDummy = new bankerThread(m);

    }

    void displayMain(Customer user, Stock inventory, ShoppingCart cart, Parent root) {
        Scene stockDisplay = new Scene(root, 800, 400);
        for(Item items : inventory.getInventory()){

        }
    }

    void displayProductPage(Customer user, Item item, ShoppingCart cart, Parent root) {
        Scene itemDisplay = new Scene(root, 800, 400);
    }

    void displayCheckout(Customer user, ShoppingCart cart, Parent root) {
        Scene displayCheckout = new Scene(root, 800, 400);
    }


    //Action Method for createAcc(register)
    void registerCustomer(log customerLog) {
        TextField newUsername = new TextField("Username");
        TextField newPass = new TextField("Password");
        this.customerName = new TextField("First and Last Name");
        this.shippingStreet = new TextField("Home Address");
        //Button
        this.shippingCity = new TextField("City");
        this.shippingState = new ChoiceBox();
        this.shippingState.getItems().addAll("AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY");
        this.shippingCountry = new TextField("Country");
        TextField phoneNum = new TextField("Phone Number");
        this.cardInfo = new TextField("CC number");
        this.cardCVV = new TextField("CVV");
        this.cardExpiry = new TextField("Expiration date (MM/DD/YYYY)");
        Stage registerStage = new Stage();
        registerStage.setTitle("Create your Account!");


    }
    /*
    button.setMaxSize(100, 200) = button.setMaxWidth(100);
                                  button.setMaxHeight(200);
    button.setMinWidth()
    button.setMaxWidth()
    button.setPrefWidth()

    button.setMinHeight()
    button.setMaxHeight()
    button.setPrefHeight()

    button.setMinSize()
    button.setMaxSize()
    button.setPrefSize()
     */
    void registerShipping(Customer user, log customerLog, Parent root) {
        Scene shippingSetup = new Scene(root, 800, 400);
    }


}
