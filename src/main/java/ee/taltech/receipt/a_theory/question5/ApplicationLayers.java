package ee.taltech.receipt.a_theory.question5;

public class ApplicationLayers {

    //todo
    // Architects insist on having layered architecture in the back-end... ¯\_(ツ)_/¯

    //todo p1
    // Name 3 layers of back-end architecture. Give a brief description for each.
    // 1 - Controller
    // Description: Provides the interface for external communication and handles basic request/response tasks
    // 2 - Service
    // Description: Handles the business logic part of the application, for example makes a decision based on user based
    // on previous user behavior.
    // 3 - Model/data
    // Description: Specifies how domain objects look like, how they are stored for long term and mediates that.

    //todo p2
    // Do you agree with the architects? Why?
    // Yes/No
    // Because: Yes, because that way its easier to change the different aspects of a system - if we need to start
    // providing the same information but in a different format to our API consumers then we only need to modify
    // the outer most layer and don't need to worry about changes to services and storage.

    //todo p3
    // We use objects to transport data between different layers.
    // What is the difference between Entity and Dto? What is same between them?
    // Answer: Entity represents a complete view of an object (usually as it is stored in the database), Dto provides
    // a way to modify that view to match the need of other layers. They are both objects for just holding the data.

}
