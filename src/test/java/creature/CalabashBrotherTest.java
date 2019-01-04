package creature;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalabashBrotherTest {
	private static CalabashBrother bros;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Start");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("End");
	}

	@Before
	public void setUp() throws Exception {
		bros = new CalabashBrother(false);
	}

	@After
	public void tearDown() throws Exception {
		bros = null;
	}

	@Test
	public void test() {
		bros.bubbleSort();
		ArrayList<CalabashBody> bodies = bros.getOrder();
		assertEquals(7, bodies.size());
		for (int i = 0; i < 7; i++) {
			assertEquals(i, bodies.get(i).getNo());
		}
	}

}
