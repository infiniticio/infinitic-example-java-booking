package example.booking;

import io.infinitic.pulsar.PulsarInfiniticAdmin;

public class Setup {
    public static void main(String[] args) {
        PulsarInfiniticAdmin admin = PulsarInfiniticAdmin.fromConfigFile("configs/infinitic.yml");

        admin.setupPulsar();
        admin.close();
    }
}
