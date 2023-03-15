# Choreography Extraction (Kotlin version)

This is a Kotlin implementation of the choreography extraction algorithm described in <https://arxiv.org/abs/2205.02636>.

# Requirements

Java 11 or above.

# Compilation

`mvn install`

# Running instructions

After compilation, you can run

```java -jar target/pe-0.1-jar-with-dependencies.jar help```

to get a list of available commands.

These will allow you to run some of the tests used in <https://arxiv.org/abs/2205.02636>.

The commands `theory`, `lty15`, and `lty15-seq` should execute relatively quickly.

The commands `benchmark` and `bisimcheck` are very long and extensive tests, which require high amounts of RAM and CPU time. They might require weeks, depending on your harware, so the tool allows for getting some meaningful partial results:
- You can interrupt the `benchmark` task at any time (by CTRL-C). All results accumulated so far are saved in files inside of subdirectory "tests". You can run this task incrementally, i.e., it will not re-compute results computed in previous runs.
- If the "bisimcheck" task does not find some choreographies (because you stopped extraction), it simply skips them and does not count them in the final total.