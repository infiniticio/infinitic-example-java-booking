package example.booking.tasks.carRental;

public class CarRentalCart {
    private String cartId;

    public CarRentalCart(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }

    // code below is needed for serialization/deserialization

    public CarRentalCart() { super(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRentalCart that = (CarRentalCart) o;
        return cartId.equals(that.cartId);
    }

    @Override
    public int hashCode() {
        return cartId.hashCode();
    }
}
