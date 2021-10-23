//ForumInMemoryDAO
package database;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import domain.Article;
import domain.Purchase;
import domain.User;

public class ForumInMemoryDAO implements ForumDAOInterface  {
	HashMap<String, User> dbUsers=new HashMap<String,User>();
	
	HashMap<String, Article> dbArticles=new HashMap<String,Article>();

	@Override
	public User addUserDAO(String id, String name, String tel) {
		User u=new User(id, name, tel);
		dbUsers.put(id, u);
		return u;
	}
	
	@Override
	public User removeUserDAO(String id) {
		User u=dbUsers.remove(id);
		return u;
	}
	
	@Override
	public User getUserDAO(String id) {
		return dbUsers.get(id);
	}

	@Override
	public void addBasketDAO(User u, Article art,int quantity) {
		u.addBasket(art, quantity);
	}

	@Override
	public void buyDAO(User u, Date d) {
		 u.buy(d);
	}
	
	@Override
	public Iterator<Purchase> getPurchasesDAO(User u, Date firstDate, Date lastDate) {
		return u.getPurchases(firstDate, lastDate);
	}

	@Override
	public Article addStockDAO(String id, String desc, double price, boolean isOutlet, int stock) {
		Article art=new Article(id,  desc,  price,  isOutlet, stock);
		dbArticles.put(id, art);
		return art;
	}

	@Override
	public Article removeStockDAO(String id) {
		return dbArticles.remove(id);
		}
	
	@Override
	public boolean existUserDAO(String id) {
		return dbUsers.get(id)!=null;
	}



}
