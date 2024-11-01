package br.com.orben.bookservice.controllers;

import br.com.orben.bookservice.model.Book;
import br.com.orben.bookservice.proxy.CambioProxy;
import br.com.orben.bookservice.repository.BookRepository;
import br.com.orben.bookservice.response.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @Autowired
    private CambioProxy cambioProxy;

    @Operation(summary = "Find a specific book by your ID")
    @GetMapping("/{id}/{currency}")
    public Book getBook(
            @PathVariable(name = "id") Long id,
            @PathVariable(name = "currency") String currency
    ){
        var port = environment.getProperty("local.server.port");
        var book = repository.findById(id).get();
        book.setEnvironment(port);
        book.setCurrency(currency);
        if(book == null) throw new RuntimeException("Book not found");

        var cambio = cambioProxy.getCambio(book.getPrice(), "USD", book.getCurrency());

        book.setPrice(cambio.getConvertedValue());

        return book;
    }

}
