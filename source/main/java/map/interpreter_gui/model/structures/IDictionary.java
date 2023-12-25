package map.interpreter_gui.model.structures;

import java.util.Map;

public interface IDictionary<K, V> 
{
    Boolean containsKey(K key);
    Boolean containsValue(V value);
    V get(K key);
    void put(K key, V value);
    V remove(K key);
    String toString();
    String toStringKeySet();
    Map<K, V> getContent();
}
