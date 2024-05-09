Functional utils for java
======================

javafunctionalutils is a library containing various utilities for functional programming in java.
The main goal is to provide a small set of the most useful concepts enabled by the sealed types feature introduced 
in java version 17, as well as providing some immutable data structures with a signature that reflects their 
immutability.

Here is an example of what the code might look like when using Opt from this library instead of the java standard 
library's Optional:

```java
public class OptDemonstration {
    public void demonstrateOpt() {
        Optional<String> optionalString = optionalString();

        // Alternative 1, use unsafe method get() / orElseThrow()
        if (optionalString.isEmpty()) {
            System.out.println("No string");
        } else {
            // this method may throw, but will not here
            String string = optionalString.orElseThrow();
            System.out.println("Found string: " + string);
        }

        // Alternative 2 use functional interfaces
        optionalString.ifPresentOrElse((string) -> {
            System.out.println("Found string: " + string);
        }, () -> {
            System.out.println("No string");
        });

        // Meanwhile Opt is compile-time safe and does not rely on 
        // functional interfaces which are harder to read/write/debug
        Opt<String> optString = optString();
        if (!(optString instanceof Some<String> some)) {
            System.out.println("No string");
        } else {
            System.out.println("Found string: " + some.value());
        }
    }

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private Optional<String> optionalString() {
        if (random.nextBoolean()) {
            return Optional.of("Hello");
        }
        return Optional.empty();
    }

    private Opt<String> optString() {
        if (random.nextBoolean()) {
            return Opt.of("Hello");
        }
        return Opt.empty();
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
