package com.studio.android.chp05.ex01;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.PhoneNumberUtils;

public class TinyDialer extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final EditText phoneNumber = (EditText) findViewById(R.id.phonenumber_id);
        final Button button = (Button) findViewById(R.id.button_id);
        button.setOnClickListener(new Button.OnClickListener()
        		{
					@Override
					public void onClick(View b) {
						String callee = phoneNumber.getText().toString();
						if (PhoneNumberUtils.isGlobalPhoneNumber(callee)){
							//Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://" + callee));
							Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + callee));
							startActivity(i);
						} else {
							Toast.makeText(TinyDialer.this, R.string.notify_incorrect_phonenumber,
				                    Toast.LENGTH_LONG).show();
						}
					}      			
        		}
        );
    }
}