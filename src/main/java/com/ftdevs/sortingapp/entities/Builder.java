package com.ftdevs.sortingapp.entities;

import java.util.Arrays;
import java.util.Comparator;

public abstract class Builder<T> {

    protected boolean tryBuild(String input, String separator){

        var setters =  Arrays.stream(this.getClass().getMethods())
                .filter(method -> method.getName().contains("set"))
                .sorted(Comparator.comparing(method -> method.getAnnotation(MethodOrder.class).value())).toList();
        var values = input.split(separator);
        if(setters.size() != values.length){
            System.out.println(String.format("Неверное количество аргументов, необходимо: %d, введено: %d",
                    setters.size(),
                    values.length));
            return false;
        }

        try {
            for (int i = 0; i < setters.size(); i++)
            {
                setters.get(i).invoke(this, values[i]);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    public abstract T build(String input, String separator);
}
