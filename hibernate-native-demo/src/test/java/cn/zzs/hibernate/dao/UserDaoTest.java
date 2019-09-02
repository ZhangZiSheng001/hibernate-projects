package cn.zzs.hibernate.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zzs.hibernate.dao.impl.RoleDaoImpl;
import cn.zzs.hibernate.dao.impl.UserDaoImpl;
import cn.zzs.hibernate.pojo.User;
import cn.zzs.hibernate.utils.HibernateUtils;

/**
 * @ClassName: UserDaoTest
 * @Description: 测试UserDao
 * @author: zzs
 * @date: 2019年9月2日 上午11:59:11
 */
public class UserDaoTest {
	private UserDao userDao = new UserDaoImpl();
	private RoleDao roleDao = new RoleDaoImpl();

	private static Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

	/**
	 * 测试添加用户
	 */
	@Test
	public void testSave() {
		//开启事务
		HibernateUtils.beginTransaction();
		//创建用户对象
		User user = new User("zzs005", 18, new Date(), new Date());
		try {
			//设置用户角色
			user.setRole(roleDao.find(2L));
			//添加用户
			userDao.save(user);
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("添加用户失败", e);
		}
	}

	/**
	 * 测试根据id查询用户
	 * @throws Exception 
	 */
	@Test
	public void testFind() throws Exception {
		//开启事务
		HibernateUtils.beginTransaction();

		try {
			//查询用户
			User user = userDao.find(4L);
			System.out.println(user);
			System.out.println(user.getRole());
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("查询用户失败", e);
		}
	}

	/**
	 * 测试更新用户
	 */
	@Test
	public void testUpdate() {
		//开启事务
		HibernateUtils.beginTransaction();

		try {
			//获取用户对象
			User user = userDao.find(1L);
			//修改用户
			user.setAge(20);
			user.setModified(new Date());
			//更新用户：利用持久态的特性，其实这里不update也可以更新。
			//userDao.update(user);
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("更新用户失败", e);
		}
	}

	/**
	 * 测试根据id删除用户
	 */
	@Test
	public void testDelete() {
		//开启事务
		HibernateUtils.beginTransaction();

		try {
			//删除用户
			userDao.delete(userDao.find(1L));
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("删除用户失败", e);
		}
	}

	/**
	 * 测试使用HQL查询用户
	 */
	@Test
	public void testFindByNameLikeWithPageUserHQL() {
		//开启事务
		HibernateUtils.beginTransaction();
		try {
			//查询用户
			List<User> list = userDao.findByNameLikeWithPageUserHQL("zzs%", 0, 3);
			//遍历查询结果
			if (list != null) {
				for (User user : list) {
					System.out.println(user);
				}
			}
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("查询用户失败", e);
		}
	}

	/**
	 * 测试使用QBC查询用户
	 */
	@Test
	public void testFindByNameLikeWithPageUserQBC() {
		//开启事务
		HibernateUtils.beginTransaction();
		try {
			//查询用户
			List<User> list = userDao.findByNameLikeWithPageUserQBC("zzs%", 0, 3);
			//遍历查询结果
			if (list != null) {
				for (User user : list) {
					System.out.println(user);
				}
			}
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("查询用户失败", e);
		}
	}

	/**
	 * 测试使用SQL查询用户
	 */
	@Test
	public void testFindByNameLikeWithPageUserSQL() {
		//开启事务
		HibernateUtils.beginTransaction();
		try {
			//查询用户
			List<User> list = userDao.findByNameLikeWithPageUserSQL("zzs%", 0, 3);
			//遍历查询结果
			if (list != null) {
				for (User user : list) {
					System.out.println(user);
				}
			}
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("查询用户失败", e);
		}
	}
}
