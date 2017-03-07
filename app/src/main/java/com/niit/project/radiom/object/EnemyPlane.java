package com.niit.project.radiom.object;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.niit.project.radiom.ObjectFactroy.EnemyType;
import com.niit.project.radiom.object.BaseBullet.BulletType;

/**
 * �л��࣬ͨ��ʵ��������࣬���Բ�����ͬ���͵ĵл���
 * ��С�͵л���boss�л��ȵ�
 * @author songhui
 *
 */
public class EnemyPlane extends BasePlane {
	public static enum MoveStyle { left_right, line, slash, pos_slash, still };
	private int awardScores;//�л�������֮��Ľ�������
	private EnemyType type;//�л������ͣ���boss1
	private MoveStyle moveStyle;
	private int step;

	
	/**
	 * ���캯����ͨ������һ�ų�ͼ������������ʵ������
	 * ��Ҫͨ��һ��ͼ�����Ʊ�ըЧ����ʱ����ô˹��캯��
	 * @param imagePath
	 * @param boomImagePath
	 * @param boomCount
	 * @param assetManager
	 * @param speed
	 * @param currentX
	 * @param currentY
	 * @param destX
	 * @param destY
	 * @param blood
	 * @param boomFrameCount
	 * @param awardScores
	 * @param type
	 */
	public EnemyPlane(String imagePath, String boomImagePath, int boomCount, 
			AssetManager assetManager, float speed, int currentX, int currentY,
			int destX, int destY, int blood, int boomFrameCount, int awardScores, EnemyType type) {
		super(imagePath, boomImagePath, boomCount, assetManager, speed, currentX, currentY,
				destX, destY, blood, boomFrameCount);
		this.awardScores = awardScores;
		this.type = type;
	}

	/*
	 * һЩgetter��setter����
	 * */
	public int getAwardScores() {
		return awardScores;
	}

	public void setAwardScores(int awardScores) {
		this.awardScores = awardScores;
	}

	public EnemyType getType() {
		return type;
	}

	public void setType(EnemyType type) {
		this.type = type;
	}

	public MoveStyle getMoveStyle() {
		return moveStyle;
	}

