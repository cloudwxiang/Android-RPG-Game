package com.example.xiangwan.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class GameProgressActivity extends AppCompatActivity {

    private SimpleCursorAdapter mAdapter;
    private ListView listView;
    private EditText et_id;
    private EditText et_name;
    private Button btn_add;
    private Button btn_delete;
    private Button btn_read;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_progress);

        btn_add = (Button) findViewById(R.id.savegame);
        btn_delete = (Button) findViewById(R.id.deletegame);
        btn_back = (Button)findViewById(R.id.exit);
        btn_read = (Button)findViewById(R.id.readgame);
        listView = (ListView) findViewById(R.id.list);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                refleshListView();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                refleshListView();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_SHORT).show();
                int positions = position + 1;
                DBlist dblist = new DBlist(GameProgressActivity.this);
                SQLiteDatabase dblistWrite = dblist.getWritableDatabase();
                dblistWrite.delete("user2","_id=?",new String[]{""+positions});
                dblistWrite.close();
                refleshListView();
            }
        });
    }

    public void addData() {
        btn_back = (Button) findViewById(R.id.exit);

        btn_add = (Button) findViewById(R.id.savegame);
        btn_delete = (Button) findViewById(R.id.deletegame);
        listView = (ListView) findViewById(R.id.list);
        et_id.setVisibility(View.VISIBLE);
        et_name.setVisibility(View.VISIBLE);
        btn_add.setVisibility(View.GONE);
        btn_delete.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);

        btn_back.setVisibility(View.VISIBLE);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_add.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                et_id.setVisibility(View.GONE);
                et_name.setVisibility(View.GONE);
                btn_back.setVisibility(View.GONE);

                String id = et_id.getText().toString(),
                        name =et_name.getText().toString();

                DBlist dblist = new DBlist(GameProgressActivity.this);
                SQLiteDatabase dblistWrite = dblist.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put("_id", id);
                cv.put("name", name);
                dblistWrite.insert("user2", null, cv);

                dblistWrite.close();

                refleshListView();
            }
        });
    }

    public void deleteData() {
        DBlist dblist = new DBlist(GameProgressActivity.this);
        SQLiteDatabase dblistWrite = dblist.getWritableDatabase();
        dblistWrite.delete("user2",null,new String[]{});
        dblistWrite.close();
    }

    public void refleshListView() {
        DBlist dblist = new DBlist(GameProgressActivity.this);
        SQLiteDatabase dbRead = dblist.getReadableDatabase();
        Cursor c = dbRead.query("user2", null, null, null, null, null, null);

        mAdapter = new SimpleCursorAdapter(GameProgressActivity.this, R.layout.user_info, c,
                new String[]{"_id","name"}, new int[]{R.id._id,R.id._name});

        listView.setAdapter(mAdapter);

        dbRead.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button.
        int id = item.getItemId();
        if (id == R.id.action_Menu) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_Game) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_Settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_GameProgress) {
            Intent intent = new Intent(this, GameProgressActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
