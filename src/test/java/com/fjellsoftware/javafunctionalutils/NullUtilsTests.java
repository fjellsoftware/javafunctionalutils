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
import java.util.List;
import java.util.Map;

public class NullUtilsTests {

     @Test
    public void requireAllNonNullTest(){
         NullUtils.requireAllNonNull(1,2,"test", new Object());
         try {
             NullUtils.requireAllNonNull(1,2,"test", null, new Object());
             assert false;
         } catch (Exception e) {}
     }

     @Test
    public void requireAllNonNullIterable(){
         try {
             NullUtils.requireAllNonNullIterable(null);
             assert false;
         } catch (Exception e) {}
         try {
             NullUtils.requireAllNonNullIterable(List.of(null));
             assert false;
         } catch (Exception e) {}
         NullUtils.requireAllNonNullIterable(List.of("test"));
     }

     @Test
    public void requireNotContainsNullMap(){
         Map<String,Object> strings = new HashMap<>();
         strings.put("test", null);
         try {
             NullUtils.requireNotContainsNull(strings);
             assert false;
         } catch (Exception e) {}
         strings.clear();
         strings.put(null, "test");
         try {
             NullUtils.requireNotContainsNull(strings);
             assert false;
         } catch (Exception e) {}
         strings.clear();
         NullUtils.requireNotContainsNull(strings);
         strings.put("test", "test");
         NullUtils.requireNotContainsNull(strings);
     }
}
