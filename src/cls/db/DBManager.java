package cls.db;

import cls.utils.Utilities;

import java.sql.*;
import java.util.Map;

public class DBManager {

    public static void initDataBase(){
        // Read content of file
        String fileContent = Utilities.getFileContent("src/cls/db/Initialization.sql");

        if(runQuery(fileContent)){
            System.out.println("DB INIT OK");
        } else {
            System.out.println("DB INIT ERROR!");
        }
    }

    public static boolean runQuery(String sql){
        //use connection from connection pool to send queries to our DB
        Connection connection = null;
        try{
            //get a connection for connection pool
            connection = ConnectionPool.getInstance().getConnection();

            //prepare our sql (string) and convert it to a language that mysql will understand
            PreparedStatement statement = connection.prepareStatement(sql);

            //run statment
            statement.execute();

            return true;
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    public static boolean runQuery(String sql, Map<Integer, Object> params){
        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            /*
            for (Map.Entry<Integer, Object> item: params.entrySet()){

            }
            */
            params.forEach((key,value)->{
                try{
                    //integer,string,date,double,boolean,float
                    if (value instanceof Integer){
                        statement.setInt(key,(Integer)value);
                    } else if (value instanceof String){
                        statement.setString(key,String.valueOf(value));
                    } else if (value instanceof Date){
                        statement.setDate(key,(Date)value);
                    } else if (value instanceof Double){
                        statement.setDouble(key,(Double) value);
                    } else if (value instanceof Boolean){
                        statement.setBoolean(key,(Boolean) value);
                    } else if (value instanceof Float){
                        statement.setFloat(key, (Float) value);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            //run the prepared statment
            statement.execute();
            return true;
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    public static ResultSet runQueryForResult(String sql, Map<Integer, Object> params){
        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            /*
            for (Map.Entry<Integer, Object> item: params.entrySet()){

            }
            */
            params.forEach((key,value)->{
                try{
                    //integer,string,date,double,boolean,float
                    if (value instanceof Integer){
                        statement.setInt(key,(Integer)value);
                    } else if (value instanceof String){
                        statement.setString(key,String.valueOf(value));
                    } else if (value instanceof Date){
                        statement.setDate(key,(Date)value);
                    } else if (value instanceof Double){
                        statement.setDouble(key,(Double) value);
                    } else if (value instanceof Boolean){
                        statement.setBoolean(key,(Boolean) value);
                    } else if (value instanceof Float){
                        statement.setFloat(key, (Float) value);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            //run the prepared statment
            return statement.executeQuery();
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }
}