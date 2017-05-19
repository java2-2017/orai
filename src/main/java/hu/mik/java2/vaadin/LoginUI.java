package hu.mik.java2.vaadin;

import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringUI(path = "/login")
@Widgetset("hu.mik.java2.vaadin.BookWidgetSet")
public class LoginUI extends UI {

	private VerticalLayout pageLayout;
	private BookLoginForm loginForm;


	@Override
	protected void init(VaadinRequest request) {
		Page.getCurrent().setTitle("Bejelentkezés");
		this.pageLayout = new VerticalLayout();
		this.pageLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		this.pageLayout.setSizeFull();
		this.setContent(this.pageLayout);

		this.loginForm = new BookLoginForm();

		this.pageLayout.addComponent(loginForm);
		if(request.getParameter("error") != null){
			this.loginForm.setVisibleErrorLabel(true);
		}else{
			this.loginForm.setVisibleErrorLabel(false);
		}
	}

}
