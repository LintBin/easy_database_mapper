# easy_database_mapper

Bint_DatabaseUtil的2.0版本，新增可自定义Java类名和数据库中的表名的对应命名规则（如t_user生成User类）和Java类属性名和表中的字段名的对应命名规则（m_name生成name字段）。


数据库工具：根据指定的数据库来生成表的相应的JavaBean

使用说明：请在dbconfig.xml中修改自己数据库所对应的相关数据，执行Main.java文件。生成的java文件在javabean文件夹里面。

补充： 1、支持自定义数据库字段类型和生成java类的域的对应关系，例如：MySQL中的Blob类型，可自定义为为java的Blob类，也可以对应byte[]。 详细可在src/type.properties中进行修改

2、暂仅支持MySQL，SqlServer

3、可修改生成的mybatis中的xml文件里面的jdbctype，可在src/jdbc-type.properties中修改

4、可定义指定的表中的字段名字转为指定的名字，可在src/customized-word中定义

5、mapper.xml文件中的命名空间和实体类包路径可在src/path-generate.properties定义

6、如果需要自定义Java类名和数据库中的表名的对应命名规则，复写JavaClassNameCallBack.execute方法即可。

7、如果需要自定义Java属性名和表中的字段名的对应命名规则，复写JavaPropertyNameCallback.execute方法即可。
