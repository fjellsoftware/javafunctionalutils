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

/**
 * Implementation of Opt that does not have a value present.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public record None<T>() implements Opt<T>{
    private static final None INSTANCE = new None();

    /**
     * Returns an instance of None.
     * @return an instance of None
     */
    public static <T> @NotNull None<T> getInstance(){
        return INSTANCE;
    }
}
