package com.niit.project.radiom.object;

import java.util.HashMap;
import java.util.Map;

import com.niit.project.radiom.ObjectFactroy.CaseType;
import com.niit.project.radiom.object.BaseBullet.BulletType;

import android.content.res.AssetManager;

/**
 * @author songhui
 * ��ҩ���࣬ͨ��ʵ���������Ķ��󣬿���ʵ�ֲ�ͬ���͵ĵ�ҩ�䣬
 * ÿ�ֵ�ҩ���в�ͬ���ͣ���ͬ�������ӵ�
 */
public class BulletsCase extends Case {
	/**
	 * ÿ�������ӵ�������
	 */
	private Map<BulletType, Integer> bulletsMap
							= new HashMap<BulletType, Integer>();

	/**
	 * ���캯�����򵥵ص����˸���Ĺ��캯��
	 * @param imagePath
	 * @param assetManager
	 * @param speed
	 * @param currentX
	 * @param currentY
	 * @param destX
	 * @param destY
	 * @param life
	 * @param isUsed
	 * @param type
	 */
	public BulletsCase(String imagePath, AssetManager assetManager,
			float speed, int currentX, int currentY, int destX, int destY,
			int life, boolean isUsed, CaseType type) {
		super(imagePath, assetManager, speed, currentX, currentY, destX, destY, life,
				isUsed, type);
	}

	public Map<BulletType, Integer> getBulletsMap() {
		return bulletsMap;
	}

	public void setBulletsMap(Map<BulletType, Integer> bulletsMap) {
		this.bulletsMap = bulletsMap;
	}
	
	/**
	 * Ϊ��ҩ�����һ��������ĳ�������ӵ�
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
}
