package cn.zzs.hibernate.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zzs.hibernate.dao.impl.MenuDaoImpl;
import cn.zzs.hibernate.dao.impl.RoleDaoImpl;
import cn.zzs.hibernate.dao.impl.UserDaoImpl;
import cn.zzs.hibernate.pojo.Menu;
import cn.zzs.hibernate.pojo.Role;
import cn.zzs.hibernate.pojo.User;
import cn.zzs.hibernate.utils.HibernateUtils;

/**
 * @ClassName: RoleDaoTest
 * @Description: 测试RoleDao
 * @author: zzs
 * @date: 2019年9月2日 上午11:59:11
 */
public class RoleDaoTest {
	//RoleDao
	private RoleDao roleDao = new RoleDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	private MenuDao menuDao = new MenuDaoImpl();
	private static Logger logger = LoggerFactory.getLogger(RoleDaoTest.class);
	
	/**
	 * 测试添加角色
	 */
	@Test
	public void testSave() {
		//开启事务
		HibernateUtils.beginTransaction();
		//创建角色对象
		Role role = new Role("销售经理", new Date(), new Date());
		try {
			//添加菜单权限
			Set<Menu> set = new HashSet<Menu>(5);
			set.add(menuDao.findByName("销售订单申请"));
			set.add(menuDao.findByName("销售订单审核"));
			set.add(menuDao.findByName("销售订单提交"));
			role.setMenus(set);
			//添加角色
			roleDao.save(role);
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("添加角色失败", e);
		}
	}
	
	/**
	 * 测试根据id查询角色
	 * @throws Exception 
	 */
	@Test
	public void testFind() throws Exception {
		//开启事务
		HibernateUtils.beginTransaction();
		try {
			//查询角色
			Role role = roleDao.find(2L);
			System.out.println(role);
			System.out.println(role.getUsers());
			System.out.println(role.getMenus());
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("查询角色失败", e);
		}
	}
	
	/**
	 * 测试更新角色
	 */
	@Test
	public void testUpdate() {
		//开启事务
		HibernateUtils.beginTransaction();

		try {
			//获取角色对象
			Role role = roleDao.find(1L);
			//修改角色
			role.setModified(new Date());
			//更新角色：利用持久态的特性，其实这里不update也可以更新。
			//roleDao.update(role);
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("更新角色失败", e);
		}
	}

	/**
	 * 测试根据id删除角色
	 */
	@Test
	public void testDelete() {
		//开启事务
		HibernateUtils.beginTransaction();
		try {
			Long id = 1L;
			//删除用户和角色的关联
			User user = userDao.find(id);
			user.setRole(null);
			//删除角色
			roleDao.delete(roleDao.find(1L));
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("删除角色失败", e);
		}
	}
}
