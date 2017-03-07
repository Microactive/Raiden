package com.niit.project.radiom.object;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.niit.project.radiom.object.BaseBullet.BulletType;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * �����ķɻ��࣬ͨ���̳�����࣬����ʵ�ֲ�ͬ���͵ķɻ�
 * ����ҷɻ���boss�ɻ���ආ��ɻ�
 * 
 * ���ڱ�ըЧ���Ļ��ƣ������ṩ������ʵ�ַ�ʽ��
 * һ����ͨ��һϵ��ͼƬ������λͼ������֡���ƣ�
 * ��һ�ַ�ʽ��ͨ��һ�ų�ͼ����һ��λͼ����ÿ֡����λͼ�����һ����
 * @author songhui
 * @see drawBoom()����
 * 
 */
public abstract class BasePlane {
	/**
	 * �洢ÿ�������ӵ����ӳ�ʱ�䣬���ڿ��Ʒɻ������ӵ��ļ��
	 */
	public static Map<BulletType, Integer> 
			bulletDelayMap = new HashMap<BulletType, Integer>();
	
	/**
	 * ��¼�ɻ���Я����ÿ�������ӵ�������
	 * */
	protected Map<BaseBullet.BulletType, Integer> bulletsMap 
						= new HashMap<BaseBullet.BulletType, Integer>();
	
	protected String imagePath;//�ɻ���ͼƬ·��
	protected String[] boomImagePaths;//��ըЧ����ϵ��ͼƬ·��
	protected String boomImagePath;//��ըЧ���ĵ���ͼƬ·��
	protected Bitmap bitmap;//�ɻ���λͼ����
	protected Bitmap[] boomBitmaps;//��ű�ըλͼ���������
	protected Bitmap boomBitmap;//��ըЧ���ĵ���ͼƬλͼ����
	protected int boomFrameCount;//��ըЧ����֡������������������
	protected long shootFrameCount;//�����ӵ���֡������������������
	
	protected AssetManager assetManager;
	protected double speed = 0.1;//ÿ����ǰ����px
	protected double currentX;
	protected double currentY;
	protected double destX;
	protected double destY;
	protected int blood;
	protected int boomCount;
	
//	/**
//	 * ͨ������һϵ�б�ըͼƬ������������ʵ��������
//	 * @param imagePath �ɻ�ͼƬ·��
//	 * @param boomImagePaths ϵ�б�ըͼƬ·��
//	 * @param assetManager
//	 * @param speed �ɻ��ٶȣ���λΪÿ����ǰ����������
//	 * @param currentX ��ǰX����
//	 * @param currentY ��ǰY����
//	 * @param destX Ŀ��X����
//	 * @param destY Ŀ��Y����
//	 * @param blood Ѫ��
//	 * @param boomFrameCount ���Ʊ�ըʱ��֡�����������᲻������
//	 */
//	public BasePlane(String imagePath, String[] boomImagePaths,
//			AssetManager assetManager, float speed, int currentX, int currentY,
//			int destX, int destY, int blood, int boomFrameCount) {
//		if (imagePath == null)
//			this.imagePath = "img/F22.png";
//		else
//			this.imagePath = imagePath;
//		if (boomImagePaths == null)
//			this.boomImagePaths = new String[]{"img/boom1.png", "img/boom1.png",
//				"img/boom1.png", "img/boom1.png", "img/boom1.png" };
//		else
//			this.boomImagePaths = boomImagePaths;
//		this.assetManager = assetManager;
//		this.speed = speed;
//		this.currentX = currentX;
//		this.currentY = currentY;
//		this.destX = destX;
//		this.destY = destY;
//		this.blood = blood;
//		this.boomFrameCount = 0;
//		this.shootFrameCount = 0;
//		
//		//��÷ɻ���Bitmap����
//		InputStream is = null;
//		try {
//			is = assetManager.open(imagePath);
//			bitmap = BitmapFactory.decodeStream(is);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//��÷ɻ���ը��Bitmap��������
//		try {
//			boomBitmaps = new Bitmap[this.boomImagePaths.length];
//			for (int i = 0; i < this.boomImagePaths.length; i ++) {
//				is = assetManager.open(this.boomImagePaths[i]);
//				boomBitmaps[i] = BitmapFactory.decodeStream(is);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//Ϊÿ�����͵��ӵ��������ӳ�ʱ��
//		bulletDelayMap.put(BulletType.bullet1, Integer.valueOf(20));
//		bulletDelayMap.put(BulletType.bullet1_left, Integer.valueOf(20));
//		bulletDelayMap.put(BulletType.bullet1_right, Integer.valueOf(20));
//		bulletDelayMap.put(BulletType.bullet2, Integer.valueOf(2));
//		bulletDelayMap.put(BulletType.bullet2_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet2_right, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet3, Integer.valueOf(1));
//		bulletDelayMap.put(BulletType.bullet3_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet3_right, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet4, Integer.valueOf(1));
//		bulletDelayMap.put(BulletType.bullet4_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet4_right, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet5, Integer.valueOf(1));
//		bulletDelayMap.put(BulletType.bullet5_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet5_right, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet6, Integer.valueOf(1));
//		bulletDelayMap.put(BulletType.bullet6_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet6_right, Integer.valueOf(10));
//	}
	
