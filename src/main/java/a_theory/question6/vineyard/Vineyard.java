package a_theory.question6.vineyard;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("vineyard")
@RestController
public class Vineyard {

    //todo for question 6 there are 4 assignments in total
    // Each person has to do only 1. So 2 person team has to do 2 different ones, 3 person - 3, 4 person - 4.
    // Make sure to commit under your user otherwise points won't count.
    // I didn't number these so you can pick your favorite

    //todo create a working api for a wineyard
    // It compiles and runs. You need to use proper annotations, methods, etc, however to ease the process you can use empty methods (examples below).
    // Follow the story to have only the necessary methods in it

    //todo 1
    // Maxime retired to start a vineyard business.
    // As people are shopping more online Maxime has decided to follow the trend.
    // His cousin Garpard recently started a job with a major software firm in Paris.
    // Maxime writes:
    // Dear Gaspard!
    // I want to share my sweet vines with all the Parisians.
    // I want to create a web shop with the best selection!
    // Imagine! People can view a list of my magnificent wines:
    // they can filter by the region (optional), year (optional),
    // they can search by name (optional) and grape (optional).
    // After they search they can click on a wine and see a detailed view with the best description.
    // I do not need to add new wines because I have excel-macro done by Josephine.
    // However as she is only 5 years old I need to update the region, description, grape etc.
    // Create me the best back-end API!
    // Uncle Maxime


    @GetMapping()
    @ApiOperation(
        value = "Get all wines",
        produces = "text/plain"
    )
    ResponseEntity<?> getWines() {
        return new ResponseEntity<>(List.of(new Wine()), HttpStatus.OK);
    }

    @GetMapping("wine")
    @ApiOperation(
        value = "Display wines by filter/search parameters",
        produces = "text/plain"
    )
    ResponseEntity<?> getSomeWines(
        @RequestParam(defaultValue = "") String region,
        @RequestParam(defaultValue = "") Integer year,
        @RequestParam(defaultValue = "") String name,
        @RequestParam(defaultValue = "") String grape
    ) {
        return new ResponseEntity<>(List.of(new Wine()), HttpStatus.OK);
    }

}
