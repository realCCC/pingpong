package kr.co.company.pingpong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MySurfaceView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MySurfaceView(this);
        setContentView(view);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.red:
                for(int i=0;i<MySurfaceView.basket.length;i++){
                    MySurfaceView.basket[i].ballaction1();
                }
                return true;
            case R.id.blue:
                for(int i=0;i<MySurfaceView.basket1.length;i++){
                    MySurfaceView.basket1[i].ballaction2();
                }
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause(){
        super.onPause();
    }
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
}