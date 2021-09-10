package example.booking;

import io.infinitic.factory.InfiniticWorkerFactory;
import io.infinitic.worker.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        String file = args.length > 0 ? args[0] : "configs/all.yml";

        try (InfiniticWorker worker = InfiniticWorkerFactory.fromConfigFile(file, "configs/infinitic.yml")) {
            worker.start();
        }
    }
}
