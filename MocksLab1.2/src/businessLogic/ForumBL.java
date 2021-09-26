package businessLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import database.*;
import domain.Article;
import domain.Purchase;
import domain.PurchasedArticle;
import domain.User;

public class ForumBL {
	private static final double VAT = 0.1735;

	private ForumDAOInterface dao;

	public ForumBL(ForumDAOInterface dao) {
		this.dao = dao;
	}

	/**
	 * Add a new non-existing user
	 * 
	 * @param id   the id of the new user
	 * @param name the name of the new user
	 * @param tel  the telephone of the new user (perhaps null)
	 * @return the new user (if not exist)
	 */
	public User addUser(String id, String name, String tel) {
		if (id == null || name == null)
			throw new RuntimeException("id or name is null");
		if (!dao.existUserDAO(id)) {
			dao.addUserDAO(id, name, tel);
			return dao.getUserDAO(id);
		} else
			throw new RuntimeException("id yet in DB");

	}

	public boolean removeUser(String dni) {
		dao.removeUserDAO(dni);
		return false;
	}

	public User getUser(String id) {
		return dao.getUserDAO(id);
	}

	/**
	 * Adds the article to the basket
	 * 
	 * @param u        the user that add the article
	 * @param art      the article to be added
	 * @param quantity the quantity of the article wished
	 */
	public void addBasket(User u, Article art, int quantity) {
		if (quantity <= 0)
			throw new RuntimeException("ERROR, quantity must be greater that 0");
		if (u == null || art == null)
			throw new RuntimeException("ERROR, user or article is null");

		// in other case
		dao.addBasketDAO(u, art, quantity);
	}

	/**
	 * All articles in the basket are purchased
	 * 
	 * @param u the user that buy her/his basquet
	 * @param d the current date of purchase
	 */
	public void buy(User u, Date d) {
		dao.buyDAO(u, d);
	}

	public Iterator<Purchase> getPurchases(User u, Date firstDate, Date lastDate) {
		return dao.getPurchasesDAO(u, firstDate, lastDate);
	}

	public Article addStock(String id, String desc, int precio, boolean isOutlet, int stock) {
		return dao.addStockDAO(id, desc, precio, isOutlet, stock);
	}

	public Article removeStock(String id) {
		return dao.removeStockDAO(id);
	}

	public double getBonus(String id) throws Exception {
		if ((id == null) || (!IdValidator.validate(id)))
			throw new Exception("id null or not valid");

		User u = dao.getUserDAO(id);

		if (u == null)
			throw new Exception("Id not in Database");

		if (u.getTelephone() == null)
			throw new Exception(id + " not registered telephone");

		double sumPurchases = calculatePurchase(u);
		double bonus = 0;
		if (sumPurchases > 30) {
			bonus = sumPurchases * VAT;
			bonus = Math.min(50, bonus);
		}
		return bonus;
	}

	private double calculatePurchase(User u) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date firstDate = sdf.parse("17/09/2019");
		Date lastDate = sdf.parse("06/10/2019");

		Iterator<Purchase> purchases = dao.getPurchasesDAO(u, firstDate, lastDate);
		double sumPurchases = 0;
		while (purchases.hasNext()) {
			Purchase c = purchases.next();
			sumPurchases = sumPurchases + calculateOnePurchase(c);
		}
		return sumPurchases;
	}

	private double calculateOnePurchase(Purchase c) {
		double sumPurchases = 0;
		Iterator<PurchasedArticle> articles = c.getArticles();
		while (articles.hasNext()) {
			PurchasedArticle article = articles.next();
			if (!article.isOutlet())
				sumPurchases = sumPurchases + article.getPrice() * article.getQuantity();
		}
		return sumPurchases;
	}

	public static double getVat() {
		return VAT;
	}

}
