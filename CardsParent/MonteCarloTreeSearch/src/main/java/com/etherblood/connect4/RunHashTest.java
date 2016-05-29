package com.etherblood.connect4;

import java.util.HashSet;

/**
 *
 * @author Philipp
 */
public class RunHashTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connect4Controller controller = new Connect4Controller();
        HashTest<LongWrapper> test = new HashTest<>(1 << 20);
        Connect4Hash hashFunc = new Connect4Hash(7, 6);
        for (int i = 0; i < 1000000; i++) {
            while (!controller.getState().hasGameEnded()) {
                controller.randomMove();
                long[] playerTokens = controller.getState().getPlayerTokens();
                long longHash = hashFunc.hash(playerTokens[0], playerTokens[1]);
                test.add(new LongWrapper(longHash));
            }
            controller.reset();
        }
        float quality = test.quality();
        System.out.println(String.format("%.0f%%", quality * 100));
    }

    static class LongWrapper {

        public final long value;

        public LongWrapper(long value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            int hash = Long.hashCode(value);
//            hash = jenkinsHash(hash);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return ((LongWrapper) obj).value == value;
        }
    }

    static int jenkinsHash(int a) {
        a = (a + 0x7ed55d16) + (a << 12);
        a = (a ^ 0xc761c23c) ^ (a >> 19);
        a = (a + 0x165667b1) + (a << 5);
        a = (a + 0xd3a2646c) ^ (a << 9);
        a = (a + 0xfd7046c5) + (a << 3);
        a = (a ^ 0xb55a4f09) ^ (a >> 16);
        return a;
    }
}
