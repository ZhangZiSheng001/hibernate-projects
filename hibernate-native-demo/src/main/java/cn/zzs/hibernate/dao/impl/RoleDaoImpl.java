package cn.zzs.hibernate.dao.impl;

import java.io.Serializable;

import cn.zzs.hibernate.dao.RoleDao;
import cn.zzs.hibernate.pojo.Role;
import cn.zzs.hibernate.utils.HibernateUtils;

/**
 * @ClassName: RoleDaoImpll
 * @Description: 角色操作接口的实现类
 * @author: zzs
 * @date: 2019年9月2日 下午3:09:27
 */
public class RoleDaoImpl implements RoleDao {
	@Override
	public void save(Role role) throws Exception {
		//把对象添加到数据库中
		HibernateUtils.getCurrentSession().save(role);
	}

	@Override
	public void update(Role role) throws Exception {
		//把对象更新到数据库中
		HibernateUtils.getCurrentSession().update(role);
	}

	@Override
	public Role find(Serializable id) throws Exception {
		//根据id查询角色:注意以下两种方式的区别
		Role role = HibernateUtils.getCurrentSession().get(Role.class, id);
		//Role role = session.load(Role.class, id);
		return role;
	}

	@Override
	public void delete(Role role) throws Exception {
		//根据id删除角色
		HibernateUtils.getCurrentSession().delete(role);
	}

}
