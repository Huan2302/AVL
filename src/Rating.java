public class Rating {
    private Customer customer;
    private Product product;
    private int w;

    public Rating(Customer customer, Product product, int w) {
        this.customer = customer;
        this.product = product;
        this.w = w;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }
}
