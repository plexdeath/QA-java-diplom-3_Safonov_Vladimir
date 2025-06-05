package Utils;

import java.util.UUID;

public class DataTests {
    private final String name;
    private final String email;
    private final String password;

    public DataTests() {
        this.name = "SuperName" + UUID.randomUUID();
        this.email = "super" + UUID.randomUUID() + "@ya.ru";
        this.password = "NaGorshkeSidelK@rol" + UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
