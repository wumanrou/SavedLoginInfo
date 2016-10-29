package com.example.savelogininfo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SharedPreferences loginPreferences, accessPreferences;
	private Boolean isSavedPsd,isAutoLogin;
	private Button login;
	private EditText name,psd;
	private CheckBox rememberPsdBox, autoLoginBox;
	SharedPreferences.Editor loginEditor,accessEditor;
	private TextView userInfo;
	String userPsd,userName;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginPreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
		//����Ӧ�ó���ɶ�
		accessPreferences=getSharedPreferences("access",Context.MODE_WORLD_READABLE);
		//��ȡ���ʴ�����Ĭ��Ϊ1
		int count=accessPreferences.getInt("count",1);
		//ÿ�ε�¼ʱ��ʾ���ʴ�����Ϣ
		Toast.makeText(MainActivity.this,"��ӭ�������ǵ�"+count+"�η���!",Toast.LENGTH_LONG).show();
		//��ȡд���¼��Ϣ��Editor����
		loginEditor=loginPreferences.edit();
		//��ȡд�������Ϣ��Editor����
		accessEditor=accessPreferences.edit();
		//д����ʴ�����Ϣ��ÿ���Զ���1
		accessEditor.putInt("count",++count);
		//�ύд�������
		accessEditor.commit();
		//��ȡ������û���Ϣ
		userName=loginPreferences.getString("name",null);
	   //��ȡ�����������Ϣ
		userPsd=loginPreferences.getString("psd",null);
		//�Ƿ񱣴�����
		isSavedPsd=loginPreferences.getBoolean("isSavePsd",false);
		//�Ƿ��Զ���¼
		isAutoLogin=loginPreferences.getBoolean("isAutoLogin", false);
		//����Զ���¼Ϊtrue
		if(isAutoLogin){
			//��ʾΪ��ӭ����
			this.setContentView(R.layout.activity_welcome);
			userInfo=(TextView)findViewById(R.id.userInfo);
			userInfo.setText("��ӭ��:"+userName+",��¼�ɹ�!");
		}else{
			//����Զ���¼Ϊfalse
			loadActivity();
		}
	}

	private void loadActivity() {
		// TODO Auto-generated method stub
		//���ý���Ϊ��¼����
		this.setContentView(R.layout.activity_main);
		login=(Button)findViewById(R.id.login);
		rememberPsdBox=(CheckBox)findViewById(R.id.rememberPsd);
		autoLoginBox=(CheckBox)findViewById(R.id.autoLogin);
		name=(EditText)findViewById(R.id.name);
		psd=(EditText)findViewById(R.id.psd);
		//�����ȡ�ı�������Ϊtrue
		if(isSavedPsd){
			//����������ֵΪ�����ֵ
			psd.setText(userPsd);
			//��ʾ�û���Ϊ������û���
			name.setText(userName);
			//���á��������롱��ѡ��Ϊѡ��״̬
			rememberPsdBox.setChecked(true);
		}
		login.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//д���û���
				loginEditor.putString("name",name.getText().toString());
				//д������
				loginEditor.putString("psd",psd.getText().toString());
				loginEditor.putBoolean("isSavedPsd",rememberPsdBox.isChecked());
				loginEditor.putBoolean("isAutoLogin",autoLoginBox.isChecked());
				//�ύд��ĵ�¼��Ϣ
				loginEditor.commit();
				//�л�����ӭ����
				MainActivity.this.setContentView(R.layout.activity_welcome);
				userInfo=(TextView)findViewById(R.id.userInfo);
				userInfo.setText("��ӭ��:"+name.getText().toString()+",��¼�ɹ�!");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
