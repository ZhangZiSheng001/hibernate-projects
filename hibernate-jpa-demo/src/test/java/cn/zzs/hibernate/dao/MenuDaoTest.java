package cn.zzs.hibernate.dao;

import java.util.Date;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zzs.hibernate.dao.impl.MenuDaoImpl;
import cn.zzs.hibernate.pojo.Menu;
import cn.zzs.hibernate.utils.HibernateUtils;

/**
 * @ClassName: MenuDaoTest
 * @Description: 测试MenuDao
 * @author: zzs
 * @date: 2019年9月2日 上午11:59:11
 */
public class MenuDaoTest {
	//MenuDao
	private MenuDao menuDao = new MenuDaoImpl();
	private static Logger logger = LoggerFactory.getLogger(MenuDaoTest.class);

	/**
	 * 测试添加菜单
	 */
	@Test
	public void testSave() {
		//开启事务
		HibernateUtils.beginTransaction();
		//创建菜单对象
		//Menu menu = new Menu("系统菜单", "http://", 0, new Date(), new Date());
		Menu menu = new Menu("销售订单确认", "http://", 0, new Date(), new Date());
		try {
			//设置父菜单
			//menu.setParent(menuDao.findByName("系统菜单"));
			menu.setParent(menuDao.findByName("销售管理"));
			//添加菜单
			menuDao.save(menu);
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("添加菜单失败", e);
		}
	}

	/**
	 * 测试根据name查询菜单
	 * @throws Exception 
	 */
	@Test
	public void testFind() throws Exception {
		//开启事务
		HibernateUtils.beginTransaction();

		try {
			//查询菜单
			Menu menu = menuDao.findByName("系统菜单");
			System.out.println(menu);
			//遍历子菜单
			if (menu != null) {
				Set<Menu> children = menu.getChildren();
				if (children != null) {
					for (Menu menu2 : children) {
						System.out.println(menu2);
					}
				}
			}
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("查询菜单失败", e);
		}
	}

	/**
	 * 测试更新菜单
	 */
	@Test
	public void testUpdate() {
		//开启事务
		HibernateUtils.beginTransaction();

		try {
			//获取菜单对象
			Menu menu = menuDao.findByName("销售管理");
			//修改菜单
			menu.setModified(new Date());
			//更新菜单：利用持久态的特性，其实这里不update也可以更新。
			//menuDao.update(menu);
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("更新菜单失败", e);
		}
	}

	/**
	 * 测试根据id删除菜单
	 */
	@Test
	public void testDelete() {
		//开启事务
		HibernateUtils.beginTransaction();
		try {
			//删除菜单
			menuDao.delete(menuDao.findByName("销售订单添加"));
			//提交事务
			HibernateUtils.commit();
		} catch (Exception e) {
			HibernateUtils.rollback();
			logger.error("删除菜单失败", e);
		}
	}
}
