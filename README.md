# 尝试用 Java Spring 重写原作 [仿Reddit论坛应用](https://github.com/Enfield-Li/PERN_Stack_REST_api) 后端

    ⏬⏬⏬ English Description Below ⏬⏬⏬

作品描述：

    实现一些主要功能，如用户登录注册，帖子 CRUD，用户与帖子的投票等互动行为，向前端应用提供同样的 REST api 路径及数据。

技术栈：

    Java + Spring
    ORM 相关: Spring Data JPA(Hibernate) + Mybatis
    数据库: Mysql
    额外依赖项：Lombok, mapstruct
      
值得注意的实施与细节：

1. 使用 [@Transactional](https://docs.oracle.com/javaee/7/api/javax/transaction/Transactional.html) 注解：

    细节：
        1) [在 StackOverflow 询问 @Transactional 注解](https://stackoverflow.com/questions/71907612/how-to-understand-transactional-with-setters-in-java) 的底层运作机制;
        2) 使用 `@Transactional` 配合 setter 用于在数据库事务中更新数据；

2. 运用 Spring Data JPA [Projections](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections) (数据映射)：

    细节：使用基于 Interface 的映射，获取指定的字段，简化 Hibernate 生成的 SQL 语句，指定返还的数据；
      
3. 整合 [Mybatis](https://mybatis.org/mybatis-3/index.html)：

    细节：  
        1) 使用 Mybatis 提供的 `Dynamic SQL` API，按不同条件查询数据; 
        2) 使用 `@Mapper` 等注解，将对应的 Mapper 类，注入到需要用到的服务层中； 
        3) 使用 `@Select` 注解，替换原来按 Spring Data JPA 的思路编写的相对较难处理的、含 join 的查询语句；

4. 使用 [mapstruct](https://mapstruct.org/) 将 POJO 对象，转换成响应对象：
    
    细节：使用 mapstruct 提供的 `@Mappings` 等注解，将 POJO 对象，转换成正规化的响应对象。
    
5. （整合 Mybatis 之前）使用 [@SqlResultSetMapping](https://docs.oracle.com/javaee/7/api/javax/persistence/SqlResultSetMapping.html) 注解：

    细节: 因 Spring Data JPA 不支持包含 join 的相对复杂 Query，所以使用 `EntityManager.createNativeQuery();` 创建 SQL 查询语句，将 NativeQuery 返还的值，转换成 POJO。



# An attempt to rewrite the backend from [Mock-Reddit app](https://github.com/Enfield-Li/PERN_Stack_REST_api) With Java Spring

Description:

    Implement some major features like user register/login, post CRUD, user's interaction with post, 
    provideing the same REST api end point and data to frontend.

Stacks:
    
    Java + Spring
    ORM provider: Spring Data JPA(Hibernate) + Mybatis
    Database: Mysql
    Additional dependency: Lombok, mapstruct
    
Some worth noting implementation details:

1. Utilize [@Transactional](https://docs.oracle.com/javaee/7/api/javax/transaction/Transactional.html) annotation:

    Details: 
        1) Ask about [How to understand @Transactional annotation](https://stackoverflow.com/questions/71907612/how-to-understand-transactional-with-setters-in-java) on StackOverflow;
        2) Using @Transactional, and with setters, to update data in a database transaction;

2. Utilize Spring Data JPA [Projections](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections):

   Details: Using interface-based projections, by specifying certain columns that limited the result of the queries, and therefore minimized SQL produced by Hibernate;

3. Integrate [Mybatis](https://mybatis.org/mybatis-3/index.html):

    Details: 
        1) Using `Dynamic SQL` API provided by Mybatis, conditionally query data;
        2) Using it's `@Mappping` annotation, produce mapper class and injecting to relavent service layers;
        3) Using it's `@Select` annotation, in an effort to replace inconvinent, before-coded, join-included query statement that's according to Spring Data JPA's specification;

4. Utilize [mapstruct](https://mapstruct.org/) that transform POJO to response object:
   
   Details: Using `@Mappings` provided by mapstruct, transforming POJO to normalized response object.

5. Utilize [@SqlResultSetMapping](https://docs.oracle.com/javaee/7/api/javax/persistence/SqlResultSetMapping.html) annotation：
   
   Details: Since Spring Data JPA does not supprot relatively complex/custom query, `EntityManager.createNativeQuery();` is used to create native queries, and with the help of @SqlResultSetMapping annotation that transform the raw data to POJO;