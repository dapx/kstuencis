A real-time tick-based server from scratch for learning purposes

## How to build

There are two ways, using Intellij IDEA or Gradle:

```shell
./gradlew build
java -jar build/libs/kstuencis-all.jar
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

You can send messages through a socket server:

```shell
echo increment:5 | nc localhost 9999
```

## TODO

There are many things that need to be done, I will list some of them:

 - Write tests
 - A concurrent and consistent way to have a state
 - A better way to handle the events
 - A socket emitter implementation 
 - A gracefully shutdown hook
 - A way to test the interval loop without waiting the interval, maybe using a fake clock.
