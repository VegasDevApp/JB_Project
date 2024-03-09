package cls;

import cls.enums.ClientType;
import cls.facade.ClientFacade;
import cls.strategy.impl.DefaultLoginStrategy;

import static java.util.Objects.nonNull;

public class LoginManager {

    private static LoginManager instance = null;
    private final static DefaultLoginStrategy loginStrategy = new DefaultLoginStrategy();
    private LoginManager(){

    }

    public static LoginManager getInstance(){
        if(!nonNull(instance)){
            instance = new LoginManager();
        }
            return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType){
        return loginStrategy.getFacadeByLogin(email, password, clientType);
    }

}
