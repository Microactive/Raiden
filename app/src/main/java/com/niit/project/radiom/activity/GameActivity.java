package com.niit.project.radiom.activity;

import com.niit.project.radiom.ObjectFactroy;
import com.niit.project.radiom.R;
import com.niit.project.radiom.ObjectFactroy.RoundType;
import com.niit.project.radiom.object.Round;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GameActivity extends Activity {
	private Round round;//�ؿ�����
	private ObjectFactroy factroy;//�����࣬���ڴ����ؿ�����
	private boolean gameIsPaused = false;//��Ϸ��ͣ�ı�־λ
	private Button pauseStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		factroy = new ObjectFactroy(this, 318, 455);
		
		//��ȡ��ǰ�Ĺؿ�����Ȼ�󴴽���صĹؿ�����
		int roundInt = getIntent().getIntExtra("round", 1);
		Log.v("round", "��GameActivity�еõ��Ĺ���Ϊ" + roundInt);
		switch (roundInt) {
		case 1:
			round = factroy.createRound(RoundType.round1);
			break;
		case 2:
			round = factroy.createRound(RoundType.round2);
			break;
		case 3:
			round = factroy.createRound(RoundType.round3);
			break;
		}
		//���ò����ļ�
		setContentView(R.layout.activity_game);		
		
		pauseStart = (Button) findViewById(R.id.pause_start);
		gameIsPaused = false;
		//Ϊ��ͣ/��ʼ��ť��Ӽ�����
		pauseStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (gameIsPaused) {
					gameIsPaused = false;
					pauseStart.setBackgroundResource(R.drawable.pause);
				}
				else {
					gameIsPaused = true;
					pauseStart.setBackgroundResource(R.drawable.start);
				}
			}
		});
	}

	/*
	 * һЩgetter��setter����
	 * */
	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public ObjectFactroy getFactroy() {
		return factroy;
	}

	public void setFactroy(ObjectFactroy factroy) {
		this.factroy = factroy;
	}

	public boolean isGameIsPaused() {
		return gameIsPaused;
	}

	public void setGameIsPaused(boolean gameIsPaused) {
		this.gameIsPaused = gameIsPaused;
	}
	
}
