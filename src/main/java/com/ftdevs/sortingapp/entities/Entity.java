package org.example.entities;

import org.example.validation.InputValidator;

public class Entity {
    public Integer age;
    public Float weight;
    public Double height;

    @Override
    public String toString(){
        return String.format("|%d |%f |%f", age, weight, height);
    }
    public Entity(EntityBuilder builder){
        age = builder.age;
        weight = builder.weight;
        height = builder.height;
    }
    public static class EntityBuilder extends Builder<Entity>{

        @FieldOrder(1)
        private Integer age;
        @FieldOrder(2)
        private Float weight;
        @FieldOrder(3)
        private Double height;

        @MethodOrder(1)
        public EntityBuilder setAge(String age){
            var input = InputValidator.tryParseInteger(age);
            this.age = input != null ? input : 0;
            return this;
        }
        @MethodOrder(2)
        public EntityBuilder setWeight(String weight){
            var input = InputValidator.tryParseFloat(weight);
            this.weight = input != null ? input : 0f;
            return this;
        }
        @MethodOrder(3)
        public EntityBuilder setHeight(String height){
            var input = InputValidator.tryParseDouble(height);
            this.height = input != null ? input : 0d;
            return this;
        }

        @Override
        public Entity build(String input, String separator){
            if(tryBuild(input, separator))
                return new Entity(this);
            else
                return null;
        }
    }
}
