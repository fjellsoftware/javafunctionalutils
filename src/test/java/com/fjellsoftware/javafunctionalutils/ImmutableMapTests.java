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

import java.util.HashMap;
import java.util.Map;

public class ImmutableMapTests {

    @Test
    public void testDoesCopy(){
        Map<String, Integer> stringIntMap = new HashMap<>();
        stringIntMap.put("one", 1);

        ImmutableMap<String, Integer> stringIntegerImmutableMap = new ImmutableMap<>(stringIntMap);
        assert stringIntegerImmutableMap.size() == 1;
        stringIntMap.put("two", 2);
        assert stringIntMap.size() == 2;
        assert stringIntegerImmutableMap.size() == 1;
        assert !stringIntegerImmutableMap.get("two").isPresent();

        Map<String, Integer> copy = stringIntegerImmutableMap.copyToMap();
        assert copy.size() == 1;
        copy.put("two", 2);
        assert copy.size() == 2;
        assert stringIntegerImmutableMap.size() == 1;

        ImmutableMap<String, Integer> copy2 = stringIntegerImmutableMap.copyAndPut("two", 2);
        assert copy2.size() == 2;
        assert stringIntegerImmutableMap.size() == 1;
        ImmutableMap<String, Integer> copy3 = stringIntegerImmutableMap.copyAndPut(stringIntMap);
        assert copy3.size() == 2;
        assert stringIntegerImmutableMap.size() == 1;
    }

    @Test
    public void staticConstructors(){
        ImmutableMap<String, Integer> singletonMap = ImmutableMap.singletonMap("one", 1);
        assert singletonMap.size() == 1;
        assert singletonMap.get("one").getOrThrow().equals(1);
        assert !singletonMap.get("two").isPresent();

        ImmutableMap<Object, Object> empty = ImmutableMap.empty();
        assert empty.isEmpty();

        ImmutableMap<String, Integer> tuples = ImmutableMap.of(new ImmutableMap.Entry<>("one", 1), new ImmutableMap.Entry<>("two", 2));
        assert tuples.size() == 2;
        assert tuples.get("one").getOrThrow().equals(1);
        assert tuples.get("two").getOrThrow().equals(2);
        assert !tuples.get("three").isPresent();
        assert tuples.getRaw("one").getOrThrow().equals(1);
        assert !tuples.getRaw("four").isPresent();
    }

    @Test
    public void objectOverrides(){
        ImmutableMap<Object, Object> empty = ImmutableMap.empty();
        assert empty.toString().equals("{}");

        ImmutableMap<String, Integer> one = ImmutableMap.of(new ImmutableMap.Entry<>("one", 1));
        assert one.toString().equals("{one=1}");

        ImmutableMap<String, Integer> oneTwoThree = ImmutableMap.of(
                new ImmutableMap.Entry<>("one", 1), new ImmutableMap.Entry<>("two", 2), new ImmutableMap.Entry<>("three", 3));
        assert oneTwoThree.toString().equals("{one=1, two=2, three=3}");

        assert one.equals(ImmutableMap.of(new ImmutableMap.Entry<>("one", 1)));
        assert !one.equals(ImmutableMap.empty());
        assert !one.equals(ImmutableMap.of(new ImmutableMap.Entry<>("one", "one")));

        assert one.hashCode() == ImmutableMap.of(new ImmutableMap.Entry<>("one", 1)).hashCode();
        assert one.hashCode() != ImmutableMap.empty().hashCode();
    }

    @Test
    public void operations(){
        ImmutableMap<String, Integer> tuples = ImmutableMap.of(new ImmutableMap.Entry<>("one", 1), new ImmutableMap.Entry<>("two", 2));

        assert tuples.containsKey("one");
        assert !tuples.containsKey("three");
        assert !tuples.containsKeyRaw(1);

        assert tuples.containsValue(2);
        assert !tuples.containsValue(3);
        assert !tuples.containsValueRaw("three");

        ImmutableSet<String> strings = tuples.keySet();
        assert strings.size()==2;
        assert strings.contains("one");
        assert !strings.contains("three");

        Iterable<Integer> values = tuples.values();
        try {
            values.iterator().remove();
            assert false;
        }catch (Exception e){}

        Iterable<ImmutableMap.Entry<String, Integer>> entries = tuples.entries();
        try {
            entries.iterator().remove();
            assert false;
        } catch (Exception e) {}

        int i = 0;
        for (ImmutableMap.Entry<String, Integer> entry : entries) {
            i++;
            String key = entry.key();
            assert key.equals("one") || key.equals("two");
            Integer value = entry.value();
            assert value.equals(1) || value.equals(2);
        }
        assert i == 2;

    }

    @Test
    public void notNull(){

        Map<String, Integer> stringIntegerMap = new HashMap<>();
        ImmutableMap<String, Integer> immutableMap = new ImmutableMap<>(stringIntegerMap);
        stringIntegerMap.put("one", null);
        try {
            new ImmutableMap<>(stringIntegerMap);
            assert false;
        } catch (Exception e) {}

        stringIntegerMap.clear();
        stringIntegerMap.put(null, 1);
        try {
            new ImmutableMap<>(stringIntegerMap);
            assert false;
        } catch (Exception e) {}

        try{
            new ImmutableMap<>(null);
            assert false;
        }catch (Exception e){}

        try {
            ImmutableMap.of(new ImmutableMap.Entry<>("one", 1), null, new ImmutableMap.Entry<>("three", 3));
            assert false;
        } catch (Exception e) {}

        try {
            ImmutableMap.singletonMap(null, 1);
            assert false;
        } catch (Exception e) {
        }
        try {
            ImmutableMap.singletonMap("one", null);
            assert false;
        } catch (Exception e) {
        }
        try {
            immutableMap.containsValue(null);
            assert false;
        } catch (Exception e) {
        }
        try {
            immutableMap.containsKey(null);
            assert false;
        } catch (Exception e) {
        }
    }
}
