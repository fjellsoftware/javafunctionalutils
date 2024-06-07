Functional utils for java
======================

javafunctionalutils is a library containing various utilities for functional programming in java.
The main goal is to provide a small set of the most useful concepts enabled by the sealed types feature introduced 
in java version 17, as well as providing some immutable data structures with a signature that reflects their 
immutability.

Here is an example of what the code might look like when using Opt and ImmutableList/Map from this library:

```java

public void typeSafeOptional() {
    Opt<String> optString = random.nextBoolean() ? Opt.of("test") : Opt.empty();

    // Pattern matching using java 21+ or early access feature in java 17
    switch (optString) {
        case None<String> none -> 
            System.out.println("No string");
        case Some<String> some -> 
            System.out.println("Found string: " + some.value());
    }

    // Alternatively use instanceof
    if (!(optString instanceof Some<String> some)) {
        System.out.println("No string");
    } else {
        String value = some.value();
        System.out.println("Found string: " + value);
    }
}

public void immutableListExample() {
    List<String> mutableStrings = new ArrayList<>(List.of("hello", "world"));
    ImmutableList<String> immutableStrings1 = new ImmutableList<>(mutableStrings);
    String hello = immutableStrings1.get(0);
    mutableStrings.add("test");
    for(String value : immutableStrings1){
        // Prints:
        // hello
        // world
        System.out.println(value);
    }
    ImmutableList<String> immutableStrings2 = ImmutableList.of("hello", "world");
    assert immutableStrings1.equals(immutableStrings2);

    mutableStrings.add(null);
    // Runtime ERROR and @NotNull (potential IDE compile-time error)
    ImmutableList.of("hello", "world", null);
    new ImmutableList<>(mutableStrings);
    // Compiler ERROR
    // immutableStrings1.add("test");
    // immutableStrings1.clear();
    // etc.
}

public void immutableMapExample() {
    Map<String,String> mutableStrings = new HashMap<>();
    mutableStrings.put("hello", "world");
    ImmutableMap<String,String> immutableStrings = new ImmutableMap<>(mutableStrings);
    Opt<String> worldOpt = immutableStrings.get("hello");
    // Prints: world
    System.out.println(worldOpt.getOrThrow());
    Opt<String> asdfOpt = immutableStrings.get("asdf");
    assert asdfOpt.equals(Opt.empty());
    Iterable<ImmutableMap.Entry<String, String>> entries = immutableStrings.entries();
    for(ImmutableMap.Entry<String,String> entry : entries){
        // Prints: Key: hello, value: world
        System.out.println("Key: " + entry.key() + ", value: " + entry.value());
    }

    mutableStrings.put("test", null);
    // Runtime ERROR, nulls are not allowed
    new ImmutableMap<>(mutableStrings);
    // Compiler ERROR
    // immutableStrings.put("not", "allowed");
    // immutableStrings.clear();
    // etc.
}

```

## Artifacts

_**Java 17+** maven artifact:_
```xml
<dependency>
    <groupId>com.fjellsoftware.javafunctionalutils</groupId>
    <artifactId>javafunctionalutils</artifactId>
    <version>2.0</version>
</dependency>
```
