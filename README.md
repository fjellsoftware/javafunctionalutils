Functional utils for java
======================

javafunctionalutils is a library containing various utilities for functional programming in java.
The main goal is to provide a small set of the most useful concepts enabled by the sealed types feature introduced 
in java version 17, as well as providing some immutable data structures with a signature that reflects their 
immutability.

Here is an example of what the code might look like when using Opt from this library instead of the java standard 
library's Optional:

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
    Opt<String> optString = optString();
    if (!(optString instanceof Some<String> some)) {
        System.out.println("No string");
    } else {
        System.out.println("Found string: " + some.value());
    }
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
