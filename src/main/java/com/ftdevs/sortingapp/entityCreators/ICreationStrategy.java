package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.entities.Builder;

public interface ICreationStrategy {
    String[] createEntities(String input, Class<? extends Builder> type);

    String getMessage();
}
