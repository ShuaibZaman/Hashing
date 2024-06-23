class OpenAddressingHashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 11;
    private static final double LOAD_FACTOR = 0.75;

    private int size;
    private int capacity;
    private OpenAddressingHashNode<K, V>[] table;

    static class OpenAddressingHashNode<K, V> {
        K key;
        V value;
        boolean deleted;

        OpenAddressingHashNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }

    @SuppressWarnings("unchecked")
    public OpenAddressingHashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = new OpenAddressingHashNode[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    private int probe(int hashValue, int i) {
        return (hashValue + i) % capacity;
    }

    public void insert(K key, V value) {
        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }

        int hashValue = hash(key);
        int i = 0;
        int index = probe(hashValue, i);
        while (table[index] != null && !table[index].deleted && i < capacity) {
            i++;
            index = probe(hashValue, i);
        }

        if (i >= capacity) {
            throw new RuntimeException("Hash table is full");
        }

        table[index] = new OpenAddressingHashNode<>(key, value);
        size++;
    }

    public V search(K key) {
        int hashValue = hash(key);
        int i = 0;
        int index = probe(hashValue, i);
        while (table[index] != null && i < capacity) {
            OpenAddressingHashNode<K, V> node = table[index];
            if (!node.deleted && node.key.equals(key)) {
                return node.value;
            }
            i++;
            index = probe(hashValue, i);
        }
        return null;
    }

    public boolean delete(K key) {
        int hashValue = hash(key);
        int i = 0;
        int index = probe(hashValue, i);
        while (table[index] != null && i < capacity) {
            OpenAddressingHashNode<K, V> node = table[index];
            if (!node.deleted && node.key.equals(key)) {
                node.deleted = true;
                size--;
                return true;
            }
            i++;
            index = probe(hashValue, i);
        }
        return false;
    }

    private void resize() {
        capacity = capacity * 2 + 1;
        size = 0;
        OpenAddressingHashNode<K, V>[] oldTable = table;
        table = new OpenAddressingHashNode[capacity];
        for (OpenAddressingHashNode<K, V> node : oldTable) {
            if (node != null && !node.deleted) {
                insert(node.key, node.value);
            }
        }
    }

    public void printTable() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && !table[i].deleted) {
                System.out.println("Index " + i + ": " + table[i].key + " -> " + table[i].value);
            } else {
                System.out.println("Index " + i + ": empty");
            }
        }
    }

    public static void main(String[] args) {
        OpenAddressingHashTable<String, Integer> hashTable = new OpenAddressingHashTable<>();

        hashTable.insert("apple", 1);
        hashTable.insert("banana", 2);
        hashTable.insert("cherry", 3);
        hashTable.insert("date", 4);
        hashTable.insert("elderberry", 5);

        System.out.println("Open Addressing Hash Table:");
        hashTable.printTable();

        System.out.println("\nSearch 'banana': " + hashTable.search("banana"));
        System.out.println("Search 'fig': " + hashTable.search("fig"));

        hashTable.delete("banana");
        System.out.println("\nAfter deleting 'banana':");
        hashTable.printTable();
    }
}
