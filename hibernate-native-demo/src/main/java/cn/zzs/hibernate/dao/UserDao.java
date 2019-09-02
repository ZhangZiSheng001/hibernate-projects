package cn.zzs.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import cn.zzs.hibernate.pojo.User;

/**
 * @ClassName: UserDao
 * @Description: 用户操作的接口
 * @author: zzs
 * @date: 2019年9月2日 上午11:30:11
 */
public interface UserDao {
	/**
	 * 
	 * @Title: save
	 * @Description: 添加用户
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:42:31
	 * @param user
	 * @return: void
	 */
	void save(User user) throws Exception;

	/**
	 * 
	 * @Title: update
	 * @Description: 更新用户
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:44:03
	 * @param user
	 * @throws Exception
	 * @return: void
	 */
	void update(User user) throws Exception;

	/**
	 * 
	 * @Title: find
	 * @Description: 根据id查询用户
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:45:11
	 * @param id
	 * @throws Exception
	 * @return: User
	 */
	User find(Serializable id) throws Exception;

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除用户
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:49:58
	 * @param user
	 * @throws Exception
	 * @return: void
	 */
	void delete(User user) throws Exception;

	/**
	 * 
	 * @Title: findByNameLikeWithPageUserHQL
	 * @Description: 使用HQL根据用户名分页模糊查询用户
	 * @author: zzs
	 * @date: 2019年9月2日 下午1:19:29
	 * @param name
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 * @return: List<User>
	 */
	List<User> findByNameLikeWithPageUserHQL(String name,Integer firstResult,Integer maxResults) throws Exception;

	/**
	 * 
	 * @Title: findByNameLikeWithPageUserQBC
	 * @Description: 使用QBC根据用户名分页查询用户
	 * @author: zzs
	 * @date: 2019年9月2日 下午1:19:47
	 * @param name
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 * @return: List<User>
	 */
	List<User> findByNameLikeWithPageUserQBC(String name,Integer firstResult,Integer maxResults) throws Exception;

	/**
	 * 
	 * @Title: findByNameLikeWithPageUserSQL
	 * @Description: 使用SQL根据用户名分页查询用户
	 * @author: zzs
	 * @date: 2019年9月2日 下午1:20:00
	 * @param name
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 * @return: List<User>
	 */
	List<User> findByNameLikeWithPageUserSQL(String name,Integer firstResult,Integer maxResults) throws Exception;
}
