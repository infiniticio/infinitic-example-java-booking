package example.booking;

import example.booking.tasks.carRental.CarRentalServiceFake;
import io.infinitic.workers.InfiniticWorker;

import java.io.IOException;

public class Worker {
    public static void main(String[] args) throws IOException {
        String file = args.length > 0 ? args[0] : "/configs/all.yml";

        try (InfiniticWorker worker = InfiniticWorker.fromConfigResource(file, "/configs/infinitic.yml")) {
            worker.start();
        }
    }
}
