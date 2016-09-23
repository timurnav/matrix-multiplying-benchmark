package com.developer.timurnav.multipliers;

import com.developer.timurnav.MatrixMultiplier;

/**
 * @author timurnav
 *         on 23.09.2016.
 */
public class BaseMatrixMultiplier implements MatrixMultiplier {

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        final int matrixSize = a.length;
        final int[][] result = new int[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                int sum = 0;
                for (int k = 0; k < matrixSize; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }
}
