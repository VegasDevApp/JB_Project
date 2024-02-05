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
    public void updateCoupon(Coupon coupon) {
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
            if (DBManager.runQuery(sql, params)) {
                System.out.println("Coupon is updated: \n" + coupon);
            }
        }
    }

    @Override
    public void deleteCoupon(int couponID) {

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
        if(!isCustomerHasCoupon(customerID, couponID)){
            Coupon coupon = getOneCoupon(couponID);
            if(nonNull(coupon)
                    && coupon.getAmount() > 0
                    && !LocalDate.now().isAfter(coupon.getEndDate())
                    && LocalDate.now().isAfter(coupon.getStartDate())){
                coupon.setAmount(coupon.getAmount()-1);
                updateCoupon(coupon);
                attachCouponToCustomer(customerID,couponID);
            }
        }
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {

    }

    private boolean isCustomerHasCoupon(int customerID, int couponID) {
        String sql = "SELECT count(*) AS COUNT FROM COUPONS " +
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

    private boolean attachCouponToCustomer(int customerID, int couponID){
        String sql = "INSERT INTO COUPONS (CUSTOMER_ID, COUPON_ID) " +
                "VALUES (?, ?)";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        return DBManager.runQuery(sql, params);
    }

    /**
     * Take coupon and returns hash map with index=value for sql prepared statement
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
