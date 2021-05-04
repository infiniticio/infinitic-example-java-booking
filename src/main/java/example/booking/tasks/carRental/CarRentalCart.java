package example.booking.tasks.carRental;

import java.util.UUID;

public class CarRentalCart {
    private UUID cartId = UUID.randomUUID();

    public CarRentalCart(UUID cartId) {
        this.cartId = cartId;
    }

    public UUID getCartId() {
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
