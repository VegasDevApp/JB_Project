package cls.strategy;

import cls.enums.ClientType;
import cls.facade.ClientFacade;

public interface LoginStrategy {
    ClientFacade getFacadeByLogin(String email, String password, ClientType clientType);
}
