public class Item {
    private double weight;
    private double price;

    public double getWeight(){return weight;}
    public void setWeight(double weight){this.weight = weight;}
    public double getPrice(){return price;}
    public void setPrice(double price) {this.price = price;}

    public Item(double weight, double price ){
        this.weight = weight;
        this.price = price;
    }
}
