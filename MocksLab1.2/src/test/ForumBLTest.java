package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import businessLogic.ForumBL;
import database.ForumDAOInterface;
import database.ForumInMemoryDAO;
import domain.Article;
import domain.User;

class ForumBLTest {


		ForumDAOInterface forumDAO = new ForumInMemoryDAO();
		ForumBL sut = new ForumBL(forumDAO);
		
		String id;
		String name;
		String telephone;
		
		@Test
		@DisplayName("Test4:id no tiene tel?fono")
		public void test4() {
			
			id = "75075708M";
			name = "Jon";
			telephone = null;
			
			
			assertThrows(Exception.class,
					()-> sut.getBonus(id));
		}
		
		
		@Test
		public void test7() {
		forumDAO.addUserDAO("75075708M", "Jon", "654478722");
		User u = forumDAO.getUserDAO("75075708M");
		Article art2 = forumDAO.addStockDAO("2", "asics nimbus 16 44",150, 
		false,500);
		try {
		sut.addBasket(u, art2, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Date date = sdf.parse("25/09/2019"); //java.util
		sut.buy(u, date);
		double expectedValue = 150 * 1 * ForumBL.getVat(); //26,025?
		try {
		double bonus = sut.getBonus("75075708M");
		assertTrue(bonus == expectedValue);
		} catch (Exception e) {
		fail("getBonus: It is not working properly");
		} finally {
		sut.removeUser("75075708M");
		sut.removeStock("2");
		}
		} catch (ParseException e1) {
		e1.printStackTrace();
		fail("SimpleDateFormat fail");
		}
		}
		
		@Test
		@DisplayName("Test9:")
		public void test9() {
			id = "75075708M";
			forumDAO.addUserDAO("75075708M", "Jon", "654478722");
			User u = forumDAO.getUserDAO("75075708M");
			Article art4 = forumDAO.addStockDAO("4", "nike basket socks 39/42",
					15, false,1);
			
			try {
			sut.addBasket(u, art4, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			Date date = sdf.parse("25/09/2019"); //java.util
			sut.buy(u, date);
			double expectedValue = 0;
			try {
			double bonus = sut.getBonus(id);
			assertTrue(bonus == expectedValue);
			} catch (Exception e) {
			fail("getBonus: It is not working properly");
			} finally {
			sut.removeUser("75075708M");
			sut.removeStock("4");
			}
			} catch (ParseException e1) {
			e1.printStackTrace();
			fail("SimpleDateFormat fail");
			}
			
			
		}
}
