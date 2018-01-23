package faq.unit.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import faq.dao.FaqDao;
import faq.model.Faq;
import faq.service.impl.FaqServiceImpl;

/**
 * @author jcvidal
 *
 *
 * Test FaqService
 */
public class FaqServiceImplTest {

	@Mock
	FaqDao dao;
	
	@InjectMocks
	FaqServiceImpl faqService;
	
	@Spy
	List<Faq> faqs = new ArrayList<Faq>();
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		faqs = getFaqList();
	}
	
	/**
	 * Test Service findAllFaqs method 
	 */
	@Test
	public void list(){
		when(dao.findAllFaqs()).thenReturn(faqs);
		Assert.assertEquals(faqService.findAllFaqs(), faqs);
	}

	/**
	 * Test Service findFaqByQuestionOrReponse method 
	 */
	@Test
	public void search(){
		String[] search = new String[1];
		search[0] = "1";
		when(dao.findFaqByQuestionOrReponse(search,false)).thenReturn(faqs);
		Assert.assertEquals(faqService.findFaqByQuestionOrReponse("1"), faqs);
		search[0] = "5";
		when(dao.findFaqByQuestionOrReponse(search,false)).thenReturn(faqs);
		Assert.assertEquals(faqService.findFaqByQuestionOrReponse("5"), faqs);
	}
	
	/**
	 * Test Service addFaq method 
	 */
	@Test
	public void save(){
		doNothing().when(dao).addFaq(any(Faq.class));
		faqService.addFaq(any(Faq.class));
		verify(dao, atLeastOnce()).addFaq(any(Faq.class));
	}
	
	/**
	 * 
	 * Mock list
	 * 
	 * @return list of Faq
	 */
	public List<Faq> getFaqList(){
		Faq faq = new Faq();
		faq.setId(1);
		faq.setQuestion("Q1");
		faq.setReponse("R1");
		
		faqs.add(faq);

		faq = new Faq();
		faq.setId(2);
		faq.setQuestion("Q2");
		faq.setReponse("R2");
		
		return faqs;
	}
	
}
