package com.developer.timurnav.multipliers;

import com.developer.timurnav.MatrixMultiplier;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author timurnav
 *         on 23.09.2016.
 */
public class ConcurrentMatrixMultiplier implements MatrixMultiplier {

    private final ExecutorService executor;

    public ConcurrentMatrixMultiplier(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public int[][] multiply(int[][] a, int[][] b) throws InterruptedException {
        final int matrixSize = a.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        List<Callable<Void>> tasks = IntStream.range(0, matrixSize)
                .parallel()
                .mapToObj(i -> new Callable<Void>() {
                    private final int[] tempColumn = new int[matrixSize];

                    @Override
                    public Void call() throws Exception {
                        for (int c = 0; c < matrixSize; c++) {
                            tempColumn[c] = b[c][i];
                        }
                        for (int j = 0; j < matrixSize; j++) {
                            int row[] = a[j];
                            int sum = 0;
                            for (int k = 0; k < matrixSize; k++) {
                                sum += tempColumn[k] * row[k];
                            }
                            matrixC[j][i] = sum;
                        }
                        return null;
                    }
                })
                .collect(toList());

        executor.invokeAll(tasks);

        return matrixC;
    }
}
