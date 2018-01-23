package faq.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import faq.dao.UserDao;
import faq.model.User;

/**
 * @author jcvidal
 *
 *
 * Implementation of UserDao
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	/* (non-Javadoc)
	 * @see faq.dao.UserDao#getUserByName(java.lang.String)
	 */
	@Override
	public User getUserByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (User)criteria.uniqueResult();
	}

}
