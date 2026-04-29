package project20280.hashtable;

import project20280.interfaces.Entry;

import java.util.Arrays;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K, V>[] table;
    private final MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    public ProbeHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ProbeHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ProbeHashMap(int cap, int p) {
        super(cap, p);
    }

    @Override
    protected void createTable() {
        table = new MapEntry[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new MapEntry<>(null, null);
        }
    }

    @Override
    protected V bucketGet(int h, K k) {
        int i = h, p = 0;
        while (p < capacity) {
            System.out.println(table[i].getKey());
            if (table[i].getKey() == k) {
                return table[i].getValue();
            }
            i = (i + 1) % capacity;
            p++;
        }
        return null;
    }

    //This doesn't work despite the fact that it literally should,
    //I can't find anything wrong with it. The logic is sound, it is perfect
    //I hate java.
    @Override
    protected V bucketPut(int h, K k, V v) {
        int i = h, p = 0;
        while (p < capacity) {
            if (table[i].getKey() == null) {
                table[i] = new MapEntry<>(k, v);
                return null;
            } else if (table[i].getKey() == k) {
                V tmp = table[i].getValue();
                table[i] = new MapEntry<>(k, v);
                return tmp;
            }
            i = (i + 1) % capacity;
            p++;
        }
        return null;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        int i = h;
        int p = 0;
        V tmp;
        while (p < capacity) {
            if (table[i].getKey() == k) {
                tmp = table[i].getValue();
                table[i] = DEFUNCT;
                return tmp;
            }
            else {
                i = (i + 1) % capacity;
                p++;
            }
        }
        return null;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return null;
    }


    public static void main(String[] args) {
        ProbeHashMap<String, Integer> test = new ProbeHashMap<>();

        int n = 10;
        for (int i = 0; i < n; ++i) {
            test.put(Integer.toString(i), i);
        }
        for (int i = 0; i < n; ++i) {
            System.out.println(test.get(Integer.toString(i)));
        }
    }
}
