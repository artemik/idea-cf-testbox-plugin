package idea.plugin.cf.testbox.enums;

import java.util.Arrays;
import java.util.Optional;

public enum HostOptions {
    LOCALHOST("http://localhost:8500/"),
    DOCKER_IP("http://192.168.99.100:8500/");

    private final String value;

    HostOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static Optional<HostOptions> fromValue(String value) {
        return Arrays.stream(HostOptions.values())
                .filter(hostOptions -> hostOptions.getValue().equals(value))
                .findFirst();
    }
}
