package faq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import faq.dao.FaqDao;
import faq.model.Faq;

/**
 * @author jcvidal
 *
 * Implementation of FaqService
 */
@Service("faqService")
@Transactional
public class FaqServiceImpl implements faq.service.FaqService {

	@Autowired
	private FaqDao faqDao;
	
	/* (non-Javadoc)
	 * @see faq.service.FaqService#findAllFaqs()
	 */
	@Override
	public List<Faq> findAllFaqs() {
		return faqDao.findAllFaqs();
	}

	/* (non-Javadoc)
	 * @see faq.service.FaqService#findFaqByQuestionOrReponse(java.lang.String)
	 */
	@Override
	public List<Faq> findFaqByQuestionOrReponse(String value) {
		value = value.replace('+', ' '); 
		String[] values = value.split(" ");
		List<Faq> faqs = faqDao.findFaqByQuestionOrReponse(values,true);
		if ((faqs != null) && (faqs.size() != 0)) {
			return faqs;
		}
		return faqDao.findFaqByQuestionOrReponse(values,false);
	}

	/* (non-Javadoc)
	 * @see faq.service.FaqService#addFaq(faq.model.Faq)
	 */
	@Override
	public void addFaq(Faq faq) {
		faqDao.addFaq(faq);
	}

	/* (non-Javadoc)
	 * @see faq.service.FaqService#deleteByQuestion(java.lang.String)
	 */
	@Override
	public void deleteByQuestion(String question) {
		faqDao.deleteByQuestion(question);
	}

}
