package ufc.cmu.promocity.backend.controller;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import ufc.cmu.promocity.backend.model.Book;
import ufc.cmu.promocity.backend.service.BookService;

/**
 * Controlador de livros
 * @author armandosoaressousa
 *
 */
@Component
@Path("/books")
public class BookController {
    private BookService bookService;
    
    /**
     * Construtor
     * @param bookService
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
        bookService.createBooks();
    }
    
    /**
     * Retorna em um JSON todos os livros cadastrados
     * @return
     */
    @GET
    @Produces("application/json")
    public Collection getAllBooks() {
        return bookService.getAllBooks();
    }
    
    /**
     * Dado um id retorna o JSON dos dados do livro
     * @param oid
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("/{oid}")
    public Book getBook(@PathParam("oid") String oid) {
        return bookService.getBook(oid);
    }
    
    /**
     * Dados os dados de um livro adiciona um livro no repositorio
     * @param book
     * @return
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addBook(Book book) {
        bookService.addBook(book);
        return Response.created(URI.create("/" + book.getOid())).build();
    }
    
    /**
     * Dado um id e os dados do livro faz sua atualizacao
     * @param oid
     * @param book
     * @return
     */
    @PUT
    @Consumes("application/json")
    @Path("/{oid}")
    public Response updateBook(@PathParam("oid") String oid, Book book) {
        bookService.updateBook(oid, book);
        return Response.noContent().build();
    }
    
    /**
     * Dado um id de um livro faz sua remocao do repositorio
     * @param oid
     * @return
     */
    @DELETE
    @Path("/{oid}")
    public Response deleteBook(@PathParam("oid") String oid) {
        bookService.deleteBook(oid);
        return Response.ok().build();
    }
}