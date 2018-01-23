package faq.dao;

import java.util.List;

import faq.model.Faq;

/**
 * @author jcvidal
 *
 * Interface for FaqDao
 *
 */
public interface FaqDao {
	/**
	 * 
	 * List all Faq from BDD
	 * 
	 * @return
	 */
	public List<Faq>findAllFaqs();
	
	/**
	 * 
	 * Search all values of 'values' in BDD. each value is searched in reponse or question column. if and=true, values are search following an AND method, otherwise an OR. 
	 * 
	 * @param values Array of values to be searched
	 * @param and true if AND method
	 * @return list of Faq
	 */
	public List<Faq>findFaqByQuestionOrReponse(String[] values,boolean and);
	
	/**
	 * 
	 * Insert a Faq in DB
	 * 
	 * @param faq
	 */
	public void addFaq(Faq faq);
	
	/**
	 * 
	 * find an unique Faq by Id (for integration tests)
	 * 
	 * @param id id of a Faq
	 * @return Unique Faq
	 */
	public Faq findById(int id);
	
	/**
	 * 
	 * Delete a Faq by question  (for integration tests)
	 * 
	 * @param question question to be deleted
	 */
	public void deleteByQuestion(String question);
}
