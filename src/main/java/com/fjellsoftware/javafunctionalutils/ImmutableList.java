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
 * An immutable ordered collection. Duplicate elements are allowed, nulls are not. It implements Iterable, but does
 * not support Iterator::remove(). It does not implement Serializable. Other than that, it behaves the same as its
 * underlying ArrayList.
 * Thread safe.
 *
 * @param <E> the type of elements
 */
public class ImmutableList<E> implements Iterable<E> {
    private final ArrayList<E> data;

    /**
     * Creates an ImmutableList containing the elements in the submitted iterable, in the order they are returned by
     * its iterator.
     * @param data the iterable whose elements will be present in the ImmutableList
     * @throws NullPointerException if the specified iterable or any elements are null
     */
    public ImmutableList(@NotNull Iterable<? extends E> data){
        NullUtils.requireAllNonNullIterable(data);
        ArrayList<E> objects = new ArrayList<>();
        for (E datum : data) {
            objects.add(datum);
        }
        this.data = objects;
    }

    private ImmutableList(@NotNull ImmutableList<? extends E> data, @NotNull E dataToAppend){
        NullUtils.requireAllNonNullIterable(data);
        Objects.requireNonNull(dataToAppend);
        ArrayList<E> tmp = new ArrayList<>(data.data);
        tmp.add(dataToAppend);
        this.data = tmp;
    }

    private ImmutableList(@NotNull ImmutableList<? extends E> data, @NotNull Iterable<? extends E> dataToAppend){
        Objects.requireNonNull(data);
        NullUtils.requireAllNonNullIterable(dataToAppend);
        ArrayList<E> tmp = new ArrayList<>(data.data);
        for (E e : dataToAppend) {
            tmp.add(e);
        }
        this.data = tmp;
    }

    private ImmutableList(@NotNull E element){
        Objects.requireNonNull(element);
        ArrayList<E> tmp = new ArrayList<>(1);
        tmp.add(element);
        this.data = tmp;
    }

    /**
     * Creates an ImmutableList containing the submitted element.
     * @param element the element that will be present in the ImmutableList
     * @return an ImmutableList containing only the element provided
     * @throws NullPointerException if the specified element is null
     */
    public static <T> @NotNull ImmutableList<T> singletonList(@NotNull T element){
        return new ImmutableList<>(element);
    }

    /**
     * Creates an ImmutableList containing the elements submitted as arguments, in the order they are supplied.
     * @param data the elements that will be present in the ImmutableList
     * @return an ImmutableList containing the elements provided.
     * @throws NullPointerException if any elements are null
     */
    @SafeVarargs
    public static <T> @NotNull ImmutableList<T> of(@NotNull T... data){
        return new ImmutableList<>(Arrays.asList(data));
    }

    /**
     * Returns an empty ImmutableList.
     * @return an empty ImmutableList
     */
    @SuppressWarnings("unchecked")
    public static <T> @NotNull ImmutableList<T> empty(){
        return (ImmutableList<T>) empty;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static final ImmutableList empty = new ImmutableList(new ArrayList(0));

    /**
     * Creates and returns an ImmutableList based on the elements in this, and adds the supplied element to the end of
     * the sequence.
     * @param dataToAppend the element to include in the new ImmutableList in addition to the current elements
     * @return the newly constructed ImmutableList
     * @throws NullPointerException if the element is null
     */
    public @NotNull ImmutableList<E> copyAndAdd(@NotNull E dataToAppend){
        return new ImmutableList<>(this, dataToAppend);
    }

    /**
     * Creates and returns an ImmutableList based on the elements in this and adds the supplied iterable of elements
     * to the end of the sequence, in the order they are returned by the iterator.
     * @param dataToAppend the elements to include in the new ImmutableList in addition to the current elements.
     * @return the newly constructed ImmutableList
     * @throws NullPointerException if the iterable or any elements are null
     */
    public @NotNull ImmutableList<E> copyAndAdd(@NotNull Iterable<? extends E> dataToAppend){
        return new ImmutableList<>(this, dataToAppend);
    }

    /**
     * Copies this ImmutableList and returns it.
     * @return the copy of this ImmutableList
     */
    public @NotNull List<E> copyToList(){
        return new ArrayList<>(data);
    }

    /**
     * Returns the size of the ImmutableList.
     * @return the size of the ImmutableList
     */
    public int size(){
        return data.size();
    }

    /**
     * Returns true if this ImmutableList contains no elements.
     * @return true if this ImmutableList contains no elements
     */
    public boolean isEmpty(){
        return data.isEmpty();
    }

    /**
     * Returns true if this ImmutableList contains the element supplied.
     * @param o The element whose presence in this ImmutableList is to be tested
     * @return true if the supplied element is present
     * @throws NullPointerException if the element is null
     */
    public boolean contains(@NotNull E o){
        Objects.requireNonNull(o);
        return data.contains(o);
    }

    /**
     * Checks if the supplied element is present in this ImmutableList. If it is, it returns the index, otherwise
     * returns -1.
     * @param o The element whose presence in this list is to be tested
     * @return The index of the element if it is present, otherwise -1
     * @throws NullPointerException if the element is null
     */
    public int indexOf(@NotNull E o){
        Objects.requireNonNull(o);
        return data.indexOf(o);
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index index of the element to return
     * @return the element at the supplied position
     * @throws IndexOutOfBoundsException if the position supplied is out of range
     */
    public @NotNull E get(int index) throws IndexOutOfBoundsException{
        return data.get(index);
    }

    /**
     * Returns an iterator over the elements in this ImmutableList.
     * @return an iterator over the elements in this ImmutableList
     */
    @Override
    public @NotNull Iterator<E> iterator() {
        return new ImmutableListIterator();
    }

    /**
     * Returns a Spliterator created by the underlying ArrayList.
     * @return a Spliterator created by the underlying ArrayList
     */
    @Override
    public @NotNull Spliterator<E> spliterator(){
        return data.spliterator();
    }

    /**
     * Creates and returns a string representation of the underlying data. Example representations:
     * "[]", "[1]", "[1, 2, 3]".
     * @return a string representation of the underlying data
     */
    @Override
    public @NotNull String toString(){
        return data.toString();
    }

    /**
     * Returns a hash code for this ImmutableList using ArrayList's hashCode method.
     * @return a hash code for this ImmutableList
     */
    @Override
    public int hashCode(){
        return data.hashCode();
    }

    /**
     * Returns true if the supplied object to compare with is also an ImmutableList and has the same underlying data and
     * order.
     * @param o the object to compare with this ImmutableList
     * @return true if the supplied object is an ImmutableList and has the same underlying data and order
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof ImmutableList<?> o1)){
            return false;
        }
        return data.equals(o1.data);
    }

    private class ImmutableListIterator implements Iterator<E> {
        private final Iterator<E> listIterator = data.iterator();
        ImmutableListIterator(){}
        @Override
        public boolean hasNext(){
            return listIterator.hasNext();
        }

        @Override
        public @NotNull E next(){
            return listIterator.next();
        }
    }
}
