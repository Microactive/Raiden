package com.niit.project.radiom.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.niit.project.radiom.ObjectFactroy;
import com.niit.project.radiom.ObjectFactroy.CaseType;
import com.niit.project.radiom.ObjectFactroy.EnemyType;
import com.niit.project.radiom.ObjectFactroy.PlaneType;
import com.niit.project.radiom.activity.FailActivity;
import com.niit.project.radiom.activity.GameActivity;
import com.niit.project.radiom.activity.SuccessActivity;
import com.niit.project.radiom.activity.WinActivity;
import com.niit.project.radiom.music.MusicPlayer;
import com.niit.project.radiom.music.MusicPlayer.MusicType;
import com.niit.project.radiom.object.Background;
import com.niit.project.radiom.object.BaseBullet;
import com.niit.project.radiom.object.BaseBullet.BulletType;
import com.niit.project.radiom.object.BulletsCase;
import com.niit.project.radiom.object.Case;
import com.niit.project.radiom.object.EnemyPlane;
import com.niit.project.radiom.object.MedicineCase;
import com.niit.project.radiom.object.Player;
import com.niit.project.radiom.object.PlayerPlane;
import com.niit.project.radiom.object.Round;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	//Holder����
	private SurfaceHolder holder;
	//�����Ķ���
	private GameActivity context;
	
	/**
	 * ��Ļ��Ⱥ͸߶�
	 */
	private int screenWidth;
	private int screenHeight;
	
	/**
	 * һЩ״̬λ�ͼ�����
	 */
	private boolean gameIsRunning;//��Ϸ�Ƿ���������
	private boolean successed;//�Ƿ�ɹ�ͨ��
	private long planesFrameCount = 0;//���������л��͹������֡��������
	private boolean musicIsPaused;//�����Ƿ���ͣ
	private boolean gameIsBreaked = false;
	
	/**
	 * �ж�˫���¼��ı���
	 * */
	private long firClick;  
	private long secClick;
	private int count;
	
	/**
	 * ��Ϸ�е�һЩ����
	 */
	private ObjectFactroy factroy;//�����������ֶ���Ĺ�����
	private Canvas canvas;//��������
	private Background background;//��������
	private Round round;//�ؿ�����
	private Player player;//��Ҷ���
	private PlayerPlane playerPlane;//��ҷɻ�
	private List<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();//�л��б�
	private List<BaseBullet> playerBullets = new ArrayList<BaseBullet>();//��ҵ��ӵ��б�
	private List<BaseBullet> enemyBullets = new ArrayList<BaseBullet>();//���˵��ӵ��б�
	private List<Case> cases = new ArrayList<Case>();//�������б�
	private EnemyPlane boss1;
	private EnemyPlane boss2;
	private EnemyPlane boss3;
	
	/**
	 * һЩ���͵�����
	 * */
	private EnemyType[] enemyTypes = new EnemyType[]{
			EnemyType.simpleEnemy, EnemyType.smallEnemy1, EnemyType.smallEnemy2,
			EnemyType.smallEnemy3, EnemyType.smallEnemy4, EnemyType.smallEnemy5, 
			EnemyType.smallEnemy6, EnemyType.boss1, EnemyType.boss2, EnemyType.boss3};
	private BulletType[] bulletTypes = new BulletType[] {
			BulletType.bullet1, BulletType.bullet1_left, BulletType.bullet1_right,
			BulletType.bullet2,	BulletType.bullet2_left, BulletType.bullet2_right,
			BulletType.bullet3,	BulletType.bullet3_left, BulletType.bullet3_right,
			BulletType.bullet4,	BulletType.bullet4_left, BulletType.bullet4_right,
			BulletType.bullet5,	BulletType.bullet5_left, BulletType.bullet5_right,
			BulletType.bullet6,	BulletType.bullet6_left, BulletType.bullet6_right,
		};
	private CaseType[] caseTypes = new CaseType[] { 
			CaseType.medicineCase,
			CaseType.bulletsCase, CaseType.bulletsCase1, CaseType.bulletsCase2 };
	
	/**
	 * ���ڿ���ʱ��ĳ�Ա
	 */
	private TimerTask timerTask;
