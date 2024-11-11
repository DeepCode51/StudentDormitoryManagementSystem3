//sql代码
package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class SQL {
    public Connection conn;
    //Connection接口是构造类的基础，其他接口出现需要它
    public Statement st;
    //Statement是执行数据库操作的接口，其对象也就是st用于执行简单的sql语句
    public ResultSet rs;
    //ResultSet结果集接口

    //获取链接
    public Connection getConn() throws SQLException, ClassNotFoundException {
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/graduation_318_student?useUnicode=true&characterEncoding=UTF-8&tinyInt1isBit=false";
        //数据库默认端口号，一般为3306（我的可能特殊，可以改成3306）学生是数据库名字
        String user = "root"; //登录用的用户名，下面是密码，可以自己改。
        String password = "123456";
        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功");
        } catch (SQLException ex1) {
            System.out.println("数据库连接失败");
        }
        return conn;
    }

    //关闭链接
//几个接口就捕捉异常几次，（用其对象）
    public void Close() {
       try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            st.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //登录
    public int landing(String name1, String password1) {
        int num = 0;
        String sql = "select *from user";
        try {
            getConn();
            st = conn.createStatement();
            rs = st.executeQuery(sql);//Statement接口对象rs执行上面的查询用户表sql操作
            while (rs.next()) {
                String name = rs.getString(1).trim();//得到用户名
                //trim函数清除单元格中的空格
                String password = rs.getString(2).trim();//得到密码
                if (name.equals(name1) && password.equals(password1)) {
                    num = 1;//a
                }

            }
        } catch (SQLException e) {
            // TODO: handle exception
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //查询异常 固定的
        }
        Close();//关闭
        return num;//注意初始是0，如果用户名和密码符合就变成了1在上面a处，后面同理
    }

    //查询
    public Vector<Vector<Object>> query(String tableName, int column) {
        int num = 0;
        String sql = "select * from " + tableName;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();//利用vector创建存数据对象
        try {
            getConn();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector<Object> rowdata = new Vector<Object>();
                for (num = 1; num <= column; num++) {
                    rowdata.add(rs.getString(num));
                }
                data.add(rowdata);
            }
        } catch (SQLException ex1) {
            System.out.println("失败" + ex1);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();//也是捕捉异常，后面同理
        }
        Close();
        return data;
    }

    //删除
    public int delete(String sql) {
        int num = 0;
        try {
            getConn();
            st = conn.createStatement();
            num = st.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO: handle exception
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Close();
        return num;
    }

    //保存
    public Vector<Vector<Object>> Save(String[] sqlvalue, String tableName, int row, int column) {
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        try {
            getConn();
            st = conn.createStatement();
            st.executeUpdate("delete from " + tableName);
            for (int i = 0; i < row; i++) {
                st.executeUpdate(sqlvalue[i].toString());
            }
            data = query(tableName, column);

        } catch (SQLException e) {
            // TODO: handle exception
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }
}
