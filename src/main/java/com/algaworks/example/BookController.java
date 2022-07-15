package com.algaworks.example;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/books")
@Tag(name = "Book")
public class BookController {

    private final Set<Book> books = new HashSet<>();

    @GetMapping
    @Operation(summary = "List books")
    public Set<Book> getBooks(){
        return books;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a book by id")
    public Book getBookById(@PathVariable UUID id){
        return books.stream().filter(book -> book.getId().equals(id)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping

    @Operation(summary = "Create a book", responses = {
            @ApiResponse(responseCode = "200"),
//  Uncomment the lines bellow to fix the problem
//            @ApiResponse(responseCode = "400", description = "Invalid data", content = {
//                    @Content(schema = @Schema(ref = "Problem")) })
    })
    public Book createBook(@Valid @RequestBody Book book) {
        book.setId(UUID.randomUUID());
        books.add(book);
        return book;
    }

}
