package infinitic.example.java.booking.tasks.hotel;

public class HotelBookingCart {
    private String cartId;

    public HotelBookingCart(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }

    // code below is needed for serialization/deserialization

    public HotelBookingCart() { super(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelBookingCart that = (HotelBookingCart) o;
        return cartId.equals(that.cartId);
    }

    @Override
    public int hashCode() {
        return cartId.hashCode();
    }
}