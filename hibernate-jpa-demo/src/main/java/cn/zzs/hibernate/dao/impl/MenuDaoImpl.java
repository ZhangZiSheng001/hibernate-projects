package cn.zzs.hibernate.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.zzs.hibernate.dao.MenuDao;
import cn.zzs.hibernate.pojo.Menu;
import cn.zzs.hibernate.utils.HibernateUtils;

/**
 * @ClassName: MenuDaoImpll
 * @Description: 菜单操作接口的实现类
 * @author: zzs
 * @date: 2019年9月2日 下午3:09:27
 */
public class MenuDaoImpl implements MenuDao {
	@Override
	public void save(Menu menu) throws Exception {
		//把对象添加到数据库中
		HibernateUtils.getEntityManager().persist(menu);
	}

	@Override
	public void update(Menu menu) throws Exception {
		//把对象更新到数据库中
		HibernateUtils.getEntityManager().merge(menu);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Menu findByName(String name) throws Exception {
		//定义sql语句
		String sql = "from Menu where name = ?1 ";
		//获得Query对象
		Query query = HibernateUtils.getEntityManager().createQuery(sql);
		//设置参数
		query.setParameter(1, name);
		//执行查询,并返回结果
		List<Menu> list = query.getResultList();
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void delete(Menu menu) throws Exception {
		//根据id删除菜单
		HibernateUtils.getEntityManager().remove(menu);
	}

}
