package hu.mik.java2.vaadin;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.mik.java2.book.bean.Book;
import hu.mik.java2.service.BookService;

@SpringView(name = BookView.BOOK_VIEW_NAME)
public class BookView extends VerticalLayout implements View {

	protected static final String BOOK_VIEW_NAME = "book";

	private BeanContainer<Long, Book> bookBean;

	Set<Object> selectItemIds = new HashSet<Object>();
	
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;

	Table bookTable = new Table("Könyvek listája");

	@Override
	public void enter(ViewChangeEvent event) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.addComponent(createFunctionLayout());
		Component newBookComponent = createNewBook();
		layout.addComponent(newBookComponent);
		layout.setComponentAlignment(newBookComponent, Alignment.BOTTOM_CENTER);
		this.addComponent(layout);
		this.addComponent(createTable());
		
		
		
	}

	private Component createNewBook() {
		Button newBookButton = new Button("Új könyv hozzáadása");
		
		newBookButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Book book = new Book();
				ctx.getBean(BookWindow.class).showWindow(book, "Új könyv hozzáadása");
			}
		});
		
		return newBookButton;
	}

	private Component createFunctionLayout() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.addComponent(createSearchField());
		return horizontalLayout;
	}

	private Component createSearchField() {
		final TextField searchfield = new TextField("Szerző keresése");
		searchfield.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (searchfield.getValue().isEmpty()) {
					refreshTable();
				} else {
					bookBean.removeAllItems();
					bookBean.addAll(bookService.listBooksByAuthor(searchfield.getValue()));
				}
			}
		});
		return searchfield;
	}

	protected void refreshTable() {
		bookBean.removeAllItems();
		bookBean.addAll(bookService.listBooks());
	}

	private Component createTable() {
		bookBean = new BeanContainer<Long, Book>(Book.class);
		bookBean.setBeanIdProperty("id");
		bookBean.addAll(bookService.listBooks());
		bookTable.setContainerDataSource(bookBean);
		bookTable.setSizeFull();

		bookTable.addGeneratedColumn("selector", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, final Object itemId, Object columnId) {
				CheckBox deleteCB = new CheckBox();
				deleteCB.addValueChangeListener(new ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						if (selectItemIds.contains(itemId)) {
							selectItemIds.remove(itemId);
						} else {
							selectItemIds.add(itemId);
						}
					}
				});

				return deleteCB;
			}
		});

		bookTable.addGeneratedColumn("view", new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				Button viewBookButton = new Button("Megtekintés");
				viewBookButton.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						BeanItem<Book> bookItem = (BeanItem<Book>) source.getItem(itemId);
					}
				});

				return viewBookButton;
			}
		});

		bookTable.addGeneratedColumn("editBook", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Button editButton = new Button("Szerkesztés");
				return editButton;
			}
		});

		bookTable.setVisibleColumns("selector", "id", "author", "view", "editBook");

		bookTable.setColumnHeader("selector", "Válasszon!");

		return bookTable;
	}

}
