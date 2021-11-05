**访问学者查询系统后端**

基于`springboot`开发，用到了`shiro` `mybatis-plus` `jwt` `swagger`

接口文档可在项目启动时通过  http://localhost:8080/swagger-ui/index.html 访问

shiro也可以产生token 不需要用到jwt

实际上shiro给前端一个sessionId jwt给前端一个token

**项目部署**

用到idea上的插件docker链接服务器上的docker

`mvn clean`

`mvn package` (在`pom.xml`中绑定了`mvn docker:build`)

生成镜像后再生成容器

需要指定端口映射 `docker`容器的 `8575`端口映射到服务器的`8575`端口

