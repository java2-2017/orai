package hu.mik.java2.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hu.mik.java2.book.bean.Book;
import hu.mik.java2.service.BookService;

@Controller
@RequestMapping("/booking")
public class BookController {

	@Autowired
	private BookService bookService;

//	public BookController(BookService bookService) {
//		this.bookService = bookService;
//	}

	@RequestMapping("/book_list")
	public String listBooks(Model model) {
		model.addAttribute("books", bookService.listBooks());

		return "book_list";
	}

	@RequestMapping("/book_details")
	public String getBookDetails(Integer bookId, Model model) {
		Book book = new Book();

		if (bookId != null) {
			book = bookService.getBookById(bookId);
		}

		model.addAttribute("book", book);

		return "book_details";
	}

	@RequestMapping(value = "/book_edit", method = RequestMethod.GET)
	public String editBookGet(@RequestParam(required = false) Integer bookId, Model model) {
		Book book;
		if (bookId != null) {
			book = bookService.getBookById(bookId);
		} else {
			book = new Book();
		}

		model.addAttribute("book", book);

		return "book_edit";
	}

	@RequestMapping(value = "/book_edit", method = RequestMethod.POST)
	public String editBookPost(Book book, Model model) {

		Book updateBook;
		if (book.getId() == null) {
			updateBook = bookService.saveBook(book);
		} else {
			updateBook = bookService.updateBook(book);
		}

		model.addAttribute("book", updateBook);

		return "book_details";
	}

}
