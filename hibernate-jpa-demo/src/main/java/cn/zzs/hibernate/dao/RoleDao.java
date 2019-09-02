package cn.zzs.hibernate.dao;

import java.io.Serializable;

import cn.zzs.hibernate.pojo.Role;

/**
 * @ClassName: RoleDao
 * @Description: 角色操作的接口
 * @author: zzs
 * @date: 2019年9月2日 上午11:30:11
 */
public interface RoleDao {
	/**
	 * 
	 * @Title: save
	 * @Description: 添加角色
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:42:31
	 * @param role
	 * @return: void
	 */
	void save(Role role) throws Exception;

	/**
	 * 
	 * @Title: update
	 * @Description: 更新角色
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:44:03
	 * @param role
	 * @throws Exception
	 * @return: void
	 */
	void update(Role role) throws Exception;

	/**
	 * 
	 * @Title: find
	 * @Description: 根据id查询角色
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:45:11
	 * @param id
	 * @throws Exception
	 * @return: Role
	 */
	Role find(Serializable id) throws Exception;
	
	/**
	 * 
	 * @Title: findByName
	 * @Description: 根据name查询角色
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:45:11
	 * @param name
	 * @throws Exception
	 * @return: Role
	 */
	Role findByName(String name) throws Exception;
	
	/**
	 * 
	 * @Title: delete
	 * @Description: 删除角色
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:49:58
	 * @param role
	 * @throws Exception
	 * @return: void
	 */
	void delete(Role role) throws Exception;
}
