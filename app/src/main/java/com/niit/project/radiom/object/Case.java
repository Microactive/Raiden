package com.niit.project.radiom.object;

import java.io.IOException;
import java.io.InputStream;

import com.niit.project.radiom.ObjectFactroy.CaseType;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author songhui
 * �����࣬ͨ���̳�����࣬����ʵ�ֲ�ͬ�Ľ������ߣ�
 * �絯ҩ�䣬Ѫƿ�ȵ�
 */
public class Case {
	protected String imagePath;//�������ͼƬ·��
	protected AssetManager assetManager;
	protected Bitmap bitmap;
	protected double speed = 0.1f;//ÿ����ǰ����px
	protected double currentX;
	protected double currentY;
	protected double destX;
	protected double destY;
	protected int life;//�������ڣ����ϼ��٣���С�ڵ���0ʱ����ʧ
	protected boolean isUsed;//������Ŀ����߳����߽�ʱ�ӵ��ͱ�ʹ����
	protected CaseType type; //�����������
	
	public Case(String imagePath, AssetManager assetManager, float speed,
			int currentX, int currentY, int destX, int destY, int life,
			boolean isUsed, CaseType type ) {
		if (imagePath == null)
			imagePath = "img/bullet2.png";
		else
			this.imagePath = imagePath;
		this.assetManager = assetManager;
		this.speed = speed;
		this.currentX = currentX;
		this.currentY = currentY;
		this.destX = destX;
		this.destY = destY;
		this.life = life;
		this.isUsed = isUsed;
		this.type = type;
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
	
	/*
	 * һЩgetter��setter����
	 * 
	 * */
	
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

	public void setDestY(double destY) {
		this.destY = destY;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public void minusLife(int minus) {
		life -= minus;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public CaseType getType() {
		return type;
	}

	public void setType(CaseType type) {
		this.type = type;
	}

	/**
	 * �ڻ����ϻ���ͼ��
	 * @param canvas
	 */
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
	
	/**
	 * ����Ƿ񳬳���ָ���ķ�Χ
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 * @return
	 */
	public boolean isOverRange(int left, int right, int top, int bottom) {
		if (currentX <= left
				|| currentX >= right
				|| currentY <= top
				|| currentY >= bottom)
			return true;
		return false;
	}
	
	/**
	 * ��⹤����ͷɻ�����ײ�����ж�����Ƿ�Ե��������ʱ�򱻵���
	 * @param plane
	 * @return 
	 */
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
	
	/**
	 * �ƶ������䣬�ӵ�ǰλ����Ŀ��λ���ƶ���ÿ�θ��µ�ǰλ��
	 * @param time
	 */
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
}
