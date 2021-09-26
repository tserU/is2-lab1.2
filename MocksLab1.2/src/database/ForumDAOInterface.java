package database;

import java.util.Date;
import java.util.Iterator;

import domain.Article;
import domain.Purchase;
import domain.User;

public interface ForumDAOInterface {

	public boolean existUserDAO(String id);
	
	public void addUserDAO(String id, String name, String tel);

	public User removeUserDAO(String id);

	public User getUserDAO(String id);
	
	public void addBasketDAO(User u, Article art, int quantity);

	public void buyDAO(User u, Date d);

	public Iterator<Purchase> getPurchasesDAO(User u, Date firstDate, Date lastDate);

	public Article addStockDAO(String id, String desc, double price, boolean isOutlet, int stock);

	public Article removeStockDAO(String id);

}