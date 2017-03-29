package hu.mik.java2.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import hu.mik.java2.book.bean.Book;

public class BookServiceNativeDbImpl implements BookService {

	private DataSource dataSource;

	public BookServiceNativeDbImpl() {
		try {
			InitialContext context = new InitialContext();

			this.dataSource = (DataSource) context.lookup("book/bookDatasource");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Book> listBooks() {
		List<Book> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(
					"SELECT id, author, title, description, pub_year" + " FROM t_book b ORDER BY b.id");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Book book = this.mapResultSetToBook(resultSet);

				books.add(book);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.closeResource(resultSet);
			this.closeResource(preparedStatement);
			this.closeResource(connection);
		}

		return books;
	}

	private void closeResource(AutoCloseable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
		Book book = new Book();

		book.setId(resultSet.getInt(1));
		book.setAuthor(resultSet.getString(2));
		book.setTitle(resultSet.getString(3));
		book.setDescription(resultSet.getString(4));
		book.setPubYear(resultSet.getInt(5));

		return book;
	}

	@Override
	public Book getBookById(Integer id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(
					"SELECT id, author, title, description, pub_year"
			+ " FROM t_book b WHERE b.id = ?");
			preparedStatement.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()) {
				return this.mapResultSetToBook(resultSet);
			} else {
				throw new RuntimeException("Book not found with id: " + id);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.closeResource(resultSet);
			this.closeResource(preparedStatement);
			this.closeResource(connection);
		}
	}

	@Override
	public Book saveBook(Book book) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Integer id = this.getNextId();

		try {
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(
					"INSERT INTO t_book(id, author, title, description, pub_year)"
			+ " VALUES(?,?,?,?,?)");
			
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, book.getAuthor());
			preparedStatement.setString(3, book.getTitle());
			preparedStatement.setString(4, book.getDescription());
			preparedStatement.setInt(5, book.getPubYear());
			
			preparedStatement.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.closeResource(preparedStatement);
			this.closeResource(connection);
		}

		return this.getBookById(id);
	}

	@Override
	public Book updateBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBook(Book book) {
		// TODO Auto-generated method stub

	}
	
	private Integer getNextId() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(
					"SELECT s_book.nextval FROM dual");
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new RuntimeException();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.closeResource(resultSet);
			this.closeResource(preparedStatement);
			this.closeResource(connection);
		}
	}

}
