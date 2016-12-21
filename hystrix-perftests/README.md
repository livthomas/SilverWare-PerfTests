# SilverWare Hystrix Performance Tests

This test suite contains performance tests that measure the overhead added by Hystrix method handler.

## Scenarios

There is a simple scenario containing `CounterMicroservice` with a single `count()` method.
The only thing that this method does is counting the number of its executions.

`CounterMicroservice` can be invoked both from `CdiCounterMicroservice` and `HystrixCounterMicroservice`.
While the former one only injects `CounterMicroservice` using CDI, the latter one also configures Hystrix circuit breaker and timeout mechanisms while doing so.

All microservice calls are executed in a single JVM in order to see the real difference not influenced by remote calls.

## Execution

First, you need to install SilverWare to your local Maven repository. Clone [SilverWare](https://github.com/SilverThings/SilverWare) repository to your machine and run the following command in its parent directory:

```
mvn clean install -DskipTests
```

Proceed with running `CdiPerformanceTest` in order to measure the performance of pure CDI invocation on your machine:

```
mvn clean test -Dtest=CdiPerformanceTest
```

It takes some time to execute the tests (approximately 90 seconds). After they finish, you should see a result similar to this one:

```
[0:01:00][12339322 iterations][100%] [205681.0 iterations/s] [warmUp => false]
```

Finally, run `HystrixPerformanceTest` to measure the performance of microservice invocation using Hystrix:

```
mvn clean test -Dtest=HystrixPerformanceTest
```

You should again see similar result as in the previous run:

```
[0:01:00][6838748 iterations][100%] [113980.81666666667 iterations/s] [warmUp => false]

```
 
Notice that the number of iterations per second is much lower than in the previous run.
This is caused by the overhead introduced by Hystrix.