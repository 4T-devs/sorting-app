package com.ftdevs.sortingapp.entities;

import com.ftdevs.sortingapp.validation.InputValidator;

public class Entity {
    private final Integer age;
    private final Float weight;
    private final Double height;

    public Entity(
            Integer age,
            Float weight,
            Double height) { // Конструктор создан только для теста, нет необходимости создавать
        // такой для реальной сущности
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("|%d |%f |%f", age, weight, height);
    }

    public boolean isEqual(final Object entity) {
        return this.toString().equals(entity.toString());
    }

    public Entity(final EntityBuilder builder) {
        age = builder.age;
        weight = builder.weight;
        height = builder.height;
    }

    public static class EntityBuilder extends Builder<Entity> {

        @FieldOrder(1)
        private Integer age;

        @FieldOrder(2)
        private Float weight;

        @FieldOrder(3)
        private Double height;

        @MethodOrder(1)
        public EntityBuilder setAge(final String age) {
            var input = InputValidator.tryParseInteger(age);
            this.age = input != null ? input : 0;
            return this;
        }

        @MethodOrder(2)
        public EntityBuilder setWeight(final String weight) {
            var input = InputValidator.tryParseFloat(weight);
            this.weight = input != null ? input : 0f;
            return this;
        }

        @MethodOrder(3)
        public EntityBuilder setHeight(final String height) {
            var input = InputValidator.tryParseDouble(height);
            this.height = input != null ? input : 0d;
            return this;
        }

        @Override
        public Entity build(final String input, final String separator) {
            if (tryBuild(input, separator)) return new Entity(this);
            else return null;
        }
    }
}
