package hu.mik.java2.book.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.mik.java2.book.bean.Book;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class BookDaoImpl implements BookDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Book findOne(Integer id) {
		return this.entityManager.find(Book.class, id);
	}

	@Override
	public List<Book> findAll() {
		return this.entityManager
				.createQuery("SELECT b FROM Book b ORDER BY b.id", Book.class)
				.getResultList();
	}

	@Override
	public Book save(Book book) {
		if(book.getId() == null) {
			this.entityManager.persist(book);
			
			return book;
		} else {
			return this.entityManager.merge(book);
		}
	}

	@Override
	public void delete(Book book) {
		if(!this.entityManager.contains(book)) {
			this.entityManager.merge(book);
		}
		
		this.entityManager.remove(book);
	}

	@Override
	public List<Book> findByAuthorLike(String author) {
		return this.entityManager
			.createQuery("SELECT b FROM Book b WHERE b.author LIKE :author", Book.class)
			.setParameter("author", author)
			.getResultList();
	}
}
