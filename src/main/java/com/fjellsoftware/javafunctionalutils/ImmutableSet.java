/*
 *    Copyright 2023 Fjell Software AS
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.fjellsoftware.javafunctionalutils;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * An immutable, unordered collection with no duplicates. It does not allow null element. It implements Iterable,
 * but does not support Iterator::remove(). It does not implement Serializable. Other than that, it behaves the same as
 * its underlying HashSet.
 * Thread safe.
 *
 * @param <E> the type of elements
 */
public class ImmutableSet<E> implements Iterable<E> {
    private final HashMap<E, Object> data;
    private static final Object dummy = new Object();

    /**
     * Creates an ImmutableSet containing the elements in the submitted iterable.
     * @param data the iterable whose elements will be present in the ImmutableSet
     * @throws NullPointerException if the specified iterable or any elements are null
     */
    public ImmutableSet(@NotNull Iterable<? extends E> data){
        Objects.requireNonNull(data);
        HashMap<E, Object> tmp = new HashMap<>();
        for (E datum : data) {
            Objects.requireNonNull(datum);
            tmp.put(datum, dummy);
        }
        this.data = tmp;
    }

    @SuppressWarnings("unchecked")
    ImmutableSet(HashMap<? extends E, ?> data, boolean shouldCopy){
        if(shouldCopy){
            this.data = new HashMap<>(data);
        }
        else {
            this.data = (HashMap<E, Object>) data;
        }
    }

    @SafeVarargs
    private ImmutableSet(E... entries){
        HashMap<E, Object> map = new HashMap<>();
        for (E entry : entries) {
            Objects.requireNonNull(entry);
            map.put(entry, dummy);
        }
        this.data = map;
    }

    private ImmutableSet(ImmutableSet<E> data, E dataToAppend){
        HashMap<E, Object> tmp = new HashMap<>(data.data);
        tmp.put(dataToAppend, dummy);
        this.data = tmp;
    }

    private ImmutableSet(ImmutableSet<E> data, Iterable<? extends E> dataToAppend){
        HashMap<E, Object> tmp = new HashMap<>(data.data);
        for (E e : dataToAppend) {
            Objects.requireNonNull(e);
            tmp.put(e, dummy);
        }
        this.data = tmp;
    }

    /**
     * Creates an ImmutableSet containing the elements submitted as arguments.
     * @param data the elements that will be present in the ImmutableSet
     * @return an ImmutableSet containing the elements provided
     * @throws NullPointerException if any elements are null
     */
    @SafeVarargs
    public static <E> @NotNull ImmutableSet<E> of(@NotNull E... data){
        Objects.requireNonNull(data);
        return new ImmutableSet<>(data);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static final ImmutableSet emptySet = new ImmutableSet(Collections.emptySet());

    /**
     * Returns an empty ImmutableSet.
     * @return an empty ImmutableSet
     */
    @SuppressWarnings("unchecked")
    public static <E> @NotNull ImmutableSet<E> empty(){
        return emptySet;
    }

    /**
     * Create and returns an ImmutableSet base on the elements in this, and adds the supplied element.
     * @param dataToAppend the element to include in the new ImmutableSet in addition to the current elements
     * @return the newly constructed ImmutableSet
     * @throws NullPointerException if the element is null
     */
    public @NotNull ImmutableSet<E> copyAndAdd(@NotNull E dataToAppend){
        Objects.requireNonNull(dataToAppend);
        return new ImmutableSet<>(this, dataToAppend);
    }

    /**
     * Create and returns an ImmutableSet base on the elements in this, and adds the supplied iterable of elements.
     * @param dataToAppend the elements to include in the new ImmutableSet in addition to the current elements
     * @return the newly constructed ImmutableSet
     * @throws NullPointerException if the iterable of any elements are null
     */
    public @NotNull ImmutableSet<E> copyAndAdd(Iterable<? extends E> dataToAppend){
        Objects.requireNonNull(dataToAppend);
        return new ImmutableSet<>(this, dataToAppend);
    }

    /**
     * Copies this ImmutableSet and returns it.
     * @return the copy of this ImmutableSet
     */
    public @NotNull Set<E> copyToSet(){
        return new HashSet<>(data.keySet());
    }

    /**
     * Returns the size of the ImmutableSet.
     * @return the size of the ImmutableSet
     */
    public int size(){
        return data.size();
    }

    /**
     * Returns true if the ImmutableSet contains no elements.
     * @return true if the ImmutableSet contains no elements
     */
    public boolean isEmpty(){
        return data.isEmpty();
    }

    /**
     * Returns true if this ImmutableSet contains the element supplied.
     * @param o The element whose presence in this ImmutableSet is to be tested
     * @return true if the supplied element is present
     * @throws NullPointerException if the element is null
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean contains(@NotNull Object o){
        Objects.requireNonNull(o);
        return data.containsKey(o);
    }

    /**
     * Returns an iterator over the elements in this ImmutableSet.
     * @return an iterator over the elements in this ImmutableSet
     */
    @Override
    public @NotNull Iterator<E> iterator(){
        return new ImmutableSetIterator();
    }

    private class ImmutableSetIterator implements Iterator<E> {
        Iterator<E> setIterator = data.keySet().iterator();
        ImmutableSetIterator(){}
        public boolean hasNext(){
            return setIterator.hasNext();
        }

        public @NotNull E next(){
            return setIterator.next();
        }
    }

    /**
     * Returns a Spliterator created by the underlying HashSet.
     * @return a Spliterator created by the underlying HashSet
     */
    @Override
    public @NotNull Spliterator<E> spliterator(){
        return data.keySet().spliterator();
    }

    /**
     * Creates and returns a string representation of the underlying data. Example representations:
     * "[]", "[1]", "[1, 2, 3]".
     * @return a string representation of the underlying data
     */
    @Override
    public @NotNull String toString(){
        return data.keySet().toString();
    }

    /**
     * Returns a hash code for this ImmutableSet using HashSet's hashCode method.
     * @return a hash code for this ImmutableSet
     */
    @Override
    public int hashCode(){
        return data.keySet().hashCode();
    }

    /**
     * Returns true if the supplied object to compare with is also an ImmutableSet and has the same underlying data.
     * @param o the object to compare with this ImmutableSet
     * @return true if the supplied object is an ImmutableSet and has the same underlying data
     */
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof ImmutableSet<?> immutableSet)){
            return false;
        }
        return data.keySet().equals(immutableSet.data.keySet());
    }
}
