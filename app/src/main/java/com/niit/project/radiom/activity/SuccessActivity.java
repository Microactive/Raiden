package com.niit.project.radiom.activity;

import com.niit.project.radiom.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessActivity extends Activity {
	private Button nextRound;
	private Button backToMain;
	private TextView showResult;
	private int currentTopScore;
	private int score;
	private SharedPreferences sp;
	private int round;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success);
		
		nextRound = (Button) findViewById(R.id.nextRound);
		backToMain = (Button) findViewById(R.id.backToMain);
		showResult = (TextView) findViewById(R.id.showResult);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sp.edit();
		
		currentTopScore = getIntent().getIntExtra("currentTopScore", 0);
		score = getIntent().getIntExtra("score", 0);
		round = getIntent().getIntExtra("round", 1);
		
		Log.v("score", "sharedpreference round1:" + sp.getInt("round1TopScore", 0)
				+ "round2:" + sp.getInt("round2TopScore", 0)
				+ "round3:" + sp.getInt("round3TopScore", 0)
				+ "currentTopScore" + currentTopScore
				+ "score" + score
				+ "round" + round);
		
		String result = "";
		if (currentTopScore > score) {
			result = "���سɹ�\n����Ŀǰ��߷�Ϊ" + currentTopScore + "\n��ķ���Ϊ" + score + "\n����Ŭ����";
		}
		else if (currentTopScore == score) {
			result = "���سɹ�����׷ƽ�˵�ǰ����߷֣���ķ���Ϊ" + score + "\n��ϲ��";
		}
		else {
			switch (round) {
			case 1:
				editor.putInt("round1TopScore", score);
				editor.commit();
				break;
			case 2:
				editor.putInt("round2TopScore", score);
				editor.commit();
				break;
			case 3:
				editor.putInt("round3TopScore", score);
				editor.commit();
				break;
			}
			result = "���سɹ���������ˢ������߷ּ�¼����ķ���Ϊ" + score + "\n��ϲ��";
		}
		showResult.setText(result);
		
		nextRound.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int round = getIntent().getIntExtra("round", 1);
				Log.v("round", "��SucceActivity�еõ��Ĺ���Ϊ" + round);
				Intent intent = new Intent();
				switch (round) {
				case 1://ͨ����һ�أ�ǰ���ڶ���
					intent.setClass(SuccessActivity.this, GameActivity.class);
					intent.putExtra("round", round + 1);
					SuccessActivity.this.startActivity(intent);
					SuccessActivity.this.finish();
					break;
				case 2://ͨ���ڶ��أ�ǰ��������
					intent.setClass(SuccessActivity.this, GameActivity.class);
					intent.putExtra("round", round + 1);
					SuccessActivity.this.startActivity(intent);
					SuccessActivity.this.finish();
					break;
				case 3://ͨ�������أ�ͨ��
//					intent.setClass(SuccessActivity.this, WinActivity.class);
////					intent.putExtra("round", round + 1);
//					SuccessActivity.this.startActivity(intent);
//					SuccessActivity.this.finish();
					break;
				default:
					break;
				}
			}
		});
		
		backToMain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SuccessActivity.this, MainActivity.class);
				SuccessActivity.this.startActivity(intent);
				SuccessActivity.this.finish();
			}
		});
	}
}
