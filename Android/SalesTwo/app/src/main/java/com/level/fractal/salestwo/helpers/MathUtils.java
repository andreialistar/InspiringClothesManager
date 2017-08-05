package com.level.fractal.salestwo.helpers;

public class MathUtils {
    public static int IncrementToSquare(int n) {
        double root;
        do {
            root = Math.sqrt(n++);
        } while (root - (int)root != 0);

        return n - 1;
    }

    public static int ClosestIntervalStepToPoint(int step, int n) {
        int offset = 0;
        int tolerance = Math.abs(step / 2);
        while (Math.abs(offset - n) > tolerance) {
            offset += step;
        }

        return offset;
    }
}
