/*
 * Name:        Lesson 14: Inheritance (Slime.java)
 * Description: A Slime is a weak Enemy. Every slime starts with the same
 *              health and damage, so its constructor only needs a name. It
 *              adds a split method. Used by InheritanceDemo.java.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-13
 */
public class Slime extends Enemy {

    // no new attributes: everything a Slime needs is in Enemy

    // constructor: the child decides the values it passes up to Enemy
    public Slime(String name) {
        super(name, 10, 1);
    }

    // new method: returns a brand new, smaller slime
    public Slime split() {
        return new Slime(getName() + " Jr");     // getName() is inherited from Enemy
    }

    // no overrides: a Slime attacks and takes damage exactly like an Enemy
}
