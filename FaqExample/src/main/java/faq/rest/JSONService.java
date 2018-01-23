package faq.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import faq.auth.AuthErr;
import faq.auth.Authenticator;
import faq.model.Faq;
import faq.service.FaqService;

/**
 * @author jcvidal
 *
 *
 * JSON server itself in Jersey
 */
@Component
@Path("/faq")
public class JSONService {
	
	private static final String LIST_URL = "/list";
	private static final String SEARCH_URL = "/search/{toSearch}";
	private static final String PUT_URL = "/put";
	private static final String DELETE_URL = "/delete/{question}";
	
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(JSONService.class);
	
	@Autowired
	private Authenticator authenticator;
	@Autowired
	private FaqService faqService;
	
	/**
	 * 
	 * User Story 2. Method canvases are always the same : 1) test Auth 2) Do th ejob 3) return the data if any
	 * 
	 * @param headers HTTP headers
	 * @return Jersey Response (mainly status+Entity)
	 */
	@GET
	@Path(LIST_URL) 
	@Produces(MediaType.APPLICATION_JSON)
	public Response listFaqs(@Context HttpHeaders headers) {
		AuthErr aerr = authenticator.checkAdmin(headers);
		if (aerr.isError()) {
			return Response.status(HttpStatus.RET_AUTH.getStatus()).entity(aerr).build();
		}
		List<Faq> faqs = null;
		try {
			faqs = faqService.findAllFaqs();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(HttpStatus.RET_FATAL.getStatus()).build();
		}
		return Response.status(HttpStatus.RET_OK.getStatus()).entity(faqs).build();
	}
	
	/**
	 * 
	 * User Story 3
	 * 
	 * @param toSearch keywords to search (keyword1+keyword2+...)
	 * @param headers HTTP headers
	 * @return Jersey Response (mainly status+Entity)
	 */
	@GET
	@Path(SEARCH_URL) 
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchFaqs(@PathParam("toSearch") String toSearch,@Context HttpHeaders headers) {
		AuthErr aerr = authenticator.checkUser(headers);
		if (aerr.isError()) {
			return Response.status(HttpStatus.RET_AUTH.getStatus()).entity(aerr).build();
		}
		List<faq.model.Faq> faqs  = null;
		try {
			faqs = faqService.findFaqByQuestionOrReponse(toSearch);
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(HttpStatus.RET_FATAL.getStatus()).build();
		}
		return Response.status(HttpStatus.RET_OK.getStatus()).entity(faqs).build();
	}

	/**
	 * 
	 * User Story 1
	 * 
	 * @param faq Faq Object from JSON
	 * @param headers HTTP headers
	 * @return Jersey Response (mainly status)
	 */
	@PUT
	@Path(PUT_URL)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertFaq(Faq faq,@Context HttpHeaders headers) {
		AuthErr aerr = authenticator.checkAdmin(headers);
		if (aerr.isError()) {
			return Response.status(HttpStatus.RET_AUTH.getStatus()).entity(aerr).build();
		}
		try {
			faqService.addFaq(faq);
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(HttpStatus.RET_FATAL.getStatus()).build();
		}
		return Response.status(HttpStatus.RET_DONE.getStatus()).build();
	}
	
	/**
	 * 
	 * Delete a Faq by question (used by integration tests)
	 * 
	 * @param question question
	 * @param headers HTTP headers
	 * @return Jersey Response (mainly status)
	 */
	@DELETE
	@Path(DELETE_URL)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFaq(@PathParam("question") String question,@Context HttpHeaders headers) {
		AuthErr aerr = authenticator.checkAdmin(headers);
		if (aerr.isError()) {
			return Response.status(HttpStatus.RET_AUTH.getStatus()).entity(aerr).build();
		}
		try {
			faqService.deleteByQuestion(question);
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(HttpStatus.RET_FATAL.getStatus()).build();
		}
		return Response.status(HttpStatus.RET_DONE.getStatus()).build();
	}
}