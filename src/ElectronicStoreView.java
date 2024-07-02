import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ElectronicStoreView extends Pane {
    private double amountSpent;
    private double totalValue;
    private ElectronicStore store;
    private Map<Product, Integer> cart;
    private Label cartLabel;
    private Label sales;
    private Label revenue;
    private Label dollars;
    private Label popItems;
    private TextField salesText;
    private TextField revenueText;
    private TextField dollarsText;
    private ListView<Product> stockView;
    private ListView<String> cartView;
    private ListView<Product> popItemsView;
    private Button add;
    private Button complete;
    private Button remove;
    private Button reset;
    private double total;


    public ElectronicStoreView(ElectronicStore store) {
        this.store = store;
        cart = new HashMap<>();
        total = 0.0;

        //Creating Labels
        Label summaryLabel = new Label("Store Summary:");
        summaryLabel.relocate(30, 20);

        Label stockLabel = new Label("Store Stock:");
        stockLabel.relocate(290, 20);

        cartLabel = new Label("Current Cart ($" + String.format("%.2f",total) + ")");
        cartLabel.relocate(600, 20);

        sales = new Label("# Sales:");
        sales.relocate(22, 45);

        revenue = new Label("Revenue:");
        revenue.relocate(15, 75);

        dollars = new Label("$ / Sale:");
        dollars.relocate(20, 105);

        popItems = new Label("Most Popular Items:");
        popItems.relocate(30, 145);

        //Creating TextFields
        salesText = new TextField();
        salesText.relocate(65, 43);
        salesText.setPrefSize(100, 20);
        salesText.setText("0");

        revenueText = new TextField();
        revenueText.relocate(65, 71);
        revenueText.setPrefSize(100, 20);
        revenueText.setText("0.00");

        dollarsText = new TextField();
        dollarsText.relocate(65, 100);
        dollarsText.setPrefSize(100, 20);
        dollarsText.setText("N/A");

        //Creating ListViews
        popItemsView = new ListView<Product>();
        popItemsView.relocate(10, 170);
        popItemsView.setPrefSize(155, 170);
        popItemsView.setItems(FXCollections.observableArrayList((store.getProducts()).subList(0, 3)));

        stockView = new ListView<Product>();
        stockView.relocate(175, 43);
        stockView.setPrefSize(300, 297);
        stockView.setItems(FXCollections.observableList(store.getProducts()));

        cartView = new ListView<String>();
        cartView.relocate(485, 43);
        cartView.setPrefSize(300, 297);

        //Creating Buttons
        reset = new Button("Reset Store");
        reset.relocate(25, 345);
        reset.setPrefSize(150, 40);

        add = new Button("Add to Cart");
        add.relocate(255, 345);
        add.setPrefSize(150, 40);
        add.setDisable(true);


        remove = new Button("Remove from Cart");
        remove.relocate(485, 345);
        remove.setPrefSize(150, 40);
        remove.setDisable(true);

        complete = new Button("Complete Sale");
        complete.relocate(635, 345);
        complete.setPrefSize(150, 40);
        complete.setDisable(true);


        getChildren().addAll(summaryLabel, stockLabel, cartLabel, sales, revenue, dollars, popItems, salesText, revenueText, dollarsText, popItemsView, stockView, cartView, reset, add, remove, complete);
    }



    public void updatePopView(ElectronicStore model) {
        List<Product> topSellingProducts = model.getTopSellingProduct();

        popItemsView.getItems().clear();
        for (Product product : topSellingProducts) {
            popItemsView.getItems().add(product);
        }
    }

    public void updateCartView(){
        getCartView().getItems().clear();
        for (Map.Entry<Product, Integer> entry : getCart().entrySet()) {
            Product product = entry.getKey();
            String item = product.getQuanInCart() + " x " + product.toString();
            getCartView().getItems().add(item);
        }
    }

    public void updateStoreStockView(ArrayList<Product> displayProd){
        stockView.setItems(FXCollections.observableList(displayProd));

    }

    public ListView<Product> getStoreStock(){
        return stockView;
    }
    public ListView<String> getCartView(){
        return cartView;
    }
    public ListView<Product> getPopItemsView(){
        return popItemsView;
    }


    public Button getAdd(){
        return add;
    }
    public Button getComplete(){
        return complete;
    }
    public Button getRemove(){
        return remove;
    }
    public Button getReset(){
        return reset;
    }
    public Map<Product,Integer> getCart(){
        return cart;
    }

    public void calculateTotal(Product p){
        store.addToTotal(p);
        updateCartLabel();
    }

    public void decreaseTotal(Product p){
        store.decreaseTotal(p);
        updateCartLabel();
    }

    public void resetTotal() {
        store.resetTotal();
        updateCartLabel();
    }

    public void updateCartLabel(){
        total = store.getTotal();
        cartLabel.setText("Current Cart ($" + String.format("%.2f",total)+ "):");
    }

    public void updateSales(int saleVal){
        salesText.setText(String.valueOf(saleVal));
    }

    public void updateRevenue(double revenueVal){
        revenueText.setText(String.format("%.2f", revenueVal));
    }

    public void updateDollars(double dollarsVal){
        if(dollarsVal != 0){
            dollarsText.setText(String.format("%.2f", dollarsVal));
        }else{
            dollarsText.setText("N/A");
        }
    }

    public void resetGUI(){
        cartView.getItems().clear();
        resetTotal();
        salesText.setText("0");
        revenueText.setText("0.00");
        dollarsText.setText("N/A");
        popItemsView.setItems(FXCollections.observableArrayList((store.getProducts()).subList(0, Math.min(3,store.getProducts().size()))));
    }









}
