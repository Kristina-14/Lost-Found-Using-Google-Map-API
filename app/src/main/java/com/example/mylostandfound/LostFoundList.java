package com.example.mylostandfound;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LostFoundList extends AppCompatActivity {
    private DBHandler dbHandler = new DBHandler(this);
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_found_list);
        listView = findViewById(R.id.list1);
        //get all data from DB
        //display all data in list view
        ArrayList<Post> detailsList = dbHandler.getAllItems();
        ArrayList<String> list = new ArrayList<>();
        for(Post p: detailsList){
            list.add(p.getName());
        }
        //ArrayList<String> list = detailsList.stream().map(i -> i.getName()).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post post = detailsList.get(i);
                onButtonShowPopupWindowClick(view, post);
            }
        });
    }


    public void onButtonShowPopupWindowClick(View v, Post post) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LostFoundList.this);

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_remove_items, null);

        // create the popup window
//        int width = LinearLayout.LayoutParams.MATCH_PARENT;
//        int height = LinearLayout.LayoutParams.MATCH_PARENT;
//        boolean focusable = true; // lets taps outside the popup also dismiss it
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
       // popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
/*
*  View popupView=getLayoutInflater().inflate(R.layout.activity_remove_items,null,false);
 PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
 popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
* */
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        //popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        TextView tv1, tv2, tv3, tv4, tv5;

        tv1 = popupView.findViewById(R.id.textView13);
        tv2 = popupView.findViewById(R.id.textView14);
        tv3 = popupView.findViewById(R.id.textView15);
        tv4 = popupView.findViewById(R.id.textView16);
        tv5 = popupView.findViewById(R.id.textView17);

        tv1.setText(post.getName());
        tv2.setText(post.getPhone());
        tv3.setText(post.getDecs());
        tv4.setText(post.getDate());
        tv5.setText(post.getLoc());

        Button remove = popupView.findViewById(R.id.remove);


        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               // popupWindow.dismiss();
                return true;
            }
        });

        dialogBuilder.setView(popupView);
        AlertDialog alertDialog = dialogBuilder.show();
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.removeItem(post.getId());
                alertDialog.dismiss();
                Toast.makeText(LostFoundList.this, "Removed Successfully!!", Toast.LENGTH_SHORT).show();            }

        });

    }
}