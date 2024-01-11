package cls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
    //number of connections to the sql is maximum 20 but the default is 10.
    private static final int NUMBER_OF_CONNECTIONS=10;
    public static ConnectionPool instance=null; //singleton connection.
    private final Stack<Connection> connections=new Stack<>();


    private ConnectionPool() throws SQLException{
        System.out.println("Creating new connection pool instance");
        openAllConnection();  //initialize the openAllConnection method.
    }

    //opens a new connection
    private void openAllConnection() throws SQLException {
        //creates connection according to the number of connections assigned
        for (int counter=0; counter<NUMBER_OF_CONNECTIONS; counter++){
            //creates the connection each loop
            Connection connection = DriverManager.getConnection(DBManager.URL,DBManager.SQL_USER,DBManager.SQL_PASS);
            //pushes the new connection into the connections stack
            connections.push(connection);
        }
    }

    //closes the connection
    private void closeAllConnections() throws InterruptedException {
        synchronized (connections){ //critical code!
            while (connections.size()<NUMBER_OF_CONNECTIONS){ //sync all the connections and hold them while the number of connections is less than the NUMBER_OF_CONNECTIONS.
                connections.wait();
            }
            connections.removeAllElements(); //removes all the connections from the Stack.
        }

    }

    public static ConnectionPool getInstance(){
        //checking if the instance is null
        if (instance==null){
            //synchronized critical code, that another thread will not pass here: Singleton
            synchronized (ConnectionPool.class){
                //double check, that no other thread has created an instance.....
                if (instance==null){
                    try {   //tries to create a new connection pool
                        instance=new ConnectionPool();
                    } catch (SQLException e) { //if failed shows an error message
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return instance;
    }


    public Connection getConnection() throws InterruptedException {
        //critical code, locks all connections while they are empty
        synchronized (connections){
            if (connections.isEmpty()){
                connections.wait();
            }
            return connections.pop(); //the last connection to get in, is the first one out
        }
    }

    public void returnConnection(Connection connection){
        //critical code
        synchronized (connections){
            connections.push(connection);
            //notifies that we got back a connection for the user
            connections.notify();
        }
    }


}
