package com.herrera.behavioural.observer.abstracts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.herrera.behavioural.observer.contracts.Observer;

public abstract class Subject<T> {

    // properties
    protected T payload;
    protected Set<Observer<T>> observers = new HashSet<>();

    // constructors
    public Subject() {
    };

    // initialized payload
    public Subject(T payload) {
        this.payload = payload;
    }

    // initialize payload and add observer
    public Subject(T payload, Observer<T> observer) {
        this.payload = payload;
        this.observers.add(observer);
    }

    public Subject(T payload, Observer<T>... observer) {
        this.payload = payload;
        this.attachMany(observer);
    }

    // methods
    public void attach(Observer<T> observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void attachMany(Observer<T>... observers) {
        if (observers != null) {
            for (Observer<T> o : observers) {
                if (o != null) {
                    this.observers.add(o);
                }
            }
        }
    }

    public void detach(Observer<T> observer) {
        if (observer != null) {
            this.observers.remove(observer);
        }
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public void clearObservers() {
        this.observers.clear();
    }

    public int countObservers() {
        return observers.size();
    }

    public boolean containsObserver(Observer<T> observer) {
        return observers.contains(observer);
    }

    // notify observer
    // broadcast update
    public void broadcast() {
        if (haveObserver()) {
            this.observers.forEach(this::notifyObserver);
        }
    }

    public void multicast(Observer<T> exclude) {
        if (haveObserver()) {
            this.observers.stream()
                    .filter(o -> !o.equals(exclude))
                    .forEach(this::notifyObserver);
        }
    }

    public void multicast(Observer<T>... exclude) {
        if (haveObserver()) {
            var excludedList = Arrays.asList(exclude);
            this.observers.stream()
                    .filter(o -> !excludedList.contains(o))
                    .forEach(this::notifyObserver);
        }
    }

    public void whisper(Observer<T> observer) {
        if (observer != null) {
            notifyObserver(observer);
        }
    }

    // utility
    public boolean haveObserver() {
        return !this.observers.isEmpty();
    }

    public boolean havePayload() {
        return this.payload != null;
    }

    private void notifyObserver(Observer<T> o) {
        try {
            if (havePayload()) {
                o.received(payload);
            } else {
                o.received();
            }
        } catch (Exception e) {
            System.err.println("Observer notification failed: " + e.getMessage());
        }
    }

}
