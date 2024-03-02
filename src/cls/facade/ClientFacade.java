package cls.facade;

import cls.company.dao.impl.CompanyDaoImpl;
import cls.company.dao.interfaces.CompanyDAO;
import cls.coupon.dao.impl.CouponsDaoImpl;
import cls.customer.dao.impl.CustomersDaoImpl;

public abstract class ClientFacade {
    protected CompanyDAO companyDao = new CompanyDaoImpl();
    protected CustomersDaoImpl customersDao = new CustomersDaoImpl();
    protected CouponsDaoImpl couponsDao = new CouponsDaoImpl();

    protected  abstract boolean  login(String email, String password);

}
