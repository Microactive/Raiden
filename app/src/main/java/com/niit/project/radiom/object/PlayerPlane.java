package com.niit.project.radiom.object;

import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.niit.project.radiom.object.BaseBullet.BulletType;

/**
 * ��ҷɻ���
 * @author songhui
 *
 */
public class PlayerPlane extends BasePlane {
	/**
	 * �ӵ����͵����飬�ڷ����ӵ��ͼ����ײ��ʱ��ᱻʹ��
	 */
	BulletType[] types = new BulletType[]
			{
				BulletType.bullet1, BulletType.bullet1_left, BulletType.bullet1_right,
				BulletType.bullet2,	BulletType.bullet2_left, BulletType.bullet2_right,
				BulletType.bullet3,	BulletType.bullet3_left, BulletType.bullet3_right,
				BulletType.bullet4,	BulletType.bullet4_left, BulletType.bullet4_right,
				BulletType.bullet5,	BulletType.bullet5_left, BulletType.bullet5_right,
				BulletType.bullet6,	BulletType.bullet6_left, BulletType.bullet6_right,
			};

	
	/**
	 * ���캯����ͨ��һ�ų�ͼ·��������������ʵ������
	 * ��ʹ��һ�ų�ͼ�����Ʊ�ըЧ����ʱ����ô˹��캯��
	 * */
	public PlayerPlane(String imagePath, String boomImagePath, int boomCount, 
			AssetManager assetManager, float speed, int currentX, int currentY,
			int destX, int destY, int blood, int boomFrameCount) {
		super(imagePath, boomImagePath, boomCount, assetManager, speed, currentX, currentY,
				destX, destY, blood, boomFrameCount);
	}

	/**
	 * ������ҷɻ���Ѫ��������ҳԵ���ҽҩ���ʱ��ص��ô˷���
	 * @param addAmount
	 */
	public void addBlood(int addAmount) {
		blood += addAmount;
	}
	
	/**
	 * Ϊ������һ��������ĳ�������ӵ�
	 * @param type �ӵ�����
	 * @param amount �ӵ�������
	 */
	public void addBullets(BulletType type, int amount) {
		//�����ǰ�ɻ�û��װ���������͵��ӵ�������Ӵ�����
		if (!bulletsMap.containsKey(type))
			bulletsMap.put(type, Integer.valueOf(amount));
		else { //�����ǰ�ɻ��Ѿ�װ�����������͵��ӵ�,���޸Ĵ�����
			int oldAmount = bulletsMap.get(type);
			bulletsMap.remove(type);
			bulletsMap.put(type, oldAmount + amount);
		}
	}
	
	/**
	 * ͨ��һ��Map������Ϊ������һϵ��һ�����������͵��ӵ�
	 * @param map
	 */
	public void addBullets(Map<BulletType, Integer> map) {
		for (int i = 0; i < types.length; i ++) {
			if (map.containsKey(types[i]))
				addBullet(types[i], map.get(types[i]));
		}
	}
	
	/**
	 * ����ĳ�����͵��ӵ���������ÿ�η�����������ӵ�֮�������������
	 * @param type
	 * @param amount
	 */
	public void cutBullets(BulletType type, int amount) {
		for (int i = 0; i < types.length; i ++) {
			if (type == types[i]) {
				int oldAmount = bulletsMap.get(type);
				bulletsMap.remove(type);
				bulletsMap.put(type, oldAmount - amount);
			}
		}
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
		//ÿ��Сͼ�Ĵ�С
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

	/*
	 * �ȶ��ӵ�����-��Ŀӳ����б���ѭ����ÿ��ѭ���жϵ�ǰ��ʲô������ӵ�
	 * Ȼ�󿴵�ǰ��֡���������Ƿ���ڸ������ӵ�������ӳ�����ȣ�
	 * ���Ҳ鿴��ǰ�ĵ�ǰ�����ӵ�����Ŀ�Ƿ�����㣬
	 * ����������������ϵĻ����򷵻�true�������򷵻�false
	 * */
	@Override
	public boolean canShoot(BulletType type) {
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
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX, currentY, currentX, -1, 20, false);
		case bullet1_left://����λ��Ϊ���
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX - 10, currentY, currentX - 20, -1, 20, false);
		case bullet1_right://����λ��Ϊ�ұ�
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX + 10, currentY, currentX + 20, -1, 20, false);
		case bullet2:
			return new Bullet("img/bullet2.png", assetManager, 0.1, currentX, currentY, currentX, -1, 20, false);
		case bullet2_left://����λ��Ϊ���
			return new Bullet("img/bullet2.png", assetManager, 0.1f, currentX - 10, currentY, currentX - 20, -1, 20, false);
		case bullet2_right://����λ��Ϊ�ұ�
			return new Bullet("img/bullet2.png", assetManager, 0.1f, currentX + 10, currentY, currentX + 20, -1, 20, false);
		case bullet3:
			return new Bullet("img/bullet3.png", assetManager, 0.5f, currentX, currentY, currentX, -1, 100, false);		
		case bullet3_left:
			return new Bullet("img/bullet3.png", assetManager, 0.5f, currentX - 10, currentY, currentX - 80, -1, 100, false);
		case bullet3_right:
			return new Bullet("img/bullet3.png", assetManager, 0.5f, currentX + 10, currentY, currentX + 80, -1, 100, false);
		case bullet4:
			return new Bullet("img/bullet4.png", assetManager, 0.5f, currentX, currentY, currentX, -1, 100, false);		
		case bullet4_left:
			return new Bullet("img/bullet4.png", assetManager, 0.5f, currentX - 10, currentY, currentX - 20, -1, 100, false);
		case bullet4_right:
			return new Bullet("img/bullet4.png", assetManager, 0.5f, currentX + 10, currentY, currentX + 20, -1, 100, false);
		default:
			return null;
		}
	}

	@Override
	public boolean isOverRange(int left, int top, int right, int bottom) {
		if (currentX < left + bitmap.getWidth()/2 
				|| currentX > right - bitmap.getWidth()/2
				|| currentY < bitmap.getHeight()/2
				|| currentY > bottom - bitmap.getHeight()/2)
			return true;
		return false;
	}

	@Override
	public boolean detectCrash(BasePlane plane) {
		double distanceX = Math.abs(plane.getCurrentX() - currentX);
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
		double distanceX = Math.abs(bullet.getCurrentX() - currentX);
		double distanceY = Math.abs(bullet.getCurrentY() - currentY);
		if (((bitmap.getWidth() + bullet.getBitmap().getWidth())/2 >= distanceX)
				&&
				((bitmap.getHeight() + bullet.getBitmap().getHeight())/2 >= distanceY))
			return true;
		else
			return false;
	}

}
