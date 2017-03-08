package hu.mik.java2.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HelloTag extends SimpleTagSupport{

	@Override
	public void doTag() throws JspException, IOException {
		JspFragment jspBody = getJspBody();
		StringWriter sw = new StringWriter();
		jspBody.invoke(sw);
		JspWriter out = getJspContext().getOut();
		out.print("Hello ");
		out.print(sw);
		out.print("!");
	}
	
	

}
