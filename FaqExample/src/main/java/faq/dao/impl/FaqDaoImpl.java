package faq.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import faq.dao.FaqDao;
import faq.model.Faq;

/**
 * @author jcvidal
 * 
 * Dao impl for Faq Model
 * 
 */
@Repository("faqDao")
public class FaqDaoImpl extends AbstractDao<Integer, Faq> implements FaqDao {

	/* (non-Javadoc)
	 * @see faq.dao.FaqDao#findAllFaqs()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Faq> findAllFaqs() {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Faq>) criteria.list();
	}

	/* (non-Javadoc)
	 * @see faq.dao.FaqDao#findFaqByQuestionOrReponse(java.lang.String[], boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Faq> findFaqByQuestionOrReponse(String[] values,boolean and) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Junction junction = null;
		if (and) {
			junction = Restrictions.conjunction();
		} else {
			junction = Restrictions.disjunction();
		}
		for (String value:values) {
			Disjunction or = Restrictions.disjunction();
			or.add(Restrictions.ilike("question", value,MatchMode.ANYWHERE)); 
			or.add(Restrictions.ilike("reponse", value,MatchMode.ANYWHERE));
			junction.add(or);
		}
		criteria.add(junction);
		return (List<Faq>) criteria.list();
	}

	/* (non-Javadoc)
	 * @see faq.dao.FaqDao#addFaq(faq.model.Faq)
	 */
	@Override
	public void addFaq(Faq faq) {
		persist(faq);
	}

	/* (non-Javadoc)
	 * @see faq.dao.FaqDao#findById(int)
	 */
	@Override
	public Faq findById(int id) {
		return getByKey(id);
	}

	/* (non-Javadoc)
	 * @see faq.dao.FaqDao#deleteByQuestion(java.lang.String)
	 */
	@Override
	public void deleteByQuestion(String question) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("question", question));
		Faq faq = (Faq)criteria.uniqueResult();
		delete(faq);
	}
	
}
