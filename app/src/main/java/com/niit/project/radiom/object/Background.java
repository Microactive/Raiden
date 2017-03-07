package com.niit.project.radiom.object;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author songhui
 * ����һ�������࣬ͨ��ʵ��������࣬��������صķ�����
 * ����ʵ�ֱ������ƶ�����
 * �����ƶ���ʽ��ͨ����һ��ͼƬ�ķֳ������֣�
 * Ȼ��ÿ�ΰ��������ַ�����Ļ��ͬ��λ���ϣ��Դ���ʵ���ƶ�Ч��
 *
 */
public class Background {
	private Bitmap bitmap;//���ڻ��Ʊ���
	private AssetManager assetManager;
	private String imagePath;//����ͼƬ��Դ
	private int screenWidth;//��Ļ���
	private int screenHeight;//��Ļ�߶�
	private int top;//�����ƶ�ʱ�ڶ���ͼƬ�����Ϸ���y������

	/**
	 * ���캯��
	 * @param assetManager
	 * @param imagePath
	 * @param screenWidth
	 * @param screenHeight
	 * @param top
	 */
	public Background(AssetManager assetManager,
			String imagePath, int screenWidth, int screenHeight, int top) {
		super();
		this.assetManager = assetManager;
		this.imagePath = imagePath;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.top = top;
		//���bitmap����
		InputStream is;
		try {
			is = assetManager.open(imagePath);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * ÿ�θı䱳��ͼƬ�ڶ���������Ļ����λ�ã��Դ�ʵ�ֱ����ƶ�
	 */
	public void move() {
		top = (top + 1) % screenHeight;
	}
	
	/**
	 * ���ݵ�ǰ��topֵ�����Ʊ���
	 * @param canvas ���ڻ�ͼ�Ļ�������
	 */
	public void drawSelf(Canvas canvas) {		
		Rect src = new Rect(0, 0, screenWidth, screenHeight);//��ȡ����ͼƬ�ķ�Χ
		Rect dst1 = new Rect(0, top, screenWidth, top + screenHeight);//�����Ļ�ĵ�һ������
		Rect dst2 = new Rect(0, top-screenHeight, screenWidth, top);//�����Ļ�ĵڶ�������
		
		//���Ʊ���
		if (canvas == null)
			return;
		canvas.drawBitmap(bitmap, src, dst1, null);
		canvas.drawBitmap(bitmap, src, dst2, null);
	}
}
