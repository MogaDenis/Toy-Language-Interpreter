package source.model.types;

import source.model.values.Value;

public interface Type 
{
    Value defaultValue();

    Type deepCopy();
}
