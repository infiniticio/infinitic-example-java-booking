package example.booking.tasks.flight;

public class FlightBookingCart {
    private String cartId;

    public FlightBookingCart(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }

    // code below is needed for serialization/deserialization

    public FlightBookingCart() { super(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightBookingCart that = (FlightBookingCart) o;
        return cartId.equals(that.cartId);
    }

    @Override
    public int hashCode() {
        return cartId.hashCode();
    }
}
