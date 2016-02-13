package com.etherblood.connect4;

/**
 *
 * @author Philipp
 */
public class Main {

    public static void main(String... args) {
        long millis = 500;
        Connect4Controller controller = new Connect4Controller();
        
//        for (int i = 0; i < 10; i++) {
//            long a = System.currentTimeMillis() + 1000;
//            int count = 0;
//            while(System.currentTimeMillis() < a) {
//                controller.randomPlayout();
//                count++;
//            }
//            System.out.println(count);
//        }
        controller.think(2 * millis);//fill cache
        while(controller.getVictor() == 3) {
            int index = controller.think(millis);
            controller.move(index);
            controller.getState().print();
            System.out.println();
        }
        System.out.println(controller.getVictor());
    }
}
