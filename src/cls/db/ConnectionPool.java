package cls.db;

import cls.db.configuration.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

    private DbConfig dbConfig = DbConfig.getInstance();

    //number of connections to the sql is maximum 20 but the default is 10.
    private static final int NUMBER_OF_CONNECTIONS = 10;

    //singleton connection.
    public static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();


    private ConnectionPool() throws SQLException {
        System.out.println("Creating new connection pool instance");
        //initialize the openAllConnection method.
        openAllConnection();
    }

    //opens a new connection
    private void openAllConnection() throws SQLException {
        //creates connection according to the number of connections assigned
        for (int counter = 0; counter < NUMBER_OF_CONNECTIONS; counter++) {
            //creates the connection each loop
            Connection connection = DriverManager.getConnection(dbConfig.getConnectionString(), dbConfig.getUser(), dbConfig.getPassword());
            //pushes the new connection into the connections stack
            connections.push(connection);
        }
    }

    //closes the connection
    private void closeAllConnections() throws InterruptedException {
        //critical code!
        synchronized (connections) {
            //sync all the connections and hold them while the number of connections is less than the NUMBER_OF_CONNECTIONS.
            while (connections.size() < NUMBER_OF_CONNECTIONS) {
                connections.wait();
            }
            //removes all the connections from the Stack.
            connections.removeAllElements();
        }

    }

    public static ConnectionPool getInstance() {
        //checking if the instance is null
        if (instance == null) {
            //synchronized critical code, that another thread will not pass here: Singleton
            synchronized (ConnectionPool.class) {
                //double check, that no other thread has created an instance.....
                if (instance == null) {
                    try {   //tries to create a new connection pool
                        instance = new ConnectionPool();
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
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop(); //the last connection to get in, is the first one out
        }
    }

    public void restoreConnection(Connection connection) {
        //critical code
        synchronized (connections) {
            connections.push(connection);
            //notifies that we got back a connection for the user
            connections.notify();
        }
    }


}
