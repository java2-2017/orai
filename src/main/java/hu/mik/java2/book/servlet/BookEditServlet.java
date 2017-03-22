package hu.mik.java2.book.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import hu.mik.java2.book.bean.Book;
import hu.mik.java2.service.BookService;
import hu.mik.java2.service.ServiceUtils;

@WebServlet(urlPatterns = "/book_edit")
public class BookEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Book book;
		BookService bookService = ServiceUtils.getBookService();
		if (req.getParameter("bookId") != null) {
			Integer bookId = new Integer(req.getParameter("bookId"));
			book = bookService.getBookById(bookId);
		} else {
			book = new Book();
		}
		
		req.setAttribute("book", book);
		
		RequestDispatcher requestDispatcher = 
				req.getRequestDispatcher("/book_edit.jsp");
		
		requestDispatcher.forward(req, resp);
	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Book book = new Book();
		try {
			BeanUtils.populate(book, req.getParameterMap());
			if (req.getParameter("id") == null ||
					req.getParameter("id").isEmpty()) {
				book.setId(null);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		BookService bookService = ServiceUtils.getBookService();
	
		Book updateBook;
		if (book.getId() == null) {
			updateBook = bookService.saveBook(book);
		} else {
			updateBook = bookService.updateBook(book);
		}
		req.setAttribute("book", updateBook);
		
		RequestDispatcher requestDispatcher = 
				req.getRequestDispatcher("/book_details.jsp");
		
		requestDispatcher.forward(req, resp);
	}
	
}
