package project20280.hashtable;

import project20280.interfaces.Entry;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K, V>[] table;
    private final MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    public ProbeHashMap() {
        super();
        createTable();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ProbeHashMap(int cap) {
        super(cap);
        createTable();
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
    }

    int findSlot(int h, K k) {
        int i = h;
        int p = 0;
        while (p < capacity) {
            if (table[i % capacity] == DEFUNCT) {
                return i % capacity;
            }
            i++;
        }
        return -1;
    }

    @Override
    protected V bucketGet(int h, K k) {
        int i = h;
        int p = 0;
        V tmp;
        while (p < capacity) {
            if (table[i].getKey() == null) {
                return null;
            } else if (k == table[i].getKey()) {
                return table[i].getValue();
            } else {
                i = (i + 1) % capacity;
                p++;
            }
        }
        return null;
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        int i = h;
        int p = 0;
        V tmp;
        while (p < capacity) {
            if (table[i].getKey() == null) {
                table[i] = new MapEntry<>(k, v);
                return null;
            } else if (k == table[i].getKey()) {
                tmp = table[i].getValue();
                table[i].setValue(v);
                return tmp;
            } else {
                i = (i + 1) % capacity;
                p++;
            }
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
}
