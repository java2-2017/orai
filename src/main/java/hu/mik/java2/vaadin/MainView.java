package hu.mik.java2.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
@SpringView(name = MainView.MAIN_VIEW_NAME)
public class MainView extends VerticalLayout implements View{
	
	protected static final String MAIN_VIEW_NAME = "";

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Kezdőlap");
		this.setMargin(true);
		this.setSpacing(true);
		Label htmlLabel = new Label("<h1>Hello World!</h1>");
		htmlLabel.setContentMode(ContentMode.HTML);
		htmlLabel.setSizeUndefined();
		this.addComponent(htmlLabel);
		this.setComponentAlignment(htmlLabel, Alignment.TOP_CENTER);
		
		Button navToBookListButoon = new Button("Könyvek listája");
		
		navToBookListButoon.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(BookView.BOOK_VIEW_NAME);
			}
		});
		
		this.addComponent(navToBookListButoon);
		this.setComponentAlignment(navToBookListButoon, Alignment.BOTTOM_CENTER);
	}

}
