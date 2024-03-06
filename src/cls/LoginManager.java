package cls;

import static java.util.Objects.nonNull;

public class LoginManager {

    private static LoginManager instance = null;

    private LoginManager(){

    }

    public static LoginManager getInstance(){
        if(!nonNull(instance)){
            instance = new LoginManager();
        }
            return instance;
    }

//    public ClientFacade login(String email, String password, ClientType clientType){
//
//    }

}
