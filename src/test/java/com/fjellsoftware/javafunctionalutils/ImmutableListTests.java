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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ImmutableListTests {

    @Test
    public void testDoesCopy(){
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        ImmutableList<Integer> integers1 = new ImmutableList<>(integers);
        assert integers1.size() == 3;
        integers.add(4);
        assert integers1.size() == 3;
        List<Integer> integers2 = integers1.copyToList();
        integers2.add(4);
        assert integers2.size() == 4;
        assert integers1.size() == 3;
        ImmutableList<Integer> integers3 = integers1.copyAndAdd(4);
        assert integers1.size() == 3;
        assert integers3.size() == 4;

        ImmutableList<Integer> integers4 = integers1.copyAndAdd(List.of(4, 5, 6));
        assert integers4.size() == 6;
        assert integers1.size() == 3;

    }

    @Test
    public void staticConstructors(){
        assert ImmutableList.empty().isEmpty();

        ImmutableList<Integer> integers = ImmutableList.singletonList(1);
        assert integers.size() == 1;
        assert integers.get(0).equals(1);

        ImmutableList<Integer> integers1 = ImmutableList.of(1, 2, 3);
        assert integers1.size() == 3;
        assert integers1.get(2).equals(3);
        assert integers1.contains(2);
        assert integers1.indexOf(2) == 1;
        assert ImmutableList.of().isEmpty();
    }

    @Test
    public void objectOverrides(){
        assert ImmutableList.empty().toString().equals("[]");
        assert ImmutableList.of(1, 2, 3).toString().equals("[1, 2, 3]");

        assert ImmutableList.of(6,7,8).equals(ImmutableList.of(6,7,8));
        assert !ImmutableList.of(6,7,8).equals(ImmutableList.of(8,7,6));
        assert !ImmutableList.of(6,7,8).equals(ImmutableList.of(10,11,12));

        assert ImmutableList.of(6,7,8).hashCode() == ImmutableList.of(6,7,8).hashCode();
        assert ImmutableList.of(6,7,8).hashCode() != ImmutableList.of(10,11,12).hashCode();
    }

    @Test
    public void operations(){
        ImmutableList<String> strings = ImmutableList.of("test1", "test2", "test3", "test4", "test5");
        assert !strings.contains("asdf");
        assert strings.contains("test4");
        assert strings.indexOf("asdf") == -1;

        try {
            strings.get(5);
            assert false;
        } catch (IndexOutOfBoundsException e) {}
        assert strings.get(0).equals("test1");
        assert strings.get(4).equals("test5");
    }

    @Test
    public void iterator(){
        ImmutableList<Integer> integers = ImmutableList.of(1, 2, 3);

        int i = 0;
        for (Integer integer : integers) {
            if(i == 0){
                assert integer.equals(1);
            }
            if(i == 1){
                assert integer.equals(2);
            }
            if(i == 2){
                assert integer.equals(3);
            }
            i++;
        }
        assert i == 3;

        AtomicInteger atomicInteger = new AtomicInteger();
        integers.forEach(integer -> atomicInteger.incrementAndGet());
        assert atomicInteger.get() == 3;

        Iterator<Integer> iterator = integers.iterator();
        try {
            iterator.remove();
            assert false;
        }
        catch (Exception e){}

    }

    @Test
    public void notNull(){
        try {
            ImmutableList.of(1, 2, null, 4);
            assert false;
        }
        catch (NullPointerException e){}

        ImmutableList<Integer> integers = ImmutableList.of(1, 2, 3, 4);

        try {
            integers.indexOf(null);
            assert false;
        }
        catch (Exception e){}

        try {
            integers.contains(null);
            assert false;
        }
        catch (Exception e){}

        List<Integer> listWithNull = new ArrayList<>();
        listWithNull.add(1);
        listWithNull.add(null);
        listWithNull.add(3);

        try {
            new ImmutableList<>(listWithNull);
            assert false;
        }
        catch (Exception e){}
    }
}
