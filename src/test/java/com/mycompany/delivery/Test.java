package com.mycompany.delivery;

import com.mycompany.delivery.model.entity.Status;

public class Test {
    @org.junit.Test
    public void test() {
        System.out.println(Status.valueOf("PAID").toString());
    }
}
