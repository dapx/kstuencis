A real-time tick-based server from scratch for learning purposes

## How to build

There are two ways, using Intellij IDEA or Gradle:

```shell
./gradlew build
java -jar modules/example/build/libs/example-all.jar
```

> Do not use `./gradlew run` because It does not have a console and throws a continuous error.

## How to use

The project has a console implementation to handle input and output:

You can send `increment:5` to increment the counter:

```
Current state: 0
increment:5
Current state: 5
```

The `decrement` also works.

> When using the Intellij IDEA app run console, the output line break conflicts with the input, giving an error message: Unknown input

You can even send and receive messages through a socket server in the same way as the console:

```shell
nc localhost 9999
```

## Project Structure

```
- build-logic:
  - kotlin-convention gradle plugin
- modules:
  - :core (Interfaces and Classes to implement a server)
  - :common (Common classes and utilities)
  - :console (Console communication implementation)
  - :socket (Socket communication implementation)
  - :counter (Business implementation as counter)
  - :example (The composition root where implement all those modules to show how it works)
```

## TODO

There are many things that need to be done, I will list some of them:

- A better way to handle the events
- A gracefully shutdown hook
- Extract counter and example from rootProject and use includeBuild
