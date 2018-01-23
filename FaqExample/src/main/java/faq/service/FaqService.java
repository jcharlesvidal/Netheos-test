package faq.service;

import java.util.List;

import faq.model.Faq;

/**
 * @author jcvidal
 *
 * Service using FaqDao
 *
 */
public interface FaqService {
	/**
	 * 
	 * return all Faqs on DB
	 * 
	 * @return
	 */
	public List<Faq> findAllFaqs();
	
	public List<Faq>findFaqByQuestionOrReponse(String value);
	
	/**
	 * 
	 * insert FAq in DB 
	 * 
	 * @param faq
	 */
	public void addFaq(Faq faq);
	
	/**
	 * 
	 * Delete a Faq matching a question (used only with integration tests)
	 * 
	 * @param question question to be matched
	 */
	public void deleteByQuestion(String question);
}
