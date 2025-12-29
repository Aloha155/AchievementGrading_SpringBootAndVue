package com.bank.performance;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankPerformanceApplicationTests {

    @Test
    void contextLoads() {
        int i = 0;
        int j = 0;
        while (i++ < ++j) {
            ++i;
            j--;
        }
        System.out.println("i=" + (i++) + ";j=" + (++j));
    }


}
