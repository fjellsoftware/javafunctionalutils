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

import com.fjellsoftware.javafunctionalutils.either.Left;
import com.fjellsoftware.javafunctionalutils.either.Right;
import com.fjellsoftware.javafunctionalutils.opt.None;
import com.fjellsoftware.javafunctionalutils.opt.Opt;
import com.fjellsoftware.javafunctionalutils.opt.Some;
import com.fjellsoftware.javafunctionalutils.result.ErrorResult;
import com.fjellsoftware.javafunctionalutils.result.Ok;
import com.fjellsoftware.javafunctionalutils.result.Result;
import org.junit.jupiter.api.Test;

public class RecordTests {

    @Test
    public void optTests(){
        assert Opt.empty() instanceof None;
        Opt<String> test1 = Opt.of("test");
        assert test1.isPresent();
        assert test1 instanceof Some<String> some && some.value().equals("test");
        assert test1.getOrThrow().equals("test");
        try {
            Opt.of(null);
            assert false;
        } catch (Exception e) {}
        Opt<String> test2 = Opt.ofNullable("test");
        assert test2.isPresent();
        assert test2 instanceof Some<String> some && some.value().equals("test");
        assert test2.getOrThrow().equals("test");
        assert Opt.ofNullable(null) instanceof None;

        assert test1.orElse("else").equals("test");
        assert Opt.empty().orElse("else").equals("else");
    }

    @Test
    public void eitherTests(){
        Left<String, Integer> test = new Left<>("test");
        assert test.isLeft();
        assert !test.isRight();
        assert test.value().equals("test");

        Right<String, Integer> test2 = new Right<>(1);
        assert !test2.isLeft();
        assert test2.isRight();
        assert test2.value().equals(1);
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void resultTests(){
        Ok<String, RuntimeException> test = new Ok<>("test");
        assert test.isOk();
        assert test.value().equals("test");
        assert Result.ok("test") instanceof Ok<String, Throwable>;
        assert !Result.error(new RuntimeException()).isOk();
        assert Result.error(new RuntimeException()) instanceof ErrorResult<Object, RuntimeException>;
        assert Result.error(new RuntimeException()).value() instanceof Exception;
    }
}
