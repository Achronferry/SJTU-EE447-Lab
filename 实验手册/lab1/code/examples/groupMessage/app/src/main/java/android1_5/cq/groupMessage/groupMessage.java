package android1_5.cq.groupMessage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class groupMessage extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final EditText msg_txt_edtxt = (EditText)findViewById(R.id.groupMessageText);
        //消息确认按钮
        //单击此按钮，进入messageConfirm activity
        //识别空白信息
        //如果包含有效文本信息则，显示不可编辑文本框（群发信息），再次确认按钮，取消按钮
       Button msg_cnfrm_btn = (Button)findViewById(R.id.entr_btn);
        msg_cnfrm_btn.setOnClickListener(new Button.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		if(msg_txt_edtxt.getText().toString().length()<=0||msg_txt_edtxt.getText().toString()==" ")
        		{
        			new AlertDialog.Builder(groupMessage.this)
        			.setTitle("error")
        			.setMessage("信息输入为空，请重新输入群发信息")
        			.setIcon(R.drawable.icon)
        			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							setResult(RESULT_OK);						
						}						
					}).show();
        		}
        		//启动messageConfirm
        		//向messageConfirm传递groupMessage
        		else
        		{
        			new AlertDialog.Builder(groupMessage.this)
        			.setTitle("请确定输入信息")
        			.setMessage(msg_txt_edtxt.getText().toString())
        			.setIcon(R.drawable.icon)
        			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							setResult(RESULT_OK);		
							Intent msg_cnfrm = new Intent();
		        			msg_cnfrm.putExtra("groupMessage",msg_txt_edtxt.getText().toString());
		        			msg_cnfrm.setClass(groupMessage.this, contactCheck.class);
		        			groupMessage.this.startActivity(msg_cnfrm);
						}						
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						//	setResult(RESULT_OK);		
							
						}						
					}).show();
        		
        			
        		}
        	}
	
        });
       final Button cncl_btn = (Button)findViewById(R.id.cncl_btn);
       cncl_btn.setOnClickListener(new Button.OnClickListener(){
       	@Override
       	public void onClick(View v){
       		finish();
       	}
	
       });
    }

	
    
}