package com.niit.project.radiom.object;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * �������ӵ��࣬ͨ���̳�����࣬
 * ����ʵ�ֲ�ͬ���͵��ӵ�Ч��
 * @author songhui
 *
 */
public abstract class BaseBullet {
	/**
	 * @author songhui
	 * �ӵ����͵�ö������
	 * �������ӵ��Ĳ�ͬ���ͺͷ���λ��
	 *
	 */
	public static enum BulletType {
		bullet1, bullet1_left, bullet1_right,
		bullet2, bullet2_left, bullet2_right,
		bullet3, bullet3_left, bullet3_right,
		bullet4, bullet4_left, bullet4_right,
		bullet5, bullet5_left, bullet5_right,
		bullet6, bullet6_left, bullet6_right
		};
	
	protected String imagePath;//�ӵ�ͼƬ
	protected AssetManager assetManager;
	protected Bitmap bitmap;//���ڻ����ӵ�
	protected double speed = 0.1f;//ÿ����ǰ����px��
	protected double currentX;//��ǰ��X������
	protected double currentY;//��ǰ��Y������
	protected double destX;//Ŀ��λ�õ�X������
	protected double destY;//Ŀ��λ�õ�Y������
	protected int harm;//�ӵ����˺�ֵ
	protected boolean isUsed;//������Ŀ����߳����߽�ʱ�ӵ��ͱ�ʹ����
	
	public BaseBullet(String imagePath, AssetManager assetManager, double speed,
			double currentX, double currentY, double destX, double destY, int harm,
			boolean isUsed ) {
		if (imagePath == null)
			imagePath = "img/bullet2.png";//Ĭ�ϵ��ӵ�ͼƬ
		else
			this.imagePath = imagePath;
		this.assetManager = assetManager;
		this.speed = speed;
		this.currentX = currentX;
		this.currentY = currentY;
		this.destX = destX;
		this.destY = destY;
		this.harm = harm;
		this.isUsed = isUsed;
		//���Bitmap����
		InputStream is = null;
		try {
			is = assetManager.open(imagePath);
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getCurrentX() {
		return currentX;
	}

	public void setCurrentX(double currentX) {
		this.currentX = currentX;
	}

	public double getCurrentY() {
		return currentY;
	}

	public void setCurrentY(double currentY) {
		this.currentY = currentY;
	}

	public double getDestX() {
		return destX;
	}

	public void setDestX(double destX) {
		this.destX = destX;
	}

	public double getDestY() {
		return destY;
	}

	public void setDestY(int destY) {
		this.destY = destY;
	}

	public int getHarm() {
		return harm;
	}

	public void setHarm(int harm) {
		this.harm = harm;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * ��ָ����ʱ���ڸ��������ٶȣ���ǰλ�ú�Ŀ��λ�����ƶ�����һ��λ��
	 * ͨ���Դ˺����Ĳ�ͬʵ�֣����Բ����ӵ���ͬ���˶��켣
	 * @param time ��λΪ����
	 */
	public abstract void move (int time);
	/**
	 * ��ָ����ʱ���ڸ��������ٶȣ���ǰλ�ú�ָ����Ŀ��λ�����ƶ�����һ��λ��
	 * @param time ��λΪ����
	 * @param destX ָ����X����
	 * @param destY ָ����Y����
	 */
	public abstract void move (int time, int destX, int destY);
	/**
	 * ������������ڻ����ϻ���ͼ��
	 * @param canvas
	 */
	public abstract void drawSelf (Canvas canvas);
	/**
	 * ����Ƿ񳬳��˸����ķ�Χ
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 * @return ������Χ�򷵻�true�����򷵻�false
	 */
	public abstract boolean isOverRange(int left, int right, int top, int bottom);
}