	public BasePlane(String imagePath, String boomImagePath, int boomCount,
			AssetManager assetManager, float speed, int currentX, int currentY,
			int destX, int destY, int blood, int boomFrameCount) {
		if (imagePath == null)
			this.imagePath = "img/F22.png";
		else
			this.imagePath = imagePath;
		boomImagePaths = null;
		this.boomImagePath = boomImagePath;
		this.assetManager = assetManager;
		this.speed = speed;
		this.currentX = currentX;
		this.currentY = currentY;
		this.destX = destX;
		this.destY = destY;
		this.blood = blood;
		this.boomFrameCount = 0;
		this.shootFrameCount = 0;
		this.boomCount = boomCount;
		
		//��÷ɻ���Bitmap����
		InputStream is = null;
		try {
			is = assetManager.open(imagePath);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//��÷ɻ���ը��Bitmap����
		try {
			is = assetManager.open(boomImagePath);
			boomBitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Ϊÿ�����͵��ӵ��������ӳ�ʱ��
		bulletDelayMap.put(BulletType.bullet1, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet1_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet1_right, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet2, Integer.valueOf(4));
		bulletDelayMap.put(BulletType.bullet2_left, Integer.valueOf(4));
		bulletDelayMap.put(BulletType.bullet2_right, Integer.valueOf(4));
		bulletDelayMap.put(BulletType.bullet3, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet3_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet3_right, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet4, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet4_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet4_right, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet5, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet5_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet5_right, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet6, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet6_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet6_right, Integer.valueOf(20));
		
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String[] getBoomImagePaths() {
		return boomImagePaths;
	}

	public void setBoomImagePaths(String[] boomImagePaths) {
		this.boomImagePaths = boomImagePaths;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap[] getBoomBitmaps() {
		return boomBitmaps;
	}

	public void setBoomBitmaps(Bitmap[] boomBitmaps) {
		this.boomBitmaps = boomBitmaps;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
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

	public void setDestY(double destY) {
		this.destY = destY;
	}

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}
	
	public void cutBlood(int harm) {
		blood -= harm;
	}

	public int getBoomFrameCount() {
		return boomFrameCount;
	}
	
	public void addBoomFrameCount() {
		boomFrameCount ++;
	}
	
	public long getShootFrameCount() {
		return shootFrameCount;
	}

	/**
	 * ÿ�����һ���ӵ������ô˺���������shootFrameCount
	 */
	public void addShootFrameCount() {
		shootFrameCount ++;
	}

	public Map<BaseBullet.BulletType, Integer> getBulletsMap() {
		return bulletsMap;
	}

	public void setBulletsMap(Map<BaseBullet.BulletType, Integer> bulletsMap) {
		this.bulletsMap = bulletsMap;
	}

	public void addBullet(BulletType type, int number) {
		bulletsMap.put( type, new Integer(number) );
	}
	
	public int getBoomCount() {
		return boomCount;
	}

	public void setBoomCount(int boomCount) {
		this.boomCount = boomCount;
	}

	/**
	 * ���Ʒɻ����ƶ����������п���ʵ�ַɻ��Ĳ�ͬ�ƶ�����
	 * @param time ÿ���ƶ���ʱ�䣬��λΪ����
	 * @param destX Ŀ��λ��X����
	 * @param destY Ŀ��λ��Y����
	 */
	public abstract void move (int time, int destX, int destY);
	/**
	 * ���Ʒɻ����ƶ����������п���ʵ�ַɻ��Ĳ�ͬ�ƶ�����
	 * @param time ÿ���ƶ���ʱ�䣬��λΪ����
	 */
	public abstract void move (int time);
	/**
	 * ���Ʒɻ�
	 * @param canvas
	 */
	public abstract void drawSelf (Canvas canvas);
	/**
	 * ���Ʒɻ���ը��Ч��
	 * @param canvas
	 */
	public abstract void drawBoom (Canvas canvas);
	/**
	 * �жϷɻ���ǰ�ܷ���ĳ�����͵��ӵ�
	 * @param type
	 * @return
	 */
	public abstract boolean canShoot (BaseBullet.BulletType type);
	/**
	 * ����ĳ�����͵��ӵ����ڵ��ôκ���ǰӦ��ͨ��
	 * canShoot(BaseBullet.BulletType type)�������ж��ܷ���������͵��ӵ�
	 * @param type Ҫ�������ӵ�����
	 * @return
	 */
	public abstract BaseBullet createBullet (BaseBullet.BulletType type);
	/**
	 * ���ɻ��Ƿ񳬹���ָ���ķ�Χ
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	public abstract boolean isOverRange(int left, int top, int right, int bottom);
	/**
	 * ���ɻ�������һ�ܷɻ�����ײ
	 * @param plane
	 * @return
	 */
	public abstract boolean detectCrash (BasePlane plane);
	/**
	 * ���ɻ����ӵ�����ײ
	 * @param bullet
	 * @return
	 */
	public abstract boolean detectCrash (BaseBullet bullet);
}
