package c_theory.question14.lessons;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("lessons")
@RestController
public class LessonsController {

    //todo for question 14 there are 4 assignments in total
    // Each person has to do only 1. So 2 person team has to do 2 different ones, 3 person - 3, 4 person - 4.
    // Make sure to commit under your user otherwise points won't count.
    // I didn't number these so you can pick your favorite

    //todo
    // You are creating a rest controller for lessons. Think page where you are looking at lessons like echo360.
    // You need to add necessary annotations and methods to this class.
    // This class should compile.
    // It should run successfully when moved to your application package.
    // Method body is not important and will not be graded.
    // Modifying other classes is unnecessary and will not be graded.

    // [X] todo A add necessary annotations on the class

    // [X] todo B create a method to query lessons (plural)
    @GetMapping("")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "List of lessons"),
    })
    @ApiOperation(value = "Get a list of Lessons", produces = "application/json")
    public ResponseEntity<?> getLessons(
        @RequestParam(defaultValue = "") Long courseId,
        @RequestParam(defaultValue = "") Integer year,
        @RequestParam(defaultValue = "") Boolean orderByVisitors
    ) {
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }

    // [X] todo C create a method to query single lesson
    @GetMapping("{id}")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Lesson found"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such Lesson"),
    })
    @ApiOperation(value = "Get Lesson by url", produces = "application/json")
    public ResponseEntity<?> find(@PathVariable Long id) {
        return new ResponseEntity<>(new Lesson(), HttpStatus.OK);
    }

    // [X] todo D create a method to save a lesson
    @PostMapping("")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Lesson created successfully")
    })
    @ApiOperation(value = "Create a new Lesson", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Lesson lesson) {
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    // [X] todo E create a method to update a lesson
    @PutMapping("{id}")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Lesson updated"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such Lesson")
    })
    @ApiOperation(value = "Update a Lesson", produces = "application/json")
    public ResponseEntity<?> update(@RequestBody Lesson lesson, @PathVariable Long id) {
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    // [X] todo F create a method to delete a lesson
    @DeleteMapping("{id}")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Lesson deleted"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such Lesson"),
    })
    @ApiOperation(value = "Delete Lesson by id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    // [X] todo G assuming each Lesson has students (one-to-many relation) create a method to query lesson's students
    @GetMapping("{id}/students")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Lesson found"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such Lesson"),
    })
    @ApiOperation(value = "Find all students in a lesson")
    public ResponseEntity<?> students(@PathVariable Long id) {
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }

    // [X] todo H create a method to update lesson's name (and nothing else)
    @PutMapping("{id}/name")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Lesson name updated"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such lesson"),
    })
    @ApiOperation(value = "Update lesson's name")
    public ResponseEntity<?> updateName(@PathVariable Long id, @RequestParam(defaultValue = "") String name) {
        // Return the lesson, whose name was updated
        return new ResponseEntity<>(new Lesson(), HttpStatus.OK);
    }

    // [X] todo G modify correct method to support searching lessons by course id while keeping original functionality

    // [X] todo H modify correct method to support searching by year with default being current year (2020)
    // (you can ignore semesters or use year-semester string)

    // [X] todo K modify correct method to order lessons
    // [X] * by most visitors first
    // [X] * by least visitors first
    // [X] (you can assume that by default it searches by predefined lecturer's order)

}
