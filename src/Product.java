import java.util.Comparator;


//Base class for all products the store will sell
public abstract class Product {
    private double price;
    //private int stockQuantity;
    private int quanInCart;
    private int soldAmount;
    private int totalStockQuantity;


    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        totalStockQuantity = initQuantity;

    }

    public double sellUnits(int amount) {
        if (amount > 0 && totalStockQuantity >= amount) {
            return price * amount;
        }
        return 0.0;
    }

    public int getTotalStockQuantity(){
        return totalStockQuantity;
    }

    public void setTotalStockQuantity(int quantity){
        totalStockQuantity = quantity;
    }

    public void decreaseStockQuantity(int quantity){
        totalStockQuantity -= quantity;
        if(totalStockQuantity < 0)
            totalStockQuantity = 0;
    }

    public int getSoldQuantity() {
        return soldAmount;
    }

    public double getPrice() {
        return price;
    }

    public int getQuanInCart(){
        return quanInCart;
    }

    public void setQuanInCart(int quantity){
        quanInCart = quantity;
    }

    public void increaseSoldAmount(int quantity){
        soldAmount += quantity;
    }

    public void setSoldAmount(int quantity){
        soldAmount = quantity;
    }

    public int getSoldAmount(){
        return soldAmount;
    }

    public void returnUnits(){
        totalStockQuantity += 1;
    }
}