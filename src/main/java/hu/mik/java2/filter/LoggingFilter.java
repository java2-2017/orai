package hu.mik.java2.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter
public class LoggingFilter implements Filter {
	private ServletContext servletContext;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()) {
			String name = params.nextElement();
			String value = request.getParameter(name);
			this.servletContext.log("Params: " + name + "=" + value);
		}
		chain.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.servletContext = filterConfig.getServletContext();
		this.servletContext.log("LogginFilter initialized");
	}

}
