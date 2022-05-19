# 尝试用 Java Spring 重写原作 [仿Reddit论坛应用](https://github.com/Enfield-Li/PERN_Stack_REST_api) 后端

    ⏬⏬⏬ English Description Below ⏬⏬⏬

作品描述：

    实现一些主要功能，如用户登录注册，帖子 CRUD，用户与帖子的投票等互动行为，向前端应用提供同样的 REST api 路径及数据。

技术栈：

    Java + Spring
    ORM 相关: Spring Data JPA + Hibernate
    数据库: Mysql
    额外依赖项：Lombok, mapstruct
      
值得注意的地方：

1. Spring Data JPA [Projections](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections)(数据映射)：

    细节：使用基于 Interface 的映射，获取指定的字段，简化 Hibernate 生成的 SQL 语句，指定返还的数据；
      
2. 使用 [@SqlResultSetMapping](https://docs.oracle.com/javaee/7/api/javax/persistence/SqlResultSetMapping.html) 注解：

    细节: 因 Spring Data JPA 不支持相对复杂自定义 Query，所以使用 `EntityManager.createNativeQuery();` 创建 SQL 语句，将 NativeQuery 生成的值，转换成 POJO；

3. 使用 [mapstruct](https://mapstruct.org/) 将 POJO 的数据，转换成响应对象：
    
    细节：使用 mapstruct 提供的 `@Mappings` 等注解，转换 POJO 数据。
    



# An attempt to rewrite the backend from [Mock-Reddit app](https://github.com/Enfield-Li/PERN_Stack_REST_api) With Java Spring

Description:

    Implement some major features like user register/login, post CRUD, user's interaction with post, 
    provideing the same REST api end point and data to frontend.

Stacks:
    
    Java + Spring
    ORM provider: Spring Data JPA + Hibernate
    Database: Mysql
    Additional dependency: Lombok, mapstruct
    
Some worth noting implementations:

1. Utilize Spring Data JPA [Projections](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections):

   Details: Using interface-based projections, by specifying certain columns that limited the result of the queries, and therefore minimized SQL produced by Hibernate;

2. Utilize [@SqlResultSetMapping](https://docs.oracle.com/javaee/7/api/javax/persistence/SqlResultSetMapping.html) annotation：
   
   Details: Since Spring Data JPA does not supprot relatively complex/custom query, `EntityManager.createNativeQuery();` is used to create native queries, and with the help of @SqlResultSetMapping annotation that transform the raw data to POJO;

3. Utilize [mapstruct](https://mapstruct.org/) that transform POJO to response object:
   
   Details: Using `@Mappings` provided by mapstruct, transforming POJO to response object.







