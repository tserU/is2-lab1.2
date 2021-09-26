package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import businessLogic.ForumBL;
import database.ForumDAOInterface;

class ForumPromoMockitoTest {

	private ForumDAOInterface forumDAO= Mockito.mock(ForumDAOInterface.class);
	
	//sut means System Under Test
	private ForumBL sut = new ForumBL(forumDAO);
	
	@Test
	@DisplayName("Test 0: Prueba")
	public void test0() {
		assertNull (forumDAO.getUserDAO("75075708M"));
	}
			             

}
