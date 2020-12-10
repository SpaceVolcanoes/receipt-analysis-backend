package b_theory.question11;

import java.util.Random;

public class Nr3isL {

    private static final Random random = new Random();

    //todo this is a contribution based question so make sure to keep commits separate
    //todo A What does L stand for in SOLID? Explain the principle.
    // Answer: L stands for Liskov substitution principle. The essence or correctness of a program should not change
    // when an object x of type X is used instead of an object y of type Y where X is a subclass of Y.
    //todo B Give an example. Write actual or pseudo code.

    private static class Message {

        private String content;

        public Message(String content) {
            this.content = content;
        }

    }

    private static class Recipient {

        private final String name;

        private Recipient(String name) {
            this.name = name;
        }

        public void accept(Message message) {
            System.out.println(name + " got " + message.content);
        }

    }

    private static class Messenger {

        public void deliver(Recipient recipient, Message message) {
            recipient.accept(message);
        }

    }

    private static class CarrierSeagull extends Messenger {

        @Override
        public void deliver(Recipient recipient, Message message) {
            message.content += " *guano stain*";
            super.deliver(recipient, message);
        }

    }

    private static class UberDriver extends Messenger {

        @Override
        public void deliver(Recipient recipient, Message message) {
            Recipient[] passengers = new Recipient[] {
                recipient,
                pickUpSomeoneElse()
            };

            Recipient someoneWillGetTheMessage = passengers[random.nextInt(passengers.length)];

            super.deliver(someoneWillGetTheMessage, message);
        }

        private Recipient pickUpSomeoneElse() {
            return new Recipient("Bob");
        }

    }

    private static class PostalWorker extends Messenger {

        @Override
        public void deliver(Recipient recipient, Message message) {
            super.deliver(recipient, message);
            recipient.accept(new Message("a Chinese restaurant flyer"));
        }

    }

    public static void main(String[] args) {
        Recipient person = new Recipient("Alice");
        Message greeting = new Message("Good morning wishes");

        Messenger[] messengers = new Messenger[] {
            new Messenger(),
            new CarrierSeagull(),
            new UberDriver(),
            new PostalWorker(),
        };

        messengers[random.nextInt(messengers.length)].deliver(person, greeting);
    }

}
