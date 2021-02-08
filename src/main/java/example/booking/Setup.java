package example.booking;

import io.infinitic.pulsar.InfiniticAdmin;
import org.apache.pulsar.client.api.PulsarClientException;

public class Setup {
    public static void main(String[] args) throws PulsarClientException {
        InfiniticAdmin infiniticAdmin = InfiniticAdmin.fromConfigFile("configs/infinitic.yml");

        infiniticAdmin.init();
        infiniticAdmin.close();
    }
}