//	private Timer timer;
	private int tempTime = 10;

	
	//���ڿ������ֲ��ŵĳ�Ա
	private MusicPlayer musicPlayer;
	
	//��ǰ�ؿ�����߷ּ�¼
	private int currentTopScore;
	
	private SharedPreferences sp;

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		holder = getHolder();
		holder.addCallback(this);//��ӻص��ӿ�
		factroy = ((GameActivity)context).getFactroy();
		player = new Player("007", "����", 0);//��ʼ����Ҷ���
		round = ((GameActivity)context).getRound();//��ʼ���ؿ�����
		this.context = (GameActivity)context;//��ȡ�����Ķ���
		musicPlayer = new MusicPlayer(context);//��ʼ�����ֲ��Ŷ���
		//��ȡSharedPreference����
		sp = PreferenceManager.getDefaultSharedPreferences(context);
	}
		
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		holder = getHolder();
		holder.addCallback(this);//��ӻص��ӿ�
		factroy = ((GameActivity)context).getFactroy();
		player = new Player("007", "����", 0);//��ʼ����Ҷ���
		round = ((GameActivity)context).getRound();//��ʼ���ؿ�����
		this.context = (GameActivity)context;//��ȡ�����Ķ���
		musicPlayer = new MusicPlayer(context);//��ʼ�����ֲ��Ŷ���
		//��ȡSharedPreference����
		sp = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		/*
		 * ��ʼ��һЩ״̬λ
		 * */
		gameIsRunning = true;
		musicIsPaused = false;
		successed = true;
		
		//��ȡ��Ļ�Ŀ�Ⱥ͸߶�
		screenWidth = getWidth();
		screenHeight = getHeight();
		
		//��ȡ��ǰ�ؿ�����߷ּ�¼
		switch (round.getRoundNumber()) {
		case 1:
			currentTopScore = sp.getInt("round1TopScore", 0);
			break;
		case 2:
			currentTopScore = sp.getInt("round2TopScore", 0);
			break;
		case 3:
			currentTopScore = sp.getInt("round3TopScore", 0);
			break;
		default:
			break;
		}
		
		/*
		 * ��ʼ��ʱ
		 * */
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				gameIsRunning = false;
			}
		};
