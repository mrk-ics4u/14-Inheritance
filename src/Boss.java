/*
 * Name:        Lesson 14: Inheritance (Boss.java)
 * Description: A Boss is an Enemy with armour. It inherits everything from
 *              Enemy, adds an armour attribute, and overrides takeDamage so
 *              armour blocks part of every hit. Used by InheritanceDemo.java.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-13
 */
public class Boss extends Enemy {

    // new attribute: only a Boss has armour
    private int armour;

    // constructor: super(...) builds the Enemy part first, then set the new attribute
    public Boss(String name, int health, int damage, int armour) {
        super(name, health, damage);
        this.armour = armour;
    }

    // new method: only a Boss has it
    public int getArmour() {
        return armour;
    }

    // override: same header as Enemy's takeDamage, different behaviour
    @Override
    public void takeDamage(int amount) {
        int blocked = amount - armour;
        if (blocked < 0) {
            blocked = 0;
        }
        super.takeDamage(blocked);      // reuse Enemy's version (it stops health at 0)
    }
}
