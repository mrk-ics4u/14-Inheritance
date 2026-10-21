/*
 * Name:        Lesson 14: Inheritance (Spider.java)
 * Description: A Spider is a weak Enemy. Every spider starts with the same
 *              health and damage, so its constructor only needs a name. It
 *              adds a spawn method. Used by InheritanceDemo.java.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-13
 */
public class Spider extends Enemy {

    // no new attributes: everything a Spider needs is in Enemy

    // constructor: the child decides the values it passes up to Enemy
    public Spider(String name) {
        super(name, 10, 1);
    }

    // new method: returns a brand new, smaller spider
    public Spider spawn() {
        return new Spider(getName() + " Jr");     // getName() is inherited from Enemy
    }

    // no overrides: a Spider attacks and takes damage exactly like an Enemy
}
