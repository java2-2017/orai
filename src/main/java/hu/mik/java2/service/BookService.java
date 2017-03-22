package hu.mik.java2.service;

import java.util.List;

import hu.mik.java2.book.bean.Book;

public interface BookService {
	public List<Book> listBooks();
	
	public Book getBookById(Integer id);
	
	public Book saveBook(Book book);
	
	public Book updateBook(Book book);
	
	public void deleteBook(Book book);
}
