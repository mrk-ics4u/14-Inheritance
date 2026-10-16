/*
 * Name:        Lesson 14: Inheritance (Enemy.java)
 * Description: The Enemy class from Lesson 11, unchanged. It's the parent
 *              (superclass) of Boss and Slime. Used by InheritanceDemo.java.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-13
 */
public class Enemy {

    // attributes (instance variables): private, so only code in this class can touch them
    private String name;
    private int health;
    private int damage;

    // constructor: same name as the class, no return type, sets every attribute
    public Enemy(String name, int health, int damage) {
        this.name = name;          // this.name is the attribute, name is the parameter
        this.health = health;
        this.damage = damage;
    }

    // getters: return a copy of an attribute's value
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    // setters: replace an attribute's value
    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // no setHealth: health only changes through takeDamage

    // other methods: behaviours that use the attributes
    public int attack() {
        return damage;
    }

    public void takeDamage(int amount) {
        health = health - amount;
        if (health < 0) {
            health = 0;
        }
    }

    public boolean isDefeated() {
        return health == 0;
    }
}
