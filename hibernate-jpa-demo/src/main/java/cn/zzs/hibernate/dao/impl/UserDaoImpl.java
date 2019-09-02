package cn.zzs.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
		HibernateUtils.getEntityManager().persist(user);
	}

	@Override
	public void update(User user) throws Exception {
		//把对象更新到数据库中
		HibernateUtils.getEntityManager().merge(user);
	}

	@Override
	public User find(Serializable id) throws Exception {
		//根据id查询用户:注意以下两种方式的区别
		User user = HibernateUtils.getEntityManager().find(User.class, id);
		//User user = HibernateUtils.getEntityManager().getReference(User.class,id);
		return user;
	}

	@Override
	public void delete(User user) throws Exception {
		//根据id删除用户
		HibernateUtils.getEntityManager().remove(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByNameLikeWithPageUserHQL(String name, Integer firstResult, Integer maxResults) throws Exception {
		//定义sql语句
		String sql = "from User where name like ?1 ";
		//获得Query对象
		Query query = HibernateUtils.getEntityManager().createQuery(sql);
		//设置参数
		query.setParameter(1, name);
		//设置分页参数
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		//执行查询,并返回结果
		return query.getResultList();
	}
	
	@Override
	public List<User> findByNameLikeWithPageUserQBC(String name, Integer firstResult, Integer maxResults) throws Exception {
		//CriteriaBuilder 对象：用于创建创建查询的各个部分
		CriteriaBuilder builber = HibernateUtils.getEntityManager().getCriteriaBuilder();
		//CriteriaQuery对象：执行查询的对象
		CriteriaQuery<User> query = builber.createQuery(User.class);
		//获取要查询的实体类的对象
		Root<User> root = query.from(User.class);
		//封装查询条件:username like ?
		Predicate cate = builber.like(root.get("name"), name);
		//select * from t_user where name = 张三
		query.where(cate);
		//执行查询
		TypedQuery<User> typeQuery = HibernateUtils.getEntityManager().createQuery(query);
		//设置分页参数
		typeQuery.setFirstResult(firstResult);
		typeQuery.setMaxResults(maxResults);
		//获得结果集并返回
		return typeQuery.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findByNameLikeWithPageUserSQL(String name, Integer firstResult, Integer maxResults) throws Exception {
		//获得SQLQuery对象
		Query nativeQuery = HibernateUtils.getEntityManager().createNativeQuery("select * from jpa_user where user_name like ?1 limit ?2 , ?3 ",User.class);
		//设置条件
		nativeQuery.setParameter(1, name);
		//设置分页参数
		nativeQuery.setParameter(2, firstResult);
		nativeQuery.setParameter(3, maxResults);
		//执行查询,并遍历结果
		return nativeQuery.getResultList();
	}
}
