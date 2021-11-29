

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class itemDisplayCard extends ListCell<String> {

    HBox hbox = new HBox();
    Label itemLabel = new Label("(empty)");
    Pane pane = new Pane();
    Label statusLabel = new Label("");
    String itemName;
    Item item;
    CashRegister userCart;

    public itemDisplayCard(Stock host, CashRegister userCart) {
        super();
        this.userCart = userCart;
        hbox.getChildren().addAll(itemLabel, pane, statusLabel);
    }

    @Override
    protected void updateItem(String thisItem, boolean empty) {
        super.updateItem(thisItem, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            itemName = null;
            setGraphic(null);
        } else {
            item = userCart.matchedItem(thisItem);
            itemLabel.setText(String.format("%s: $%s In Cart: %s Total: $%s", item.getName(), item.getPrice(),
                    item.getNumberOf(), (item.getPrice() * item.getNumberOf())));
            setGraphic(hbox);
        }
    }

}