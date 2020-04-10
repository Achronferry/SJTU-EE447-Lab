package android1_5.cq.groupMessage;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;



public class contactCheck extends ListActivity {
    /** Called when the activity is first created. */
	//�����ʶ
	private static final String TAG = "[ListActivity_test]";
	//�洢����
	static String[]intg_phonesNO = new String[100]; 
	//�洢����
	static String []Strg_Names = new String[100];
	//��Ч�������
	static int int_length = 0;
	//��Ϣ����
	static String Strmsg = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent groupMsg = getIntent();
        Strmsg = groupMsg.getStringExtra("groupMessage");
        Cursor c = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        CursorAdapter adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,c,
        		new String[]{People.NAME,People.NUMBER},new int[]{android.R.id.text1,android.R.id.text2});
        setListAdapter(adapter);
        
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Cursor c = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
		c.moveToPosition(position);
		final int StrNameIndex = c.getColumnIndex(People.NAME);
		final boolean isRepeated ;//�ж�ѡ���Ƿ��ظ�
		int StrNunberIndex = c.getColumnIndex(People.NUMBER);
		//ȥ���绰�����еķǺ����ַ�
		String tem = c.getString(StrNunberIndex);
		String[]ini_Num = tem.split("-");
		String ini_Nums = "";
		for(int i = 0; i<ini_Num.length-1;i++)
		{
			ini_Nums = ini_Nums+ini_Num[i]+ ini_Num[i+1];
		}
		
		
		//��Ч��ϵ������һλ
		final String numb = ini_Nums;
		final String Nam = c.getString(StrNameIndex);
		
		isRepeated = bCheckRpted(Strg_Names,Nam,int_length);
		String StrName = "";
		for(int i = 0; i<int_length;i++)
		{
			StrName = StrName+":"+Strg_Names[i];
		}
		if(isRepeated == false){
			StrName = StrName + ":" + Nam;
			Log.v(TAG,"false");
			intg_phonesNO[int_length] = tem;
			Strg_Names[int_length]=Nam;
			int_length++;
		}
		
	//	Log.v(TAG,isRepeated.to);
		//��ѡ��,ȷ��
		/*new AlertDialog.Builder(contactCheck.this)
		.setTitle("Alter")
		.setMessage(StrName)
		.setIcon(R.drawable.icon)
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				setResult(RESULT_OK);
				

				
				
			}
			
		}).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				setResult(RESULT_CANCELED);
				int_length--;
			}
			
		}).setNeutralButton("����",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String num = "";
				for(int i = 0; i<int_length;i++)
				{
					sendSMS(intg_phonesNO[i],Strmsg);
					num = num+":"+intg_phonesNO[i];
				}
				//����
				
				Toast.makeText(contactCheck.this,"�������"+num, Toast.LENGTH_LONG).show();
				int_length = 0;
			}
			
		}).show();*/
		
		//Toast.makeText(listActivity_test.this,StrNameSelectec, Toast.LENGTH_LONG).show();
	}
	public boolean bCheckRpted(String[] Names, String snam, int len )
	{
		if (len<=0) return false;
		for(int i = 0;i<len;i++)
		{
			if(Names[i].contentEquals(snam)) return true;
		}
		return false;
	}
	 public void sendSMS(String PhoneNumber,String message){
	    	PendingIntent pi = PendingIntent.getActivity(this,0,new Intent(this,contactCheck.class), 0);
	    	SmsManager sms = SmsManager.getDefault();
	    	sms.sendTextMessage(PhoneNumber,null,message,pi,null);
	    }
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
			super.onCreateOptionsMenu(menu);
			menu.add(0,0,0,"��ϵ��");
			menu.add(0,1,1,"��Ϣ");
			menu.add(0,2,2,"����");
			menu.add(0,3,3,"�����ϵ��");
			menu.add(0,4,4,"����");
			return true;
		}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case 0://��ϵ��
			{
				String cntctgp = "";
				for(int i = 0; i< int_length;i++)
				{
					cntctgp = cntctgp +  Strg_Names[i] + intg_phonesNO[i]+";";
				}
								Toast.makeText(contactCheck.this,"��ϵ���Ա:"+cntctgp, Toast.LENGTH_LONG).show();
				return true;
			}
			case 1://��Ϣ
			{
				Toast.makeText(contactCheck.this,"��Ϣ:"+Strmsg, Toast.LENGTH_LONG).show();
				return true;
			}
			case 2://����
			{
				//Toast.makeText(contactCheck.this,"����", Toast.LENGTH_LONG).show();
				String num = "";
				for(int i = 0; i<int_length;i++)
				{
					sendSMS(intg_phonesNO[i],Strmsg);
					num = num+":"+intg_phonesNO[i];
				}
				//����
				
				Toast.makeText(contactCheck.this,"�������"+num, Toast.LENGTH_LONG).show();
				int_length = 0;
				finish();
				return true;
			}
			case 3://�����ϵ��
			{
				//Toast.makeText(contactCheck.this,"�����ϵ��", Toast.LENGTH_LONG).show();
				int_length = 0;
				return true;
			}
			case 4://����
			{
				//Toast.makeText(contactCheck.this,"�˳�", Toast.LENGTH_LONG).show();
				finish();
				return true;
			}
		}
		
		return true;
	}
	 
}