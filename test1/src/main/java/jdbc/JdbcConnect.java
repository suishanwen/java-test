package jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


class DAOException extends Exception {
    public DAOException() {
        super();
    }

    public DAOException(String msd) {
        super(msd);
    }
}

public class JdbcConnect {

    private static String user = "root";
    private static String password = "";
    private static String dbName = "db_example";
    private static  String url = "jdbc:mysql://localhost:3306/"+dbName+"?user="+user+"&password="+password+"&useUnicode=true&characterEncoding=gb2312";

    private static DataSource dataSource = null;

    /**
     * 加载驱动
     */
    static{
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e){
            System.out.println("Exception:"+e.getMessage()+"");
            throw new ExceptionInInitializerError(e);
        }
    }

    private JdbcConnect(){
    }

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url);
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

    /**
     * 释放资源
     * @param rs ResultSet
     * @param st Statement
     * @param conn Connection
     */
    public static void free(ResultSet rs,Statement st,Connection conn){
        try{
            if(rs != null){
                rs.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(st != null){
                    st.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                try{
                    if(conn != null){
                        conn.close();
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

}