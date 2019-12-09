package flashcards;

import java.util.Map;
import java.util.Objects;

public class Definition<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public Definition(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition<?, ?> entry = (Definition<?, ?>) o;
        return Objects.equals(getKey(), entry.getKey()); //&&Objects.equals(getValue(), entry.getValue());
    }

    @Override
    public int hashCode() {
        return getKey().hashCode(); //Objects.hash(getKey(), getValue());
    }
}
