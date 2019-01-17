### 一、企业级权限管理系统：  
#### 一个自己学习做的RBAC（Role-Based Access Control）系统。主要分为部门/用户模块，权限/权限点模块，角色模块，操作日志模块等部分。采用SpringMVC+Spring+Mybatis完成，数据库连接池采用druid。源代码在source文件夹下，permission.sql文件是项目对应的sql文件。数据库和redis的配置文件分别是src\main\resources目录下的settings.properties和redis.properties。编译器用的IDEA，运行环境如下：
```
jdk :  1.8
mysql : 5.7.24
redis : 3.2.1
```
#### 各个模块（表)之间的关系如下图：
![image](https://github.com/TimePickerWang/project/blob/master/ssm-permission/img/table.jpg?raw=true)


#### 下面是系统界面截图：  
#### 1.部门/用户模块（维护部门和用户的关系）  
![image](https://github.com/TimePickerWang/project/blob/master/ssm-permission/img/dept.jpg?raw=true)

#### 2.角色模块（维护角色和用户、角色和权限的关系）

##### 角色和权限
![image](https://github.com/TimePickerWang/project/blob/master/ssm-permission/img/role1.jpg?raw=true)

##### 角色和用户
![image](https://github.com/TimePickerWang/project/blob/master/ssm-permission/img/role2.jpg?raw=true)

#### 3.权限/权限点模块（维护维护权限模块和权限点的关系） 
![image](https://github.com/TimePickerWang/project/blob/master/ssm-permission/img/aclmodule.jpg?raw=true)

#### 4.操作日志模块（主要为了查看对各个模块的操作记录、可以用过记录来对操作进行回滚）
![image](https://github.com/TimePickerWang/project/blob/master/ssm-permission/img/log.jpg?raw=true)


### 二、2个小游戏：纯java做的贪吃蛇和俄罗斯方块

#### 1.[贪吃蛇]

![image](https://github.com/TimePickerWang/project/blob/master/javaGame/snake/2018-08-12_174749.png?raw=true)

![image](https://github.com/TimePickerWang/project/blob/master/javaGame/snake/2018-08-12_174804.png?raw=true)


#### 2.[俄罗斯方块]

![image](https://github.com/TimePickerWang/project/blob/master/javaGame/tetris/2018-08-12_174506.png?raw=true)

![image](https://github.com/TimePickerWang/project/blob/master/javaGame/tetris/2018-08-12_174519.png?raw=true)

