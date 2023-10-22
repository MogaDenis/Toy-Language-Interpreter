package source.model.types;

public class CharType implements Type
{
    @Override
    public boolean equals(Object anotherObject)
    {
        if (anotherObject instanceof CharType)
            return true;

        return false;
    }

    @Override
    public String toString()
    {
        return "char";
    }
}
