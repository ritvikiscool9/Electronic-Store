//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.stream.Collectors;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private String name;
    private double revenue;
    private ArrayList<Product> allProducts;
    private  ArrayList<Product> displayProd;
    private Map<Product,Integer> cart;
    private int sales;
    private double total;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        allProducts = new ArrayList<>();

        cart = new HashMap<>();
        sales = 0;
        total = 0.0;

    }

    public String getName() {
        return name;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (allProducts.size() <= MAX_PRODUCTS) {
            allProducts.add(newProduct);
            popDisplayProd(allProducts);
            return true;
        }
        return false;
    }


    public void popDisplayProd(ArrayList<Product> list){ // This method ensures no products are being removed from the allProducts array. It keeps a copy of the original list so that the store can reset successfully
        displayProd = new ArrayList<>(list);
    }

    public static ElectronicStore createStore() {

        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");

        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }

    public ArrayList<Product> getProducts(){
        return allProducts;

    }

    public void addToCart(Product p){
        Integer quantity = cart.get(p);
        if(quantity == null){
           cart.put(p,1);
        }else{
            cart.put(p,quantity + 1);
        }
    }

    public List<Product> getTopSellingProduct(){
        List<Product> sortedProducts = new ArrayList<>(displayProd);
        sortedProducts.sort(new Comparator<Product>(){
           public int compare(Product p1, Product p2){ // I got this method from geeksforgeeks https://www.geeksforgeeks.org/how-to-sort-an-arraylist-of-objects-by-property-in-java/
               return Integer.compare(p2.getSoldAmount(), p1.getSoldAmount());
           }
        });
        return sortedProducts.subList(0, Math.min(3,sortedProducts.size()));
    }

    public void removeFromCart(Product p){
        Integer quantity = cart.get(p);
        if(quantity != null){
            if(quantity > 1){
                cart.put(p,quantity - 1);
            }else{
                cart.remove(p);
            }
        }
        if(p.getQuanInCart() == 0)
            cart.remove(p);
    }

    public void addSale(double saleAmount){
        sales++;
        revenue += saleAmount;
    }

    public int getSales(){
        return sales;
    }

    public double getRevenue(){
        return revenue;
    }

    public void addToTotal(Product p){
        total += p.getPrice();
    }

    public void decreaseTotal(Product p){
        total -= p.getPrice();
        if(total < 0){
            total = 0.0;
        }
    }

    public void resetTotal(){
        total = 0.0;
    }

    public double getTotal(){
        return total;
    }

    public void clearCart(){
        cart.clear();
    }

    public void setRevenue(double revenue){
        this.revenue = revenue;
    }

    public void setSales(int sales){
        this.sales = sales;
    }


    public void completeSale(){
        for(Map.Entry<Product, Integer> entry: cart.entrySet()){
            Product p = entry.getKey();
            int quantity = entry.getValue();
            p.increaseSoldAmount(quantity);
            p.decreaseStockQuantity(quantity);
            if(p.getTotalStockQuantity() == 0)
                p.setSoldAmount(10);
        }
        cart.clear();

    }

    public void resetStock(ElectronicStoreView view){
        ArrayList<Product> toView = new ArrayList<>();
        for(Product p : displayProd){
            p.setTotalStockQuantity(10);
            p.setQuanInCart(0);
            toView.add(p);
        }
        view.updateStoreStockView(toView);

    }

    public void resetSoldAmount(){
        for(Product p: displayProd){
            p.setSoldAmount(0);
        }
    }


















}
