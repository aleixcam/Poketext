package shared.move.domain;

import shared.type.domain.Type;
import shared.move.domain.ValueObject.Category;

public final class Move {

    private int id;
    private String name;
    private Type type;
    private Category category;
    private int power;
    private int accuracy;
    private int powerPoints;

    public int id() {
        return id;
    }

    public String name() {
        return this.name;
    }

    public Type type() {
        return type;
    }

    public Category category() {
        return category;
    }

    public int power() {
        return power;
    }

    public int accuracy() {
        return accuracy;
    }

    public int powerPoints() {
        return powerPoints;
    }
}
