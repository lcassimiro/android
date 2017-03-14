package br.com.lcassimiro.testdatabaseactivity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends Activity {

    private CommentsDataSource dataSource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new CommentsDataSource(getApplicationContext());
        dataSource.open();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        List<Comment> values = dataSource.getAllComments();

        Log.i("VALUES DA LISTA",values.toString());
        /*ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(getApplicationContext(),android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);
           */

        ListView myListView = (ListView)findViewById(R.id.myListView);

       ArrayAdapter<Comment> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,values);
       myListView.setAdapter(adapter);

      //  ListView myListView = (ListView)findViewById(R.id.myListView);

        final ArrayList<String> myFamily = new ArrayList<String>();

        myFamily.add("Maria");
        myFamily.add("Paulo");
        myFamily.add("Camila");
        myFamily.add("Thiago");
        myFamily.add("Leonardo");
        myFamily.add("Mayara");

        //put the list of my family in listView (convert to listView)
        //  context, layout, array
        // (this,android.R.layout.simple_list_item_1,myFamily)
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myFamily);
        //myListView.setAdapter(arrayAdapter);


    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    /*
    public void onClick(View view){

        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();

        Comment comment = null;
        //verifica a ação
        switch (view.getId()) {
            case R.id.add:
                /*Bloco original
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                comment = dataSource.createComment(comments[nextInt]);
                adapter.add(comment);
                */

/*
                String comments = new String("Leonardo");
                // save the new comment to the database
                comment = dataSource.createComment(comments);
                adapter.add(comment);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    dataSource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    */
}
