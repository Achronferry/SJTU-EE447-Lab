package irdc.ex03_02;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

public class EX03_02 extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources resources = getBaseContext().getResources();
        Drawable HippoDrawable = resources.getDrawable(R.drawable.white);
        TextView tv = (TextView)findViewById(R.id.widget28);
        tv.setBackgroundDrawable(HippoDrawable);
    }
}