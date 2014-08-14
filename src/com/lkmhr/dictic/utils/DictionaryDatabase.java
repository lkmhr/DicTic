package com.lkmhr.dictic.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryDatabase extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "dictionary_db";
	private static final String TABLE_DEFINITION = "definition_table";
	private static String DATABASE_PATH = "data/data/com.lkmhr.dictic/databases";
	
	
	@SuppressWarnings("unused")
	private static final String KEY_ID = "_id";
	private static final String KEY_WORD = "word";
	private static final String KEY_POS = "pos"; //Part of speech
	private static final String KEY_DEFINITION = "definition";
	
	private Context mContext;
	
	public DictionaryDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createDatabase();
	}
	
	public void createDatabase(){
		if(!checkDatabase()){
	    	try {
	    		copyDataBase();
    		} catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
		}
	}
	
	/**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
	private boolean checkDatabase() {
		    File dbFile = mContext.getDatabasePath(DATABASE_NAME);
		    return dbFile.exists();
	}
	
	/**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
	private void copyDataBase() throws IOException{

           //Open your local db as the input stream
           InputStream myInput = mContext.getAssets().open("db/"+DATABASE_NAME);

           // Path to the just created empty db
           String outFileName = DATABASE_PATH +"/"+ DATABASE_NAME;
           File databaseFile = new File(DATABASE_PATH);
            // check if databases folder exists, if not create one and its subfolders
           if (!databaseFile.exists()){
               databaseFile.mkdir();
           }

           //Open the empty db as the output stream
           OutputStream myOutput = new FileOutputStream(outFileName);

           //transfer bytes from the inputfile to the outputfile
           byte[] buffer = new byte[1024];
           int length;
           while ((length = myInput.read(buffer))>0){
           myOutput.write(buffer, 0, length);
           }

           //Close the streams
           myOutput.flush();
           myOutput.close();
           myInput.close();

   }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFINITION);
        onCreate(db);
	}

	
	/**
	 * Used to add new words is added to the dictionary. 
	 * @param wordsList list of words to add.
	 */
	public void createWordEntry(List<Word> wordsList) {
		
		Iterator<Word> words = wordsList.iterator();
		SQLiteDatabase db = this.getWritableDatabase();

		while (words.hasNext()) {
			ContentValues values = new ContentValues();
			Word word = (Word) words.next();
			
			values.put(KEY_WORD, word.getWord());
			values.put(KEY_POS, word.getPartOfSpeech());
			values.put(KEY_DEFINITION, word.getDefinition());
			db.insert(TABLE_DEFINITION, null, values);
		}
		db.close();
	}
	
	/**
	 * Returns a list of definitions of word provided.
	 * @param word The word to search.
	 */
	public List<Word> searchWords(String searchWord){		
		
		List<Word> words = new ArrayList<Word>();
		SQLiteDatabase db = this.getReadableDatabase();
		
//		Full Scan
//		
//		String [] columns = {
//			KEY_WORD,
//			KEY_POS,
//			KEY_DEFINITION
//		};
//		Cursor cursor = db.query(TABLE_DEFINITION, columns, KEY_WORD + " LIKE ?", new String[] {searchWord + "%"}, null, null, null);
		
		String COLUMNS = KEY_WORD + ", " + KEY_POS + ", " + KEY_DEFINITION; 
		Cursor cursor = db.rawQuery("SELECT " + COLUMNS + " FROM " + TABLE_DEFINITION +
				" WHERE word like '"+searchWord+"%'" +
				getRange(searchWord.charAt(0)) + " LIMIT 50",			
				null);
		
		if(cursor.moveToFirst()) {
			while(cursor.moveToNext()){
				Word word = new Word();
				word.setWord(cursor.getString(0));
				word.setPartOfSpeech(" ("+cursor.getString(1)+") ");
				word.setDefinition(cursor.getString(2));
				
				words.add(word);
			}
		} else {
			words.add(new Word("", "", "No Results Found"));
		}
		
		//close db instances. 
		cursor.close();
		db.close();
		
		return words;
	}
	
	/**
	 * Returns a specific range of _id in the database for the provided letter.
	 * 
	 * @param ch letter whose range is to be found
	 * @return range in the form " AND _id BETWEEN XXX AND YYY;" 
	 */
	private String getRange(char ch){
		String range = " AND _id BETWEEN ";
		
		switch (ch) {
		case 'a': case 'A': range += "6 AND 11622"; break;
		case 'b': case 'B': range += "11622 AND 21499"; break;
		case 'c': case 'C': range += "21499 AND 38235"; break;
		case 'd': case 'D': range += "38235 AND 49041"; break;
		case 'e': case 'E': range += "49041 AND 56561"; break;
		case 'f': case 'F': range += "56561 AND 63980"; break;
		case 'g': case 'G': range += "63980 AND 69397"; break;
		case 'h': case 'H': range += "69397 AND 75666"; break;
		case 'i': case 'I': range += "75666 AND 83449"; break;
		case 'j': case 'J': range += "83449 AND 84787"; break;
		case 'k': case 'K': range += "84787 AND 86070"; break;
		case 'l': case 'L': range += "86070 AND 91884"; break;
		case 'm': case 'M': range += "91884 AND 100730"; break;
		case 'n': case 'N': range += "100730 AND 103764"; break;
		case 'o': case 'O': range += "103764 AND 108225"; break;
		case 'p': case 'P': range += "108225 AND 123654"; break;
		case 'q': case 'Q': range += "123654 AND 124697"; break;
		case 'r': case 'R': range += "124697 AND 133607"; break;
		case 's': case 'S': range += "133607 AND 155110"; break;
		case 't': case 'T': range += "155110 AND 164395"; break;
		case 'u': case 'U': range += "164395 AND 167753"; break;
		case 'v': case 'V': range += "167753 AND 170549"; break;
		case 'w': case 'W': range += "170549 AND 175006"; break;
		case 'x': case 'X': range += "175006 AND 175153"; break;
		case 'y': case 'Y': range += "175153 AND 175639"; break;
		case 'z': case 'Z': range += "175639 AND 176053"; break;

		default:
			range += "6 AND 176053";
		}
		return range;
	}
	
}
