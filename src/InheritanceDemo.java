/*
 * Name:        Lesson 14: Inheritance (InheritanceDemo.java)
 * Description: Uses Enemy and its two child classes, Boss and Spider:
 *              inherited methods, new attributes and methods, overriding,
 *              and a list that holds all three.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-13
 */
import java.util.ArrayList;

// Enemy.java is the parent class. Boss.java and Spider.java extend it.
// Open all three side by side.

public class InheritanceDemo {

    public static void main(String[] args) {

        // creating child objects: each constructor calls super(...) to build the Enemy part
        Enemy goblin = new Enemy("Goblin", 30, 4);
        Boss dragon = new Boss("Dragon", 200, 25, 8);
        Spider webster = new Spider("Webster");

        // inherited methods: Boss and Spider never wrote getName, getHealth, or attack
        System.out.println(dragon.getName() + " has " + dragon.getHealth() + " health and attacks for " + dragon.attack());
        System.out.println(webster.getName() + " has " + webster.getHealth() + " health and attacks for " + webster.attack());

        // new methods: only the child class has them
        System.out.println("Dragon armour: " + dragon.getArmour());
        Spider baby = webster.spawn();
        System.out.println("Webster spawned " + baby.getName());

        // these don't compile: the parent doesn't have the child's methods
        //goblin.getArmour();
        //goblin.spawn();

        // this doesn't compile either: health is private in Enemy, even to a child
        // (inside Boss.java) health = health - amount;
        
        // overriding: the same call does different things
        goblin.takeDamage(10);
        dragon.takeDamage(10);           // armour blocks 8, so only 2 gets through
        System.out.println("Goblin after 10 damage: " + goblin.getHealth());
        System.out.println("Dragon after 10 damage: " + dragon.getHealth());

        // a Boss is an Enemy, so dragon can go in an ArrayList<Enemy>
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(goblin);
        enemies.add(dragon);
        enemies.add(webster);
        enemies.add(baby);

        // every element runs its OWN version of takeDamage
        for (Enemy e : enemies) {
            e.takeDamage(12);
        }
        for (Enemy e : enemies) {
            System.out.println(e.getName() + ": " + e.getHealth() + " health, defeated? " + e.isDefeated());
        }

        // the list's type is Enemy, so only Enemy methods can be called on its elements
        //enemies.get(1).getArmour();    // doesn't compile
    }

}
