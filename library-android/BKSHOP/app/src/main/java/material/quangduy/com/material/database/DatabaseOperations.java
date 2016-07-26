package material.quangduy.com.material.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import material.quangduy.com.material.entities.ProductEntity;

public class DatabaseOperations extends SQLiteOpenHelper {
	public static final int database_version = 1;
	private Context context;
	public String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS "
			+ TableData.TableInfo.TABLE_NAME + " (" + TableData.TableInfo.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ TableData.TableInfo.PRODUCT_NAME + " TEXT,"
			//+ TableData.TableInfo.PRODUCT_STOCK+ " INTEGER," + TableData.TableInfo.PRODUCT_RATE + " FLOAT,"
			+ TableData.TableInfo.PRODUCT_PRICE + " FLOAT,"
			+ TableData.TableInfo.PRODUCT_SOLUONG + " INTEGER,"
			+ TableData.TableInfo.PRODUCT_LINKIMAGE + " TEXT);";

	public DatabaseOperations(Context context) {
		super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
//		SQLiteDatabase db = this.getWritableDatabase();
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void putInfomation(ProductEntity product) {
		SQLiteDatabase SQ = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(TableData.TableInfo.PRODUCT_ID, product.getmProductID());
		cv.put(TableData.TableInfo.PRODUCT_NAME, product.getmProductName());
//		if (product.getmProductStock() == true)
//			cv.put(TableData.TableInfo.PRODUCT_STOCK, 1);
//		else
//			cv.put(TableData.TableInfo.PRODUCT_STOCK, 0);
//		cv.put(TableData.TableInfo.PRODUCT_RATE, product.getRate());
		cv.put(TableData.TableInfo.PRODUCT_PRICE, product.getmProductPrice());
		cv.put(TableData.TableInfo.PRODUCT_LINKIMAGE, product.getLinkImage());
		cv.put(TableData.TableInfo.PRODUCT_SOLUONG, product.getSoluong());
		long k = SQ.insert(TableData.TableInfo.TABLE_NAME, null, cv);
		Log.e("DATABASE operation", "One row inserted");

	}

	public Cursor getAllData() {
		SQLiteDatabase SQ = this.getWritableDatabase();
		Cursor CR = SQ.rawQuery("select * from "+TableData.TableInfo.TABLE_NAME+" order by "+TableData.TableInfo.PRODUCT_ID+" ASC",null);

		return CR;
	}

	public void update( int id, int soluong) {
		SQLiteDatabase SQ = this.getWritableDatabase();
		String selection = TableData.TableInfo.PRODUCT_ID + " = ?";
		String args[] = { String.valueOf(id) };
		ContentValues values = new ContentValues();
		values.put(TableData.TableInfo.PRODUCT_SOLUONG, soluong);
		SQ.update(TableData.TableInfo.TABLE_NAME, values, selection, args);
	}

	public void delete(int id) {
		SQLiteDatabase SQ = this.getWritableDatabase();
		SQ.execSQL("delete from " + TableData.TableInfo.TABLE_NAME + " where " + TableData.TableInfo.PRODUCT_ID + " = " + id);
		SQ.close();
	}
	public void delete_all() {
		SQLiteDatabase SQ = this.getWritableDatabase();
		SQ.execSQL("DELETE FROM "+TableData.TableInfo.TABLE_NAME); //delete all rows in a table
		SQ.close();

	}

	public int getSoluongById(DatabaseOperations dop, int id) {
		Cursor cr = dop.getInformationById(dop, id);
		cr.moveToFirst();
		int sl = cr.getInt(3);

		return sl;
	}

	public Cursor getInformationById(DatabaseOperations dop, int id) {
		SQLiteDatabase SQ = dop.getWritableDatabase();
		Cursor CR = SQ.rawQuery("select * from "+TableData.TableInfo.TABLE_NAME +" where "+TableData.TableInfo.PRODUCT_ID +" ="+id,null);
		return CR;
	}

}
