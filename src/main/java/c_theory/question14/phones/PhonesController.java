package c_theory.question14.phones;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("phones")
@RestController
@AllArgsConstructor
public class PhonesController {

    //todo for question 14 there are 4 assignments in total
    // Each person has to do only 1. So 2 person team has to do 2 different ones, 3 person - 3, 4 person - 4.
    // Make sure to commit under your user otherwise points won't count.
    // I didn't number these so you can pick your favorite

    //todo
    // You are creating a rest controller for lessons. Think of a phone shop.
    // You need to add necessary annotations and methods to this class.
    // This class should compile.
    // It should run successfully when moved to your application package.
    // Method body is not important and will not be graded.
    // Modifying other classes is unnecessary and will not be graded.

    //todo A add necessary annotations to the class

    //todo B create a method to query phones (plural)
    @GetMapping("")
    public ResponseEntity<List<Phone>> getPhones(
        @RequestParam(defaultValue = "") String manufacturer,
        @RequestParam(defaultValue = "") Long priceFrom,
        @RequestParam(defaultValue = "") Long priceTo,
        @RequestParam(defaultValue = "true") Boolean sortNewFirst
    ) {
        return new ResponseEntity<>(List.of(new Phone()), HttpStatus.OK);
    }

    //todo C create a method to query single phone
    @GetMapping("{name}")
    public ResponseEntity<Phone> getPhone(@PathVariable String name) {
        return new ResponseEntity<>(new Phone(), HttpStatus.OK);
    }

    //todo D create a method to add a phone
    @PostMapping("")
    public ResponseEntity<Phone> addPhone(@RequestBody Phone phone) {
        return new ResponseEntity<>(new Phone(), HttpStatus.OK);
    }

    //todo E create a method to update a phone
    @PatchMapping("{name}")
    public ResponseEntity<Phone> updatePhone(@RequestBody Phone phone, @PathVariable String name) {
        return new ResponseEntity<>(new Phone(), HttpStatus.OK);
    }

    //todo F create a method to delete a phone
    @DeleteMapping("{name}")
    public ResponseEntity<Phone> deletePhone(@PathVariable String name) {
        return new ResponseEntity<>(new Phone(), HttpStatus.OK);
    }

    //todo G assuming each phone has apps installed (one-to-many relation) create a method to query phone's apps
    @GetMapping("{name}/apps")
    public ResponseEntity<List<App>> getApps(@PathVariable String name) {
        return new ResponseEntity<>(List.of(new App()), HttpStatus.OK);
    }

    //todo H create a method to update phone's price (and nothing else)
    @PatchMapping("{name}/price")
    public ResponseEntity<Phone> updatePrice(@PathVariable String name, @RequestParam(defaultValue = "") String price) {
        return new ResponseEntity<>(new Phone(), HttpStatus.OK);
    }

    //todo I modify correct method to support searching by manufacturer while keeping original functionality
    //todo J modify correct method to support searching by price range: priceFrom-priceTo while keeping original functionality

    //todo K modify correct method to order/sort chairs (e: phones?)
    // * by latest released date first
    // * by earliest released date first
    // (you can assume that by default it searches most popular (e: newest?) first)
}
