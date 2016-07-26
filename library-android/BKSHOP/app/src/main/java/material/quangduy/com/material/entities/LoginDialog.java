package material.quangduy.com.material.entities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import material.quangduy.com.material.R;

public class LoginDialog extends Dialog implements
		View.OnClickListener {
	private Activity c;
	private Button btn_ok;
	private String text;
	private TextView mess;

	public LoginDialog(Activity a, String s) {
		super(a);
		this.c = a;
		this.text = s;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logindialog_layout);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		mess = (TextView) findViewById(R.id.mess);
		mess.setText(text);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_ok:
			dismiss();
		}
	}

}
