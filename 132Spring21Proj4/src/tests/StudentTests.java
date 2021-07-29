package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import listClass.BasicLinkedList;

public class StudentTests {

	/* Write a lot of tests! */

	@Test
	public void test3() {

		BasicLinkedList<String> blist = new BasicLinkedList<String>();

		blist.addToFront("Zebra").addToFront("Bear").addToFront("Dove");
		String answer = "";
		for (String entry : blist) {
			answer += entry;
		}
		assertEquals(answer, "DoveBearZebra");
		System.out.println(answer);
	}

	@Test
	public void test4() {
		BasicLinkedList<String> blist = new BasicLinkedList<String>();

		blist.addToFront("Zebra").addToFront("Bear").addToFront("Dove");
		String answer = "";
		for (String entry : blist) {
			answer += entry;
		}
		blist.getFirst();
		assertEquals(blist.getFirst(),"Dove");
	}
	@Test
	public void test5() {
		BasicLinkedList<String> blist = new BasicLinkedList<String>();

		blist.addToFront("Zebra").addToFront("Bear").addToFront("Dove");
		String answer = "";
		for (String entry : blist) {
			answer += entry;
		}
		blist.getFirst();
		assertEquals(blist.getLast(),"Zebra");
	}
	@Test
	public void test6() {
		BasicLinkedList<String> blist = new BasicLinkedList<String>();

		blist.addToFront("Zebra").addToFront("Bear").addToFront("Dove");
		String answer = "";
		for (String entry : blist) {
			answer += entry;
		}
		
		assertEquals(blist.getFirst(),"Dove");
	}
	@Test
	public void test7() {
		BasicLinkedList<String> blist = new BasicLinkedList<String>();

		blist.addToFront("Zebra").addToFront("Bear").addToFront("Dove");
		String answer = "";
		for (String entry : blist) {
			answer += entry;
		
		}
	
		assertEquals(blist.retrieveFirstElement(),"Dove");
		System.out.println();
	}

}
