package card.developer.lln.database;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import card.developer.lln.database.DB.DataBaseHelper;
import card.developer.lln.database.DB.DBManagerHelper;


public class MainActivity extends AppCompatActivity {

    private DBManagerHelper dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DataBaseHelper._ID,
            DataBaseHelper.NOME, DataBaseHelper.SOBRENOME };

    final int[] to = new int[] { R.id.id, R.id.nome, R.id.sobrenome };

    private Button add, deletar, atualizar;

    private EditText nomeEdittEXT;
    private EditText sobrenomeEditText;
    Cursor cursor;

    private String id;
    private String nome;
    private String sobrenome;

    private long _id;

    private View viewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManagerHelper(this);
        dbManager.open();
        cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.listview);
        //listView.setEmptyView(findViewById(R.id.listview));

        add = findViewById(R.id.add);

        atualizar = findViewById(R.id.atualizar);
        atualizar.setEnabled(false);

        deletar = findViewById(R.id.deletar);
        deletar.setEnabled(false);

        nomeEdittEXT = findViewById(R.id.nome);
        sobrenomeEditText = findViewById(R.id.sobrenome);

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {

                deletar.setEnabled(true);
                atualizar.setEnabled(true);
                add.setEnabled(false);
                setViewList(view);


            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nomeEdittEXT.getText().toString();
                final String desc = sobrenomeEditText.getText().toString();

                dbManager.insert(name, desc);

                Cursor cursor = dbManager.fetch();
                adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.activity_view_record, cursor, from, to, 0);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);

                nomeEdittEXT.setText("");
                sobrenomeEditText.setText("");

            }
        });


        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView titleTextView = (TextView) getViewList().findViewById(R.id.nome);
                TextView descTextView = (TextView) getViewList().findViewById(R.id.sobrenome);

                nome = titleTextView.getText().toString();
                sobrenome = descTextView.getText().toString();


                String nome = nomeEdittEXT.getText().toString();
                String sobrenome = sobrenomeEditText.getText().toString();

                TextView idTextView = (TextView) getViewList().findViewById(R.id.id);
                id = idTextView.getText().toString();

                dbManager.update(Long.parseLong(id), nome, sobrenome);

                Cursor cursor = dbManager.fetch();
                adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.activity_view_record, cursor, from, to, 0);
                adapter.notifyDataSetChanged();

                listView.setAdapter(adapter);
                deletar.setEnabled(false);
                atualizar.setEnabled(false);
                add.setEnabled(true);

                nomeEdittEXT.setText("");
                sobrenomeEditText.setText("");
            }
        });

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView idTextView = (TextView) getViewList().findViewById(R.id.id);
                id = idTextView.getText().toString();

                dbManager.delete(Long.parseLong(id));

                Cursor cursor = dbManager.fetch();
                adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.activity_view_record, cursor, from, to, 0);
                adapter.notifyDataSetChanged();

                listView.setAdapter(adapter);
                deletar.setEnabled(false);
                atualizar.setEnabled(false);
                add.setEnabled(true);
            }
        });

        //Android - Google
        //IOS - Apple
        //MIUI - Xiaomi
        //Windows Phone - Microsoft


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.clearDB) {

            dbManager.clearDatabase();
            Cursor cursor = dbManager.fetch();
            adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.activity_view_record, cursor, from, to, 0);
            adapter.notifyDataSetChanged();
            listView.setAdapter(null);

        }
        return super.onOptionsItemSelected(item);
    }


    public View getViewList() {
        return viewList;
    }

    public void setViewList(View viewList) {
        this.viewList = viewList;
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

}
