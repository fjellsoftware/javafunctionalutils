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

package com.fjellsoftware.javafunctionalutils.result;

/**
 * An interface that signals that an operation finished either successfully or exceptionally, and contains either the
 * successful return value or a Throwable, depending on success. In other words, this is a similar to a special case of
 * {@literal Either<T,Y>}, where the generic type of Right is Throwable.
 * @param <T> successful result type
 * @param <E> exceptional result type
 */
public sealed interface Result<T, E extends Throwable> permits Ok, ErrorResult {

    /**
     * Creates a successful result with a value. Value cannot be null.
     * @param value success value, cannot be null
     * @return successful result
     */
    static <T, E extends Throwable> Ok<T, E> ok(T value){
        return new Ok<>(value);
    }

    /**
     * Creates an exceptional result with a Throwable. Throwable cannot be null.
     * @param error throwable error, cannot be null
     * @return exceptional result
     */
    static <T, E extends Throwable> ErrorResult<T, E> error(E error){
        return new ErrorResult<>(error);
    }

    /**
     * Returns true if result is successful
     * @return true if result is successful
     */
    default boolean isOk(){
        return this instanceof Ok<T,E>;
    }
}
