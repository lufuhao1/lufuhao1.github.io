package dataBase.com.data;

import java.sql.SQLException;
import java.util.*;

/****************
 * Author:接口，链接数据库 Day:2016年8月26日 Time:下午5:24:01 Functions:
 *****************/
public interface Data {
	//增删改操作 
	int zsg(String sal) throws SQLException;
   //查询数据  返回集合显示
	List<Map<String, String>> cha(String sql) throws SQLException;
   //批处理  统一处理整张表单
	int[] piChuLi(String[] sqls) throws SQLException;
   //事物的提交  完成数据的操作
	boolean shiWu(String[] sqls) throws SQLException;

}
