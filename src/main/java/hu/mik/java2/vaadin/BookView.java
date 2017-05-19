package hu.mik.java2.vaadin;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.mik.java2.book.bean.Book;
import hu.mik.java2.service.BookService;

@SuppressWarnings({"serial", "unchecked"})
@SpringView(name = BookView.BOOK_VIEW_NAME)
public class BookView extends VerticalLayout implements View {

	protected static final String BOOK_VIEW_NAME = "book";

	private BeanContainer<Long, Book> bookBean;

	Set<Object> selectedItemIds = new HashSet<Object>();
	
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;

	Table bookTable = new Table("Könyvek listája");

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Könyv lista");
		this.setMargin(true);
		this.setSpacing(true);
		Component functionComponent = createFunctionLayout();
		this.addComponent(functionComponent);
		this.setComponentAlignment(functionComponent, Alignment.TOP_CENTER);
		this.addComponent(createTable());
		
	}


	private Component createFunctionLayout() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.setMargin(true);
		Component searchBookComponent = createSearchField();
		horizontalLayout.addComponent(searchBookComponent);
		horizontalLayout.setComponentAlignment(searchBookComponent, Alignment.BOTTOM_CENTER);
		Component newBookComponent = createNewBookButton();
		horizontalLayout.addComponent(newBookComponent);
		horizontalLayout.setComponentAlignment(newBookComponent, Alignment.BOTTOM_CENTER);
		Component deleteBookComponent = deleteBookButton();
		horizontalLayout.addComponent(deleteBookComponent);
		horizontalLayout.setComponentAlignment(deleteBookComponent, Alignment.BOTTOM_CENTER);
		Component backToMainComponent = backToMainViewButton();
		horizontalLayout.addComponent(backToMainComponent);
		horizontalLayout.setComponentAlignment(backToMainComponent, Alignment.BOTTOM_CENTER);
		Component logOutComponent = logOutButton();
		horizontalLayout.addComponent(logOutComponent);
		horizontalLayout.setComponentAlignment(logOutComponent, Alignment.BOTTOM_CENTER);
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
	
	private Component createNewBookButton() {
		Button newBookButton = new Button("Új könyv hozzáadása");
		
		newBookButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Book book = new Book();
				ctx.getBean(BookWindow.class).showWindow(book, "Új könyv hozzáadása", false, BookView.this);
			}
		});
		
		return newBookButton;
	}
	
	private Component deleteBookButton() {
		Button deleteBookButton = new Button("Kijelöltek törlése");

		deleteBookButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					for (Object itemId : selectedItemIds) {
						BeanItem<Book> bookItem = (BeanItem<Book>) bookTable.getItem(itemId);
						BeanFieldGroup<Book> bookFieldGroup = new BeanFieldGroup<Book>(Book.class);
						bookFieldGroup.setItemDataSource(bookItem);
						bookFieldGroup.commit();
						Book bean = bookFieldGroup.getItemDataSource().getBean();
						bookService.deleteBook(bean);
					}
					refreshTable();
					selectedItemIds.removeAll(selectedItemIds);
					Notification.show("Sikeres könyv törlés!");
				} catch (Exception e) {
					Notification.show("Hiba törlés közben!");
				}
			}
		});
		return deleteBookButton;
	}
	
	private Component backToMainViewButton() {
		Button backButton = new Button("Vissza a főoldalra");

		backButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainView.MAIN_VIEW_NAME);
			}
		});
		return backButton;
	}
	
	private Component logOutButton() {
		Button logOutButoon = new Button("Kilépés");

		logOutButoon.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getPage().setLocation(
						VaadinServlet.getCurrent().getServletContext().getContextPath() + "/login?logout=1");
			}
		});
		
		return logOutButoon;
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
						if (selectedItemIds.contains(itemId)) {
							selectedItemIds.remove(itemId);
						} else {
							selectedItemIds.add(itemId);
						}
					}
				});

				return deleteCB;
			}
		});

		bookTable.addGeneratedColumn("viewBook", new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				Button viewBookButton = new Button("Megtekintés");
				viewBookButton.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						BeanItem<Book> bookItem = (BeanItem<Book>) source.getItem(itemId);
						ctx.getBean(BookWindow.class).showWindow(bookItem.getBean(), "Megtekintés", true,
								BookView.this);
					}
				});

				return viewBookButton;
			}
		});

		bookTable.addGeneratedColumn("editBook", new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				Button editBookButton = new Button("Szerkesztés");

				editBookButton.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						BeanItem<Book> bookItem = (BeanItem<Book>) source.getItem(itemId);
						ctx.getBean(BookWindow.class).showWindow(bookItem.getBean(), "Szerkesztés", false,
								BookView.this);
					}
				});
				return editBookButton;
			}
		});
		
		bookTable.setVisibleColumns("selector", "id", "title", "author", "description", "pubYear", "viewBook", "editBook");

		bookTable.setColumnHeader("selector", "Válasszon!");
		bookTable.setColumnHeader("id", "Azonosító");
		bookTable.setColumnHeader("title", "Cím");
		bookTable.setColumnHeader("author", "Szerző");
		bookTable.setColumnHeader("description", "Leírás");
		bookTable.setColumnHeader("pubYear", "Kiadás éve");
		bookTable.setColumnHeader("viewBook", "Megtekintés");
		bookTable.setColumnHeader("editBook", "Szerkesztés");

		bookTable.setColumnAlignment("selector", Align.CENTER);
		bookTable.setColumnAlignment("id", Align.CENTER);
		bookTable.setColumnAlignment("title", Align.CENTER);
		bookTable.setColumnAlignment("author", Align.CENTER);
		bookTable.setColumnAlignment("description", Align.CENTER);
		bookTable.setColumnAlignment("pubYear", Align.CENTER);
		bookTable.setColumnAlignment("viewBook", Align.CENTER);
		bookTable.setColumnAlignment("editBook", Align.CENTER);

		return bookTable;
	}

}