	public void setMoveStyle(MoveStyle moveStyle) {
		this.moveStyle = moveStyle;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	@Override
	public void move(int time, int destX, int destY) {
		double distanceX = destX - currentX;//��ǰλ�ú�Ŀ��λ�õ�X����룬���з�����
		double distanceY = destY - currentY;//��ǰλ�ú�Ŀ��λ�õ�Y����룬���з�����
		//��ǰλ�ú�Ŀ��λ�õ�ֱ�߾���
		double distance = Math.sqrt(distanceX*distanceX + distanceY*distanceY);
		//���һ���ƶ����ٶȴ��ڵ�ǰλ�ú�Ŀ��λ�õ�ֱ�߾��룬ֱ�ӽ���ǰλ�ø���ΪĿ��λ��
		if (distance <= speed * time) {
			currentX = destX;
			currentY = destY;
		}
		else {//���һ���ƶ����ٶ�С�ڵ�ǰλ�ú�Ŀ��λ�õ�ֱ�߾��룬����µ�ǰλ��
			currentX = currentX + distanceX * speed * time / distance;
			currentY = currentY + distanceY * speed * time / distance;
		}
	}

	@Override
	public void move(int time) {
		double distanceX = destX - currentX;//��ǰλ�ú�Ŀ��λ�õ�X����룬���з�����
		double distanceY = destY - currentY;//��ǰλ�ú�Ŀ��λ�õ�Y����룬���з�����
		//��ǰλ�ú�Ŀ��λ�õ�ֱ�߾���
		double distance = Math.sqrt(distanceX*distanceX + distanceY*distanceY);
		//���һ���ƶ����ٶȴ��ڵ�ǰλ�ú�Ŀ��λ�õ�ֱ�߾��룬ֱ�ӽ���ǰλ�ø���ΪĿ��λ��
		if (distance <= speed * time) {
			currentX = destX;
			currentY = destY;
		}
		else {//���һ���ƶ����ٶ�С�ڵ�ǰλ�ú�Ŀ��λ�õ�ֱ�߾��룬����µ�ǰλ��
			currentX = currentX + distanceX * speed * time / distance;
			currentY = currentY + distanceY * speed * time / distance;
		}
	}

	public void styleMove (int time) {
		switch (moveStyle) {
		case left_right:
			if (currentX < 20 || currentX > 300)
				step = -step;
			currentX += step;
			break;
		case line:
			currentY += step;
			break;
		case slash:
			currentX += step;
			currentY += step;
			break;
		case pos_slash:
			currentX -= step;
			currentY -= step;
		case still:
			break;
		}
	}
	
	@Override
	public void drawSelf(Canvas canvas) {
		//��ȡbitmap�Ŀ�Ⱥ͸߶�
		int bitmapHeight = bitmap.getHeight();
		int bitmapWidth= bitmap.getWidth();
		
		//����ͼƬ��Ҫ������������
		Rect src = new Rect(0, 0, bitmapWidth, bitmapHeight);
		//������Ļ��Ҫ���ͼƬ������
		Rect dst = new Rect((int)(currentX - bitmapWidth/2),
				(int)(currentY - bitmapHeight/2), 
				(int)(currentX + bitmapWidth/2), 
				(int)(currentY + bitmapHeight/2));
		
		//�������˳��ǿ��ܻ����canvasΪ�յ����
		if (canvas == null)
			return;
		
        //����bitmap������ͼ��
		canvas.drawBitmap(bitmap, src, dst, null);
	}

	@Override
	public void drawBoom(Canvas canvas) {
		//��ȡbitmap�Ŀ�Ⱥ͸߶�
		int bitmapHeight = boomBitmap.getHeight();
		int bitmapWidth= boomBitmap.getWidth();
		
		//����ͼƬ��Ҫ������������
		Rect src = new Rect(boomFrameCount * bitmapWidth / boomCount, 0, 
				(boomFrameCount + 1) * bitmapWidth / boomCount, bitmapHeight);
		//������Ļ��Ҫ���ͼƬ������
		Rect dst = new Rect((int)(currentX - bitmapWidth/(2 * boomCount)),
				(int)(currentY - bitmapWidth/(2 * boomCount)), 
				(int)(currentX + bitmapWidth/(2 * boomCount)), 
				(int)(currentY + bitmapWidth/(2 * boomCount)));
		
		//�������˳��ǿ��ܻ����canvasΪ�յ����
		if (canvas == null)
			return;
		
        //����bitmap������ͼ��
		canvas.drawBitmap(boomBitmap, src, dst, null);
	}
	
	@Override
	public boolean canShoot(BulletType type) {
		// TODO Auto-generated method stub
		if ( getShootFrameCount() % bulletDelayMap.get(type).longValue() == 0 
				&& bulletsMap.get(type).intValue() > 0 )
			return true;
		else
			return false;
	}

	@Override
	public BaseBullet createBullet(BulletType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case bullet1:
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX, currentY, currentX, 480, 20, false);
		case bullet1_left://����λ��Ϊ���
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX - 40, currentY, currentX - 120, 480, 20, false);
		case bullet1_right://����λ��Ϊ�ұ�
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX + 40, currentY, currentX + 120, 480, 20, false);
		case bullet2:
			return new Bullet("img/bullet2.png", assetManager, 0.1, currentX, currentY, currentX, 480, 20, false);
		case bullet2_left://����λ��Ϊ���
			return new Bullet("img/bullet2.png", assetManager, 0.1f, currentX - 40, currentY, currentX - 60, 480, 20, false);
		case bullet2_right://����λ��Ϊ�ұ�
			return new Bullet("img/bullet2.png", assetManager, 0.1f, currentX + 40, currentY, currentX + 60, 480, 20, false);
		case bullet3:
			return new Bullet("img/bullet3.png", assetManager, 0.1f, currentX, currentY, currentX, 480, 100, false);		
		case bullet3_left:
			return new Bullet("img/bullet3.png", assetManager, 0.1f, currentX - 10, currentY, currentX - 40, 480, 100, false);
		case bullet3_right:
			return new Bullet("img/bullet3.png", assetManager, 0.1f, currentX + 10, currentY, currentX + 40, 480, 100, false);
		case bullet4:
			return new Bullet("img/bullet4.png", assetManager, 0.1f, currentX, currentY, currentX, 480, 100, false);		
		case bullet4_left:
			return new Bullet("img/bullet4.png", assetManager, 0.1f, currentX - 10, currentY, currentX - 40, 480, 100, false);
		case bullet4_right:
			return new Bullet("img/bullet4.png", assetManager, 0.1f, currentX + 10, currentY, currentX + 40, 480, 100, false);
		default:
			return null;
		}
	}

	@Override
	public boolean isOverRange(int left, int top, int right, int bottom) {
		if (currentX <= left
				|| currentX >= right
				|| currentY <= top
				|| currentY >= bottom)
			return true;
		return false;
	}

	@Override
	public boolean detectCrash(BasePlane plane) {
		//�л�����ҷɻ���X��������ֵ
		double distanceX = Math.abs(plane.getCurrentX() - currentX);
		//�л�����ҷɻ���Y��������ֵ
		double distanceY = Math.abs(plane.getCurrentY() - currentY);
		if (((bitmap.getWidth() + plane.getBitmap().getWidth())/2 >= distanceX)
				&&
				((bitmap.getHeight() + plane.getBitmap().getHeight())/2 >= distanceY))
			return true;
		else
			return false;
	}

	@Override
	public boolean detectCrash(BaseBullet bullet) {
		//�л�������ӵ���X��������ֵ
		double distanceX = Math.abs(bullet.getCurrentX() - currentX);
		//�л�������ӵ���Y��������ֵ
		double distanceY = Math.abs(bullet.getCurrentY() - currentY);
		if (((bitmap.getWidth() + bullet.getBitmap().getWidth())/2 >= distanceX)
				&&
				((bitmap.getHeight() + bullet.getBitmap().getHeight())/2 >= distanceY))
			return true;
		else
			return false;
	}

}
