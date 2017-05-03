package hu.mik.java2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.mik.java2.book.bean.Book;
import hu.mik.java2.book.dao.SimpleBookDao;

@Service("bookServiceImpl")
public class BookServiceImpl implements BookService {

	@Autowired
	private SimpleBookDao bookDao;
	
	@Override
	public List<Book> listBooks() {
		return this.bookDao.findAll();
	}

	@Override
	public Book getBookById(Integer id) {
		return this.bookDao.findOne(id);
	}

	@Override
	public Book saveBook(Book book) {
		return this.bookDao.save(book);
	}

	@Override
	public Book updateBook(Book book) {
		return this.bookDao.save(book);
	}

	@Override
	public void deleteBook(Book book) {
		this.bookDao.delete(book);
	}

	@Override
	public List<Book> listBooksByAuthor(String author) {
		return this.bookDao.findByAuthorLike(author);
	}

}
