package faq.server;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import faq.rest.JSONService;

/**
 * @author jcvidal
 *
 * Needed (in web.xml) for Jersey + Spring + JSON 
 */
@SuppressWarnings("unused")
public class MyApplication extends ResourceConfig {
	public MyApplication() {
		register(RequestContextFilter.class);
		register(JSONService.class);
		//Only for debug
		//register(LoggingFilter.class);    		
	}
}
