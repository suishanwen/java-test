package jdbc;


import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) throws Exception{
        //测试代码：
        test();
        //标准规范代码：
        template();
    }

    //模板代码
    public static void template(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcConnect.getConnection();
            //创建语句
            st = conn.createStatement();
            //执行语句
            rs = st.executeQuery("select * from user");
            //处理结果
            while(rs.next()){
                System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" + rs.getObject(3) + "\t");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            JdbcConnect.free(rs, st, conn);
        }
    }

    //测试
    static void test() throws Exception{
        //注册驱动
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        //通过系统属性来注册驱动
        System.setProperty("jdbc.drivers","");
        //静态加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        //建立连接
        String url = "jdbc:mysql://localhost:3306/db_example";
        String userName = "root";
        String password = "";
        Connection conn = DriverManager.getConnection(url,userName,password);

        //创建语句
        Statement st = conn.createStatement();

        //执行语句
        ResultSet rs = st.executeQuery("select * from user");

        //处理结果
        while(rs.next()){
            System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" + rs.getObject(3) + "\t");
        }

        //释放资源
        rs.close();
        st.close();
        conn.close();
    }
}
