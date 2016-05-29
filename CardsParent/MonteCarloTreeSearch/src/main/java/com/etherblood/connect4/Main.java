package com.etherblood.connect4;

import java.util.Random;

/**
 *
 * @author Philipp
 */
public class Main {

    public static void main(String... args) {
//        long millis = 500;
//        Connect4Controller controller = new Connect4Controller();
//
//        controller.think(2 * millis);//fill cache
//        while(controller.getVictor() == 3) {
//            int index = controller.think(millis);
//            controller.move(index);
//            controller.getState().print();
//            System.out.println();
//        }
//        System.out.println(controller.getVictor());

        System.out.println(-925920985 * 899468951);

        System.out.println(gcd(36, 66));
        tmp();
        
            long k = 82374;
            System.out.println(Integer.toUnsignedString((int) k));
            long millis = -System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            
            long a = randomBitsSet(new Random(), i % 33, 32) | 1;
            long b = modinvIt(a, 1L << 32);
            k *= a;
            k *= b;
        
        }
        millis += System.currentTimeMillis();
        System.out.println(millis + "ms");
        millis = -System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            
            long a = randomBitsSet(new Random(), i % 33, 32) | 1;
            long b = modinv(a, 1L << 32);
            k *= a;
            k *= b;
        
        }
        millis += System.currentTimeMillis();
        System.out.println(millis + "ms");
            System.out.println(Integer.toUnsignedString((int) k));
    }

    private static void tmp() {
        //sets random k bits of the first size bits of mask to true
        Random rng = new Random();
        long mask = randomBitsSet(rng, 32, Long.SIZE);
        System.out.println(Long.toUnsignedString(mask, 16));
    }

    private static long randomBitsSet(Random rng, int numTrueBits, int numTotalBits) {
        long mask = 0;
        for (int j = numTotalBits - numTrueBits; j < numTotalBits; ++j) {
            int r = rng.nextInt(j + 1);
            long b = 1L << r;
            if ((mask & b) != 0) {
                mask |= (1L << j);
            } else {
                mask |= b;
            }
        }
        return mask;
    }
    
    private static int gcd(int a, int b) {
        while(b != 0) {
            int tmp = a;
            a = b;
            b = tmp % b;
        }
        return a;
    }

    private static long modinv(long x, long n) {
        long[] arr = new long[2];
        euclid(x % n, n, arr);
        long a = arr[0];
//        long b = euclidIt(x % n, n);
//        assert a == b;
//////        long g = arr[2];
//////        assert g == 1;

        // a * x + b * n = 1 therefore
        // a * x = 1 (mod n)
        return a % n;
    }

    private static long modinvIt(long x, long n) {
        return euclidIt(x % n, n) % n;
    }
    
    private static long euclidIt(long a, long b) {
        long x = 1, y = 0;
        long xLast = 0, yLast = 1;
        long q, r, tmp;
        while (a != 0) {
            r = b % a;
            q = b / a;
            b = a;
            a = r;
            
            tmp = xLast - q * x;
            xLast = x;
            x = tmp;
            tmp = yLast - q * y;
            yLast = y;
            y = tmp;
        }
        return xLast;
    }

    private static void euclid(long x, long y, long[] out) {
        /*"""Given x < y, find a and b such that a * x + b * y = g where, g is the
    gcd of x and y.  Returns (a,b,g)."""*/
        assert x < y;
        assert x >= 0;
        assert y > 0;

        if (x == 0) {
            // gcd(0,y) = y
            out[0] = 0;
            out[1] = 1;
            assert y == 1;
//////            out[2] = y;
        } else {
            // Write y as y = dx + r
            long d = y / x;
            long r = y - d * x;

            // Compute for the simpler problem.
            euclid(r, x, out);
            long a = out[0];
            long b = out[1];

            // Then ar + bx = g     -->
            //      a(y-dx) + bx = g    -->
            //      ay - adx + bx = g    -->
            //      (b-ad)x + ay = g
            out[0] = b - a * d;
            out[1] = a;
        }
    }
}
