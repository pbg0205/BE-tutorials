package com.cooper.springtest.dirtiescontext;

import com.cooper.springtest.SpringTestApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.annotation.DirtiesContext.MethodMode;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringTestApplication.class)
class DirtiesContextIntegrationTest01 {

    @Autowired
    protected UserCache userCache;

    @Test
    @Order(1)
    void addJaneDoeAndPrintCache() {
        userCache.addUser("Jane Doe");
        userCache.printUserList("addJaneDoeAndPrintCache");
    }

    @Test
    @Order(2)
    void printCache() {
        userCache.printUserList("printCache"); // printCache: [Jane Doe]
    }

    @Test
    @Order(3)
    void addJohnDoeAndPrintCache() {
        userCache.addUser("John Doe");
        userCache.printUserList("addJohnDoeAndPrintCache"); // addJohnDoeAndPrintCache: [John Doe, Jane Doe]
    }

    @Test
    @Order(4)
    void printCacheAgain() {
        userCache.printUserList("printCacheAgain"); // printCacheAgain: []
    }

}
