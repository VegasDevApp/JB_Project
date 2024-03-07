package cls.strategy.impl;

import cls.enums.ClientType;
import cls.facade.AdminFacade;
import cls.facade.ClientFacade;
import cls.facade.CompanyFacade;
import cls.facade.CustomerFacade;
import cls.strategy.LoginStrategy;

public class DefaultLoginStrategy implements LoginStrategy {
    @Override
    public ClientFacade getFacadeByLogin(String email, String password, ClientType clientType) {
        ClientFacade result = switch (clientType) {
            case Company -> new CompanyFacade();
            case Customer -> new CustomerFacade();
            case Administrator -> new AdminFacade();
        };
        return result.login(email, password) ? result : null;
    }
}
