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
		//其他应用程序可读
		accessPreferences=getSharedPreferences("access",Context.MODE_WORLD_READABLE);
		//获取访问次数。默认为1
		int count=accessPreferences.getInt("count",1);
		//每次登录时显示访问次数信息
		Toast.makeText(MainActivity.this,"欢迎您，这是第"+count+"次访问!",Toast.LENGTH_LONG).show();
		//获取写入登录信息的Editor对象
		loginEditor=loginPreferences.edit();
		//获取写入访问信息的Editor对象
		accessEditor=accessPreferences.edit();
		//写入访问次数信息，每次自动加1
		accessEditor.putInt("count",++count);
		//提交写入的数据
		accessEditor.commit();
		//获取保存的用户信息
		userName=loginPreferences.getString("name",null);
	   //获取保存的密码信息
		userPsd=loginPreferences.getString("psd",null);
		//是否保存密码
		isSavedPsd=loginPreferences.getBoolean("isSavePsd",false);
		//是否自动登录
		isAutoLogin=loginPreferences.getBoolean("isAutoLogin", false);
		//如果自动登录为true
		if(isAutoLogin){
			//显示为欢迎界面
			this.setContentView(R.layout.activity_welcome);
			userInfo=(TextView)findViewById(R.id.userInfo);
			userInfo.setText("欢迎您:"+userName+",登录成功!");
		}else{
			//如果自动登录为false
			loadActivity();
		}
	}

	private void loadActivity() {
		// TODO Auto-generated method stub
		//设置界面为登录界面
		this.setContentView(R.layout.activity_main);
		login=(Button)findViewById(R.id.login);
		rememberPsdBox=(CheckBox)findViewById(R.id.rememberPsd);
		autoLoginBox=(CheckBox)findViewById(R.id.autoLogin);
		name=(EditText)findViewById(R.id.name);
		psd=(EditText)findViewById(R.id.psd);
		//如果获取的保存密码为true
		if(isSavedPsd){
			//设置密码框的值为保存的值
			psd.setText(userPsd);
			//显示用户名为保存的用户名
			name.setText(userName);
			//设置“保存密码”复选框为选中状态
			rememberPsdBox.setChecked(true);
		}
		login.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//写入用户名
				loginEditor.putString("name",name.getText().toString());
				//写入密码
				loginEditor.putString("psd",psd.getText().toString());
				loginEditor.putBoolean("isSavedPsd",rememberPsdBox.isChecked());
				loginEditor.putBoolean("isAutoLogin",autoLoginBox.isChecked());
				//提交写入的登录信息
				loginEditor.commit();
				//切换到欢迎界面
				MainActivity.this.setContentView(R.layout.activity_welcome);
				userInfo=(TextView)findViewById(R.id.userInfo);
				userInfo.setText("欢迎您:"+name.getText().toString()+",登录成功!");
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
