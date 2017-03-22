package hu.mik.java2.book.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.mik.java2.book.bean.Book;

@WebServlet(urlPatterns = "/book_details")
public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Book book = new Book();
		book.setId(1);
		book.setAuthor("Könyv szerzője");
		book.setDescription("Könyv leírása");
		book.setPubYear(2000);
		book.setTitle("Könyv címe");
		
		req.setAttribute("book", book);
		
		RequestDispatcher requestDispatcher = 
				req.getRequestDispatcher("/book_details.jsp");
		
		requestDispatcher.forward(req, resp);
	}
	
	

}
