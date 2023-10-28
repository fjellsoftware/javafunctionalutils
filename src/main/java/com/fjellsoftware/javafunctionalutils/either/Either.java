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

package com.fjellsoftware.javafunctionalutils.either;

/**
 * An interface where an instance of an implementation contains a value of either one generic type or another. This is
 * useful for example when you would like a method to return either an Integer or String, in which case you can declare
 * the return type as {@literal Either<Integer,String>}, and in the method return either {@literal new Left<>(5) or
 * new Right<>("return value")}. Implementations do not accept null.
 * @param <T> left type
 * @param <Y> right type
 */
public sealed interface Either<T,Y> permits Left, Right {

    /**
     * Returns true if this is Left.
     * @return true if this is Left
     */
    default boolean isLeft(){
        return this instanceof Left<T,Y>;
    }

    /**
     * Returns true if this is Right.
     * @return true if this is Right
     */
    default boolean isRight(){
        return this instanceof Right<T,Y>;
    }
}
