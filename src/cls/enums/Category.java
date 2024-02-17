package cls.enums;

public enum Category {
    FOOD,
    ELECTRICITY,
    RESTAURANT,
    VACATION;


    public static Category getCategoryNameById(int id){
        var categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            if(i == id-1) return categories[i];
        }
        return categories[0];
    }

}
