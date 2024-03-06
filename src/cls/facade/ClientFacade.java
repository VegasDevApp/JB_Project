package cls.facade;

import cls.company.dao.impl.CompanyDaoImpl;
import cls.company.dao.interfaces.CompanyDAO;
import cls.coupon.dao.impl.CouponsDaoImpl;
import cls.coupon.dao.interfaces.CouponsDAO;
import cls.customer.dao.impl.CustomersDaoImpl;
import cls.customer.dao.interfaces.CustomersDAO;

public abstract class ClientFacade {
    protected CompanyDAO companyDao = new CompanyDaoImpl();
    protected CustomersDAO customersDao = new CustomersDaoImpl();
    protected CouponsDAO couponsDao = new CouponsDaoImpl();

    protected  abstract boolean  login(String email, String password);

}
