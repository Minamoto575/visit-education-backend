**访问学者查询系统后端**

基于`springboot`开发，用到了`shiro` `mybatis-plus` `jwt` `swagger`

接口文档可在项目启动时通过  `http://localhost:8575/swagger-ui/index.html` 访问

shiro也可以产生token 不需要用到jwt

实际上shiro给前端一个sessionId jwt给前端一个token

前端代码：coding私有仓库，email:911190218@qq.com

数据库：见`visitor_education.sql`

**私人服务器部署**

采用腾讯的平台coding进行持续集成,生成docker镜像，以容器的形式运行在服务器上。

**2021.12.17 甲方服务器部署**

由于甲方服务器安全策略，80端口不支持`delete` `put`请求，遂改成`post`。采用jar包+nginx反向代理部署。

跳过测试打成jar包： `mvn clean package -DskipTests`

项目`/target/`下生成`visit-education-backend.jar`

放在服务器`/root/visitor_education/`下,替换原来的`jar`包

运行shell脚本 `./strat.sh`

查看`jar`包是否正常运行 `ps -ef|grep java`

**2021.12.27**

弃用jwt和shiro，采用sa-token

测试人员账号密码： `test` `whucs123`