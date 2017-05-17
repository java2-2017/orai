package hu.mik.java2.service.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;

import hu.mik.java2.book.bean.Book;

public interface BookApi {

	ResponseEntity<List<Book>> listBooks();
	
	ResponseEntity<Book> getBookById(Integer id);
	
	ResponseEntity<Book> saveBook(Book book);
	
	ResponseEntity<Void> deleteBook(Integer id);
	
	ResponseEntity<List<Book>> listBooksByAuthor(String author);
}
