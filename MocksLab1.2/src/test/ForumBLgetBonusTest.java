package test;

import static org.junit.jupiter.api.Assertions.*;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import java.util.ListIterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import businessLogic.ForumBL;
import database.ForumDAOInterface;
import domain.Article;
import domain.Purchase;
import domain.User;

class ForumBLgetBonusTest {


	String id;
	String name;
	String telephone;
	User u = new User("75075708M","Jon","654478722");
	private ForumDAOInterface forumDAO = Mockito.mock(ForumDAOInterface.class);
	private ForumBL sut = new ForumBL(forumDAO);
	
	@Test
	@DisplayName("Test0: Id es null")
	public void test0() {
		id = null;
		
		assertThrows(Exception.class,
		         ()->sut.getBonus(id));
	}
	
	@Test
	@DisplayName("Test1: id no es DNI ")
	public void test1() {
		id = "75075708K";
		
		assertThrows(Exception.class,
				()->sut.getBonus(id));
	}
	
	@Test
	@DisplayName("Test2: Id tiene caracteres especiales")
	public void test2() {
		id ="$$075708M";
		
		assertThrows(Exception.class,
				()-> sut.getBonus(id));
	}
	
	@Test
	@DisplayName("Test3:id no es socio")
	public void test3() {
		id = "75075708M";
		
		Mockito.doReturn(null).when(forumDAO).
		             getUserDAO(Mockito.anyString());
		
		assertThrows(Exception.class,
				()->sut.getBonus(id));
	}
	
	@Test
	@DisplayName("Test4:id no tiene tel?fono")
	public void test4() {
		
		id = "75075708M";
		name = "Jon";
		telephone = null;
		
		Mockito.doReturn(new User(id,name,telephone)).when(forumDAO).
		                getUserDAO(Mockito.anyString());
		
		assertThrows(Exception.class,
				()-> sut.getBonus(id));
	}

	@Test
	@DisplayName("Test5:no ha comprado art?culos en fechas 17/9 a 6/10")
	public void test5() {
		
		try {
			User u = new User("75075708M","Jon","654478722");
			id = "75075708M";
			Mockito.doReturn(u).when(forumDAO).
			          getUserDAO(Mockito.anyString());

			ListIterator<Purchase> purchase = Collections.emptyListIterator();
			
			Mockito.doReturn(purchase).when(forumDAO).
			           getPurchasesDAO(Mockito.any(User.class), Mockito.any(Date.class), Mockito.any(Date.class));
		double expected = 0.0;
		double obtained = sut.getBonus(id);
		assertEquals(expected,obtained);
		
		}catch(Exception e) {
			fail("If any exception is thrown, it is not working properly");
		}
	}
	
	@Test
	@DisplayName("Test6:ha comprado art?culos outlet en esas fechas")
	public void test6() {
		try {
		User u = new User("75075708M","Jon","654478722");
		id = "75075708M";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = sdf.parse("25/09/19");
		Article art = new Article("1","asics kayano 16 44",99,true,1);
		u.addBasket(art, 1);
		u.buy(fecha);
		
		Date firstDate = sdf.parse("17/09/2019");
		Date lastDate = sdf.parse("06/10/2019");
		Mockito.doReturn(u).when(forumDAO).
		           getUserDAO(Mockito.anyString());
		
		Mockito.doReturn(u.getPurchases(firstDate, lastDate)).
		               when(forumDAO).getPurchasesDAO(Mockito.eq(u),
		            		   Mockito.eq(firstDate), Mockito.eq(lastDate));
		
		double expected =0;
		double obtained = sut.getBonus(id);
		assertEquals(expected,obtained);
		
		
		}catch(Exception e) {
			fail("If any exception is thrown, it is not working properly");
			
		}
	}
	
	@Test
	@DisplayName("Test7;")
	public void test7() {
		User u = new User("75075708M","Jon","654478722");
		id = "75075708M";
		Mockito.doReturn(u).when(forumDAO).getUserDAO(id);
		Article art2 = new Article("2", "asics nimbus 16 44",150, false,5);
		try {
			u.addBasket(art2, 1);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse("25/09/2019");
			u.buy(date);
			
			Date firstDate = sdf.parse("17/09/2019");
			Date lastDate = sdf.parse("06/10/2019");
			
			Mockito.doReturn(u.getPurchases(firstDate, lastDate)).
			             when(forumDAO).getPurchasesDAO(Mockito.eq(u),
			            		 Mockito.eq(firstDate), Mockito.eq(lastDate));
			
			double expected = 150* ForumBL.getVat();
			double obtained = sut.getBonus(id);
			
			assertEquals(expected,obtained);
		}catch(Exception e) {
			fail("If any exception is thrown, it is not working properly");
		}
	}
	
	@Test
	@DisplayName("Test8:")
	public void test8() {
		User u = new User("75075708M","Jon","654478722");
		id = "75075708M";
		Mockito.doReturn(u).when(forumDAO).getUserDAO(id);
		Article art3 = new Article("3", "orbea alma 29 xtr",1500, false,1);
		
		try {
			u.addBasket(art3, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse("25/09/2019");
			u.buy(date);
			
			Date firstDate = sdf.parse("17/09/2019");
			Date lastDate = sdf.parse("06/10/2019");
			
			Mockito.doReturn(u.getPurchases(firstDate, lastDate)).
			             when(forumDAO).getPurchasesDAO(Mockito.eq(u),
			            		 Mockito.eq(firstDate), Mockito.eq(lastDate));
			
			double expected = 50;
			double obtained = sut.getBonus(id);
			
			assertEquals(expected,obtained);
		}catch(Exception e) {
			fail("If any exception is thrown, it is not working properly");
		}
	}
	@Test
	@DisplayName("Test9:")
	public void test9() {
		User u = new User("75075708M","Jon","654478722");
		id = "75075708M";
		Mockito.doReturn(u).when(forumDAO).getUserDAO(id);
		Article art4 = new Article("4", "nike basket socks 39/42",15, false,1);
		
		try {
			u.addBasket(art4, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse("25/09/2019");
			u.buy(date);
			
			Date firstDate = sdf.parse("17/09/2019");
			Date lastDate = sdf.parse("06/10/2019");
			
			Mockito.doReturn(u.getPurchases(firstDate, lastDate)).
			             when(forumDAO).getPurchasesDAO(Mockito.eq(u), 
			            		 Mockito.eq(firstDate), Mockito.eq(lastDate));
			
			double expected = 0;
			double obtained = sut.getBonus(id);
			
			assertEquals(expected,obtained);
		}catch(Exception e) {
			fail("If any exception is thrown, it is not working properly");
		}
	}
	
	
}
