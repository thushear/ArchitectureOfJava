package com.github.thushear.springboot;

import com.github.thushear.springboot.async.AsyncTask;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class SpringbootApplicationTests {

	static MockDao mockDao = null;


	@Autowired
	AsyncTask asyncTask;

	@BeforeClass
	public static void setUp() throws Exception {
		mockDao = Mockito.mock(MockDao.class);
		Mockito.when(mockDao.getName()).thenReturn("mock");
	}


	@Test
	public void testMono(){

		Mono<String> mono = WebClient.create("http://localhost:8080/hellomono").get().retrieve().bodyToMono(String.class);

		System.err.println("mono:" + mono.toString());

	}


	@Test
	public void contextLoads() {
		System.out.println(mockDao.getName());
	}



	@Test
	public void asyncTask() throws InterruptedException {
		long start = System.currentTimeMillis();
		Future<String> future1 = asyncTask.doTaskOne();
		Future<String> future2 = asyncTask.doTaskTwo();
		Future<String> future3 = asyncTask.doTaskThree();

		while (true){
			if (future1.isDone() && future2.isDone() && future3.isDone()){
				System.err.println("asyncTask cost :" + (System.currentTimeMillis() - start) + " ms" );
				break;
			}
			Thread.sleep(500);
		}

	}


}

interface MockDao{

	String getName();

}
