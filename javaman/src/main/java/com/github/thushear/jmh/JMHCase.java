package com.github.thushear.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2017/5/19.
 */
public class JMHCase {

//
//    /**
//     *  吞吐量
//     * @throws InterruptedException
//     */
//    @Benchmark
//    @BenchmarkMode(Mode.Throughput)
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public void measureThroughOutPut() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }
//
//
//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    public void measureAvgTime() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }
//
//
//
//    /*
//     * Mode.SingleShotTime measures the single method invocation time. As the Javadoc
//     * suggests, we do only the single benchmark method invocation. The iteration
//     * time is meaningless in this mode: as soon as benchmark method stops, the
//     * iteration is over.
//     *
//     * This mode is useful to do cold startup tests, when you specifically
//     * do not want to call the benchmark method continuously.
//     */
//    @Benchmark
//    @BenchmarkMode(Mode.SingleShotTime)
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    public void measureSingleShot() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }
//
//    /*
//     * We can also ask for multiple benchmark modes at once. All the tests
//     * above can be replaced with just a single test like this:
//     */
//    @Benchmark
//    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    public void measureMultiple() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }

    /*
     * Or even...
     */
//
//    @Benchmark
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    public void measureAll() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }



    @State(Scope.Benchmark)
    public static class BenchMarkState {
        volatile double x = Math.PI;
    }

    @State(Scope.Thread)
    public static class ThreadState{
        volatile double x = Math.PI;
    }


    @Benchmark
    public void measureShared(BenchMarkState benchMarkState){
        benchMarkState.x ++;
    }

    @Benchmark
    public void measureUnshared(ThreadState threadState){
        threadState.x ++;
    }


    /*
     * ============================== HOW TO RUN THIS TEST: ====================================
     *
     * You are expected to see the different run modes for the same benchmark.
     * Note the units are different, scores are consistent with each other.
     *
     * You can run this test:
     *
     * a) Via the command line:
     *    $ mvn clean install
     *    $ java -jar target/benchmarks.jar JMHSample_02 -wi 5 -i 5 -f 1
     *    (we requested 5 warmup/measurement iterations, single fork)
     *
     * b) Via the Java API:
     *    (see the JMH homepage for possible caveats when running from IDE:
     *      http://openjdk.java.net/projects/code-tools/jmh/)
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHCase.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .threads(4)
                .forks(1)
                .build();
        new Runner(options).run();
    }


}
