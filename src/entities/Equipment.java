package entities;

public class Equipment {
    private int id;
    private String name;
    private String category;
    private int quantity;
    private int price;
    private String imageName;
    private int id_supp;

    public Equipment() {
    }

    public Equipment(int id, String name, String category, int quantity, int price,String imageName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.imageName = imageName;
    }

    public Equipment(String name, String category, int quantity, int price,String imageName) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.imageName = imageName;
    }
    
    public Equipment(String name, String category, int quantity, int price) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        
    }

    public Equipment(String name, String category, int quantity, int price, String imageName, int id_supp) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.imageName = imageName;
        this.id_supp = id_supp;
    }

    public int getId_supp() {
        return id_supp;
    }

    public void setId_supp(int id_supp) {
        this.id_supp = id_supp;
    }
    
    

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

   public double getPriceInDollars(double exchangeRate) {
    return price * exchangeRate;
}

  @Override
public String toString() {
    return "Equipment{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", category='" + category + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            ", imageName='" + imageName + '\'' +
            '}';
}

}
