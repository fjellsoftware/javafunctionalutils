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

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImmutableSetTests {

    @Test
    public void testDoesCopy(){
        Set<String> mutableStrings = new HashSet<>();
        mutableStrings.add("one");
        ImmutableSet<String> immutableStrings = new ImmutableSet<>(mutableStrings);
        assert immutableStrings.size() == 1;
        mutableStrings.add("two");
        assert immutableStrings.size() == 1;

        Set<String> copy = immutableStrings.copyToSet();
        copy.add("two");
        assert immutableStrings.size() == 1;

        ImmutableSet<String> copyAndPut1 = immutableStrings.copyAndAdd("two");
        assert copyAndPut1.size() == 2;
        assert immutableStrings.size() == 1;

        ImmutableSet<String> copyAndPut2 = immutableStrings.copyAndAdd(List.of("two", "three"));
        assert copyAndPut2.size() == 3;
        assert immutableStrings.size() == 1;
    }

    @Test
    public void staticConstructors(){
        assert ImmutableSet.empty().isEmpty();

        ImmutableSet<String> strings = ImmutableSet.of("one", "two", "three");
        assert strings.size() == 3;
        assert strings.contains("one");
        assert !strings.contains("four");
    }

    @Test
    public void objectOverrides(){
        assert ImmutableSet.empty().toString().equals("[]");
        assert ImmutableSet.of("one", "two").toString().equals("[one, two]");
        assert ImmutableSet.of(1,2,3).toString().equals("[1, 2, 3]");

        assert ImmutableSet.of("one", "two", "three").equals(ImmutableSet.of("three", "two", "one"));
        assert !ImmutableSet.of("one", "two", "three").equals(ImmutableSet.of("one", "two"));
        assert !ImmutableSet.of("one", "two", "three").equals(ImmutableSet.of(1,2,3));

        assert ImmutableSet.of("one", "two", "three").hashCode() == ImmutableSet.of("one", "three", "two").hashCode();
        assert ImmutableSet.of("two", "one").hashCode() != ImmutableSet.of(1,2,3).hashCode();
    }

    @Test
    public void operations(){
        ImmutableSet<String> strings = ImmutableSet.of("one", "two", "three");
        assert !strings.contains("asdf");
        assert strings.contains("three");
        assert !strings.isEmpty();
        assert !strings.containsRaw(1);
    }

    @Test
    public void iterator(){
        ImmutableSet<String> strings = ImmutableSet.of("one", "two", "three");

        int i = 0;
        for (String string : strings) {
            assert string.equals("one") || string.equals("two") ||string.equals("three");
            i++;
        }
        assert i == 3;

        try {
            strings.iterator().remove();
            assert false;
        } catch (Exception e) {}
    }

    @Test
    public void notNull(){
        Set<String> hashSet = new HashSet<>();
        hashSet.add("one");
        hashSet.add(null);
        try {
            new ImmutableSet<>(hashSet);
            assert false;
        } catch (Exception e) {}

        try {
            new ImmutableSet<>(null);
            assert false;
        } catch (Exception e) {}

        try {
            ImmutableSet.of(null);
            assert false;
        } catch (Exception e) {}

        try {
            ImmutableSet.of("test", null);
            assert false;
        } catch (Exception e) { }

        try {
            ImmutableSet.empty().copyAndAdd(null);
            assert false;
        } catch (Exception e) {}
        try {
            ImmutableSet.empty().copyAndAdd(List.of(null));
            assert false;
        } catch (Exception e) {}

        try {
            ImmutableSet.empty().contains(null);
            assert false;
        } catch (Exception e) {}
    }

}
