package hu.mik.java2.vaadin;

import org.springframework.context.annotation.Scope;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import hu.mik.java2.book.bean.Book;

@org.springframework.stereotype.Component
@Scope(scopeName = "prototype")
public class BookWindow extends Window{

	public void showWindow(Book book, String title) {
		this.setHeight("50%");
		this.setWidth("50%");
		this.center();
		this.setCaption(title);
		this.setContent(createEditLayout(book));
		UI.getCurrent().addWindow(this);
		
	}

	private Component createEditLayout(Book book) {
		BeanFieldGroup<Book> beanField = new BeanFieldGroup<Book>(Book.class);
		beanField.setItemDataSource(book);
		
		VerticalLayout layout = new VerticalLayout();
		Component formComponent = createFormLayout(beanField);
		formComponent.setSizeUndefined();
		layout.addComponent(formComponent);
		layout.setComponentAlignment(formComponent, Alignment.BOTTOM_CENTER);
		return layout;
	}

	private Component createFormLayout(BeanFieldGroup<Book> beanField) {
		FormLayout formLayout = new FormLayout();
		TextField titleField = beanField.buildAndBind("Cím", "id", TextField.class);
		titleField.setNullRepresentation("");
		formLayout.addComponent(titleField);
		return formLayout;
	}

}
