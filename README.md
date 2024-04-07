This repository contains the homework written by Stan Andrei for the course Event Based Systems (Sisteme Bazate pe Evenimente)

## Homework

**Language**: Java SDK 22

**Parallelization type**: Threads

**Tests done on threads**: 1, 2, 5, 10

**Operating system**: Windows 10 Pro

**Processor**: Intel(R) Core(TM) i7-9750H CPU @ 2.60GHz   2.59 GHz

**Parameters**:
```json
{
  Configuration[
    publicationCount=1000000,
    subscriptionCount=10000000,
    weights={company=0.5},
    limits={date=[Sun Jan 01 00:00:00 EET 2023,Mon Jan 01 00:00:00 EET 2024], value=[1.0,100.0], drop=[1.0,100.0], variation=[0.0,1.0]},
    companies=[Google, Amazon, Netflix, Microsoft],
    equalWeights={company=0.4},
    threadCount=5
  ]
}
```

See logs/stats.xlsx for stats.