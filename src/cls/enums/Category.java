package cls.enums;

public enum Category {
    FOOD(1),
    ELECTRICITY(2),
    RESTAURANT(3),
    VACATION(4);

    private int id;
    Category(int id){
        this.id = id;
    }
}
