package hu.mik.java2.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.mik.java2.book.bean.Book;

public interface SimpleBookDao extends JpaRepository<Book, Integer> {
	public List<Book> findByAuthorLike(String author);
}
