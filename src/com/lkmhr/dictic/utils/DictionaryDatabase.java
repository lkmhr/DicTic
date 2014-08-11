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
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryDatabase extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "dictionary_db";
	private static final String TABLE_DEFINITION = "definition_table";
	private static String DATABASE_PATH = "data/data/com.lkmhr.dictic/databases";
	
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

           //copyDataBase();
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
		
		String [] columns = {
				KEY_WORD,
				KEY_POS,
				KEY_DEFINITION
		};
		Cursor cursor = db.query(TABLE_DEFINITION, columns, KEY_WORD + " LIKE ?", new String[] {searchWord + "%"}, null, null, null);
		
		if(cursor.moveToFirst()) {
			while(cursor.moveToNext()){
				Word word = new Word();
				word.setWord(cursor.getString(0));
				word.setPartOfSpeech(cursor.getString(1));
				word.setDefinition(cursor.getString(2));
				
				words.add(word);
			}
		}
		
		cursor.close();
		db.close();
		
		if(words.size()<1){
			words.add(new Word("", "", "No Results Found"));
			return words;
		} else {
			return words;
		}
	}
	
}
