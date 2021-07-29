import static org.junit.Assert.*;

import org.junit.Test;

public class PublicTests {

	@Test
	public void testSimpleAdd() {
		MyHashSet<String> s = new MyHashSet<String>();
		s.add("hello");
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.size());
	}
	
	@Test
	public void testReHash() {
		MyHashSet<String> s = new MyHashSet<String>();
		for (int i = 0; i < 1000; i++) {
			s.add("Entry " + i);
		}
		assertEquals(2048, s.getCapacity());
		assertEquals(1000, s.size());
	}

	@Test
	public void testNoDuplicates() {
		MyHashSet<String> s = new MyHashSet<String>();
		for(int index=0;index<10;index++) {
			s.add("hello");
			s.add("apple");
			s.add("cat");
			s.add("last");
		}
		
		assertEquals(8, s.getCapacity());
		assertEquals(4, s.size());
		
	}
	@Test
	public void testSimpleContains() {
		MyHashSet<String> s = new MyHashSet<String>();
		s.add("hello");
		s.add("apple");
		assertTrue(s.contains("apple"));
	}
	@Test
	public void testRemoves() {
		MyHashSet<String> s = new MyHashSet<String>();
		s.add("hello");
		s.add("apple");
		s.add("dog");
		s.add("mouse");
		s.remove("hello");
		for(String str:s) {
			System.out.print(str);
		}
		
		
		assertEquals(3,s.size());
		assertEquals(8,s.getCapacity());
		assertTrue(s.contains("dog"));
		
		
		
	}
	
}
