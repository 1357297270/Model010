package com.example.administrator.model01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Bitmap mbitmap;
    private Bitmap mbmcopy;
    private Canvas mcanvas;
    private Paint mpaint;
    private int mstartx;
    private int mstarty;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        img = findViewById( R.id.img );
        button1 = findViewById( R.id.but1 );
        button2 = findViewById( R.id.but2 );
        button3 = findViewById( R.id.but3 );
        button4 = findViewById( R.id.but4);
        mbitmap = BitmapFactory.decodeResource( getResources(), R.drawable.bg );
        mbmcopy = Bitmap.createBitmap( mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig() );
        mcanvas = new Canvas( mbmcopy );
        //画板为白色
        mcanvas.drawColor( Color.WHITE );
        mpaint = new Paint();
        //画笔的颜色
       // mpaint.setColor( Color.RED );
        //像素
        mpaint.setStrokeWidth( 5 );
        //final int red = paint.getColor();

        mcanvas.drawBitmap( mbitmap,new Matrix( ), mpaint );

        img.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mstartx = (int) event.getX();
                        mstarty = (int) event.getY();
                        System.out.print( "m"+mstartx+"y"+mstarty );
                        break;
                    case MotionEvent.ACTION_MOVE:
                       int  newx = (int) event.getX();
                       int   newy = (int) event.getY();
                        System.out.print( "x"+newx+"y"+newy );
                        mcanvas.drawLine( mstartx,mstarty,newx,newy,mpaint );
                            mstartx=newx;
                           mstarty=newy;
                          img.setImageBitmap( mbmcopy );
                        break;
                    case MotionEvent.ACTION_UP:
                        break;

                }
                return true;
            }
        } );
button1.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mpaint.setColor( Color.RED );
    }
} );
        button2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpaint.setColor( Color.BLUE );
            }
        } );
        button3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mpaint.setStrokeWidth( 15 );;
            }
        } );
        button4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                OutputStream stream;
                try {
                    stream = new FileOutputStream(file);
                    mbitmap.compress( Bitmap.CompressFormat.JPEG, 200, stream);

                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText( MainActivity.this, mbitmap+"图片保存成功", Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}
