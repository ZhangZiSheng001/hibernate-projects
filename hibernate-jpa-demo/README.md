
# Hibernate JPA

## 简介
`Hibernate`是一个持久层框架，通过建立表和对象的`ORM`，让我们可以以操作对象的形式对数据库进行增删改查，从而有效地避免sql语句的硬代码，简化了数据库操作。另外，`Hibernate`整合了`c3p0`可以创建和管理连接，而且`Hibernate`提供了事务机制。  

针对`JPA`规范，`Hibernate`提供了一套实现，这个操作上比`Hibernate`原有的api要复杂，独立性较差，但`Spring Data JPA`通过对`JPA`的封装，提供了非常简便的使用方式，后续会补充相关内容。  

学习具体需要掌握的重点如下：
1. **怎么配置对象映射，包括一对多关联、多对多关系关联、自关联、级联操作、主键生成策略、抓取策略等；**  

2. **CRUD的api操作；**  

3. **三种查询方式：HQL，QBC或SQL；**  

## 项目实现的需求
分别采用`Hibernate JPA`的API(均采用注解方式配置对象映射)，针对三个实体进行增删改查操作：  

1. 用户：  

2. 角色：和用户是一对多关系  

3. 菜单：和角色是多对多关系，本身**自关联**  

## 项目工程环境
JDK：1.8.0_201  
maven：3.6.1  
IDE：Spring Tool Suites4 for Eclipse  
mysql：5.7  
Hibernate：5.4.4.Final  

## persistence.xml文件的配置
注意，主配置文件存放路径必须classpath下的`META-INF`目录下，而且文件名必须是`persistence.xml`，这样才能自动加载。  
这个文件配置需要注意以下几点：  

1. 数据库的方言配置：一般使用`MySQL5Dialect`、`MySQL55Dialect`、`MySQL57Dialect`，分别对应不同版本的数据库；  

2. 数据库引擎：`MyISAM`和`InnoDB`，默认是`MyISAM`，一般都需要修改。  

3. 自动建表：`update/create-drop/create/none`。一般使用`update`或者`none`。  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
    http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd" version="1.0">
    
    <!-- 事务类型使用本地事务 -->
    <persistence-unit name="JPA" transaction-type="RESOURCE_LOCAL">
          <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
          <properties>
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL55Dialect"/>
            <property name="hibernate.dialect.storage_engine" value="innodb"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name = "hibernate.connection.driver_class" value = "com.mysql.cj.jdbc.Driver"/>
            <property name = "hibernate.connection.url" value = "jdbc:mysql://localhost:3306/hibernate?useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT%2B8&amp;useSSL=true"/>
            <property name = "hibernate.connection.username" value = "root"/>
            <property name = "hibernate.connection.password" value = "root"/>
          </properties>
    </persistence-unit>
  
</persistence>
```

## 对象映射的配置-注解
### 常规配置
这里需要注意主键的配置，支持`IDENTITY`、`SEQUENCE`、`TABLE`和`AUTO`。  

```java
/**
 * @ClassName: User
 * @Description: 用户实体类
 * @author: zzs
 * @date: 2019年9月2日 上午11:14:53
 */
@Entity
@Table(name = "native_user")
public class User {
	/**
	 * 用户id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //使用主键自动增长
	@Column(name = "user_id")
	private Long id;

	/**
	 * 用户名
	 */
	@Column(name = "user_name", unique = true)
	private String name;

	/**
	 * 用户年龄
	 */
	@Column(name = "user_age")
	private Integer age;

	/**
	 * 记录创建时间
	 */
	@Column(name = "gmt_create")
	private Date create;

	/**
	 * 记录最后一次修改时间
	 */
	@Column(name = "gmt_modified")
	private Date modified;

    //以下方法省略
}
```

如果是`uuid`的主键，配置方式如下：  

```java
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	@Column(name = "menu_id")
	private String id;
```

### 一对多配置
用户只有一个角色，一个角色下有多个用户。  

#### 一方
```java
	/**
	 * 角色关联的用户
	 */
	@OneToMany(mappedBy = "role")
	@org.hibernate.annotations.ForeignKey(name = "none")
	private Set<User> users = new HashSet<User>();
```

#### 多方
```java
	/**
	 * 用户角色
	 */
	@ManyToOne
	@JoinColumn(name = "user_role_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
	private Role role;
```

### 多对多配置
一个角色可以有多个权限菜单，一个菜单可以分配多个角色。  

#### 多方1
```java
	/**
	 * 角色包含的权限菜单
	 */
	//@JoinTable:配置中间表信息
	//@joinColumns:建立当前表在中间表中的外键字段
	@ManyToMany
	@JoinTable(name = "native_role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"), foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
	private Set<Menu> menus = new HashSet<Menu>();
```

#### 多方2
```java
	/**
	 * 包含的角色
	 */
	@ManyToMany(mappedBy = "menus")
	@org.hibernate.annotations.ForeignKey(name = "none")
	private Set<Role> roles = new HashSet<Role>();
```

### 自关联配置
一个权限菜单有多个子菜单，且指向父菜单。  

```java
	/**
	 * 父菜单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_parent_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
	private Menu parent;

	/**
	 * 子菜单
	 */
	@SuppressWarnings("deprecation")
	@OneToMany(targetEntity = Menu.class, cascade = { CascadeType.ALL }, mappedBy = "parent")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("order")
	@org.hibernate.annotations.ForeignKey(name = "none")
	private Set<Menu> children = new HashSet<Menu>();
```

### 关于取消外键自动生成
实际项目中，我们都不会在数据库中建立外键，因为外键会降低数据库操作的效率。一般都是通过代码逻辑来控制。但是`Hibernate`会自动地更新外键，这里说说解决办法。  

第一种比较简单，即配置`hibernate.hbm2ddl.auto`为`none`，这样`Hibernate`就不会自动维护外键，当然，我们要提前建好表。这种方式其实对性能也是有好处的，推荐使用。  

第二种就是本项目采取的方式。其实，主要是不想手动建表。这种方式需要在一方和多方都配置，缺点就是`@org.hibernate.annotations.ForeignKey`注解已经过时，但目前我还没找到替代方案。  
```java
	/**
	 * 多方的@JoinColumn中增加以下foreignKey属性
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_parent_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
	private Menu parent;

	/**
	 * 一方增加@org.hibernate.annotations.ForeignKey属性
	 */
	@SuppressWarnings("deprecation")
	@OneToMany(targetEntity = Menu.class, cascade = { CascadeType.ALL }, mappedBy = "parent")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("order")
	@org.hibernate.annotations.ForeignKey(name = "none")
	private Set<Menu> children = new HashSet<Menu>();
```

## 常用的API
使用`Hibernate JPA`获取数据库操作对象`EntityManager`时，没有像`Session`一样的`getCurrentSession`方法来获取当前线程的对象，所以我们需要手动绑定。
  
注意，以下为DAO层代码，事务在服务层开启。另外，sql语句的占位符一般有三种：`?`、`?1`、`:abc`，在JPQL中其实已不支持第一种方式了，所以，建议还是采用后面两种。    

### 基本的增删改查
```java
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
```

### HQL
注意：`HQL`不支持`limit`等非通用的语句。  

```java
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
```

### QBC
针对`QBC`的API，不得不吐槽，虽然查询的逻辑清晰，但实在太繁琐了。  

```java
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
```

### SQL
```java
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
```

> 学习使我快乐！！
