package b_theory.question11;

import java.util.List;

public class Nr1isS {

    //todo this is a contribution based question so make sure to keep commits separate
    //todo A What does S stand for in SOLID? Explain the principle.
    // Answer: S stands for Single responsibility principle
    // Principle states that every class should have a single reason to change. Principle applies to functions and to modules as well.
    // Sometimes it is very clear that your class is doing different things and as such there are different reasons why it could change.
    // At other times one should use common sense when determining what the single thing for a class to do is.
    // In java one starts with a typical single service for a domain, let's call it UserService.
    // As class expands with new functionality and lines increase one should reconsider and refactor the service to separate components.
}

//todo B Give an example. Write actual or pseudo code.
// this is an example of service doing 2 separate things
class UserServiceBefore{

    public List<User> getUsers(){
        //calls a chain of private methods
        return List.of();
    }

    public void calculatePayForUser(User user){
        //calls a chain of private methods
    }

    //private methods cut
}

//todo here we separate calculatePayForUser functionality from userService
class UserServiceAfter{

    public List<User> getUsers(){
        //calls a chain of private methods
        return List.of();
    }

    //private methods cut
}

//todo by creating separate class for PayCalculator
class PayCalculator{

    public void calculatePayForUser(User user){
        //calls a chain of private methods
    }

    //private methods cut
}

class User{

}
