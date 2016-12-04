package com.dienbui.notetaker;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainMenu extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        TabHost host = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        // add "All Notes" tab to the main UI
        intent = new Intent().setClass(this, NoteMenu.class);
        spec = host.newTabSpec("All Notes")
               .setIndicator("All Notes")
               .setContent(intent);
        host.addTab(spec);

        // add "Groups" tab to the main UI
        intent = new Intent().setClass(this, GroupMenu.class);
        spec = host.newTabSpec("Groups")
               .setIndicator("Groups")
                .setContent(intent);
        host.addTab(spec);
        host.setCurrentTab(1);
        showDialog(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_note) {  // add note
            Intent i = new Intent(this, AddingNote.class);
                startActivity(i);
            return true;
        } else if (id == R.id.add_group) {  // add group
            // open activity to add new group
            Intent i = new Intent(this, AddingGroup.class);
            startActivity(i);
            return true;
        } else if (id == R.id.about) {
            Intent i = new Intent(this, About.class);
            startActivity(i);
            return true;
        } else if (id == R.id.action_search) {
            Intent i = new Intent(this, Searching.class);
            startActivity(i);
            return true;
        }

        return false;
    }

//    private boolean haveNetworkConnection() {
//        boolean haveConnectedWifi = false;
//        boolean haveConnectedMobile = false;
//
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
//        for (NetworkInfo ni : netInfo) {
//            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
//                if (ni.isConnected())
//                    haveConnectedWifi = true;
//            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
//                if (ni.isConnected())
//                    haveConnectedMobile = true;
//        }
//        return haveConnectedWifi || haveConnectedMobile;
//    }
}
