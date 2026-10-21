# Lesson 14 - Inheritance

A game has more than one kind of enemy. A `Boss` has everything an `Enemy` has (name, health, damage, `attack`, `takeDamage`) plus armour. Copying all of `Enemy` into a new `Boss` class would mean every bug fix has to happen twice.

**Inheritance** lets a new class start with everything another class already has, and only write what's different.

---

## 1. Is-a: When to Use Inheritance

Inheritance fits when the sentence "a ___ **is an** ___" is true:

- A `Boss` is an `Enemy`. A `Spider` is an `Enemy`. Inheritance.
- A `Game` is an `Enemy`? No. A game **has** enemies, so it gets an `ArrayList<Enemy>` attribute instead.

The class being inherited from is the **parent** (or **superclass**). The new class is the **child** (or **subclass**).

---

## 2. extends and UML

```java
public class Boss extends Enemy {
    ...
}
```

`extends Enemy` gives `Boss` every method `Enemy` has. In UML, the child points to the parent with a hollow triangle arrow. The child's box only lists what's **new or changed**:

```
┌─────────────────────────────────────┐
│ Enemy                               │
├─────────────────────────────────────┤
│ - name : String                     │
│ - health : int                      │
│ - damage : int                      │
├─────────────────────────────────────┤
│ + Enemy(name : String,              │
│         health : int,               │
│         damage : int)               │
│ + getName() : String                │
│ + getHealth() : int                 │
│ + getDamage() : int                 │
│ + setName(name : String) : void     │
│ + setDamage(damage : int) : void    │
│ + attack() : int                    │
│ + takeDamage(amount : int) : void   │
│ + isDefeated() : boolean            │
└─────────────────────────────────────┘
          △                    △
          │                    │
┌─────────┴─────────────┐  ┌───┴───────────────────┐
│ Boss                  │  │ Spider                │
├───────────────────────┤  ├───────────────────────┤
│ - armour : int        │  │                       │
├───────────────────────┤  ├───────────────────────┤
│ + Boss(name : String, │  │ + Spider(name :       │
│     health : int,     │  │     String)           │
│     damage : int,     │  │ + spawn() : Spider    │
│     armour : int)     │  │                       │
│ + getArmour() : int   │  │                       │
│ + takeDamage(amount : │  │                       │
│     int) : void       │  │                       │
└───────────────────────┘  └───────────────────────┘
```

`takeDamage` appears in both `Enemy` and `Boss`. That means `Boss` **overrides** it (section 6).

---

## 3. What the Child Gets

A `Boss` can call every public method of `Enemy`, even though `Boss.java` never writes them:

```java
Boss dragon = new Boss("Dragon", 200, 25, 8);
System.out.println(dragon.getName());    // Dragon
System.out.println(dragon.attack());     // 25
```

A child object **has** the parent's private attributes (every `Boss` has a health), but the child's code **can't use them by name**. `private` means only code inside `Enemy.java`:

```java
// inside Boss.java
health = health - amount;     // error: health has private access in Enemy
```

The child works with the parent's attributes the same way any other class does: through the parent's public methods (`getHealth()`, `takeDamage(...)`).

It doesn't work the other way. An `Enemy` doesn't get the child's methods: `goblin.getArmour()` doesn't compile.

---

## 4. The Constructor and super(...)

Because the child can't set `name`, `health`, and `damage` itself, it asks the parent's constructor to do it. `super(...)` calls the parent's constructor:

```java
public Boss(String name, int health, int damage, int armour) {
    super(name, health, damage);     // build the Enemy part
    this.armour = armour;            // then the Boss part
}
```

- `super(...)` must be the **first line** of the constructor.
- Its arguments must match one of the parent's constructors.

The child decides what to pass up. Every `Spider` starts with 10 health and 1 damage, so its constructor only needs a name:

```java
public Spider(String name) {
    super(name, 10, 1);
}
```

