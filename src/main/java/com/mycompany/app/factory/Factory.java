package com.mycompany.app.factory;

@FunctionalInterface
public interface Factory<T> {

    T create();
}
