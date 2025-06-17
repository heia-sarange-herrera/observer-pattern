package com.herrera.behavioural.observer.abstracts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.herrera.behavioural.observer.contracts.Observer;

/**
 * Abstract implementation of a Subject in the Observer Pattern.
 * <p>
 * This class manages a list of observers and a generic payload.
 * It provides various utilities for attaching, detaching, and
 * notifying observers.
 *
 * @param <T> The type of payload being observed.
 */
public abstract class Subject<T> {

    /** The current data/payload being tracked or shared. */
    protected T payload;

    /** The set of subscribed observers. */
    protected Set<Observer<T>> observers = new HashSet<>();

    /** Default constructor. Initializes an empty observer set. */
    public Subject() {
    }

    /**
     * Initializes the subject with a payload.
     *
     * @param payload The data to be tracked or shared with observers.
     */
    public Subject(T payload) {
        this.payload = payload;
    }

    /**
     * Initializes the subject with a payload and one observer.
     *
     * @param payload  The data to be tracked or shared.
     * @param observer A single observer to attach.
     */
    public Subject(T payload, Observer<T> observer) {
        this.payload = payload;
        this.observers.add(observer);
    }

    /**
     * Initializes the subject with a payload and multiple observers.
     *
     * @param payload   The data to be tracked or shared.
     * @param observer  A varargs array of observers to attach.
     */
    public Subject(T payload, Observer<T>... observer) {
        this.payload = payload;
        this.attachMany(observer);
    }

    /**
     * Attaches a single observer to the subject.
     *
     * @param observer The observer to attach.
     */
    public void attach(Observer<T> observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    /**
     * Attaches multiple observers at once.
     *
     * @param observers A varargs array of observers to attach.
     */
    public void attachMany(Observer<T>... observers) {
        if (observers != null) {
            for (Observer<T> o : observers) {
                if (o != null) {
                    this.observers.add(o);
                }
            }
        }
    }

    /**
     * Detaches a specific observer from the subject.
     *
     * @param observer The observer to remove.
     */
    public void detach(Observer<T> observer) {
        if (observer != null) {
            this.observers.remove(observer);
        }
    }

    /**
     * Gets the current payload.
     *
     * @return The payload being observed.
     */
    public T getPayload() {
        return payload;
    }

    /**
     * Sets the payload to a new value.
     *
     * @param payload The new data to be observed.
     */
    public void setPayload(T payload) {
        this.payload = payload;
    }

    /**
     * Clears all observers from the subject.
     */
    public void clearObservers() {
        this.observers.clear();
    }

    /**
     * Returns the number of observers currently attached.
     *
     * @return The observer count.
     */
    public int countObservers() {
        return observers.size();
    }

    /**
     * Checks if a specific observer is currently attached.
     *
     * @param observer The observer to check for.
     * @return true if present, false otherwise.
     */
    public boolean containsObserver(Observer<T> observer) {
        return observers.contains(observer);
    }

    /**
     * Notifies all observers of the current payload.
     */
    public void broadcast() {
        if (haveObserver()) {
            this.observers.forEach(this::notifyObserver);
        }
    }

    /**
     * Notifies all observers except one.
     *
     * @param exclude The observer to skip.
     */
    public void multicast(Observer<T> exclude) {
        if (haveObserver()) {
            this.observers.stream()
                    .filter(o -> !o.equals(exclude))
                    .forEach(this::notifyObserver);
        }
    }

    /**
     * Notifies all observers except those specified.
     *
     * @param exclude A varargs array of observers to exclude.
     */
    public void multicast(Observer<T>... exclude) {
        if (haveObserver()) {
            var excludedList = Arrays.asList(exclude);
            this.observers.stream()
                    .filter(o -> !excludedList.contains(o))
                    .forEach(this::notifyObserver);
        }
    }

    /**
     * Sends a payload update only to the specified observer.
     *
     * @param observer The observer to notify.
     */
    public void whisper(Observer<T> observer) {
        if (observer != null) {
            notifyObserver(observer);
        }
    }

    /**
     * Utility method to check if any observers are present.
     *
     * @return true if there are observers, false otherwise.
     */
    public boolean haveObserver() {
        return !this.observers.isEmpty();
    }

    /**
     * Utility method to check if a payload exists.
     *
     * @return true if the payload is not null.
     */
    public boolean havePayload() {
        return this.payload != null;
    }

    /**
     * Notifies a specific observer of the payload.
     * Will call either `received(payload)` or `received()` depending on availability.
     *
     * @param o The observer to notify.
     */
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
