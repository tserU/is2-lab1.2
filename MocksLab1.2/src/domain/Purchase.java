package domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class Purchase {
	private HashMap<String, PurchasedArticle> basket = new HashMap<String, PurchasedArticle>();
	private double cost = 0;
	private Date purchasedDate;

	public Date getPurchaseDate() {
		return purchasedDate;
	}

	public void setPurchaseDate(Date d) {
		this.purchasedDate = d;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Returns an iterator of the articles added to the basket.
	 * 
	 * @return The iterator of the articles included in the basket
	 */
	public Iterator<PurchasedArticle> getPurchaseIterator() {
		return basket.values().iterator();
	}

	/**
	 * Add a new article to the basket.
	 * @param art The article to buy.
	 * @param q The added units
	 * @return The total amount of the articles included in the basket
	 */
	public double addBasket(Article art, int q) {
		if (purchasedDate != null)
			throw new RuntimeException("The purchase is closed. No articles can be added");
		if (art.getStock() < q)
			throw new RuntimeException("There is not enough stock");

		PurchasedArticle purchasedArticle = basket.get(art.getId());
		if (purchasedArticle == null) {
			PurchasedArticle part = new PurchasedArticle(art, q);
			basket.put(art.getId(), part);
		} else
			purchasedArticle.setQuantity(purchasedArticle.getQuantity() + q);
		art.setStock(art.getStock() - q);

		cost = cost + q * art.getPrice();
		return cost;
	}

	public Iterator<PurchasedArticle> getArticles() {
		List<PurchasedArticle> purchasedArticles = new Vector<PurchasedArticle>();
		Iterator<Entry<String, PurchasedArticle>> it = basket.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, PurchasedArticle> pair = (Map.Entry<String, PurchasedArticle>) it.next();
			purchasedArticles.add(pair.getValue());
		}

		return purchasedArticles.iterator();
	}

}
