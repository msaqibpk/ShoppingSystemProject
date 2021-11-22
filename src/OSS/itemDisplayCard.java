package OSS;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class itemDisplayCard extends ListCell<String> {

    HBox hbox = new HBox();
    Label itemLabel = new Label("(empty)");
    Pane pane = new Pane();
    Button remButton = new Button("(REMOVE)");
    Button addButton = new Button("(ADD)");
    Label statusLabel = new Label("");
    String itemName;
    Item item;
    Stock host;
    ShoppingCart userCart;

    public itemDisplayCard(Stock host, ShoppingCart userCart) {
        super();
        this.host = host;
        this.userCart = userCart;
        hbox.getChildren().addAll(itemLabel, pane, addButton, remButton, statusLabel);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(host.removeFromStock(item)){
                    userCart.addToCart(item);
                    itemLabel.setText(String.format("%s: %s In Stock: %s In Cart: %s", item.getName(), item.getPrice(), host.matchedItem(item).getNumberOf(), userCart.matchedItem(item).getNumberOf()));
                    statusLabel.setText("");
                }
                else{
                    System.out.println("OUT OF STOCK");
                    statusLabel.setText("OUT OF STOCK");
                }
            }
        });
        remButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(userCart.removeFromCart(item)){
                    host.matchedItem(item).setNumberOf(host.matchedItem(item).getNumberOf() + 1);
                    itemLabel.setText(String.format("%s: %s In Stock: %s In Cart: %s", item.getName(), item.getPrice(), host.matchedItem(item).getNumberOf(), userCart.matchedItem(item).getNumberOf()));
                    statusLabel.setText("");
                }
                else{
                    System.out.println("NONE IN CART");
                    statusLabel.setText("NONE IN CART");
                }
            }
        });
    }

    @Override
    protected void updateItem(String thisItem, boolean empty) {
        super.updateItem(thisItem, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            itemName = null;
            setGraphic(null);
        } else {
            item = host.matchByID(thisItem);
            itemLabel.setText(String.format("%s: %s In Stock: %s In Cart: %s",
                    item.getName(), item.getPrice(), host.matchedItem(item).getNumberOf(),
                    userCart.matchedItem(item).getNumberOf()));
            setGraphic(hbox);
        }
    }

}