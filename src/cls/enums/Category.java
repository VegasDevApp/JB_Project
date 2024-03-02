package cls.enums;

public enum Category {
    FOOD(1),
    ELECTRICITY(2),
    RESTAURANT(3),
    VACATION(4);

    private final int categoryId;

    public static Category getCategoryNameById(int id){
        var categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            if(i == id-1) return categories[i];
        }
        return categories[0];
    }

    public int getId() {
        return categoryId;
    }
    Category(int categoryId) {
        this.categoryId = categoryId;
    }

}
