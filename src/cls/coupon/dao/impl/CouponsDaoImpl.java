package cls.coupon.dao.impl;

import cls.coupon.beans.Coupon;
import cls.coupon.dao.converter.Converter;
import cls.coupon.dao.interfaces.CouponsDAO;
import cls.db.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class CouponsDaoImpl implements CouponsDAO {


    @Override
    public void addCoupon(Coupon coupon) {
        String sql = "INSERT INTO `COUPONS` " +
                "(`COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Map<Integer, Object> params = populateParamsFull(coupon);

        if (DBManager.runQuery(sql, params)) {
            System.out.println("Coupon is Added: \n" + coupon);
        }

    }

    @Override
    public boolean updateCoupon(Coupon coupon) {
        boolean result = false;
        Coupon target = getOneCoupon(coupon.getId());
        if (target != null) {
            //COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) " +
            String sql = "UPDATE `COUPONS` " +
                    "SET " +
                    "`COMPANY_ID` = ?," +
                    "`CATEGORY_ID` = ?," +
                    "`TITLE` = ?," +
                    "`DESCRIPTION` = ?," +
                    "`START_DATE` = ?," +
                    "`END_DATE` = ?," +
                    "`AMOUNT` = ?," +
                    "`PRICE` = ?," +
                    "`IMAGE` = ?" +
                    "WHERE (`ID` = ?);";

            Map<Integer, Object> params = populateParamsFull(coupon);

            // Add ID for WHERE
            params.put(params.size() + 1, target.getId());
            result = DBManager.runQuery(sql, params);
            if (result) {
                System.out.println("Coupon is updated: \n" + coupon);
            }
        }
        return result;
    }

    @Override
    public void deleteCoupon(int couponID) {
        Coupon coupon = getOneCoupon(couponID);
        if(nonNull(coupon)){
            detachCouponFromAllCustomers(couponID);
            String sql = "DELETE FROM COUPONS WHERE ID=?;";
            Map<Integer, Object> params = new HashMap<>();
            params.put(1, couponID);
            DBManager.runQuery(sql, params);
        }
    }

    @Override
    public boolean deleteCouponsByCompanyId(int companyId) {

            String sql = "DELETE FROM COUPONS WHERE COMPANY_ID = ?;";
            Map<Integer, Object> params = new HashMap<>();
            params.put(1, companyId);
            return DBManager.runQuery(sql, params);
    }

    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> result = new ArrayList<>();

        String sql = "SELECT * FROM COUPONS";

        ResultSet dbResult = DBManager.runQueryForResult(sql);
        if (nonNull(dbResult)) {
            result = Converter.populate(dbResult);
        }
        return result;
    }

    @Override
    public Coupon getOneCoupon(int couponID) {
        Coupon result = null;

        String sql = "SELECT * FROM COUPONS WHERE ID = ?";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);

        ResultSet dbResult = DBManager.runQueryForResult(sql, params);
        if (nonNull(dbResult)) {
            ArrayList<Coupon> coupons = Converter.populate(dbResult);
            if (!coupons.isEmpty()) result = coupons.get(0);
        }
        return result;
    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) {
        if (!isCustomerHasCoupon(customerID, couponID)) {
            Coupon coupon = getOneCoupon(couponID);
            if (nonNull(coupon)
                    && coupon.getAmount() > 0
                    && (LocalDate.now().compareTo(coupon.getEndDate()) <= 0)
                    && (LocalDate.now().compareTo(coupon.getStartDate()) >= 0)) {

                coupon.setAmount(coupon.getAmount() - 1);
                if (updateCoupon(coupon)) {
                    attachCouponToCustomer(customerID, couponID);
                }
            }
        }
    }

    @Override
    public void deleteCouponPurchasesByCompanyId(int companyId){
        String sql = "DELETE FROM COSTUMERS_VS_COUPONS " +
                "WHERE COUPON_ID IN (" +
                "SELECT ID " +
                "FROM Coupon " +
                "WHERE COMPANY_ID = ?" +
                ");";

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);

        DBManager.runQuery(sql, params);
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        if (isCustomerHasCoupon(customerID, couponID)) {
            Coupon coupon = getOneCoupon(couponID);
            // Check of coupon exists and not expires
            if (nonNull(coupon)
                    && (LocalDate.now().compareTo(coupon.getEndDate()) <= 0)) {

                coupon.setAmount(coupon.getAmount() + 1);
                if (updateCoupon(coupon)) {
                    detachCouponFromCustomer(customerID, couponID);
                }
            }
        }
    }

    /**
     * Before deleting a customer, returns all his or her
     * coupons back.
     * YOU MUST-CLEAR PURCHASE HISTORY AS WELL!!!
     * @param customerID
     */
    private void increaseCouponAmountByCustomerId(int customerID){
        String sql = "UPDATE COUPONS " +
                "SET AMOUNT = AMOUNT + 1 " +
                "WHERE ID IN (" +
                    "SELECT COUPON_ID " +
                    "FROM COSTUMERS_VS_COUPONS " +
                    "WHERE CUSTOMER_ID = ?" +
                ");";

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        DBManager.runQuery(sql, params);
    }

    private void deleteCouponPurchasesByCustomerId(int customer){
        String sql = "DELETE FROM COSTUMERS_VS_COUPONS " +
                "WHERE CUSTOMER_ID = ?;";

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer);

        DBManager.runQuery(sql, params);
    }

    /**
     * Will delete all coupons from history
     * and increase coupon amount by one, so
     * the other customer can buy it if coupon still valid
     * @param customerId
     */
    @Override
    public void detachAllCouponFromCustomer(int customerId){

        // We are performing this LOGIC in DAO
        // To ensure that both these operations
        // are executed and prevent anomalies as
        // mach as is possible.
        // Actually, it should be done in TRANSACTION
        // which we didn't learn yet

        increaseCouponAmountByCustomerId(customerId);
        deleteCouponPurchasesByCustomerId(customerId);
    }

    private boolean isCustomerHasCoupon(int customerID, int couponID) {
        String sql = "SELECT count(*) AS COUNT FROM COSTUMERS_VS_COUPONS " +
                "WHERE CUSTOMER_ID = ? AND COUPON_ID = ?;";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        ResultSet dbResult = DBManager.runQueryForResult(sql, params);
        try {
            return (
                    nonNull(dbResult)
                            && dbResult.next()
                            && dbResult.getInt("COUNT") > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean attachCouponToCustomer(int customerID, int couponID) {
        String sql = "INSERT INTO COSTUMERS_VS_COUPONS (CUSTOMER_ID, COUPON_ID) " +
                "VALUES (?, ?)";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        return DBManager.runQuery(sql, params);
    }

    private boolean detachCouponFromCustomer(int customerID, int couponID) {
        String sql = "DELETE FROM COSTUMERS_VS_COUPONS " +
                "WHERE CUSTOMER_ID=? AND COUPON_ID=?;";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        return DBManager.runQuery(sql, params);
    }

    private boolean detachCouponFromAllCustomers(int couponID) {
        String sql = "DELETE FROM COSTUMERS_VS_COUPONS " +
                "WHERE COUPON_ID=?;";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);
        return DBManager.runQuery(sql, params);
    }

    /**
     * Take coupon and returns a hash map with index=value for sql prepared statement
     *
     * @param coupon instance of Coupon
     * @return hashmap for sql prepared statement
     */
    private Map<Integer, Object> populateParamsFull(Coupon coupon) {

        Map<Integer, Object> params = new HashMap<>();

        // COMPANY_ID
        params.put(1, coupon.getCompanyID());
        // CATEGORY_ID
        params.put(2, coupon.getCategoryId());
        // TITLE
        params.put(3, coupon.getTitle());
        // DESCRIPTION
        params.put(4, coupon.getDescription());
        // START_DATE
        params.put(5, coupon.getStartDate());
        // END_DATE
        params.put(6, coupon.getEndDate());
        // AMOUNT
        params.put(7, coupon.getAmount());
        // PRICE
        params.put(8, coupon.getPrice());
        // IMAGE
        params.put(9, coupon.getImage());

        return params;
    }
}
