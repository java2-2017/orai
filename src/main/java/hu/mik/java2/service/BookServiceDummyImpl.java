package hu.mik.java2.service;

import java.util.ArrayList;
import java.util.List;

import hu.mik.java2.book.bean.Book;

public class BookServiceDummyImpl implements BookService {
	private static List<Book> bookList;
	
	static {
		init();
	}
	
	private static void init() {
		bookList = new ArrayList<>();
		for (int i= 0; i < 20; i++) {
			Book temp = new Book();
			temp.setId(i + 1);
			temp.setTitle(i + 1 + ". cím");
			temp.setAuthor(i + 1 + ". szerző");
			temp.setDescription(i + 1 + ". leírás");
			temp.setPubYear(2000 + i);
			bookList.add(temp);
		}
		
	}
	
	private synchronized Integer getNextId() {
		if (bookList.isEmpty()) {
			return 1;
		}
		int max = 0;
		for (Book book : bookList) {
			if (max < book.getId()) {
				max = book.getId();
			}
		}
		return max + 1;
	}
	
	@Override
	public synchronized List<Book> listBooks() {
		return bookList;
	}

	

	@Override
	public synchronized Book getBookById(Integer id) {
		for (Book book : bookList) {
			if (book.getId().equals(id)) {
				return book;
			}
		}
		return null;
	}

	@Override
	public synchronized Book saveBook(Book book) {
		book.setId(getNextId());
		bookList.add(book);
		return book;
	}



	@Override
	public synchronized Book updateBook(Book book) {
		Book bookById = getBookById(book.getId());
		deleteBook(bookById);
		bookList.add(book);
		return book;
	}

	@Override
	public synchronized void deleteBook(Book book) {
		// TODO Auto-generated method stub
		
	}

}
