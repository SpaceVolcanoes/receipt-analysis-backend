package ee.taltech.receipt.a_theory.question4;

public class DependencyInjection {

    //todo
    // One of the reasons we use Spring is that it gives us good support for
    // Dependency Injection (DI) and Inversion of Control (IoC)

    //todo p1
    // In your words (do not use wiki definitions)
    // What is Dependency Injection?
    // Answer: Passing specific dependencies (other objects or configuration properties) that our objects need to
    // our objects during their creation either via constructor, field or method injection.

    //todo p2
    // Bring example from your code of Dependency Injection.
    // Paste your code here, but comment it out

    // Constructor injection example from package ee.taltech.receipt.service;

    // @Service
    // public class FileSystemStorageService implements StorageService {
    //
    // private final Path rootLocation;
    //
    // @Autowired
    // public FileSystemStorageService(StorageProperties properties) {
    //      this.rootLocation = Paths.get(properties.getLocation());
    // }

    //todo p3
    // Name 2 benefits of Dependency Injection
    // 1 - We don't need to worry about manually creating our objects and having all of their dependencies also
    //     at hand in the scope of where we would otherwise want to create those objects.
    // 2 - We can replace a specific dependency with one which has a matching interface through out our project
    //     in one place without having go find all the different places where that dependency was in use.

    //todo p4
    // Name 1 disadvantage of Dependency Injection
    // 1 - It comes with a learning curve and handling edge cases may initially take a lot more time. For example
    //     if most of the classes want a new instance of ServiceA but 2 special classes want the same single
    //     special ServiceA.
}
