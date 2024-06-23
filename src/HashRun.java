public class HashRun {
    public static void main(String[] args) {
        Person person1 = new Person("John", "Doe");
        Person person2 = new Person("Jane", "Doe");

        System.out.println("Person 1 hash code: " + person1.hashCode());
        System.out.println("Person 2 hash code: " + person2.hashCode());
        System.out.println("Are persons equal: " + person1.equals(person2));
    }
}


