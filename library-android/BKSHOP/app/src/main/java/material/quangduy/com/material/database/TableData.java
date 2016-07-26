package material.quangduy.com.material.database;

import android.provider.BaseColumns;

public class TableData {
	public TableData() {

	}

	public static abstract class TableInfo implements BaseColumns {
	public static String PRODUCT_NAME = "product_name";
	public static String PRODUCT_PRICE = "product_price";
	public static String PRODUCT_ID = "product_id";
	//public static String PRODUCT_STOCK = "product_stock";
	public static String PRODUCT_LINKIMAGE = "product_image";
//	public static String PRODUCT_RATE = "product_rate";
	public static String PRODUCT_SOLUONG = "product_soluong";
	public static String DATABASE_NAME = "ListCart";
	public static String TABLE_NAME = "listcart";
	}
}
