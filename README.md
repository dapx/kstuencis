A real-time tick-based server developed from scratch for learning purposes


## How to use

At this moment the project has only a console implementation to handle input and output.

You can send `increment:5` to increment the counter:

```
Current state: 0
increment:5
Current state: 5
```

The `decrement` also works.

At this moment the output line break conflicts with the input, giving an error message:

```
Current state: 10
increment:5Current state: 10

Unknown input: 
Current state: 10
```

## TODO

There are many things that need to be done, I will list some of them:

 - A concurrent and consistent way to have a state
 - A better way to handle the events
 - A socket listener and emitter implementation 
 - A gracefully shutdown hook
 - A way to test the interval loop without waiting the interval, maybe using a fake clock.

