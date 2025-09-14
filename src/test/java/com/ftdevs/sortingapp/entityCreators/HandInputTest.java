package com.ftdevs.sortingapp.entityCreators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ftdevs.sortingapp.IOSingleton;
import com.ftdevs.sortingapp.entities.Entity;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

final class HandInputTest {

    private HandInputTest() {}

    @Test
    void handInputTest() {

        String input = "2";
        String iStream = "1 2.22 3.33\n2 4.44 5.25";
        InputStream testInput = new ByteArrayInputStream(iStream.getBytes());

        IOSingleton.getInstance().setOutput(System.out);
        IOSingleton.getInstance().setInput(testInput);

        HandInput hi = new HandInput();
        var result = hi.createEntities(input, Entity.EntityBuilder.class);
        assertTrue(
                Arrays.deepEquals(iStream.split("\n"), result), "Input are the same as the output");
    }
}
