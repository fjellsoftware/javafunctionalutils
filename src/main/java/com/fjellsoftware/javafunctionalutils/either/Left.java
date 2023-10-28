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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * One of the two implementations of Either.
 * @param value the value this should contain. Cannot be null
 * @param <T> left type
 * @param <Y> right type
 */
public record Left<T, Y>(@NotNull T value) implements Either<T, Y> {
    public Left {
        Objects.requireNonNull(value);
    }
}
