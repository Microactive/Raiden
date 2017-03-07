package com.niit.project.radiom.object;

import java.util.HashMap;
import java.util.Map;

import com.niit.project.radiom.ObjectFactroy.BackgroundType;
import com.niit.project.radiom.ObjectFactroy.CaseType;
import com.niit.project.radiom.ObjectFactroy.EnemyType;

/**
 * �ؿ��࣬ͨ��ʵ��������࣬���Զ��Ƹ������͵Ĺؿ�
 * ÿ���ؿ���ÿ�����͵л��������ͳ���ʱ��
 * ������ÿ�ֹ�����������ͳ���ʱ��
 * @author songhui
 *
 */
public class Round {
	private int roundNumber;//�ؿ�����
	//ĳ�����͵л�������
	private Map<EnemyType,Integer> enemyNumberMap = new HashMap<EnemyType,Integer>();
	//ĳ�����͵л��ĳ���ʱ��
	private Map<EnemyType,Integer> enemyDelayMap = new HashMap<EnemyType,Integer>();
	//ĳ�����͹����������
	private Map<CaseType,Integer> caseNumberMap = new HashMap<CaseType,Integer>();
	//ĳ�����͹�����ĳ��ּ��ʱ��
	private Map<CaseType,Integer> caseDelayMap = new HashMap<CaseType,Integer>();
	//�ؿ����޶�ʱ�䣬��λΪ����
	private int time;
	//�ؿ��ı�������
	private BackgroundType backgroundType;
	
	public Round(int roundNumber, int time, BackgroundType backgroundType) {
		this.roundNumber = roundNumber;
		this.time = time;
		this.backgroundType = backgroundType;
	}
	
	/**
	 * ���ĳ�����͵л�
	 * @param type �л�����
	 * @param number ÿ�γ��ֵ�����
	 * @param delay ���ֵļ��ʱ��
	 */
	public void addEnemy(EnemyType type, int number, int delay) {
		if ( !enemyNumberMap.containsKey(type) && !enemyDelayMap.containsKey(type)) {
			enemyNumberMap.put(type, Integer.valueOf(number));
			enemyDelayMap.put(type, Integer.valueOf(delay));
		}
	}
	
	/**
	 * ���ĳ�����͵Ĺ�����
	 * @param type ����������
	 * @param number ÿ�γ��ֵ�����
	 * @param delay ���ֵļ��ʱ��
	 */
	public void addCase(CaseType type, int number, int delay) {
		if ( !caseNumberMap.containsKey(type) && !caseDelayMap.containsKey(type)) {
			caseNumberMap.put(type, Integer.valueOf(number));
			caseDelayMap.put(type, Integer.valueOf(delay));
		}
	}

	/*
	 * һЩgetter��setter����
	 * */
	
	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public BackgroundType getBackgroundType() {
		return backgroundType;
	}

	public void setBackgroundType(BackgroundType backgroundType) {
		this.backgroundType = backgroundType;
	}

	public Map<EnemyType, Integer> getEnemyNumberMap() {
		return enemyNumberMap;
	}

	public void setEnemyNumberMap(Map<EnemyType, Integer> enemyNumberMap) {
		this.enemyNumberMap = enemyNumberMap;
	}

	public Map<EnemyType, Integer> getEnemyDelayMap() {
		return enemyDelayMap;
	}

	public void setEnemyDelayMap(Map<EnemyType, Integer> enemyDelayMap) {
		this.enemyDelayMap = enemyDelayMap;
	}

	public Map<CaseType, Integer> getCaseNumberMap() {
		return caseNumberMap;
	}

	public void setCaseNumberMap(Map<CaseType, Integer> caseNumberMap) {
		this.caseNumberMap = caseNumberMap;
	}

	public Map<CaseType, Integer> getCaseDelayMap() {
		return caseDelayMap;
	}

	public void setCaseDelayMap(Map<CaseType, Integer> caseDelayMap) {
		this.caseDelayMap = caseDelayMap;
	}

}
