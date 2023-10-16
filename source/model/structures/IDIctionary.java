package source.model.structures;

public interface IDIctionary<TKey, TValue> 
{
    public Boolean containsKey(TKey key);
    public Boolean containsValue(TValue value);    
    public TValue get(TKey key);
    public void put(TKey key, TValue value);
    public TValue remove(TKey key);
}
