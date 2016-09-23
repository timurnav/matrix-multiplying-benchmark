package com.developer.timurnav;

import com.developer.timurnav.multipliers.BaseMatrixMultiplier;
import com.developer.timurnav.multipliers.ConcurrentMatrixMultiplier;
import com.developer.timurnav.multipliers.TranspondedMatrixMulptiplier;
import com.developer.timurnav.util.MatrixUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author timurnav
 *         on 23.09.2016.
 */
public class MatrixMultiplierTest {

    private static final MatrixUtil matrixUtil = new MatrixUtil();
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    private int[][] matrixA;
    private int[][] matrixB;

    private int[][] expectedResult;

    @BeforeClass
    public void init() {
        matrixA = matrixUtil.create(100);
        matrixB = matrixUtil.create(100);

        expectedResult = new BaseMatrixMultiplier().multiply(matrixA, matrixB);
    }

    @DataProvider
    public static Object[][] getMultipliers() {
        return new Object[][]{
                {new TranspondedMatrixMulptiplier()},
                {new ConcurrentMatrixMultiplier(executor)}
        };
    }

    @Test(dataProvider = "getMultipliers")
    public void testMultiply(MatrixMultiplier multiplier) throws Exception {
        int[][] result = multiplier.multiply(matrixA, matrixB);
        matrixUtil.compare(result, expectedResult);
    }

}