package c_theory.question14.blogs;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("blogs")
@RestController
public class BlogsController {

    //todo for question 14 there are 4 assignments in total
    // Each person has to do only 1. So 2 person team has to do 2 different ones, 3 person - 3, 4 person - 4.
    // Make sure to commit under your user otherwise points won't count.
    // I didn't number these so you can pick your favorite

    //todo
    // You are creating a rest controller for blogs. Think blog aggregator or blog collection.
    // You need to add necessary annotations and methods to this class.
    // This class should compile.
    // It should run successfully when moved to your application package.
    // Method body is not important and will not be graded.
    // Modifying other classes is unnecessary and will not be graded.

    //todo A add necessary annotations on the class

    //todo B create a method to query blogs (plural)

    //todo I-J modify correct method to support pagination, pagination is done by page and size
    //todo I add page (pagination starts at page 1)
    //todo J add size (default page size is 20)

    //todo K modify correct method to order blogs
    // * by most recent first
    // * by least recent first
    // (you can assume that by default it searches by most popular first)

    @GetMapping()
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "List of blogs"),
    })
    @ApiOperation(value = "Get a list of blogs", produces = "application/json")
    public List<Blog> listBlogs(
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = "20") int size,
        @ApiParam(allowableValues = "published-asc, published-desc")
        @RequestParam(value = "sort_by", required = false, defaultValue = "views-desc") String sortBy
    ) {
        return List.of(new Blog(), new Blog(), new Blog());
    }

    //todo C create a method to query single blog

    @GetMapping("{url}")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Blog found"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such Blog"),
    })
    @ApiOperation(value = "Get Blog by url", produces = "application/json")
    public Blog find(@PathVariable String url) {
        return new Blog();
    }

    //todo D create a method to save a new blog

    @PostMapping()
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Blog created successfully"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Blog is missing url or duplicate"),
    })
    @ApiOperation(value = "Create a new Blog", produces = "application/json")
    public Blog create(@RequestBody Blog blog) {
        return blog;
    }

    //todo E create a method to update a blog

    @PutMapping("{url}")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Blog updated"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such Blog"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Can't set Blog url to empty")
    })
    @ApiOperation(value = "Create a new Blog", produces = "application/json")
    public Blog update(@RequestBody Blog blog, @PathVariable String url) {
        return blog;
    }

    //todo F create a method to delete a blog [-]

    @DeleteMapping("{url}")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Blog deleted"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such Blog"),
    })
    @ApiOperation(value = "Delete Blog by url")
    public ResponseEntity<Object> delete(@PathVariable String url) {
        return ResponseEntity.ok().build();
    }

    //todo G assuming each blog has only 1 author (one-to-one relation) create a method to query blog's author

    @GetMapping("{url}/author")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Found Blog author"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such Blog to find the Author by"),
    })
    @ApiOperation(value = "Get Blog by url", produces = "application/json")
    public Author author(@PathVariable String url) {
        return new Author();
    }

    //todo H create a method to update blog url (and nothing else)

    @PatchMapping("{url}")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Updated"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such Blog"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Can't set Blog url to empty"),
    })
    @ApiOperation(value = "Update Blog entry url", produces = "application/json")
    public Blog patch(@RequestBody Blog blog, @PathVariable String url) {
        return new Blog();
    }

}
