package hu.mik.java2.service.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.mik.java2.book.bean.Book;
import hu.mik.java2.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/book-rest")
@Api("/book-rest")
public class BookApiImpl implements BookApi {

	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;

	@Override
	@ApiOperation(value="List all books.", notes = "This will return all the books.", 
				  response = Book.class, responseContainer = "List")
	@ApiResponses(value={@ApiResponse(code= 200, message="List of books.", 
			      response=Book.class, responseContainer = "List")})
	@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Book>> listBooks() {
		return new ResponseEntity<>(this.bookService.listBooks(), HttpStatus.OK);
	}

	@Override
	@ApiOperation(value="Get a book by id.", notes = "This will return a book by its id.", 
	  			  response = Book.class)
	@ApiResponses(value={@ApiResponse(code= 200, message="A book object.", 
    			  response=Book.class)})
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(this.bookService.getBookById(id), HttpStatus.OK);
	}

	@Override
	@ApiOperation(value="Save book.", notes = "Saves a book", 
	  			  response = Book.class)
	@ApiResponses(value={@ApiResponse(code= 201, message="Successful creation/update", 
	              response=Book.class)})
	@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Book> saveBook(Book book) {
		return new ResponseEntity<>(this.bookService.saveBook(book), HttpStatus.CREATED);
	}

	@Override
	@ApiOperation(value="Delete book.", notes = "Delete a book by id", 
	              response = Void.class)
	@ApiResponses(value={@ApiResponse(code= 200, message="Successfully deleted", 
    			  response=Void.class)})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
		Book book = this.bookService.getBookById(id);
		this.bookService.deleteBook(book);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	@ApiOperation(value="List books by author.", notes = "Lists books by the given author.", 
	  			  response = Book.class, responseContainer="List")
	@ApiResponses(value={@ApiResponse(code= 200, message="List of books.", 
    			  response=Book.class, responseContainer="List")})
	@RequestMapping(value = "/byAuthor/{author}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Book>> listBooksByAuthor(@PathVariable("author") String author) {
		return new ResponseEntity<>(this.bookService.listBooksByAuthor(author), HttpStatus.OK);
	}

}
