package infinitic.example.java.booking;

import io.infinitic.pulsar.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        InfiniticWorker.fromFile(args[0], "configs/infinitic.yml").start();
    }
}
