package faq.unit.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import faq.dao.FaqDao;
import faq.model.Faq;
import faq.model.Tag;



/**
 * @author jcvidal
 * 
 * Unit Test of Faq Dao. XML are used to fill H2 DB
 */
public class FaqDaoImplTest extends EntityDaoImplTest{

	@Autowired
	FaqDao faqDao;

	/* (non-Javadoc)
	 * @see faq.unit.dao.EntityDaoImplTest#getDataSet()
	 */
	@Override
	protected IDataSet getDataSet() throws Exception{
		  IDataSet[] datasets = new IDataSet[] {
				  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Tag.xml")),
				  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Faq.xml")),
				  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Faq_tag.xml"))
		  };
		return new CompositeDataSet(datasets);
	}

	/**
	 * Test a simple findById 
	 */
	@Test
	public void findById(){
		Assert.assertNotNull(faqDao.findById(1));
		Assert.assertNull(faqDao.findById(3));
	}

	/**
	 * Test the findAllFaqs method
	 */
	@Test
	public void listFaqs(){
		List<Faq> faqs = faqDao.findAllFaqs();
		Assert.assertEquals(faqs.size(), 2);
	}
	
	/**
	 * Test findFaqByQuestionOrReponse method
	 */
	@Test
	public void searchFaqs(){
		String[] search = {"question","2"};
		List<Faq> faqs = faqDao.findFaqByQuestionOrReponse(search,true);
		Assert.assertEquals(faqs.size(), 1);
		faqs = faqDao.findFaqByQuestionOrReponse(search,false);
		Assert.assertEquals(faqs.size(), 2);

		search = new String[1];
		search[0] = "question";
		faqs = faqDao.findFaqByQuestionOrReponse(search,true);
		Assert.assertEquals(faqs.size(), 2);
		faqs = faqDao.findFaqByQuestionOrReponse(search,false);
		Assert.assertEquals(faqs.size(), 2);
		search[0] = "reponse";
		faqs = faqDao.findFaqByQuestionOrReponse(search,true);
		Assert.assertEquals(faqs.size(), 2);
		faqs = faqDao.findFaqByQuestionOrReponse(search,false);
		Assert.assertEquals(faqs.size(), 2);
		search[0] = "2";
		faqs = faqDao.findFaqByQuestionOrReponse(search,true);
		Assert.assertEquals(faqs.size(), 1);
		faqs = faqDao.findFaqByQuestionOrReponse(search,false);
		Assert.assertEquals(faqs.size(), 1);
		
		search[0] = "5";
		faqs = faqDao.findFaqByQuestionOrReponse(search,true);
		Assert.assertEquals(faqs.size(), 0);
		faqs = faqDao.findFaqByQuestionOrReponse(search,false);
		Assert.assertEquals(faqs.size(), 0);
	}

	/**
	 * Test addFaq method
	 */
	@Test
	public void insertFaq(){
		Faq faq = new Faq();
		faq.setQuestion("question 3");
		faq.setReponse("reponse 3");
		Tag tag = new Tag();
		tag.setId(1);
		Set<Tag> tags = new HashSet<Tag>();
		tags.add(tag);
		faq.setTags(tags);
		faqDao.addFaq(faq);
		List<Faq> faqs = faqDao.findAllFaqs();
		Assert.assertEquals(faqs.size(), 3);
	}
}
