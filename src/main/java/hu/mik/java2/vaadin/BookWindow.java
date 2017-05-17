package hu.mik.java2.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import hu.mik.java2.book.bean.Book;
import hu.mik.java2.service.BookService;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope(scopeName = "prototype")
public class BookWindow extends Window{
	
	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;


	public void showWindow(Book book, String title, boolean isReadonly, BookView bookView) {
		this.setHeight("50%");
		this.setWidth("50%");
		this.center();
		this.setCaption(title);
		this.setContent(createEditLayout(book, isReadonly, bookView));
		UI.getCurrent().addWindow(this);
		
	}

	private Component createEditLayout(Book book, boolean isReadOnly, final BookView bookView) {
		final BeanFieldGroup<Book> bookBeanField = new BeanFieldGroup<Book>(Book.class);
		bookBeanField.setItemDataSource(book);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);
		Component formComponent = createFormLayout(bookBeanField, isReadOnly);
		formComponent.setSizeUndefined();
		verticalLayout.addComponent(formComponent);
		verticalLayout.setComponentAlignment(formComponent, Alignment.BOTTOM_CENTER);
		
		Button saveButton = new Button("Mentés");
		saveButton.setVisible(!isReadOnly);

		saveButton.addClickListener(new Button.ClickListener() {


			@Override
			public void buttonClick(ClickEvent event) {
				try {
					bookBeanField.commit();
					Book bean = bookBeanField.getItemDataSource().getBean();
					if (bean.getId() == null) {
						bookService.saveBook(bean);
					} else {
						bookService.updateBook(bean);
					}
					BookWindow.this.close();
					Notification.show("Sikeres könyvhozzáadás/módosítás!");
					bookView.refreshTable();
				} catch (Exception e) {
					Notification.show("Hiba történt mentés/módosítás közben!");
				}
			}
		});
		verticalLayout.addComponent(saveButton);
		verticalLayout.setComponentAlignment(saveButton, Alignment.BOTTOM_CENTER);

		return verticalLayout;
	}

	private Component createFormLayout(BeanFieldGroup<Book> bookBeanField, boolean isReadonly) {
		FormLayout formLayout = new FormLayout();
		TextField titleField = bookBeanField.buildAndBind("Könyv címe", "title", TextField.class);
		titleField.setNullRepresentation("");
		titleField.setReadOnly(isReadonly);
		formLayout.addComponent(titleField);
		TextField authorField = bookBeanField.buildAndBind("Szerző", "author", TextField.class);
		authorField.setNullRepresentation("");
		authorField.setReadOnly(isReadonly);
		formLayout.addComponent(authorField);
		TextField descriptionField = bookBeanField.buildAndBind("Leírás", "description", TextField.class);
		descriptionField.setNullRepresentation("");
		descriptionField.setReadOnly(isReadonly);
		formLayout.addComponent(descriptionField);
		TextField pubYearField = bookBeanField.buildAndBind("Kiadás éve", "pubYear", TextField.class);
		pubYearField.setNullRepresentation("");
		pubYearField.setReadOnly(isReadonly);
		formLayout.addComponent(pubYearField);
		return formLayout;
	}

}
