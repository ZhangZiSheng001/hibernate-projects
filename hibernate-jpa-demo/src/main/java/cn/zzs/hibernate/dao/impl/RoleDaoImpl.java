package cn.zzs.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

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
		HibernateUtils.getEntityManager().persist(role);
	}

	@Override
	public void update(Role role) throws Exception {
		//把对象更新到数据库中
		HibernateUtils.getEntityManager().merge(role);
	}

	@Override
	public Role find(Serializable id) throws Exception {
		//根据id查询角色
		Role role = HibernateUtils.getEntityManager().find(Role.class, id);
		return role;
	}

	@Override
	public void delete(Role role) throws Exception {
		//根据id删除角色
		HibernateUtils.getEntityManager().remove(role);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Role findByName(String name) throws Exception {
		//定义sql语句
		String sql = "from Role where name = ?1 ";
		//获得Query对象
		Query query = HibernateUtils.getEntityManager().createQuery(sql);
		//设置参数
		query.setParameter(1, name);
		//执行查询,并返回结果
		List<Role> list = query.getResultList();
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

}
