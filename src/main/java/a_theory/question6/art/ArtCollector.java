package a_theory.question6.art;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@RestController
@RequestMapping("art")
public class ArtCollector {

    //todo for question 6 there are 4 assignments in total
    // Each person has to do only 1. So 2 person team has to do 2 different ones, 3 person - 3, 4 person - 4.
    // Make sure to commit under your user otherwise points won't count.
    // I didn't number these so you can pick your favorite

    //todo create a working api for an art collector
    // It compiles and runs. You need to use proper annotations, methods, etc, however to ease the process you can use empty methods (examples below).
    // Follow the story to have only the necessary methods in it

    //todo 1
    // Hello, my friend, my name is Ruslan, I like collecting art.
    // I have bought my first painting when I was 15.
    // By now I have the best collection on this side of river Volga.
    // I need to have a proper catalog. I have so much art.
    // I heard you have a very good brain. Very smart.
    // I need a big list for my great catalog. I don't want to search for my art, I want to see it.
    // There are many pages in a catalog. Each page has 50 results, so it's size is 50. I can change the size, but 50 is default.
    // And then there are pages, I start at page 1 and change pages as I go, but first page is 1.
    // How can you start a gallery not from page one?!
    // To my gallery I always add new paintings. One or two a week. I have a good business in town.
    // I also need a details page for each painting, so I can take one painting and read important information.
    // Like when did I buy the painting and how much was the painting. Of course the author and the name.
    // I keep all my paintings, never throw anything away. Just buy bigger house.
    // How much do you want for this system?

    @GetMapping("page/{pageNr}")
    @ApiOperation(
        value = "List paintings on a page",
        produces = "application/json"
    )
    @ApiResponse(code = SC_OK, message = "Page found")
    List<Painting> getPage(@PathVariable Long pageNr, @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize) {
        return List.of();
    }

    @PostMapping
    @ApiOperation(
        value = "Add a new painting",
        produces = "application/json"
    )
    @ApiResponses({
        @ApiResponse(code = SC_OK, message = "New painting added"),
        @ApiResponse(code = SC_BAD_REQUEST, message = "Invalid painting")
    })
    public Painting add(@RequestBody Painting painting) {
        return painting;
    }

    @GetMapping("painting/{id}")
    @ApiOperation(
        value = "Get a specific painting",
        produces = "application/json"
    )
    @ApiResponses({
        @ApiResponse(code = SC_OK, message = "Painting found"),
        @ApiResponse(code = SC_BAD_REQUEST, message = "That painting was not found")
    })
    public Painting getPainting(@PathVariable Long id) {
        return new Painting();
    }

}
