package mysqlUtil;

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;

import java.sql.*;

/**
 * Created by lqp on 2019/11/18
 */
public class MysqlUtil {
    static final String TABLENAME = "acoustic_see";
    /**
     * 加载驱动
     * */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * */
    public static Connection getConnection() {

        String url = "jdbc:mysql://101.132.76.24:3306/"+ TABLENAME;
        String username = "root";
        String password = "Lqp*0911";
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }


    /**
     * 关闭连接
     * */
    public static void close(ResultSet rs, Statement stm, Connection con) {

        try {

            if (rs != null)
                rs.close();

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 插入数据，用得使用可以把 sql语句 当成参数
     * */
    public void insert(String sql) {
//        String sql = "insert into "+TABLENAME+" ( acoustic_wav ) values('acoustic_wav')";
        Connection con = getConnection();
        Statement stm = null;
        try {
            stm = con.createStatement();
            //执行 SQL 语句并返回结果
            int result = stm.executeUpdate(sql);
            if (result != 0) {
                System.out.println("操作成功,受影响" + result + "行");
            }
        } catch (SQLException e) {
            System.out.println("操作失败");
        } finally {
            close(null, stm, con);
        }
    }

    /**
     * 删除操作，用得使用可以把 sql语句 当成参数
     * */
    public void delete() {
        String sql = "delete from student_table  where name = 'pinsily'";
        Connection con = getConnection();
        Statement stm = null;
        try {
            stm = con.createStatement();
            int count = stm.executeUpdate(sql);
            System.out.println("删除 " + count + " 条数据\n");
        } catch (SQLException e) {
            System.out.println("删除数据失败");
        }finally {
            close(null, stm, con);
        }
    }

    /**
     * 查询数据库,用得使用可以把 sql语句 当成参数
     * */
    public void query() {

        String sql = "select * from student_table";
        Connection con = getConnection();
        Statement stm = null;
        ResultSet rs = null;

        try {

            stm = con.createStatement();

            //执行sql查询语句，返回查询数据的结果集
            rs = stm.executeQuery(sql);

            System.out.println("查询结果为：");

            // 判断是否还有下一个数据
            while (rs.next()) {

                // 根据字段名获取相应的值
                String name = rs.getString("name");
                int id = rs.getInt("id");

                //输出查到的记录的各个字段的值
                System.out.println(id+":"+name);

            }

        } catch (SQLException e) {
            System.out.println("查询数据失败");

        }finally {

            close(rs, stm, con);

        }
    }


    /**
     * 更新操作,用得使用可以把 sql语句 当成参数
     * */
    public void update() {

        String sql = "update student_table set name='zhuyuanpeng' where id = 3 ";
        Connection con = getConnection();
        Statement stm = null;

        try {

            //创建用于执行sql语句的Statement对象
            stm = con.createStatement();

            // 执行更新操作的sql语句，返回更新数据的个数
            int count = stm.executeUpdate(sql);

            //输出更新操作的处理结果
            System.out.println("表中更新 " + count + " 条数据");


        } catch (SQLException e) {
            System.out.println("更新数据失败");
        }finally {

            close(null, stm, con);

        }
    }


    /**
     * 测试函数
     * */
//    public static void main(String[] args) {
//
////        testJDBC j = new testJDBC();
////
////        //j.insert();
////        //j.delete();
////        //j.update();
////        j.query();
//
//    }

    }
