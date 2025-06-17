package com.herrera.behavioural.observer.contracts;

public interface Observer<T> {

    void received();

    void received(T payload);
}
