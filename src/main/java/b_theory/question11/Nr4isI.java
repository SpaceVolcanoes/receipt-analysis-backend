package b_theory.question11;

import java.util.List;

public class Nr4isI {

    //todo this is a contribution based question so make sure to keep commits separate
    //todo A What does I stand for in SOLID? Explain the principle.
    // Answer: I stands for interface segregation principle. No client should depend on methods it does not use.
    //todo B Give an example. Write actual or pseudo code.

    interface Living {
        void reproduce();
    }

    interface Animal extends Living {
        void eat();
    }

    interface Plant extends Living {
        void photosynthesize();
    }

    private static class Cow implements Animal {

        @Override
        public void reproduce() {
            System.out.println("Made new cows with other cow");
        }

        @Override
        public void eat() {
            System.out.println("Cow bigger and stronger");
        }
    }

    private static class Dandelion implements Plant {

        @Override
        public void reproduce() {
            System.out.println("Threw seed everywhere");
        }

        @Override
        public void photosynthesize() {
            System.out.println("Made the world a better place");
        }
    }

    public static void main(String[] args) {
        Dandelion dandy = new Dandelion();
        Cow thomas = new Cow();
        List<Living> preciousList = List.of(dandy, thomas);

        for (Living living : preciousList) {
            living.reproduce();
        }

        dandy.photosynthesize();
        thomas.eat();
    }

}
