package hu.mik.java2.book.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import hu.mik.java2.book.bean.Book;
import hu.mik.java2.util.HibernateUtil;

//@Repository
public class BookDaoHibernateImpl implements BookDao {

	@Autowired
	private HibernateUtil hibernateUtil;
	
	@Override
	public Book findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findAll() {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class);
		List<Book> list = criteria.list();
		
		transaction.commit();
		
		return list;
	}

	@Override
	public Book save(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Book> findByAuthorLike(String author) {
		// TODO Auto-generated method stub
		return null;
	}

}
