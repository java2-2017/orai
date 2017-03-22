package hu.mik.java2.book.bean;

public class Book {
	private Integer id;
	private String author;
	private String title;
	private String description;
	private Integer pubYear;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPubYear() {
		return pubYear;
	}
	public void setPubYear(Integer pubYear) {
		this.pubYear = pubYear;
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", author=" + author + ", title=" + title + ", description=" + description
				+ ", pubYear=" + pubYear + "]";
	}	
}
