package com.niit.project.radiom.object;

import com.niit.project.radiom.ObjectFactroy.CaseType;

import android.content.res.AssetManager;

/**
 * ҽҩ���࣬ʵ���������Ķ�����Բ�����ͬ��ҽҩ��
 * @author songhui
 *
 */
public class MedicineCase extends Case {
	private int bloodAmount;//��ҳԵ�ҽҩ������ӵ�Ѫ��

	public MedicineCase(String imagePath, AssetManager assetManager,
			float speed, int currentX, int currentY, int destX, int destY,
			int life, boolean isUsed, int bloodAmount, CaseType type) {
		super(imagePath, assetManager, speed, currentX, currentY, destX, destY, life,
				isUsed, type);
		// TODO Auto-generated constructor stub
		this.bloodAmount = bloodAmount;
	}

	public int getBloodAmount() {
		return bloodAmount;
	}

	public void setBloodAmount(int bloodAmount) {
		this.bloodAmount = bloodAmount;
	}

}
