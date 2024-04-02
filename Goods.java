public class Goods {
    private String name;
    private float price;
    private int life;
    private String productDate;
    private float discount;

    public Goods(String name, float price, int life, String productDate) {
        this.name = name;
        this.price = price;
        this.life = life;
        this.productDate = productDate;
    }
    public Goods(String name,float discount){
        this.name = name;
        this.discount = discount;
    }

    public Goods() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
