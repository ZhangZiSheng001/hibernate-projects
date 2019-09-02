
# hibernate

## 简介
Hibernate是一个持久层框架，通过建立表和对象的ORM，让我们可以以操作对象的形式对数据库进行增删改查，从而有效地避免sql语句的硬代码，简化了数据库操作。另外，Hibernate整合了c3p0可以创建和管理连接，而且Hibernate提供了事务机制。  

针对JPA规范，Hibernate提供了一套实现，这个操作上比Hibernate原有的api要复杂，独立性较差，但Spring Data JPA通过对JPA的封装，提供了非常简便的使用方式，后续会补充相关内容。  

不管是使用Hibernate的哪套api具体需要掌握的重点如下：
1. **怎么配置对象映射，包括一对多关联、多对多关系关联、自关联、级联操作、主键生成策略、抓取策略等；**  
2. **CRUD的api操作；**  
3. **三种查询方式：HQL，QBC或SQL；**  

## 项目实现的需求
分别采用Hibernate原有API和JPA的API(均采用注解方式配置对象映射)，针对三个实体进行增删改查操作：  
1. 用户：  
2. 角色：和用户是一对多关系  
3. 菜单：和角色是多对多关系，本身自关联  


## 项目工程环境
JDK：1.8.0_201  
maven：3.6.1  
IDE：Spring Tool Suites4 for Eclipse  
mysql：5.7  
Hibernate：5.4.4.Final  

## 项目目录
测试使用Hibernate原有api（非JPA实现）对数据库进行增删改查  
[hibernate-native-demo](https://github.com/ZhangZiSheng001/hibernate-projects/tree/master/hibernate-native-demo)  

测试使用Hibernate的JPA API对数据库进行增删改查  
[hibernate-jpa-demo](https://github.com/ZhangZiSheng001/hibernate-projects/tree/master/hibernate-jpa-demo)  

> 学习使我快乐！！
