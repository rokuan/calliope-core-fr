package com.rokuan.calliopecore.fr.test;

import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;

import com.rokuan.calliopecore.sentence.structure.data.count.AllItemsObject;
import com.rokuan.calliopecore.sentence.structure.data.count.CountObject;
import com.rokuan.calliopecore.sentence.structure.data.count.CountObject.Range;
import com.rokuan.calliopecore.sentence.structure.data.count.FixedItemObject;
import com.rokuan.calliopecore.sentence.structure.data.count.LimitedItemsObject;
import com.rokuan.calliopecore.source.ArrayListDataSource;

public class DataSourceTest extends TestCase {
	private ArrayListDataSource<String> dataSource = new ArrayListDataSource<String>();

	@Override
	public void setUp() throws Exception{
		super.setUp();

		dataSource.add("a");
		dataSource.add("b");
		dataSource.add("c");
		dataSource.add("d");
		dataSource.add("e");
		dataSource.add("f");
		dataSource.add("g");
		dataSource.add("h");
		dataSource.add("i");
		dataSource.add("j");
		dataSource.add("k");
		dataSource.add("l");
		dataSource.add("m");
		dataSource.add("n");
		dataSource.add("o");
		dataSource.add("p");
		dataSource.add("q");
		dataSource.add("r");
		dataSource.add("s");
		dataSource.add("t");
		dataSource.add("u");
		dataSource.add("v");
		dataSource.add("w");
		dataSource.add("x");
		dataSource.add("y");
		dataSource.add("z");
	}

	@Test
	public void testSingleElement(){
		// Le 5eme element
		CountObject count = new FixedItemObject(5);
		ArrayListDataSource<String> result = (ArrayListDataSource<String>)dataSource.getData(count);

		assertEquals(1, result.size());
		assertEquals("e", result.get(0));
	}

	@Test
	public void testFirst(){
		// Les 7 premiers elements
		CountObject count = new LimitedItemsObject(Range.FIRST, 7);
		ArrayListDataSource<String> result = (ArrayListDataSource<String>)dataSource.getData(count);

		if(!result.isEmpty()){
			String[] resultsArray = new String[result.size()];

			result.toArray(resultsArray);

			assertTrue(result.size() <= 7);
			assertTrue(Arrays.deepEquals(new String[]{ "a", "b", "c", "d", "e", "f", "g" }, resultsArray));
		}
	}

	@Test
	public void testLast(){
		// Les 3 derniers elements
		CountObject count = new LimitedItemsObject(Range.LAST, 3);
		ArrayListDataSource<String> result = (ArrayListDataSource<String>)dataSource.getData(count);

		if(!result.isEmpty()){
			String[] resultsArray = new String[result.size()];

			result.toArray(resultsArray);

			assertTrue(result.size() <= 3);
			assertTrue(Arrays.deepEquals(new String[]{ "x", "y", "z" }, resultsArray));
		}
	}

	@Test
	public void testAll(){
		CountObject count = new AllItemsObject();
		ArrayListDataSource<String> result = (ArrayListDataSource<String>)dataSource.getData(count);
		
		assertEquals(dataSource.size(), result.size());
	}
	
	@Test
	public void testRemoveAll(){
		CountObject count = new AllItemsObject();
		
		dataSource.removeData(count);		
		assertTrue(dataSource.isEmpty());
	}
	
	@Test
	public void testRemoveFirst(){
		CountObject count = new LimitedItemsObject(Range.FIRST, 3);
		ArrayListDataSource<String> removed = (ArrayListDataSource<String>)dataSource.removeData(count);
		
		assertEquals(23, dataSource.size());
		assertEquals(3, removed.size());
		assertEquals("d", dataSource.get(0));
		
		System.out.println("-- REMOVE FIRST");
		System.out.println("Current:" + dataSource.toString());
		System.out.println("Removed:" + removed.toString());
	}
	
	@Test
	public void testRemoveLast(){
		CountObject count = new LimitedItemsObject(Range.LAST, 5);
		ArrayListDataSource<String> removed = (ArrayListDataSource<String>)dataSource.removeData(count);
		
		assertEquals(21, dataSource.size());
		assertEquals(5, removed.size());
		assertEquals("u", dataSource.get(dataSource.size() - 1));
		
		System.out.println("-- REMOVE LAST");
		System.out.println("Current:" + dataSource.toString());
		System.out.println("Removed:" + removed.toString());
	}
	
	@Test
	public void testRemoveFixed(){
		CountObject count = new FixedItemObject(7);
		ArrayListDataSource<String> removed = (ArrayListDataSource<String>)dataSource.removeData(count);
		
		assertEquals(25, dataSource.size());
		assertEquals(1, removed.size());
		assertEquals("g", removed.get(0));
		
		System.out.println("-- REMOVE LAST");
		System.out.println("Current:" + dataSource.toString());
		System.out.println("Removed:" + removed.toString());
	}
}
