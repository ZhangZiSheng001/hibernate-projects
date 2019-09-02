package cn.zzs.hibernate.dao;

import cn.zzs.hibernate.pojo.Menu;

/**
 * @ClassName: MenuDao
 * @Description: 菜单操作的接口
 * @author: zzs
 * @date: 2019年9月2日 上午11:30:11
 */
public interface MenuDao {
	/**
	 * 
	 * @Title: save
	 * @Description: 添加菜单
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:42:31
	 * @param menu
	 * @return: void
	 */
	void save(Menu menu) throws Exception;

	/**
	 * 
	 * @Title: update
	 * @Description: 更新菜单
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:44:03
	 * @param menu
	 * @throws Exception
	 * @return: void
	 */
	void update(Menu menu) throws Exception;

	/**
	 * 
	 * @Title: findByName
	 * @Description: 根据name查询菜单
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:45:11
	 * @param name
	 * @throws Exception
	 * @return: Menu
	 */
	Menu findByName(String name) throws Exception;

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除菜单
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:49:58
	 * @param menu
	 * @throws Exception
	 * @return: void
	 */
	void delete(Menu menu) throws Exception;
}
