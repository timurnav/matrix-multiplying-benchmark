package com.developer.timurnav;

import com.developer.timurnav.multipliers.BaseMatrixMultiplier;
import com.developer.timurnav.multipliers.ConcurrentMatrixMultiplier;
import com.developer.timurnav.multipliers.TranspondedMatrixMulptiplier;
import com.developer.timurnav.util.MatrixUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author timurnav
 *         on 23.09.2016.
 */
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@BenchmarkMode({Mode.SingleShotTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class MatrixMultiplierBenchmark {

    // Matrix size
    @Param({"100", "1000"})
    private int matrixSize;

    private static final int THREAD_NUMBER = 10;
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);
    private static final MatrixUtil matrixUtil = new MatrixUtil();

    private static int[][] matrixA;
    private static int[][] matrixB;

    @Setup
    public void setUp() {
        matrixA = matrixUtil.create(matrixSize);
        matrixB = matrixUtil.create(matrixSize);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(MatrixMultiplierBenchmark.class.getSimpleName())
                .threads(1)
                .forks(1)
                .timeout(TimeValue.minutes(5))
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public int[][] base() throws Exception {
        return new BaseMatrixMultiplier().multiply(matrixA, matrixB);
    }

    @Benchmark
    public int[][] transponded() throws Exception {
        return new TranspondedMatrixMulptiplier().multiply(matrixA, matrixB);
    }

    @Benchmark
    public int[][] concurrent() throws Exception {
        return new ConcurrentMatrixMultiplier(executor).multiply(matrixA, matrixB);
    }

    @TearDown
    public void tearDown() {
        executor.shutdown();
    }

}