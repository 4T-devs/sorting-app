package org.example.entityCreators;

import org.example.entities.Builder;

public interface ICreationStrategy {
    public String[] createEntities(String input, Class<? extends Builder> type);

    public String getMessage();
}
