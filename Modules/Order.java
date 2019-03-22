package Modules;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import User.User;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Orders class - containing order information and customer preferences for a Product
 * @author Mia Skinner
 */
public class Order {
	
	
	private static int orderIDGen; //TODO should I make this custID + timestamp + unique #? -- should we collect custID?
	private int orderID;
	private String customerName; 
	private Timestamp orderDate;
	private Timestamp shipDate;
	private ArrayList<Product> product;
	private ArrayList<Integer> quantity;
	private String shipmentType;
	private boolean isShipped;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	
	private int shipSpeedHelper() {
		int OVERNIGHT = 10;
		int RUSH = 5;
		int STANDARD = 1;
		
		int shipSpeed;
		if (this.shipmentType ==  "Overnight Shipping") {
			shipSpeed = OVERNIGHT;
		}
		else if(this.shipmentType == "Rush Shipping") {
			shipSpeed = RUSH;
		}
		else {
			shipSpeed = STANDARD;
		}
		return shipSpeed;
	}
	
	private int daysOldHelper() {
		Date orderdate;
		int daysOld = 0;
		try {
			orderdate = sdf.parse(this.getOrderDate());
			Date now = new Date(System.currentTimeMillis());
			long diff = now.getTime() - orderdate.getTime();
			daysOld = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			
		} catch (ParseException e) {
			System.out.println("daysOldHelper(): Error parsing orderDate. " + e.getMessage());
		}
		return daysOld;
	}
	
	/* CONSTRUCTORS */
	public Order() {
		orderID = ++orderIDGen;
		customerName = "";
		orderDate = new Timestamp(System.currentTimeMillis());
		shipDate = null;
		product = null;
		quantity = null;
		shipmentType = null;
		isShipped = false;
	}
	
	public Order(ArrayList<Product> prod, ArrayList<Integer> qty, String custName, String shipType, boolean isshipped) {
		//TODO change prod and qty to arrays
		++orderID;
		customerName = custName;
		orderDate = new Timestamp(System.currentTimeMillis());
		shipDate = null;
		product = prod;
		quantity = qty;
		shipmentType = shipType;
		isShipped = isshipped;
	}
	
	/* ACCESSORS */
	
	public int getOrderID() {
		return orderID;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public String getOrderDate() {
		return sdf.format(orderDate);
		//return orderDate;
	}
	
	public String getShipDate() {
		return sdf.format(shipDate);
		//return shipDate;
	}
	
	public ArrayList<Product> getProduct() {
		return product;
	}

	public ArrayList<Integer> getQuantity() {
		return quantity;
	}

	public String getShipmentType() {
		return shipmentType;
	}

	public boolean getIsShipped() {
		return isShipped;
	}
	
	/* MUTATORS */
	
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	public void setCustomerName(String custName) {
		this.customerName = custName;
	}

	/**
	 * Sets Order date as newDate
	 */
	public void setOrderDate(Timestamp newDate) {
		this.orderDate =  newDate;
	}

	/**
	 * Sets ship date as NOW (current time & date).
	 */
	public void setShipDate() {
		this.shipDate =  new Timestamp(System.currentTimeMillis());
		isShipped = true;
	}

	public void setProduct(ArrayList<Product> product) {
		this.product = product;
	}

	public void setQuantity(ArrayList<Integer> quantity) {
		this.quantity = quantity;
	}
	
	public boolean swapShipmentType(String ship_type) {
		if (ship_type == "Rush Shipping" || ship_type == "Overnight Shipping" || ship_type == "Standard Shipping") {
			shipmentType = ship_type;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void toggleIsShipped() {
		this.isShipped = !isShipped;
	}
	
	/* OTHER METHODS */
	
	@Override public String toString() {
		String components = "";

		for (int j = 0; j < this.getProduct().size(); j ++)
		{
			if (this.getIsShipped())
				components += "TRUE,";
			else
				components += "FALSE,";
			components += this.getOrderDate() + ",";
			components += this.getShipDate() + ",";
			components += this.getShipmentType() + ",";
			components += this.getCustomerName() + ","; 
			components += this.getProduct().get(j).getProductId() + ",";
			components += this.getQuantity().get(j) + "\n"; 
		}
		return components;
	}

	@Override
	public int hashCode() {
		// TODO hashCode - redo this?
		
		//autogenerated --edit?
		final int prime = 31;
		int result = 1;
		result = prime * result + (isShipped ? 1231 : 1237);
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		//result = prime * result + quantity;
		result = prime * result + ((shipDate == null) ? 0 : shipDate.hashCode());
		result = prime * result + ((shipmentType == null) ? 0 : shipmentType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO equals redo this? autogenerated --edit?
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (isShipped != other.isShipped)
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity != other.quantity)
			return false;
		if (shipDate == null) {
			if (other.shipDate != null)
				return false;
		} else if (!shipDate.equals(other.shipDate))
			return false;
		if (shipmentType == null) {
			if (other.shipmentType != null)
				return false;
		} else if (!shipmentType.equals(other.shipmentType))
			return false;
		return true;
	}
	
	
	 /**
     * Compares two Order objects to determine priority ordering
     * Returns 0 if the two items are equal
     * Return -1 if this Order's OrderDate timestamp and ship speed comes
     * before the other Order.
     * Returns 1 if the other Order's OrderDate timestamp and ship speed comes
     * before this Order.
     * If the two Orders are the same, will return 0
     * @param the other Order object to compare to this
     * @return 0 (same Order), -1 (this Order is lower priority)
     * or 1 (this Order is higher priority) 
     */
	public int compareTo(Order otherOrder) {
		//TODO compareTo()
		
		
		if (otherOrder == null) {
			return 1;
		}
		
		int orderScore = this.daysOldHelper() + this.shipSpeedHelper();
		//System.out.println("orderScore: " + orderScore);
		int otherOrderScore = otherOrder.daysOldHelper() + otherOrder.shipSpeedHelper();
		//System.out.println("otherOrderScore: " + otherOrderScore);
		if (orderScore < otherOrderScore) {
			return -1;
		}
		else if (orderScore > otherOrderScore) {
			return 1;
		}
		else{ // scores are equal - compare timestamps
			
			Timestamp orderdate = null;
			Timestamp otherOrderDate = null;
			try {
				orderdate = new Timestamp(sdf.parse(this.getOrderDate()).getTime());
				otherOrderDate = new Timestamp(sdf.parse(otherOrder.getOrderDate()).getTime());
			} catch (ParseException e) {
				System.out.println("compareTo(): Error parsing dates. " + e.getMessage());
			}
			if (orderdate.before(otherOrderDate)) {
				return -1;
			}
			else if (orderdate.after(otherOrderDate)) {
				return 1;
			}	
			else { //they have the exact same orderDate Timestamp and shipmentType
				return 0;
			}
		}
		
	}
	
}
