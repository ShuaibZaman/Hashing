import java.util.LinkedList;

class HashNode<K, V> {
    K key;
    V value;

    // Reference to next node
    HashNode<K, V> next;

    // Constructor
    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

public class HashTable<K, V> {
    private LinkedList<HashNode<K, V>>[] chainArray;
    private int M = 11; // Prime number for the capacity of hash table
    private int size;

    // Constructor
    public HashTable() {
        chainArray = new LinkedList[M];
        size = 0;

        // Initialize each list in the array
        for (int i = 0; i < M; i++) {
            chainArray[i] = new LinkedList<>();
        }
    }

    private int hash(K key) {
        return key.hashCode() % M;
    }

    public void put(K key, V value) {
        int index = hash(key);
        LinkedList<HashNode<K, V>> chain = chainArray[index];

        for (HashNode<K, V> node : chain) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        chain.add(new HashNode<>(key, value));
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        LinkedList<HashNode<K, V>> chain = chainArray[index];

        for (HashNode<K, V> node : chain) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    public boolean remove(K key) {
        int index = hash(key);
        LinkedList<HashNode<K, V>> chain = chainArray[index];

        for (HashNode<K, V> node : chain) {
            if (node.key.equals(key)) {
                chain.remove(node);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("this", 1);
        hashTable.put("coder", 2);
        hashTable.put("this", 4);
        hashTable.put("hi", 5);

        System.out.println(hashTable.get("this"));  // Output: 4
        System.out.println(hashTable.get("coder")); // Output: 2
        System.out.println(hashTable.size());       // Output: 3
        System.out.println(hashTable.remove("this")); // Output: true
        System.out.println(hashTable.size());         // Output: 2
    }
}

