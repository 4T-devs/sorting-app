package com.ftdevs.sortingapp.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BuilderTest {

    private BuilderTest() {}

    @Test
    void builderTest() {
        final String input = "1,2.22,3.33";

        final var entity = new Entity.EntityBuilder().build(input, ",");
        final Entity entity1 = new Entity(1, 2.22f, 3.33d);

        assertTrue(
                entity1.isEqual(entity),
                String.format("%s\nequals\n%s", entity1.toString(), entity.toString()));
    }
}
