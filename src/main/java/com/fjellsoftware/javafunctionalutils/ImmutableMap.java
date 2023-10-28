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

import com.fjellsoftware.javafunctionalutils.opt.Opt;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * An immutable object that maps keys to values. Keys and values are not allowed to be null. It does not implement
 * Serializable. The get() method will wrap the value corresponding to the key in Opt. Other than that it behaves that
 * same as its underlying HashMap.
 * Thread safe.
 *
 * @param <K> the type of key elements
 * @param <V> the type of value elements
 */
public class ImmutableMap<K, V> {
    private final HashMap<K, V> data;

    /**
     * Creates an ImmutableMap containing the keys and values in the submitted Map.
     * @param data the Map whose keys and values will be present in the ImmutableMap
     * @throws NullPointerException if the specified Map or any keys or values are null
     */
    public ImmutableMap(@NotNull Map<? extends K, ? extends V> data) {
        NullUtils.requireNotContainsNull(data);
        this.data = new HashMap<>(data);
    }

    private ImmutableMap(ImmutableMap<? extends K, ? extends V> data, K key, V value){
        NullUtils.requireAllNonNull(data, key, value);
        HashMap<K, V> tmp = new HashMap<>(data.data);
        tmp.put(key, value);
        this.data = tmp;
    }

    private ImmutableMap(ImmutableMap<? extends K, ? extends V> data, Map<? extends K, ? extends V> dataToAppend){
        Objects.requireNonNull(data);
        NullUtils.requireNotContainsNull(dataToAppend);
        HashMap<K, V> tmp = new HashMap<>(data.data);
        tmp.putAll(dataToAppend);
        this.data = tmp;
    }

    private ImmutableMap(K key, V value){
        NullUtils.requireAllNonNull(key, value);
        HashMap<K,V> tmp = new HashMap<>(1);
        tmp.put(key,value);
        this.data = tmp;
    }

    @SafeVarargs
    private ImmutableMap(Entry<K,V>... entries){
        HashMap<K,V> map = new HashMap<>();
        for (Entry<K, V> entry : entries) {
            Objects.requireNonNull(entry);
            NullUtils.requireAllNonNull(entry.key(), entry.value());
            map.put(entry.key(), entry.value());
        }
        this.data = map;
    }

    /**
     * Creates and returns an ImmutableMap based on the keys and values in this, and puts the supplied key-value pair.
     * @param keyToAppend the key to include in the new ImmutableMap in addition to the current elements.
     * @param valueToAppend the value to include in the new ImmutableMap in addition to the current elements.
     * @return the newly constructed ImmutableMap
     * @throws NullPointerException if the key or value is null
     */
    public @NotNull ImmutableMap<K,V> copyAndPut(@NotNull K keyToAppend, @NotNull V valueToAppend){
        return new ImmutableMap<>(this, keyToAppend, valueToAppend);
    }

    /**
     * Creates and returns an ImmutableMap based on the keys and values in this, and puts the keys and values from the
     * supplied Map.
     * @param dataToAppend the Map whose keys and values to include in the new ImmutableMap in addition to the current elements
     * @return the newly constructed ImmutableMap
     * @throws NullPointerException if the supplied Map or any of its keys or values are null
     */
    public @NotNull ImmutableMap<K,V> copyAndPut(@NotNull Map<? extends K, ? extends V> dataToAppend){
        return new ImmutableMap<>(this, dataToAppend);
    }

    /**
     * Creates and returns an ImmutableMap based on the supplied key and value.
     * @param key the key to include in the new ImmutableMap
     * @param value the value to include in the new ImmutableMap
     * @return the newly constructed ImmutableMap
     * @throws NullPointerException if the supplied key or value is null
     */
    public static <K,V> @NotNull ImmutableMap<K,V> singletonMap(@NotNull K key, @NotNull V value){
        return new ImmutableMap<>(key, value);
    }

