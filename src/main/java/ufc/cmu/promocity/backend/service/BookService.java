package ufc.cmu.promocity.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale.Category;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import ufc.cmu.promocity.backend.model.Book;
import ufc.cmu.promocity.backend.service.exceptions.BookNotFoundException;

/**
 * Servico Livro
 * @author armandosoaressousa
 *
 */
@Service
public class BookService {
	private final ConcurrentMap<String, Book> books;

	/**
	 * Contrutor
	 */
	public BookService() {
		this.books = new ConcurrentHashMap<>();
	}

	/**
	 * Retorna todos os livros cadastrados
	 * @return
	 */
	public Collection<Book> getAllBooks() {
		Collection<Book> allBooks = books.values();
		return allBooks.isEmpty() ? Collections.emptyList() : new ArrayList<>(allBooks);
	}
	
	/**
	 * Dado um id retorna um livro
	 * @param oid
	 * @return
	 */
	public Book getBook(String oid) {
		return books.get(oid);
	}
	
	/**
	 * Dado um livro adiciona no repositorio
	 * @param book
	 */
	public void addBook(Book book) {
		if (Objects.isNull(book.getOid())) book.setOid(UUID.randomUUID().toString());
		
		this.books.put(book.getOid(), book);
	}
	
	/**
	 * Cria uma lista de livros para teste
	 */
	public void createBooks() {
		Book book1 = new Book("b1", "Teste Livro 1", "Armando Soares Sousa", ufc.cmu.promocity.backend.model.Category.FICTION);
		Book book2 = new Book("b2", "Teste Livro 2", "João da Silva Sauro", ufc.cmu.promocity.backend.model.Category.FICTION);
		this.addBook(book1);
		this.addBook(book2);
	}
	
	/**
	 * Dado um id e os dados de um livro faz a atualizacao
	 * @param oid
	 * @param book
	 * @return
	 */
	public Book updateBook(String oid, Book book) {
		if (books.containsKey(oid)) {
			return books.put(oid, book);
		}
		throw new BookNotFoundException("Can't update book. Book for oid: " + oid + " not found");
	}
	
	/**
	 * Dado um id remove o livro
	 * @param oid
	 */
	public void deleteBook(String oid) {
		if (!books.containsKey(oid)) {
			throw new BookNotFoundException("Can't delete book. Book for oid: " + oid + " not found");
		}
		this.books.remove(oid);
	}
}