package c_theory.question14.chairs;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("chairs")
@RestController
public class ChairsController {

    //todo for question 14 there are 4 assignments in total
    // Each person has to do only 1. So 2 person team has to do 2 different ones, 3 person - 3, 4 person - 4.
    // Make sure to commit under your user otherwise points won't count.
    // I didn't number these so you can pick your favorite

    //todo
    // You are creating a rest controller for chairs.
    // Think a backoffice system for furniture shop like Aatrium or some Kalamaja chair boutique.
    // You need to add necessary annotations and methods to this class.
    // This class should compile.
    // It should run successfully when moved to your application package.
    // Method body is not important and will not be graded.
    // Modifying other classes is unnecessary and will not be graded.

    // add necessary annotations on the class

    // B create a method to query chairs (plural)
    @GetMapping("")
    public ResponseEntity<?> getChairs(
        @RequestParam(defaultValue = "") String type,
        @RequestParam(defaultValue = "") Boolean inStock,
        @RequestParam(defaultValue = "") Boolean orderByPriceAscending
    ) {
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }

    // C create a method to query single chair
    @GetMapping("{id}")
    public ResponseEntity<?> getChair(@PathVariable Long id) {
        return new ResponseEntity<>(new Chair(), HttpStatus.OK);
    }

    // D create a method to save a chair
    @PostMapping("")
    public ResponseEntity<?> addChair(@RequestBody Chair chair) {
        return new ResponseEntity<>(chair, HttpStatus.OK);
    }

    // E create a method to update a chair
    @PutMapping("{id}")
    public ResponseEntity<?> updateChair(@RequestBody Chair chair, @PathVariable Long id) {
        return new ResponseEntity<>(chair, HttpStatus.OK);
    }

    // F create a method to delete a chair
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteChair(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    // G assuming each chair has a designer (one-to-one relation) create a method to query chair's designer
    @GetMapping("{id}/designer")
    public ResponseEntity<?> getDesigner(@PathVariable Long id) {
        return new ResponseEntity<>(new Designer(), HttpStatus.OK);
    }

    // H create a method to update chair's name (and nothing else)
    @PutMapping("{id}/name")
    public ResponseEntity<?> updateName(@PathVariable Long id, @RequestParam(defaultValue = "") String name) {
        return new ResponseEntity<>(new Chair(), HttpStatus.OK);
    }

    // I modify correct method to support searching chairs by type while keeping original functionality

    // J modify correct method to support searching chairs by whether chair is in stock while keeping original functionality

    // K modify correct method to order/sort chairs
    // * by lowest priced first
    // * by highest priced first
    // (you can assume that by default it searches most popular first)
}