//		timer = new Timer();
//		timer.schedule(timerTask, round.getTime());
		
		
		//������ҷɻ�
		switch (round.getRoundNumber()) {
		case 1:
			playerPlane = factroy.createPlayerPlane(PlaneType.player1);
			break;
		case 2:
			playerPlane = factroy.createPlayerPlane(PlaneType.player2);
			break;
		case 3:
			playerPlane = factroy.createPlayerPlane(PlaneType.player3);
			break;
		}
		playerPlane.addBullet(BulletType.bullet2, 100000);
		
		//������ҷɻ���Ŀ������
		playerPlane.setDestX(screenWidth /2);
		playerPlane.setDestY(screenHeight -40);
		
		//���ű�������
		musicPlayer.playMusic(MusicType.background);
		
		//��ʼ����������
		background = factroy.createBackground(round.getBackgroundType());
		
		//�����߳�
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		gameIsRunning = false;
		releaseResource();
		//ֹͣ���ű�������
		musicPlayer.stopMusic(MusicType.background);
	}

	private void releaseResource() {
		// TODO Auto-generated method stub
		if (!playerBullets.isEmpty())
			playerBullets.clear();
		if (!enemyPlanes.isEmpty())
			enemyPlanes.clear();
		if (!enemyBullets.isEmpty())
			enemyBullets.clear();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (gameIsRunning) {
			//�����Ϸ��ͣ��ͬʱ��ͣ�������֣���������������
			if (this.context.isGameIsPaused()) {
				if (!musicIsPaused) {
					musicPlayer.pauseMusic(MusicType.background);
					musicIsPaused = true;
				}
				continue;
			}
			if (musicIsPaused) {
				musicPlayer.playMusic(MusicType.background);
				musicIsPaused = false;
			}
			
			int sleepTime = 50;//�߳�����ʱ�䣬��λΪ����
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//������������ʼ��ͼ
			canvas = holder.lockCanvas();
			//�����л���֡������������
			planesFrameCount ++;
			//�����л�
			createEnemyPlanes();
			//����boss
			switch (round.getRoundNumber()) {
			case 1:
				if (player.getScores() > 1000) {
					if (boss1 == null) {
						boss1 = factroy.createEnemyPlane(EnemyType.boss1);
						boss1.setBlood(1000);
						boss1.setAwardScores(500);
						enemyPlanes.add(boss1);
					}
				}
				break;
			case 2:
				if (player.getScores() > 1000 && player.getScores() <= 2000) {
					if (boss1 == null) {
						boss1 = factroy.createEnemyPlane(EnemyType.boss1);
						boss1.setBlood(3000);
						boss1.setAwardScores(500);
						enemyPlanes.add(boss1);
					}
				}
				else if (player.getScores() > 3000 && player.getScores() <= 4000) {
					if (boss2 == null) {
						boss2 = factroy.createEnemyPlane(EnemyType.boss2);
						boss2.setBlood(10000);
						boss2.setAwardScores(10000);
						enemyPlanes.add(boss2);
					}
				}
				break;
			case 3:
				if (player.getScores() > 1000 && player.getScores() <= 2000) {
					if (boss1 == null) {
						boss1 = factroy.createEnemyPlane(EnemyType.boss1);
						boss1.setBlood(1000);
						boss1.setAwardScores(500);
						enemyPlanes.add(boss1);
					}
				}
				else if ( player.getScores() > 3000 && player.getScores() <= 4000) {
					if (boss2 == null) {
						boss2 = factroy.createEnemyPlane(EnemyType.boss2);
						boss2.setBlood(3000);
						boss2.setAwardScores(1000);
						enemyPlanes.add(boss2);
					}
				}
				else if ( player.getScores() > 7000 && player.getScores() < 10000) {
					if (boss3 == null) {
						boss3 = factroy.createEnemyPlane(EnemyType.boss3);
						boss3.setBlood(5000);
						boss3.setAwardScores(30000);
						enemyPlanes.add(boss3);
					}
				}
				break;
			}
			
			//�����л��ӵ�
			createEnemyBullets();
			//��������ӵ�
			createPlayerBullets();
			//����������
			createCases();
			//�ƶ��������ƶ���������ҷɻ�������ӵ������ел������ел��ӵ�
			move(sleepTime);
			//�����������ݼ�
			minusCaseLife();
			//����ͼ�񣬰�����������ҷɻ�������ӵ������ел������ел��ӵ�
			if (canvas == null)
				return;
			if (gameIsBreaked)
				return;
			draw();
			//��ײ��⣬������ҷɻ������ел������ел��ӵ�����ײ������������ӵ������ел�����ײ
			detectCrash();
			//������ел���״̬��������Χ��Ѫ�������������Χ��Ѫ��ΪС��0��ֱ�ӻ��Ʊ�ըЧ�����Ƴ��˵л�
			detectEnemiesState();
			//���boss״̬
//			if (boss != null && boss.getBlood() <=0 ) {
//				gameIsRunning = false;
//				successed = true;
//			}
			
			if ( boss1 != null && boss1.getBlood() <= 0 ) {
				//���Ʊ�ըЧ��
				if (canvas == null)
					return;
				if (boss1.getBoomFrameCount() 
						< boss1.getBoomCount()) {
					boss1.drawBoom(canvas);
					boss1.addBoomFrameCount();
				}
				else {
					player.addScores(boss1.getAwardScores());
					//���boss������������Ч
					musicPlayer.playMusic(MusicType.boom1);
					enemyPlanes.remove(boss1);
					boss1 = null;
					if (round.getRoundNumber() == 1) {
						gameIsRunning = false;
						successed = true;
					}
				}
			}
			
			if ( boss2 != null && boss2.getBlood() <= 0 ) {
				//���Ʊ�ըЧ��
				if (canvas == null)
					return;
				if (boss2.getBoomFrameCount() 
						< boss2.getBoomCount()) {
					boss2.drawBoom(canvas);
					boss2.addBoomFrameCount();
				}
				else {
					player.addScores(boss2.getAwardScores());
					//���boss������������Ч
					musicPlayer.playMusic(MusicType.boom1);
					enemyPlanes.remove(boss2);
					boss2 = null;
					if (round.getRoundNumber() == 2) {
						gameIsRunning = false;
						successed = true;
					}
				}
			}
			
			if ( boss3 != null && boss3.getBlood() <= 0 ) {
				//���Ʊ�ըЧ��
				if (canvas == null)
					return;
				if (boss3.getBoomFrameCount() 
						< boss3.getBoomCount()) {
					boss3.drawBoom(canvas);
					boss3.addBoomFrameCount();
				}
				else {
					player.addScores(boss3.getAwardScores());
					//���boss������������Ч
					musicPlayer.playMusic(MusicType.boom1);
					enemyPlanes.remove(boss3);
					boss3 = null;
					if (round.getRoundNumber() == 3) {
						gameIsRunning = false;
						successed = true;
					}
				}
			}
			//������ел��ӵ���״̬��������Χ���Ƿ�ʹ�ã����������Χ���Ѿ���ʹ����ֱ���Ƴ��ӵ�
			detectEnemyBulletsState();
			//�����ҷɻ���״̬������Ѫ��,���Ѫ��С��0��ֱ�ӻ��Ʊ�ըЧ��������ʧ��
			detectPlayerState();
			//�������ӵ���״̬��������Χ���Ƿ�ʹ�ã����������Χ���Ѿ���ʹ����ֱ���Ƴ��ӵ�
			detectPlayerBulletsState();
			//��⹤�����״̬
			detectCasesState();
			//��ʾ���Ѫ���ͻ���
			show();
			//��������
			holder.unlockCanvasAndPost(canvas);
		}
		//��Ϸ����ʱ�ͷ���Դ
		releaseResource();
		//ȡ����ʱ��
//		timer.cancel();
		musicPlayer.stopMusic(MusicType.background);
		//����״̬��ת����Ӧ�Ľ���
		if (!gameIsBreaked) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (successed) {
				Intent intent = new Intent();
				//��������һ�أ�����ת���ɹ�����
				if (round.getRoundNumber() < 3) {
					intent.setClass(getContext(), SuccessActivity.class);
					intent.putExtra("round", round.getRoundNumber());
					intent.putExtra("score", player.getScores());
					intent.putExtra("currentTopScore", currentTopScore);
				}
				else
					intent.setClass(getContext(), WinActivity.class);
				getContext().startActivity(intent);
				((GameActivity)getContext()).finish();
				
			}
			else {
				//��ת��ʧ�ܽ���
				Intent intent = new Intent();
				intent.setClass(getContext(), FailActivity.class);
				intent.putExtra("round", round.getRoundNumber());
				intent.putExtra("score", player.getScores());
				intent.putExtra("currentTopScore", currentTopScore);
				getContext().startActivity(intent);
				((GameActivity)getContext()).finish();
			}
		}
	}
	
	/**
	 * ���й����������ʱ�����1
	 */
	private void minusCaseLife() {
		for (int i = 0; i < cases.size(); i ++) {
			cases.get(i).minusLife(1);
		}
	}

	/**
	 * ��⹤�����״̬
	 */
	private void detectCasesState() {
		for (int i = 0; i < cases.size(); i ++) {
			//����������Ѿ���ʹ�û����������ڽ���������ӹ������б����Ƴ�
			if ( cases.get(i).isUsed() || cases.get(i).getLife() <= 0 ) {
				//�Ƴ����ӵ�
				cases.remove(i);
				
			}
			//��������䳬����Χ������ӹ������б����Ƴ�
			else if ( cases.get(i).isOverRange(0, 320, 0, 450) ) {
				cases.remove(i);
			}
		}
	}

	/**
	 * ���������䲢��ӵ��������б���
	 */
	private void createCases() {
		//�ӹؿ����õл�����-�л��������л�����-���ּ����ӳ��
		Map<CaseType, Integer> amountMap = round.getCaseNumberMap();
		Map<CaseType, Integer> delayMap = round.getCaseDelayMap();
		Case baseCase = null;
		
		for (int i = 0; i < caseTypes.length; i ++) {
			if( amountMap.containsKey(caseTypes[i]))
				if ( planesFrameCount % delayMap.get(caseTypes[i]) == 1 
				&& planesFrameCount > tempTime) {
					for (int j = 0; j < amountMap.get(caseTypes[i]); j ++) {
						baseCase = factroy.createCase(caseTypes[i]);
						cases.add(baseCase);
					}
				}
		}
	}

	/**
	 * չʾ��ҷ������ؿ�����Ѫ���ȵ�
	 */
	private void show() {
		// TODO Auto-generated method stub
		if (canvas == null)
			return;
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(16);
		canvas.drawText("��ǰ�ؿ���" + round.getRoundNumber(), 180, 20, paint);
		canvas.drawText("Ŀǰ��߷֣�" + currentTopScore, 180, 40, paint);
		canvas.drawText("���Ѫ����" + playerPlane.getBlood(), 180, 60, paint);
		canvas.drawText("��һ��֣�" + player.getScores(), 180, 80, paint);
	}

	/**
	 * ��������ӵ�
	 */
	private void createPlayerBullets() {
		// TODO Auto-generated method stub
		playerPlane.addShootFrameCount();
		
		for (int i = 0; i < bulletTypes.length; i ++) {
			if (playerPlane.getBulletsMap().containsKey(bulletTypes[i])) {
				if ( playerPlane.canShoot(bulletTypes[i]) ) {
					playerBullets.add(playerPlane.createBullet(bulletTypes[i]));
					//��Ӧ���ӵ�������1
					playerPlane.cutBullets(bulletTypes[i], 1);
				}
			}
		}
	}

	/**
	 * �������ӵ���״̬
	 */
	private void detectPlayerBulletsState() {
		for (int i = 0; i < playerBullets.size(); i ++) {
			//����ӵ��Ѿ���ʹ�ã�����ӵл��ӵ��б����Ƴ�
			if ( playerBullets.get(i).isUsed() ) {
				//�Ƴ����ӵ�
				playerBullets.remove(i);
				
			}
			//����л��ӵ�������Χ������ӵл��б����Ƴ�
			else if ( playerBullets.get(i).isOverRange(0, 320, 0, 450) ) {
				playerBullets.remove(i);
			}
		}
	}

	/**
	 * ������ел��ӵ���״̬��������Χ���Ƿ�ʹ�ã�
	 * ���������Χ���Ѿ���ʹ����ֱ���Ƴ��ӵ�
	 */
	private void detectEnemyBulletsState() {
		for (int i = 0; i < enemyBullets.size(); i ++) {
			//����ӵ��Ѿ���ʹ�ã�����ӵл��ӵ��б����Ƴ�
			if ( enemyBullets.get(i).isUsed() ) {
				//�Ƴ����ӵ�
				enemyBullets.remove(i);
			}
			//����л��ӵ�������Χ������ӵл��б����Ƴ�
			else if ( enemyBullets.get(i).isOverRange(0, screenWidth, 0, screenHeight) ) {
				enemyBullets.remove(i);
			}
		}
	}

	/**
	 * �����ҵ�״̬
	 */
	private void detectPlayerState() {
		//����ɻ������٣�����Ʊ�ըЧ����������ӵл��б����Ƴ�
		if ( playerPlane.getBlood() <= 0 ) {
			//���Ʊ�ըЧ��
			if (canvas == null)
				return;
			if (playerPlane.getBoomFrameCount() 
					< playerPlane.getBoomCount()) {
				playerPlane.drawBoom(canvas);
				playerPlane.addBoomFrameCount();
			}
			else {
				gameIsRunning = false;
				successed = false;
			}
		}			
	}

	/**
	 * ������ел���״̬��������Χ��Ѫ����
	 * ���������Χ��Ѫ��ΪС��0��ֱ�ӻ��Ʊ�ըЧ�����Ƴ��˵л�
	 */
	private void detectEnemiesState() {
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			//����л������٣�����Ʊ�ըЧ����������ӵл��б����Ƴ�
			if ( enemyPlanes.get(i).getBlood() <= 0 ) {
				//���Ʊ�ըЧ��
				if (canvas == null)
					return;
				if (enemyPlanes.get(i).getBoomFrameCount() 
						< enemyPlanes.get(i).getBoomCount()) {
					enemyPlanes.get(i).drawBoom(canvas);
					enemyPlanes.get(i).addBoomFrameCount();
				}
				else {
					player.addScores(enemyPlanes.get(i).getAwardScores());
					//���boss������������Ч
					if (enemyPlanes.get(i).getType() == EnemyType.boss1
							|| enemyPlanes.get(i).getType() == EnemyType.boss1
							|| enemyPlanes.get(i).getType() == EnemyType.boss1) {
						musicPlayer.playMusic(MusicType.boom1);
					}
					enemyPlanes.remove(i);
				}
			}
			//����л�������Χ������ӵл��б����Ƴ�
			else if ( enemyPlanes.get(i).isOverRange(0, 0, 320, 450) ) {
				enemyPlanes.remove(i);
			}
		}
		
	}

	/**
	 * �����ײ�¼�
	 */
	private void detectCrash() {
		// TODO Auto-generated method stub
		//�����ҷɻ��͵л�����ײ
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			if ( enemyPlanes.get(i).detectCrash(playerPlane) ) {
				switch (enemyPlanes.get(i).getType()) {
				//�򵥵л�
				case smallEnemy1:
					//ײ���¼���������ҷɻ���20��Ѫ��
					playerPlane.cutBlood(20);
					//ײ���¼��������з��ɻ���20��Ѫ��
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy2:
					//ײ���¼���������ҷɻ���20��Ѫ��
					playerPlane.cutBlood(20);
					//ײ���¼��������з��ɻ���20��Ѫ��
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy3:
					//ײ���¼���������ҷɻ���20��Ѫ��
					playerPlane.cutBlood(20);
					//ײ���¼��������з��ɻ���20��Ѫ��
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy4:
					//ײ���¼���������ҷɻ���20��Ѫ��
					playerPlane.cutBlood(20);
					//ײ���¼��������з��ɻ���20��Ѫ��
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy5:
					//ײ���¼���������ҷɻ���20��Ѫ��
					playerPlane.cutBlood(20);
					//ײ���¼��������з��ɻ���20��Ѫ��
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy6:
					//ײ���¼���������ҷɻ���20��Ѫ��
					playerPlane.cutBlood(20);
					//ײ���¼��������з��ɻ���20��Ѫ��
					enemyPlanes.get(i).cutBlood(20);
					break;
				case boss1:
					//ײ���¼���������ҷɻ���100��Ѫ��
					playerPlane.cutBlood(100);
					//ײ���¼��������з��ɻ���100��Ѫ��
					enemyPlanes.get(i).cutBlood(10);
					break;
				case boss2:
					//ײ���¼���������ҷɻ���100��Ѫ��
					playerPlane.cutBlood(300);
					//ײ���¼��������з��ɻ���100��Ѫ��
					enemyPlanes.get(i).cutBlood(10);
					break;
				case boss3:
					//ײ���¼���������ҷɻ���100��Ѫ��
					playerPlane.cutBlood(500);
					//ײ���¼��������з��ɻ���100��Ѫ��
					enemyPlanes.get(i).cutBlood(10);
					break;
				default:
					break;
				}
				
			}
		}
		//�����ҷɻ��͵л��ӵ�����ײ
		for (int i = 0; i < enemyBullets.size(); i ++) {
			if (playerPlane.detectCrash(enemyBullets.get(i))) {
				//�����ײ��������ҷɻ���Ѫ
				playerPlane.cutBlood(enemyBullets.get(i).getHarm());
				//�����ײ�������л��ӵ���ʹ��
				enemyBullets.get(i).setUsed(true);
			}
		}
		//���л�������ӵ�����ײ
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			for (int j = 0; j < playerBullets.size(); j ++) {
				if ( enemyPlanes.get(i).detectCrash(playerBullets.get(j)) ) {
					enemyPlanes.get(i).cutBlood(playerBullets.get(j).getHarm());
					playerBullets.get(j).setUsed(true);
				}
			}
		}
		//�����ҷɻ��͹��������ײ
		for (int i = 0; i < cases.size(); i ++) {
			CaseType type = cases.get(i).getType();
			if ( cases.get(i).detectCrash(playerPlane) ) {
				switch (type) {
				case medicineCase:
					cases.get(i).setUsed(true);
					playerPlane.addBlood(((MedicineCase)cases.get(i)).getBloodAmount());
//					Log.v("crash", "��ײ��������ҷɻ���ҽҩ����ײ��");
					break;
				default:
					cases.get(i).setUsed(true);
					Map<BulletType, Integer> map = ((BulletsCase)cases.get(i)).getBulletsMap();
					for (int j = 0; j < bulletTypes.length; j ++) {
						if (map.containsKey(bulletTypes[j])) {
							playerPlane.addBullet(bulletTypes[j], map.get(bulletTypes[j]));
							Log.v("crash", "��ɻ��������" + bulletTypes[j] + map.get(bulletTypes[j]) +"��");
						}
							
					}
//					if (map.containsKey(BulletType.bullet4)) {
//						playerPlane.addBullet(BulletType.bullet4, map.get(BulletType.bullet4))
//					}
					Log.v("crash", "��ײ��������ҷɻ��͵�ҩ����ײ��,��ʱ��ҩ���bulletMapΪ" + map);
					break;
				}
				//������Ч
				musicPlayer.playMusic(MusicType.getAward);
			}
		}
	}

	/**
	 * �ƶ���Ļ�е�������Ʒ
	 * @param time
	 */
	private void move(int time) {
		// TODO Auto-generated method stub
		//�ƶ�����
		background.move();
		//�ƶ���ҷɻ�
		playerPlane.move(time);
		//�ƶ���ҷɻ��ӵ�
		for (int i = 0; i < playerBullets.size(); i ++) {
			playerBullets.get(i).move(time);
		}
		//�ƶ��л�
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			enemyPlanes.get(i).styleMove(time);
		}
		//�ƶ��л��ӵ�
		for (int i = 0; i < enemyBullets.size(); i ++) {
			enemyBullets.get(i).move(time);
			if (i == 0) {
//				Log.v("tag", "�ӵ��ƶ���ɣ���ǰ����x��" + enemyBullets.get(i).getCurrentX() + "y:" + enemyBullets.get(i).getCurrentY());
			}
		}
		//�ƶ�����
		for (int i = 0; i < cases.size(); i ++) {
			cases.get(i).move(time);
		}
	}

	/**
	 * �����л�
	 */
	private void createEnemyPlanes() {
		// TODO Auto-generated method stub
		//�ӹؿ����õл�����-�л��������л�����-���ּ����ӳ��
		Map<EnemyType, Integer> amountMap = round.getEnemyNumberMap();
		Map<EnemyType, Integer> delayMap = round.getEnemyDelayMap();
		EnemyPlane enemyPlane = null;
		
		for (int i = 0; i < enemyTypes.length; i ++) {
			if( amountMap.containsKey(enemyTypes[i]))
				if ( planesFrameCount % delayMap.get(enemyTypes[i]) == 1
					&& planesFrameCount > tempTime) {
					for (int j = 0; j < amountMap.get(enemyTypes[i]); j ++) {
						enemyPlane = factroy.createEnemyPlane(enemyTypes[i]);
						enemyPlanes.add(enemyPlane);
					}
				}
		}
	}

	/**
	 * �����л����ӵ�
	 */
	private void createEnemyBullets() {
		// TODO Auto-generated method stub
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			enemyPlanes.get(i).addShootFrameCount();
			for (int j = 0; j < bulletTypes.length; j ++) {
				if (enemyPlanes.get(i).getBulletsMap().containsKey(bulletTypes[j])) {
					if ( enemyPlanes.get(i).canShoot(bulletTypes[j]) ) {
						enemyBullets.add(enemyPlanes.get(i).createBullet(bulletTypes[j]));
					}
				}
			}
		}
		
	}

	/**
	 * ����ͼ��
	 */
	private void draw() {
		// TODO Auto-generated method stub
		if (canvas == null)
			return;
		background.drawSelf(canvas);
		//������ҵ��ӵ�
		for (int i = 0; i < playerBullets.size(); i ++) {
			if ( ! (playerBullets.get(i).isUsed()) )
				playerBullets.get(i).drawSelf(canvas);
		}
		playerPlane.drawSelf(canvas);
		//���Ƶл��ӵ�
		for (int i = 0; i < enemyBullets.size(); i ++) {
			if ( ! (enemyBullets.get(i).isUsed()) )
				enemyBullets.get(i).drawSelf(canvas);
		}
		//���Ƶл�
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			if (enemyPlanes.get(i).getBlood() > 0)
				enemyPlanes.get(i).drawSelf(canvas);
		}
		
		//���ƹ�����
		for (int i = 0; i < cases.size(); i ++) {
			if (!cases.get(i).isUsed() && cases.get(i).getLife() > 0)
				cases.get(i).drawSelf(canvas);
		}
	}

	/**
	 * ��������
	 * ���ел���1000��Ѫ��
	 * ���ел��ӵ���ʧ
	 */
	private void createBigBoom() {
		// TODO Auto-generated method stub
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			enemyPlanes.get(i).cutBlood(1000);
		}
		enemyBullets.clear();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			//�ж��Ƿ�Ϊ˫���¼�
			count++;  
            if(count == 1){  
                firClick = System.currentTimeMillis();  
            } 
            else if (count == 2){  
                secClick = System.currentTimeMillis();  
                if(secClick - firClick < 1000){  
                    //˫���¼�  
                	if (playerPlane.getBlood() >= 500) {
                		createBigBoom();  
                		playerPlane.cutBlood(100);
                	}
                } 
                count = 0;  
                firClick = 0;  
                secClick = 0; 
            }
             
		case MotionEvent.ACTION_MOVE:
			//��¼Ŀ��λ��
			int touchX = (int)event.getX();
			int touchY = (int)event.getY();
			if(touchX > screenWidth - 22)
				playerPlane.setDestX(screenWidth -22);
			else if (touchX < 22)
				playerPlane.setDestX(22);
			else
				playerPlane.setDestX(touchX);
			if(touchY > screenHeight)
				playerPlane.setDestY(screenHeight- 35 - 25);
			else if (touchY < 70 + 25)
				playerPlane.setDestY(35);
			else
				playerPlane.setDestY(touchY - 35 - 25);
			break;
		}
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			gameIsBreaked = true;
//			timer.cancel();
		}
		return super.onKeyDown(keyCode, event);
	}
}

	
