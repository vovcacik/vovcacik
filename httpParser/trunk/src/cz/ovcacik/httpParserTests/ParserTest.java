package cz.ovcacik.httpParserTests;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.ovcacik.httpParser.Parser;

public class ParserTest {
	private Parser parser = null;
	private InputStream input = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		parser = new Parser(input);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testParser() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetNextMessage() {
		fail("Not yet implemented"); // TODO
	}

}
