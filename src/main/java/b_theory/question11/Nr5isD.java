package b_theory.question11;

public class Nr5isD {

    //todo this is a contribution based question so make sure to keep commits separate
    //todo A What does D stand for in SOLID? Explain the principle.
    // Answer: Both high- and low-level modules should depend on abstractions.
    //todo B Give an example. Write actual or pseudo code.

    interface ComputingSubstrate {
        void compute();
    }

    private static class HumanBrain implements ComputingSubstrate {

        @Override
        public void compute() {
            System.out.println("Biochemical signals are moving along neurons.");
        }
    }

    private static class CPU implements ComputingSubstrate {

        @Override
        public void compute() {
            System.out.println("Electricity is traveling along wires and through logic gates.");
        }
    }

    private static class Conciousness {
        private final ComputingSubstrate computingSubstrate;

        public Conciousness(ComputingSubstrate computingSubstrate) {
            this.computingSubstrate = computingSubstrate;
        }

        public void generateQualia() {
            this.computingSubstrate.compute();
        }
    }
}
