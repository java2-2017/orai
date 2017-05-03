package hu.mik.java2.book.dao;

import java.util.List;

import hu.mik.java2.book.bean.Book;

public interface BookDao {
	public Book findOne(Integer id);
	
	public List<Book> findAll();
	
	public Book save(Book book);
	
	public void delete(Book book);
	
	public List<Book> findByAuthorLike(String author);
}
