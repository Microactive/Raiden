package com.niit.project.radiom.object;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * ��BaseBullet�Ļ����̳�
 * ͨ��ʵ�����˶��󣬿���ʵ�ֲ�ͬ���ӵ����ͣ������ӵ���ͼƬ���ٶȵȵ�
 * @author songhui
 *
 */
public class Bullet extends BaseBullet {
	/**
	 * �򵥵����˸���Ĺ��췽��
	 * @param imagePath
	 * @param assetManager
	 * @param speed
	 * @param currentX
	 * @param currentY
	 * @param destX
	 * @param destY
	 * @param harm
	 * @param isUsed
	 */
	public Bullet(String imagePath, AssetManager assetManager, double speed,
			double currentX, double currentY, double destX, double destY, int harm,
			boolean isUsed) {
		super(imagePath, assetManager, speed, currentX, currentY, destX, destY, harm,
				isUsed);
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
	public void drawSelf(Canvas canvas) {
		//��ȡbitmap�Ŀ�Ⱥ͸߶�
		int bitmapHeight = bitmap.getHeight();
		int bitmapWidth= bitmap.getWidth();
		
		//����ͼƬ��Ҫ������������
		Rect src = new Rect(0, 0, bitmapWidth, bitmapHeight);
		//������Ļ��Ҫ���ͼƬ������
		Rect dst = new Rect( (int)(currentX - bitmapWidth/2),
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
	public boolean isOverRange(int left, int right, int top, int bottom) {
		if (currentX <= left
				|| currentX >= right
				|| currentY <= top
				|| currentY >= bottom)
			return true;
		return false;
	}

}
