package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import businessLogic.ForumBL;
import database.ForumDAOInterface;
import domain.Article;
import domain.User;

class ForumBLAddBasketTest {

	private ForumDAOInterface forumDAO = Mockito.mock(ForumDAOInterface.class);
	private ForumBL sut = new ForumBL(forumDAO);
	
	@Test
	public void test1AddBasquet() {
	 try {
	//test params
		 //user
	String idUser=" 75075708M";
	String nameUser="Jon";
	String telephoneUser=null;
	User u=new User(idUser, nameUser, telephoneUser);
	
	     //article
	String idArticle="001";
	String descArticle="MASK PINK";
	double priceArticle=199.99;
	boolean isOutletArticle=false;
	int stock= 1;
	Article art=new Article(idArticle, descArticle,
	priceArticle, isOutletArticle, stock);
	
	//call sut
	int buyUnitsArticle= 1;
	sut.addBasket(u, art, buyUnitsArticle);
	
	//define Argument captors
	 ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
	 ArgumentCaptor<Article> articleCaptor = ArgumentCaptor.forClass(Article.class);
	 ArgumentCaptor<Integer> quantityCaptor = ArgumentCaptor.forClass(Integer.class);
	 
	//verify call numbers and capture parameters
	 Mockito.verify(forumDAO,Mockito.times(1))
	.addBasketDAO(userCaptor.capture(),
	articleCaptor.capture(),quantityCaptor.capture());
	 
	//verify parameter values as usual using JUnit asserts
	 assertEquals(u, userCaptor.getValue());
	 assertEquals(art, articleCaptor.getValue());
	 assertEquals(buyUnitsArticle, quantityCaptor.getValue(), 0.001);
	 
	 System.out.println(userCaptor.getValue().getId());
	 System.out.println(userCaptor.getValue().getName());
	 System.out.println(userCaptor.getValue().getTelephone());
	 } catch (Exception e) {
	fail("If any exception is thrown, it is not working properly");
	 }
	}

}
