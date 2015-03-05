package com.groganlabs.mishmash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MishMashDB extends SQLiteOpenHelper {
	public static final int JUMBLE_MASK =1;
	public static final int CRYPTO_MASK = 2;
	public static final int DROP_MASK = 4;
	
	private static final String GAME_TABLE = "phrase";
	private static final String GAME_TABLE_ID = "phrase_id";
	private static final String COL_COMPLETED = "completed";
	private static final String COL_ELIGABLE = "eligable_for";
	private static final String COL_GAME = "phrase";
	
	private static final String PACK_TABLE = "game_pack";
	private static final String PACK_ID	= "id";
	private static final String PACK_PURCHASED = "purchased";
	
	private static final String ACTIVE_GAME_TABLE = "active_game";
	private static final String ACTIVE_GAME_ID = "id";
	private static final String ACTIVE_GAME_GAME_ID = "game_id";
	private static final String ACTIVE_GAME_GAME = "game_mask";
	private static final String ACTIVE_GAME_ANSWER = "user_answer";
	private static final String ACTIVE_GAME_SOLUTION = "solution";
	private static final String ACTIVE_GAME_PUZZLE = "puzzle";
	
	private static final String GAME_TABLE_CREATE = "create table " + GAME_TABLE + 
			"(" + GAME_TABLE_ID + " integer primary key autoincrement, " +
			COL_ELIGABLE + " integer, " + COL_COMPLETED + " integer, " +
			COL_GAME + " varchar);";
	
	private static final String PACK_TABLE_CREATE = "create table" + PACK_TABLE +
			"(" + PACK_ID + " integer primary key autoincrement, " +
			PACK_PURCHASED + " integer);";
	
	private static final String ACTIVE_TABLE_CREATE = "create table " + ACTIVE_GAME_TABLE +
			"(" + ACTIVE_GAME_ID + " integer primary key autoincrement, " +
			ACTIVE_GAME_GAME_ID + " integer, " + ACTIVE_GAME_GAME + " varchar, " +
			ACTIVE_GAME_ANSWER + " varchar, " +
			ACTIVE_GAME_SOLUTION + " varchar, " + ACTIVE_GAME_PUZZLE + " varchar);";
	

	public MishMashDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(GAME_TABLE_CREATE);
		db.execSQL(PACK_TABLE_CREATE);
		db.execSQL(ACTIVE_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * gets a game, any game. Uses the app settings to determine
	 * game reuse options
	 * @param mask Any of the static MASK vars available
	 * @return game, or null
	 */
	public Game getGame(int mask) {
		return new Game();
	}
	
	/**
	 * gets a random game from the given pack. Uses the app settings to determine
	 * game reuse options
	 * @param mask Any of the static MASK vars available
	 * @return game, or null
	 */
	public Game getGame(int mask, int pack) {
		return new Game();
	}
	
	/**
	 * Gets the specified game from the given pack
	 * @param mask Any of the static MASK vars available
	 * @param pack Any of the static PACK vars
	 * @param gameId 
	 * @return game, or null if invalid pack and gameId
	 */
	public Game getGame(int mask, int pack, int gameId) {
		return new Game();
	}
}
