package sk.upjs.ics.android.fotak;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.commonsware.cwac.cam2.CameraActivity;
import com.commonsware.cwac.cam2.CameraEngine;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Uri cestaKObrazku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onImageViewClick(View view) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = new File(dir, "fotka" + System.currentTimeMillis() +".jpg");

        Intent intent = new CameraActivity.IntentBuilder(this)
                .skipConfirm()
                .forceEngine(CameraEngine.ID.CLASSIC)
                .to(image)
                .updateMediaStore()
                .build();

        //startActivity(intent);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0){
            cestaKObrazku = data.getData();
            ImageView imageView = (ImageView) findViewById(R.id.imageView);

            Picasso.with(this).load(cestaKObrazku).into(imageView);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, this.cestaKObrazku);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}