package com.lkmhr.dictic;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lkmhr.dictic.utils.DictionaryDatabase;
import com.lkmhr.dictic.utils.Word;


public class MainActivity extends Activity {

	private ImageButton search;
	private EditText searchText;
	private ListView resultList;
	private ProgressBar loader;
	
	private DictionaryAdapter mAdapter;
	private DictionaryDatabase db;
	List<Word> words;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getActionBar().hide();
        
        db = new DictionaryDatabase(MainActivity.this);
        db.createDatabase();
        
        search = (ImageButton) findViewById(R.id.button_search);
        searchText = (EditText) findViewById(R.id.text_search);
        resultList = (ListView) findViewById(R.id.list_result);
        loader = (ProgressBar) findViewById(R.id.loader);
        loader.setVisibility(View.GONE);
        
        search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!searchText.getText().toString().equals("")){
					new AsyncDataLoader().execute(searchText.getText().toString());
				} else {
					Toast.makeText(MainActivity.this, "Nothing to search?", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private class DictionaryAdapter extends ArrayAdapter<Word> {
    	
    	private List<Word> words;
    	private Context context;
    	
		public DictionaryAdapter(Context context, List<Word> words) {
			super(context, R.layout.list_row, words);
			this.words = words; 
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final ViewHolder holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if(convertView == null ){
				convertView = inflater.inflate(R.layout.list_row, parent, false);
			}
			
			holder.wordView = (TextView) convertView.findViewById(R.id.list_item_word);
			holder.posView = (TextView) convertView.findViewById(R.id.list_item_pos);
			holder.defView = (TextView) convertView.findViewById(R.id.list_item_def);
			
			holder.wordView.setText(words.get(position).getWord());
			holder.posView.setText(" (" + words.get(position).getPartOfSpeech() + ")");
			holder.defView.setText(words.get(position).getDefinition());
			
			return convertView;
		}
		
		private class ViewHolder {
			public TextView wordView;
			public TextView posView;
			public TextView defView;
		}
    }
    
    private class AsyncDataLoader extends AsyncTask<String, String, String> {
    	
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		loader.setVisibility(View.VISIBLE);
    	}

		@Override
		protected String doInBackground(String... params) {
			words = db.searchWords(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mAdapter = new DictionaryAdapter(MainActivity.this, words);
			resultList.setAdapter(mAdapter);
			loader.setVisibility(View.GONE);
		}
    	
    }
}