    /**
     * Creates and returns an empty ImmutableMap.
     * @return the empty ImmutableMap
     */
    @SuppressWarnings("unchecked")
    public static <K,V> @NotNull ImmutableMap<K,V> empty(){
        return empty;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static final ImmutableMap empty = new ImmutableMap(Collections.emptyMap());

    /**
     * Creates and returns an ImmutableMap based on the entries supplied as arguments. If multiple entries have the
     * same key, the value of the latest entry will be used.
     * @param entries the entries to include in the new ImmutableMap
     * @return the newly constructed ImmutableMap
     */
    @SafeVarargs
    public static <K,V> @NotNull ImmutableMap<K,V> of(@NotNull Entry<K,V>... entries){
        Objects.requireNonNull(entries);
        return new ImmutableMap<>(entries);
    }

    /**
     * Copies the underlying data of this ImmutableMap into a mutable Map and returns it.
     * @return the newly constructed copy of this ImmutableMap as a mutable Map
     */
    public @NotNull Map<K, V> copyToMap(){
        return new HashMap<>(data);
    }

    /**
     * Returns the size of this ImmutableMap.
     * @return the size of this ImmutableMap
     */
    public int size(){
        return data.size();
    }

    /**
     * Returns true if this ImmutableMap contains no elements.
     * @return true if this ImmutableMap contains no elements
     */
    public boolean isEmpty(){
        return data.isEmpty();
    }

    /**
     * Returns true if this ImmutableMap contains the key supplied.
     * @param key The key whose presence in this ImmutableMap is to be tested
     * @return true if the supplied key is present
     * @throws NullPointerException if the key is null
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsKey(@NotNull Object key){
        Objects.requireNonNull(key);
        return data.containsKey(key);
    }

    /**
     * Returns true if this ImmutableMap contains the value supplied.
     * @param value The value whose presence in this ImmutableMap is to be tested
     * @return true if the supplied value is present
     * @throws NullPointerException if the value is null
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsValue(@NotNull Object value){
        Objects.requireNonNull(value);
        return data.containsValue(value);
    }

    /**
     * If the supplied key is present in this ImmutableMap, this method returns the value that it maps to, wrapped in
     * Some. Otherwise, it returns None.
     * @param key the key whose presence in this ImmutableMap is to be tested
     * @return Some(value) if the key is present, otherwise None
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public @NotNull Opt<V> get(@NotNull Object key){
        Objects.requireNonNull(key);
        return Opt.ofNullable(data.get(key));
    }

    /**
     * Returns an ImmutableSet view of the keys in this ImmutableMap.
     * @return an ImmutableSet view of the keys in this ImmutableMap
     */
    public @NotNull ImmutableSet<K> keySet(){
        return new ImmutableSet<>(data, false);
    }

    /**
     * Returns an Iterable view of the values in this ImmutableMap. Iterator::remove() is not supported.
     * @return an Iterable view of the values in this ImmutableMap
     */
    public @NotNull Iterable<V> values(){
        return new ImmutableValueSet(data.values());
    }

    private class ImmutableValueSet implements Iterable<V> {
        private final Collection<V> values;
        private ImmutableValueSet(Collection<V> values){
            this.values = values;
        }

        @Override
        public @NotNull Iterator<V> iterator() {
            return new ImmutableValueSetIterator(values.iterator());
        }

        private class ImmutableValueSetIterator implements Iterator<V> {
            private final Iterator<V> underlyingIterator;
            private ImmutableValueSetIterator(Iterator<V> iterator) {
                this.underlyingIterator = iterator;
            }

            @Override
            public boolean hasNext() {
                return underlyingIterator.hasNext();
            }

            @Override
            public @NotNull V next() {
                return underlyingIterator.next();
            }
        }
    }

    /**
     * Returns an immutable view of the entries in this ImmutableMap. Iterator::remove() is not supported.
     * @return an immutable view of the entries in this ImmutableMap
     */
    public @NotNull Iterable<Entry<K,V>> entries(){
        Set<Map.Entry<K, V>> entries = data.entrySet();
        return new ImmutableEntrySet(entries);
    }

    public record Entry<K,V>(K key, V value){}

    private class ImmutableEntrySet implements Iterable<Entry<K,V>> {
        private final Set<Map.Entry<K,V>> entries;
        private ImmutableEntrySet(Set<Map.Entry<K,V>> entries){
            this.entries = entries;
        }

        @Override
        public @NotNull Iterator<Entry<K, V>> iterator() {
            return new ImmutableEntrySetIterator(entries.iterator());
        }

        private class ImmutableEntrySetIterator implements Iterator<Entry<K,V>>{
            private final Iterator<Map.Entry<K, V>> underlyingIterator;

            private ImmutableEntrySetIterator(Iterator<Map.Entry<K, V>> iterator) {
                this.underlyingIterator = iterator;
            }

            @Override
            public boolean hasNext() {
                return underlyingIterator.hasNext();
            }

            @Override
            public @NotNull Entry<K, V> next() {
                Map.Entry<K, V> next = underlyingIterator.next();
                return new Entry<>(next.getKey(), next.getValue());
            }
        }
    }

    /**
     * Returns true if the supplied object to compare with is also an ImmutableMap and has the same key-value mappings
     * as this.
     * @param o the object to compare with this ImmutableMap
     * @return true if the supplied object is an ImmutableMap and has the same key-value mappings as this
     */
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof ImmutableMap<?,?> immutableMap)){
            return false;
        }
        return data.equals(immutableMap.data);
    }

    /**
     * Creates and returns a string representation of this ImmutableMap. Example representations:
     * "{}", "{one=1}", "{one=1, two=2, three=3}".
     * @return a string representation of this ImmutableMap
     */
    @Override
    public @NotNull String toString(){
        return data.toString();
    }

    /**
     * Returns a hash code for this ImmutableMap using HashMap's hashCode method.
     * @return a hash code for this ImmutableMap
     */
    @Override
    public int hashCode(){
        return data.hashCode();
    }
}
