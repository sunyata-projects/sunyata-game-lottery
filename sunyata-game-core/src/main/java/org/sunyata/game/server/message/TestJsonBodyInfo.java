package org.sunyata.game.server.message;

/**
 * Created by leo on 17/11/15.
 */
public class TestJsonBodyInfo extends AbstractJsonBodySerializer {
    public String getName() {
        return name;
    }

    public TestJsonBodyInfo setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public TestJsonBodyInfo setAge(int age) {
        this.age = age;
        return this;
    }

    private String name;
    private int age;
}
