import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;


public class ElectronicStoreApp extends Application {
    private ElectronicStore model;
    private ElectronicStoreView view;

    public void start(Stage primaryStage){
        model = ElectronicStore.createStore();
        view = new ElectronicStoreView(model);

        view.getStoreStock().setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event){
                if(view.getStoreStock().getSelectionModel().getSelectedItem() != null)
                    view.getAdd().setDisable(false);
            }
        });

        view.getAdd().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Product selectedProduct = view.getStoreStock().getSelectionModel().getSelectedItem();
                if(selectedProduct != null){
                        selectedProduct.setQuanInCart(selectedProduct.getQuanInCart() + 1);
                        view.calculateTotal(selectedProduct);
                        model.addToCart(selectedProduct);
                        if (selectedProduct.getQuanInCart() >= selectedProduct.getTotalStockQuantity())
                            view.getStoreStock().getItems().remove(selectedProduct);
                        view.getCart().put(selectedProduct, view.getCart().getOrDefault(selectedProduct, 0) + 1);
                        view.updateCartView();

                }
                if(!view.getCartView().getItems().isEmpty())
                    view.getComplete().setDisable(false);
                view.getRemove().setDisable(false);

            }
        });

        view.getRemove().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                String selectedItem = view.getCartView().getSelectionModel().getSelectedItem();
                if(selectedItem != null){
                    String productName = selectedItem.substring(selectedItem.indexOf(" ") + 3);
                    Product productToRemove = null;
                    for(Product p : model.getProducts()){
                        if(p.toString().equals(productName)){
                            productToRemove = p;
                            break;
                        }
                    }
                    if(productToRemove != null) {
                        model.removeFromCart(productToRemove);
                        productToRemove.returnUnits();
                        productToRemove.setQuanInCart(productToRemove.getQuanInCart() - 1);
                        view.decreaseTotal(productToRemove);
                        if(productToRemove.getQuanInCart() == 0)
                            view.getCart().remove(productToRemove);
                    }
                    view.updateCartView();
                }

            }
        });

        view.getComplete().setOnAction(new EventHandler<ActionEvent>(){
           public void handle(ActionEvent actionEvent){
               if(!view.getCart().isEmpty()){
                   double sale = 0.0;
                   for(Map.Entry<Product, Integer> entry: view.getCart().entrySet()){
                       Product p = entry.getKey();
                       int quantity = entry.getValue();
                       if(p.getQuanInCart() > 0){
                           sale += p.sellUnits(quantity);
                       }
                       p.setQuanInCart(0);
                   }
                   view.getComplete().setDisable(true);
                   view.getRemove().setDisable(true);
                   model.completeSale();
                   model.addSale(sale);
                   view.getCart().clear();
                   model.clearCart();
                   view.getCartView().getItems().clear();
                   view.updateSales(model.getSales());
                   view.updateRevenue(model.getRevenue());
                   view.updateDollars(model.getRevenue() / model.getSales());
                   view.resetTotal();
                   view.updatePopView(model);
               }
           }
        });

        view.getReset().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.setRevenue(0.0);
                //model.clearCart();
                model.resetSoldAmount();
                model.setSales(0);
                model.resetTotal();
                view.resetGUI();
                model.resetStock(view);
                view.getCart().clear();
            }
        });
        
        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setScene(new Scene(view, 800,400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);
    }


}

