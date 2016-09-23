package com.developer.timurnav.multipliers;

import com.developer.timurnav.MatrixMultiplier;

/**
 * @author timurnav
 *         on 23.09.2016.
 */
public class TransposedMatrixMultiplier implements MatrixMultiplier{


    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        final int matrixSize = a.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        for (int col = 0; col < matrixSize; col++) {
            final int[] columnB = new int[matrixSize];
            for (int k = 0; k < matrixSize; k++) {
                columnB[k] = b[k][col];
            }

            for (int row = 0; row < matrixSize; row++) {
                int sum = 0;
                final int[] rowA = a[row];
                for (int k = 0; k < matrixSize; k++) {
                    sum += rowA[k] * columnB[k];
                }
                matrixC[row][col] = sum;
            }
        }
        return matrixC;
    }
}
