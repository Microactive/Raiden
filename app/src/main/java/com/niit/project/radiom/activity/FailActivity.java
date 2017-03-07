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

/**
 * ����ʧ����ת�Ľ���
 * @author songhui
 *
 */
public class FailActivity extends Activity {
	private Button restart;
	private Button backToMain;
	private TextView showResult;
	private int currentTopScore;
	private int score;
	private SharedPreferences sp;
	private int round;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fail);

		restart = (Button) findViewById(R.id.restart);
		backToMain = (Button) findViewById(R.id.backToMain);
		showResult = (TextView) findViewById(R.id.showResult);

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sp.edit();

		currentTopScore = getIntent().getIntExtra("currentTopScore", 0);
		score = getIntent().getIntExtra("score", 0);
		round = getIntent().getIntExtra("round", 1);
		
		String result = "";
		//�Ƚ���ҷ����͵�ǰ��߷���
		if (currentTopScore > score) {
			result = "����ʧ��\n����Ŀǰ��߷�Ϊ" + currentTopScore + "\n��ķ���Ϊ" + score
					+ "\n����Ŭ����";
		} else if (currentTopScore == score) {
			result = "����ʧ�ܣ�������׷ƽ�˵�ǰ����߷֣���ķ���Ϊ" + score;
		} else {
			//�޸ĵ�ǰ�ؿ�����߷ּ�¼
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
			result = "����ʧ�ܣ�������ˢ������߷ּ�¼����ķ���Ϊ" + score + "\n���ͣ�";
		}
		//��ʾ���
		showResult.setText(result);
		restart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int round = getIntent().getIntExtra("round", 1);
				Log.v("round", "��FailActivity�еõ��Ĺ���Ϊ" + round);
				Intent intent = new Intent();
				switch (round) {
				case 1:// ��һ��ʧ�ܣ����¿�ʼ��һ��
					intent.setClass(FailActivity.this, GameActivity.class);
					intent.putExtra("round", round);
					FailActivity.this.startActivity(intent);
					FailActivity.this.finish();
					break;
				case 2:// �ڶ���ʧ�ܣ����¿�ʼ�ڶ���
					intent.setClass(FailActivity.this, GameActivity.class);
					intent.putExtra("round", round);
					FailActivity.this.startActivity(intent);
					FailActivity.this.finish();
					break;
				case 3:// ������ʧ�ܣ����¿�ʼ������
					intent.setClass(FailActivity.this, WinActivity.class);
					intent.putExtra("round", round);
					FailActivity.this.startActivity(intent);
					FailActivity.this.finish();
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
				intent.setClass(FailActivity.this, MainActivity.class);
				FailActivity.this.startActivity(intent);
				FailActivity.this.finish();
			}
		});
	}
}
