package cn.zzs.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import cn.zzs.hibernate.dao.UserDao;
import cn.zzs.hibernate.pojo.User;
import cn.zzs.hibernate.utils.HibernateUtils;

/**
 * @ClassName: UserDaoImpl
 * @Description: TODO
 * @author: zzs
 * @date: 2019年9月2日 上午11:46:43
 */
public class UserDaoImpl implements UserDao {

	@Override
	public void save(User user) throws Exception {
		//把对象添加到数据库中
		HibernateUtils.getCurrentSession().save(user);
	}

	@Override
	public void update(User user) throws Exception {
		//把对象更新到数据库中
		HibernateUtils.getCurrentSession().update(user);
	}

	@Override
	public User find(Serializable id) throws Exception {
		//根据id查询用户:注意以下两种方式的区别
		User user = HibernateUtils.getCurrentSession().get(User.class, id);
		//User user = session.load(User.class, id);
		return user;
	}

	@Override
	public void delete(User user) throws Exception {
		//根据id删除用户
		HibernateUtils.getCurrentSession().delete(user);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findByNameLikeWithPageUserHQL(String name, Integer firstResult, Integer maxResults) throws Exception {
		//定义sql语句
		String sql = "from User where name like ?1 ";
		//获得Query对象
		Query<User> query = HibernateUtils.getCurrentSession().createQuery(sql);
		//设置参数
		query.setParameter(1, name);
		//设置分页参数
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		//执行查询,并返回结果
		return query.list();
	}
	
	/**
	 * 官方从5.2版本开始建议使用JPA的Creatia，其实感觉原有的方式更简单一些。
	 * 这里暂时使用原来的
	 */
	@SuppressWarnings(value = {"unchecked","deprecation"})
	@Override
	public List<User> findByNameLikeWithPageUserQBC(String name, Integer firstResult, Integer maxResults) throws Exception {
		//获得Criteria对象
		Criteria criteria = HibernateUtils.getCurrentSession().createCriteria(User.class);
		//设置条件
		criteria.add(Restrictions.like("name", name));
		//设置分页参数
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		//执行查询,并返回结果
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findByNameLikeWithPageUserSQL(String name, Integer firstResult, Integer maxResults) throws Exception {
		//获得SQLQuery对象
		NativeQuery<User> sqlQuery = HibernateUtils.getCurrentSession().createSQLQuery("select * from native_user where user_name like ?1 limit ?2 , ?3 ");
		//设置对象的类
		sqlQuery.addEntity(User.class);
		//设置条件
		sqlQuery.setParameter(1, name);//索引从0开始
		//设置分页参数
		sqlQuery.setParameter(2, firstResult);
		sqlQuery.setParameter(3, maxResults);
		//执行查询,并遍历结果
		return sqlQuery.list();
	}
}
