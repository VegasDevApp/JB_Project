package cls.coupon.dao.converter;

import cls.coupon.beans.Coupon;
import cls.enums.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Converter {
    public static ArrayList<Coupon> populate(ResultSet resultSet) {
        ArrayList<Coupon> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Coupon coupon = new Coupon();

                Category category = Category.getCategoryNameById(resultSet.getInt("CATEGORY_ID"));

                coupon.setId(resultSet.getInt("ID"));
                coupon.setCompanyID(resultSet.getInt("COMPANY_ID"));
                coupon.setCategory(category);
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setDescription(resultSet.getString("DESCRIPTION"));
                coupon.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
                coupon.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                result.add(coupon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
