package creature;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalabashBrotherTest {
	private static CalabashBrother bros;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Start");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("End");
	}

	@BeforeEach
	void setUp() throws Exception {
		bros = new CalabashBrother(false);
	}

	@AfterEach
	void tearDown() throws Exception {
		bros = null;
	}

	@Test
	@DisplayName("bubble sort test")
	void test() {
		bros.bubbleSort();
		ArrayList<CalabashBody> bodies = bros.getOrder();
		assertEquals(7, bodies.size());
		for (int i = 0; i < 7; i++) {
			assertEquals(i, bodies.get(i).getNo());
		}
	}

}
