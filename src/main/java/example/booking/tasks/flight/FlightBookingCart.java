package example.booking.tasks.flight;

import java.util.UUID;

public class FlightBookingCart {
    private UUID cartId = UUID.randomUUID();

    public UUID getCartId() {
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
