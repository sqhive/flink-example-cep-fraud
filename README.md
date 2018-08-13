# Flink CEP Fraud Example

# Start

```
$ java -jar ... --port 9099
$ nc -vvvl 9099

```

# Output

```
13:57:44,783 INFO  org.apache.flink.runtime.state.heap.HeapKeyedStateBackend     - Initializing heap keyed state backend with stream factory.
Event(type=CREDIT, merchant=LONDON, amount=5.0)
Event(type=CREDIT, merchant=LONDON, amount=4.0)
Event(type=CREDIT, merchant=LONDON, amount=6.0)
Event(type=DEBIT, merchant=LONDON, amount=6.0)
Event(type=DEBIT, merchant=LONDON, amount=6.0)
Event(type=DEBIT, merchant=LONDON, amount=5.0)
Event(type=DEBIT, merchant=LONDON, amount=5.0)
{start=[Event(type=DEBIT, merchant=LONDON, amount=5.0)], middle=[Event(type=DEBIT, merchant=BRIGHTON, amount=5.0)]}
Event(type=DEBIT, merchant=BRIGHTON, amount=5.0)
```

