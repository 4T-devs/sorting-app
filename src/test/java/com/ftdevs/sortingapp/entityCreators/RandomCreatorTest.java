package com.ftdevs.sortingapp.entityCreators;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ftdevs.sortingapp.entities.Entity;
import org.junit.jupiter.api.Test;

final class RandomCreatorTest {

    private RandomCreatorTest() {}

    @Test
    void createEntitiesTest() {
        String input = "1";

        RandomCreator creator = new RandomCreator();
        var result = creator.createEntities(input, Entity.EntityBuilder.class);

        assertSame(
                Entity.EntityBuilder.class.getDeclaredFields().length,
                result[0].split(",").length,
                "Количество полей сгенерированных данных совпадает с количеством полей класса");
    }
}
