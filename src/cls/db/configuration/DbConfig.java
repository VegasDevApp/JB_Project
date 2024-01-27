package cls.db.configuration;

import java.util.Map;

public class DbConfig {

    private static DbConfig instance = null;

    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String schema;

    private DbConfig(String host, int port, String user, String password, String schema) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.schema = schema;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getSchema() {
        return schema;
    }

    public String getConnectionString(){
        return String.format("jdbc:mysql://%s:%d/%s?allowMultiQueries=true", host, port, schema);
    }

    public static DbConfig getInstance(){
        return instance;
    }

    // Fires ones while app is starts.
    public static void load(){
        if(instance == null){
            // Get values from db-config.json
            Map<String, String> fromFile = Loader.getConfigurationFromJSON();

            // Create instance of builder (design pattern)
            DataBaseConfigBuilder builder = new DataBaseConfigBuilder();

            if(!fromFile.isEmpty()){

                // Set host
                if(fromFile.containsKey("host"))
                    builder.setHost(fromFile.get("host"));

                // Set port
                if(fromFile.containsKey("port"))
                    builder.setPort(Integer.parseInt(fromFile.get("port")));

                // Set user
                if(fromFile.containsKey("user"))
                    builder.setUser(fromFile.get("user"));

                // Set password
                if(fromFile.containsKey("password"))
                    builder.setPassword(fromFile.get("password"));

                // Set schema
                if(fromFile.containsKey("schema"))
                    builder.setSchema(fromFile.get("schema"));

                builder.build();

            } else {
                // TODO exception;
            }

        }
    }

    public static class DataBaseConfigBuilder {
        private String host;
        private int port;
        private String user;
        private String password;
        private String schema;

        public DataBaseConfigBuilder setHost(String host) {
            this.host = host;
            return this;
        }

        public DataBaseConfigBuilder setPort(int port) {
            this.port = port;
            return this;
        }

        public DataBaseConfigBuilder setUser(String user) {
            this.user = user;
            return this;
        }

        public DataBaseConfigBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public DataBaseConfigBuilder setSchema(String schema) {
            this.schema = schema;
            return this;
        }

        public DbConfig build() {

            if(instance == null){
                synchronized (DbConfig.class){
                    if(instance == null){
                        instance = new DbConfig(
                                this.host,
                                this.port,
                                this.user,
                                this.password,
                                this.schema
                        );
                    }
                }
            }
            return instance;
        }

    }
}
