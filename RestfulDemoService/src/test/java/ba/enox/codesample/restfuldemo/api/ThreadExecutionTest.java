package ba.enox.codesample.restfuldemo.api;

import ba.enox.codesample.restfuldemo.api.util.JsonHelper;
import ba.enox.codesample.restfuldemo.model.Transaction;
import ba.enox.codesample.restfuldemo.model.TransactionStatistic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ThreadExecutionTest {

	@Autowired
	private TransactionController controller;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void beforeTests() {
		assertTrue("Controller need to be initialized ", controller != null);
	}

	@Test
	public void getStatisticsMockedDBLayerFullTest() throws Exception {

		// test for threads first part
		doWork().start();
		System.out.println("+++first thread run");
		// test for threads secondpart
		(doWork()).start();
		System.out.println("+++second thread run");
		System.out.println("+++sleep some time");
		Thread.sleep(10000);
		System.out.println("+++after sleep some time");

		TransactionStatistic ts = new TransactionStatistic();
		ts.setAvg(1000.5D);
		ts.setCount(4000L);
		ts.setMax(2000D);
		ts.setMin(1D);
		ts.setSum(2001000 * 2D);

		mockMvc.perform(get("/statistics")
				.contentType(APPLICATION_JSON)).andExpect(status().isOk())
				// .andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("avg", is(ts.getAvg())))
				.andExpect(jsonPath("count", is(Integer.valueOf(ts.getCount() + ""))))// some
																						// issue
																						// with
																						// long
				.andExpect(jsonPath("min", is(ts.getMin()))).andExpect(jsonPath("max", is(ts.getMax())))
				.andExpect(jsonPath("sum", is(ts.getSum())));
	}

	private Thread doWork() {
		return new Thread(() -> {
            // do stuff
            Transaction t = new Transaction();
            for (int i = 1; i <= 2000; i++) {
                t.setAmount((double) i);
                t.setTimestamp((Instant.now().getEpochSecond() + 60 + i) * 1000);

                try {
                    mockMvc.perform(post("/transactions").content(JsonHelper.asJsonString(t)).contentType(APPLICATION_JSON))
                            .andExpect(status().isCreated());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
	}
}
