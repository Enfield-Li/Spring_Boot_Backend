## 本作使用Spring boot重写原作[仿Reddit论坛应用](https://github.com/Enfield-Li/PERN_Stack_REST_api)后端

    实现一些主要功能，如用户登录注册，帖子CRUD，用户与帖子的投票等互动行为，向前端应用提供同样的REST api路径及数据。

技术栈：

      Java + Spring
      ORM: Hibernate
      数据库: Mysql
      额外依赖项：mapstruct
      
值得注意的地方：

1. Spring Data JPA [Projections](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections)(数据映射)：

    细节：使用[基于interface的映射](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.interfaces)，获取指定的字段，简化Hibernate生成的SQL语句，指定返还的数据；
      
2. 使用[@SqlResultSetMapping](https://docs.oracle.com/javaee/7/api/javax/persistence/SqlResultSetMapping.html)注解：

    细节:因Spring Data JPA 不支持相对复杂自定义Query，所以使用`EntityManager.createNativeQuery();` 创建SQL语句，将NativeQuery生成的值，转换成POJO；

3. 使用[mapstruct](https://mapstruct.org/)将POJO的数据，转换成响应对象：
    
    细节：使用mapstruct提供的`@Mappings`注解，手动转换POJO数据。
    

## An attempt to rewrite the backend from [Mock-Reddit app](https://github.com/Enfield-Li/PERN_Stack_REST_api)

    Implement some major features like user register/login, post CRUD, user's interaction with post, 
    provideing the same REST api end point and data.

Stacks:
    
    Java + Spring
    ORM: Hibernate
    Database: Mysql
    Additional dependency: mapstruct
    
Some worth noting implementations:

1. Utilize Spring Data JPA [Projections](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections):

   Details: Using [interface-based projections](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.interfaces), by specifying certain columns that limited the result of the queries, and therefore minimized SQL produced by Hibernate;

2. Utilize [@SqlResultSetMapping](https://docs.oracle.com/javaee/7/api/javax/persistence/SqlResultSetMapping.html) annotation：
   
   Details: Since Spring Data JPA does not supprot relatively complex/custom query, I used `EntityManager.createNativeQuery();` created native query, and transform the data to POJO;

3. Utilize [mapstruct](https://mapstruct.org/) that transform POJO to rsponse object:
   
   Details: Using `@Mappings` provided by mapstruct, mannually transforming POJO to response object.