If you leave `super(...)` out, Java tries `super()` with no arguments. `Enemy` has no constructor like that, so you get: `constructor Enemy in class Enemy cannot be applied to given types`.

---

## 5. Adding Attributes and Methods

Anything declared in the child is new, and only the child has it:

```java
private int armour;

public int getArmour() {
    return armour;
}
```

```java
public Spider spawn() {
    return new Spider(getName() + " Jr");     // getName() is inherited, so call it directly
}
```

---

## 6. Overriding

A child **overrides** a parent method by writing a method with the **same header**: same name, same parameter types, same return type. Calls on a `Boss` now run the `Boss` version.

```java
@Override
public void takeDamage(int amount) {
    int blocked = amount - armour;
    if (blocked < 0) {
        blocked = 0;
    }
    super.takeDamage(blocked);
}
```

**`super.takeDamage(blocked)`** runs the **parent's** version. `Boss` reuses the work `Enemy` already does (subtract, stop at 0) instead of copying it. Without `super.`, `takeDamage(blocked)` would call the `Boss` version again, forever.

**`@Override`** goes on the line above every override. It tells the compiler "this is meant to replace a parent method." If you misspell it (`takedamage`) or change a parameter type, it's a compile error instead of a silent new method that never gets called.

Overriding is not overloading (Lesson 12). Overloading is a different parameter list in the same class. Overriding is the **same** header in a child class.

The `toString` you wrote in Lesson 12 was already an override. Every class extends Java's `Object` class automatically, and `Object` has the `toString` that prints `BankAccount@1b6d3586`.

---

## 7. A List of Mixed Enemies

A `Boss` is an `Enemy`, so it can go anywhere an `Enemy` is expected, including an `ArrayList<Enemy>`:

```java
ArrayList<Enemy> enemies = new ArrayList<Enemy>();
enemies.add(new Enemy("Goblin", 30, 4));
enemies.add(new Boss("Dragon", 200, 25, 8));
enemies.add(new Spider("Webster"));

for (Enemy e : enemies) {
    e.takeDamage(12);
}
```

Each object runs **its own** `takeDamage`. The goblin loses 12, the dragon loses 4, the spider is defeated. One loop handles every kind of enemy, and a new kind of enemy doesn't change the loop at all.

The list's type is `Enemy`, so only `Enemy` methods can be called on its elements. `enemies.get(1).getArmour()` doesn't compile, even though element 1 is a `Boss`.

---

## 8. Common Inheritance Bugs

**Using a parent's private attribute in the child.** "has private access." Use the parent's getters, or `super(...)` in the constructor.

**No `super(...)` in the child constructor**, or not as the first line. "constructor ... cannot be applied to given types" or "call to super must be first statement."

**Writing the override's header differently** (`takeDamage(double amount)`). It's a new overloaded method, not an override, and the parent's version still runs. `@Override` turns this into a compile error.

**`toString()` instead of `super.toString()` inside `toString`.** The method calls itself until the program crashes with `StackOverflowError`.

**Inheriting where "is a" isn't true.** `Player extends Weapon` compiles, but a player isn't a weapon. Make it an attribute instead.

---

## Try It Yourself

Run the companion file from the `src` folder:

```bash
javac InheritanceDemo.java
java InheritanceDemo
```

`InheritanceDemo` uses `Enemy.java` and its two children, `Boss.java` and `Spider.java`. Open all three side by side.

Then work on the exercise in `ChequingAccount.java`. It extends the `BankAccount` class from Lesson 12.

---

## Course Expectations

### ICS 4U

"I can write a child class that extends a parent class, calls the parent's constructor with `super(...)`, adds its own attributes and methods, and overrides a parent method" is a requirement of the course (C1.4) and will be expected in your programming project.

### AP Expectations

Inheritance was removed from the AP Computer Science A exam in 2025–26. Nothing new in this lesson is on the AP exam.
