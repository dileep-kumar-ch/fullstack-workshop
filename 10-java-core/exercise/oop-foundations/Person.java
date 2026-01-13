package oops;

public class  Person {

    private String name;
    private int age;
    private String email;

    // Constructor
    public Person(String name, int age, String email) {
        this.name = name;
        setAge(age);
        setEmail(email);
    }

    // Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 150) {
            System.out.println("Invalid age. Age must be between 0 and 150.");
            return;
        }
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            System.out.println("Invalid email address.");
            return;
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", email=" + email + "]";
    }

    // Main method
    public static void main(String[] args) {
        Person p = new Person("Dileep", 24, "dileepkumar.chukkala@gmail.com");
        System.out.println(p);
    }
}
