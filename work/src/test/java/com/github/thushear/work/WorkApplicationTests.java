package com.github.thushear.work;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkApplicationTests {




	@Test
	public void contextLoads() throws IOException {
		Connection.Response response = Jsoup.connect("http://localhost/echo").timeout(7).execute();
		System.err.println("res:" + response.body());
	}


	public static void main(String[] args) {
		Integer a = 122;
		System.err.println(a/28);

		System.err.println(Math.ceil(a/28));

		BigDecimal aa = new BigDecimal(a);
		BigDecimal aaa = aa.divide(new BigDecimal(28),BigDecimal.ROUND_CEILING);
		System.err.println(aaa.intValue());

	}

}
