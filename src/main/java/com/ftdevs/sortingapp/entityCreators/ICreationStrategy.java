package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.entities.Builder;

public interface ICreationStrategy {
    public String[] createEntities(String input, Class<? extends Builder> type);

    public String getMessage();
}
