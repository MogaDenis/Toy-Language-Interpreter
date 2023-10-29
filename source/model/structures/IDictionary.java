package source.model.structures;

public interface IDictionary<K, V> 
{
    public Boolean containsKey(K key);
    public Boolean containsValue(V value);    
    public V get(K key);
    public void put(K key, V value);
    public V remove(K key);
    public String toString();
}
