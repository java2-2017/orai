package hu.mik.java2.service;

public class ServiceUtils {
	public static BookService getBookService() {
		return new BookServiceDummyImpl();
	}
}
