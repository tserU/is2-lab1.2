package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import businessLogic.ForumBL;
import database.ForumDAOInterface;
import domain.User;

class ForumBLAddUserTest {

	private ForumDAOInterface forumDAO = Mockito.mock(ForumDAOInterface.class);
	private ForumBL sut = new ForumBL(forumDAO);
	
	
	@Test
	@DisplayName("Test 1: No params null & Not User In DB ")
	public void test1NotUserInDB() {
	 try {
	 //test params
	 String id=" 75075708M";
	 String name="Jon";
	 String telephone=null;
	 
	 //configure mock
	 Mockito.doReturn(false).when(forumDAO).
	existUserDAO(Mockito.anyString());
	 Mockito.doReturn(new User(id, name, telephone)).when(forumDAO)
	.getUserDAO(Mockito.anyString());
	 
	 //call sut
	 User usr=sut.addUser(id, name, telephone);
	
	//define Argument captors
	 ArgumentCaptor<String> idCaptor =
	ArgumentCaptor.forClass(String.class);
	 ArgumentCaptor<String> nameCaptor =
	ArgumentCaptor.forClass(String.class);
	 ArgumentCaptor<String> telephoneCaptor =
	ArgumentCaptor.forClass(String.class);
	 
	//verify call numbers and capture parameters
	 Mockito.verify(forumDAO,Mockito.times(1))
	.addUserDAO(idCaptor.capture(),
	nameCaptor.capture(),telephoneCaptor.capture());
	 
	//verify parameter values as usual using JUnit asserts
	 assertEquals(id, idCaptor.getValue());
	 assertEquals(name, nameCaptor.getValue());
	 assertEquals(telephone, telephoneCaptor.getValue());
	 
	 
	 //test results or define equal in User and …
	 assertTrue (usr.getId().equals(id));
	 assertEquals(0, usr.getName().compareTo(name));
	 assertNull(usr.getTelephone());
	 
	 } catch (Exception e) {
	fail("If any exception is thrown, it is not working properly");
	 }
	}
	
	@Test
	@DisplayName("Test 2: ID es null ")
	public void test2IDnull() {
	 
	 //test params
	 String id=null;
	 String name="Jon";
	 String telephone=null;
	 
	
	 //call sut
	 assertThrows(RuntimeException.class,
			  ()->  sut.addUser(id, name, telephone));
	 
	}
	
	@Test
	@DisplayName("Test 3: name es null ")
	public void test3NameNull() {
	 
	 //test params
	 String id = "75075708M";
	 String name=null;
	 String telephone="600666666";
	 
	
	 //call sut
	 assertThrows(RuntimeException.class,
			  ()->  sut.addUser(id, name, telephone));
	 
	}
	
	@Test
	@DisplayName("Test 4: User In DB ")
	public void test4UserInDB() {
	 
	 //test params
	 String id = "75075708M";
	 String name="Jon";
	 String telephone="600666666";
	 
	 Mockito.doReturn(true).when(forumDAO).
	          existUserDAO(Mockito.anyString());
	 //call sut
	 assertThrows(RuntimeException.class,
			  ()->  sut.addUser(id, name, telephone));
	 
	}
}
