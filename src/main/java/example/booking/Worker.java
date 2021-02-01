package example.booking;

import io.infinitic.pulsar.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        String file = args.length > 0 ? args[0] :  "configs/all.yml";
        InfiniticWorker.fromFile(file, "configs/infinitic.yml").start();
    }
}
