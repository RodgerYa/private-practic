import java.util.Optional;

class User {
    int id;
    String name;
    Optional<Integer> sex;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.sex = Optional.empty();
    }

    public User(String name, int id, int sex) {
        this.name = name;
        this.id = id;
        this.sex = Optional.of(sex);
    }

    public User() {

    }
}