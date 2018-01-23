package faq.integration.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Test;

import faq.model.Faq;
import faq.model.Tag;
import faq.rest.HttpStatus;



/**
 * @author jcvidal
 *
 * Integration Test using Jersey Client
 */
public class ClientIntegrationTest {
	private static final String LIST_URL = "http://localhost:9999/FaqExample/rest/faq/list";
	private static final String SEARCH_URL = "http://localhost:9999/FaqExample/rest/faq/search/{0}";
	private static final String PUT_URL = "http://localhost:9999/FaqExample/rest/faq/put";
	private static final String DELETE_URL = "http://localhost:9999/FaqExample/rest/faq/delete/question5";
		
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(ClientIntegrationTest.class);
	
	/**
	 * 
	 * Method to get a Client with Auth header or not (depends if user && pwd are null or not)
	 * 
	 * @param user user name
	 * @param pwd user password
	 * @return Jersey Client
	 */
	private Client getClient(String user,String pwd) {
		Client client = ClientBuilder.newBuilder().build();	
		if ((user != null) && (pwd != null)) {
			HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user,pwd);
			client.register(feature);
		}
		return client;
	}
	
	/**
	 * @return Client with no Auth header
	 */
	private Client getClient() {
		return getClient(null,null);
	}
	
	/**
	 * 
	 * Method used to test Auth
	 * 
	 * @param url server URL to call
	 * @param put true to use a PUT request
	 */
	private void testAuth(String url,boolean put) {
		// No Basic Auth
		Client client = getClient();	
		WebTarget target = client.target(url);
		Response response;
		Faq faq = new Faq();
		if (put) {
			response = target.request().put(Entity.entity(faq, MediaType.APPLICATION_JSON));
		} else {
			response = target.request(MediaType.APPLICATION_JSON).get();
		}
		assertEquals(HttpStatus.RET_AUTH.getStatus(),response.getStatus());

		
		//false user
		client = getClient("admi","admi");	
		target = client.target(url);
		if (put) {
			response = target.request().put(Entity.entity(faq, MediaType.APPLICATION_JSON));
		} else {
			response = target.request(MediaType.APPLICATION_JSON).get();
		}
		assertEquals(HttpStatus.RET_AUTH.getStatus(),response.getStatus());

		//false pwd
		client = getClient("admin","admi");	
		target = client.target(url);
		if (put) {
			response = target.request().put(Entity.entity(faq, MediaType.APPLICATION_JSON));
		} else {
			response = target.request(MediaType.APPLICATION_JSON).get();
		}
		assertEquals(HttpStatus.RET_AUTH.getStatus(),response.getStatus());

	}
	
	/**
	 * Test User Story 2
	 */
	@Test
	public void list() {
		
		testAuth(LIST_URL,false);
		
		//bad role
		Client client = getClient("user","user");	
		WebTarget target = client.target(LIST_URL);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		assertEquals(HttpStatus.RET_AUTH.getStatus(),response.getStatus());
		
		// good
		client = getClient("admin","admin");	
		target = client.target(LIST_URL);
		response = target.request(MediaType.APPLICATION_JSON).get();
		assertEquals(HttpStatus.RET_OK.getStatus(),response.getStatus());
		List<Faq> faqs = (List<Faq>) response.readEntity(new GenericType<List<Faq>>(){});
		assertEquals(4,faqs.size());
		// JSON may not be in 'good' order, so we have to parse
		int i = 0;
		for (Faq faq:faqs) {
			if (faq.getQuestion().equals("question1")) {
				assertTrue(faq.getReponse().equals("reponse1"));
				i++;
			} else if (faq.getQuestion().equals("question2")) {
				assertTrue(faq.getReponse().equals("reponse2"));
				i++;
			} else if (faq.getQuestion().equals("question3")) {
				assertTrue(faq.getReponse().equals("reponse3"));
				i++;
			} else if (faq.getQuestion().equals("question4")) {
				assertTrue(faq.getReponse().equals("reponse4"));
				i++;
			}
		}
		assertEquals(4, i);
	}
	
	/**
	 * 
	 * Used by search() method
	 * 
	 * @param url server URL
	 * @param retSize Expected size of the returned list 
	 */
	private void _search(String url,int retSize) {
		testAuth(url,false);
		
		// don't test role because this request is allowed for both
		
		Client client = getClient("admin","admin");	
		WebTarget target = client.target(url);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		assertEquals(HttpStatus.RET_OK.getStatus(),response.getStatus());
		List<Faq> faqs = (List<Faq>) response.readEntity(new GenericType<List<Faq>>(){});
		assertEquals(retSize,faqs.size());
		if (retSize == 1) {
			Faq faq = faqs.get(0);
			assertTrue(faq.getQuestion().equals("question3"));
			assertTrue(faq.getReponse().equals("reponse3"));
		}
	}
	
	/**
	 * Test User story 3 
	 */
	@Test
	public void search() {
		
		String url = SEARCH_URL.replace("{0}", "3");
		_search(url,1);
		url = SEARCH_URL.replace("{0}", "reponse");
		_search(url,4);
		url = SEARCH_URL.replace("{0}", "question");
		_search(url,4);
		url = SEARCH_URL.replace("{0}", "question+3");
		_search(url,1);
	}
	
	/**
	 * Test Use story 1
	 */
	@Test
	public void put() {
		
		testAuth(PUT_URL,true);
		
		//bad role
		Client client = getClient("user","user");	
		WebTarget target = client.target(PUT_URL);
		Faq faq = new Faq();
		Response response = target.request().put(Entity.entity(faq, MediaType.APPLICATION_JSON));
		assertEquals(HttpStatus.RET_AUTH.getStatus(),response.getStatus());
		
		// good
		faq.setQuestion("question5");
		faq.setReponse("reponse5");
		Tag tag = new Tag();
		tag.setId(1);
		tag.setValue("");
		faq.getTags().add(tag);
		tag = new Tag();
		tag.setId(2);
		tag.setValue("");
		faq.getTags().add(tag);
		
		client = getClient("admin","admin");	
		target = client.target(PUT_URL);
		response = target.request().put(Entity.entity(faq, MediaType.APPLICATION_JSON));
		assertEquals(HttpStatus.RET_DONE.getStatus(),response.getStatus());
		
		client = getClient("admin","admin");	
		target = client.target(LIST_URL);
		response = target.request(MediaType.APPLICATION_JSON).get();
		assertEquals(HttpStatus.RET_OK.getStatus(),response.getStatus());
		List<Faq> faqs = (List<Faq>) response.readEntity(new GenericType<List<Faq>>(){});
		assertEquals(5,faqs.size());
		
		client = getClient("admin","admin");	
		target = client.target(DELETE_URL);
		response = target.request(MediaType.APPLICATION_JSON).delete();
		assertEquals(HttpStatus.RET_DONE.getStatus(),response.getStatus());

		client = getClient("admin","admin");	
		target = client.target(LIST_URL);
		response = target.request(MediaType.APPLICATION_JSON).get();
		assertEquals(HttpStatus.RET_OK.getStatus(),response.getStatus());
		faqs = (List<Faq>) response.readEntity(new GenericType<List<Faq>>(){});
		assertEquals(4,faqs.size());
	}
}
