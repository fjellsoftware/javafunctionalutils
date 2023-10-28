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

import java.util.Map;
import java.util.Objects;

/**
 * This class has various utility methods related to checking for null.
 */
public class NullUtils {

    /**
     * Checks if all the supplied arguments are not null.
     * @param objects the objects whose nullability is to be checked
     * @throws NullPointerException if any of the arguments are null
     */
    public static void requireAllNonNull(Object... objects){
        Objects.requireNonNull(objects);
        for (Object object : objects) {
            Objects.requireNonNull(object);
        }
    }

    /**
     * Checks if an Iterable and all its elements are not null.
     * @param objects the iterable whose elements is to be checked
     * @throws NullPointerException if the iterable or any of its elements are null
     */
    public static void requireAllNonNullIterable(Iterable<?> objects){
        Objects.requireNonNull(objects);
        for (Object object : objects) {
            Objects.requireNonNull(object);
        }
    }

    /**
     * Checks if a map and all its keys and values are not null.
     * @param map the map whose keys and values are to be checked
     * @throws NullPointerException if the map or any of its keys or values are null
     */
    public static void requireNotContainsNull(Map<?, ?> map){
        Objects.requireNonNull(map);
        requireAllNonNullIterable(map.keySet());
        requireAllNonNullIterable(map.values());
    }
}
