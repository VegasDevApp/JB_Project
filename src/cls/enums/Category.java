package cls.enums;

public enum Category {
    FOOD(1),
    ELECTRICITY(2),
    RESTAURANT(3),
    VACATION(4);

    private final int categoryId;

    public static Category getCategoryNameById(int id){
        var categories = Category.values();

        if (id >= 1 && id <= categories.length) {
            return categories[id - 1];
        } else {
            return null;
        }
    }

    public int getId() {
        return categoryId;
    }
    Category(int categoryId) {
        this.categoryId = categoryId;
    }

}
