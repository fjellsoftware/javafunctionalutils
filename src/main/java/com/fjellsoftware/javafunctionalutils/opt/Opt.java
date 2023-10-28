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

package com.fjellsoftware.javafunctionalutils.opt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * An interface that signals that a value may or may not be present. If the instance of this interface is of type Some,
 * then the value is present, and it can be extracted. Otherwise, if the type is None, then there is no value. One
 * use-case where this is useful is in cases where null is avoided as a return value or argument value, as this interface
 * explicitly signals optional value presence. This is unlike java's nullability for all object variables, which results
 * in implicit value optionality, even in cases where this is undesirable.
 *
 * @param <T> the type of value
 */
public sealed interface Opt<T> permits None, Some {

    /**
     * Creates an Opt instance without value.
     * @return an Opt instance without value
     */
    static <T> @NotNull Opt<T> empty(){
        return None.getInstance();
    }

    /**
     * Creates an instance of Opt where a value is present.
     * @param value the value that this Opt should contain
     * @return the newly created Opt with the value present
     * @throws NullPointerException if the supplied value is null
     */
    static <T> @NotNull Opt<T> of(@NotNull T value){
        Objects.requireNonNull(value);
        return new Some<>(value);
    }

    /**
     * Creates an instance of Opt where a value is either present or not, depending on whether a value or null is
     * supplied. This method will accept null as argument, unlike Opt::of(...).
     * @param value the value that this Opt should contain, or null
     * @return the newly created Opt
     */
    static <T> @NotNull Opt<T> ofNullable(@Nullable T value){
        if(value == null){
            return None.getInstance();
        }
        return new Some<>(value);
    }

    /**
     * Returns the value present in this Opt, if there is a value present, otherwise it returns the value supplied in
     * this method.
     * @param value the value to return if no value is present in this
     * @return either the value present in this, or the value supplied
     * @throws NullPointerException if the supplied value is null
     */
    default @NotNull T orElse(@NotNull T value){
        Objects.requireNonNull(value);
        return this instanceof Some ? ((Some<T>) this).value() : value;
    }

    /**
     * Returns the value present in this, otherwise throws an exception.
     * @return the value present in this
     * @throws NoSuchElementException if no value is present in this
     */
    default @NotNull T getOrThrow(){
        if(this instanceof Some<T> some){
            return some.value();
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns true if a value is present in this.
     * @return true if a value is present in this
     */
    default boolean isPresent(){
        return this instanceof Some<T>;
    }
}
