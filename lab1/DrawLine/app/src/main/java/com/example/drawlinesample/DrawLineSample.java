package com.example.drawlinesample;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


@SuppressLint("ClickableViewAccessibility")
public class DrawLineSample extends AppCompatActivity {
    Intent intent = getIntent();

    private ImageView scr;
    private Canvas canvas;
    private Paint p;
    private Bitmap bitmap;
    private DisplayMetrics dm;
    private float[] prev_pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // screen size
        prev_pos = new float[2];

        p = new Paint();
        p.setColor(Color.BLACK);              //设置线的颜色
        p.setAntiAlias(true);                //设置抗锯齿，一般设为true
        p.setStrokeCap(Paint.Cap.ROUND);     //设置线的类型
        p.setStrokeWidth(8);                //设置线的宽度

        canvas = new Canvas();
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);

        scr = findViewById(R.id.draw_layer);
        scr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();   //记录坐标
                float y = event.getY();
                if (event.getAction() == MotionEvent.ACTION_MOVE) {    //拖动屏幕
                    canvas.drawLine(x,y,prev_pos[0],prev_pos[1],p);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {    //按下屏幕
                    canvas.drawPoint(x, y, p);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {    //松开屏幕;
                }
                prev_pos[0] = x;
                prev_pos[1] = y;
                scr.setImageBitmap(bitmap);
                return true;
            }
        });
    }

    public void ResetScreen(View view) {
        bitmap.recycle();
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        scr.setImageBitmap(bitmap);
        canvas.setBitmap(bitmap);
    }

    int[] color_list={Color.BLACK,Color.RED,Color.BLUE};
    int current_color = 0;
    public void change_color(View view) {
        current_color = (current_color + 1) % color_list.length ;
        int new_color = color_list[current_color] ;
        p.setColor(new_color);
        ImageButton color_btn = findViewById(R.id.color_btn);
        color_btn.setColorFilter(new_color);
    }


}