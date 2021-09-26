package domain;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class User {
	private String id;
	private String name;
	private String telephone;
	private List<Purchase> purchases = new Vector<Purchase>();;
	private Purchase basket;

	public User(String id, String name, String telefono) {
		this.id = id;
		this.name = name;
		this.telephone = telefono;
		purchases = new Vector<Purchase>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Iterator<Purchase> getPurchases(Date firstDate, Date lastDate) {
		List<Purchase> cs = new Vector<Purchase>();
		System.out.println("#of purchases " + purchases.size());
		for (Purchase c : purchases) {
			if ((c.getPurchaseDate().compareTo(firstDate) >= 0) && (c.getPurchaseDate().compareTo(lastDate) <= 0))
				cs.add(c);
		}
		return cs.iterator();
	}

	public void buy(Date d) {
		basket.setPurchaseDate(d);
		purchases.add(basket);
		basket = null;
	}

	public void addBasket(Article a, int cantidad) {
		if (basket == null)
			basket = new Purchase();
		basket.addBasket(a, cantidad);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		} else {
			User u = (User) obj;
			if (telephone == null) {
				if (u.getTelephone() == null) {
					return id.equals(u.getId()) && name.equals(u.getName());
				} else {
					return false;
				}
			} else {
				return id.equals(u.getId()) && name.equals(u.getName()) && telephone.equals(u.getTelephone());
			}
		}
	}
}
