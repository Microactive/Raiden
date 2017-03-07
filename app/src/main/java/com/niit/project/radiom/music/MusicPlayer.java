package com.niit.project.radiom.music;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * ���ڿ������ֲ��ŵ��࣬
 * ���Բ��š�ֹͣ����ͣ�������ֺ͸�����Ч
 * @author songhui
 * 
 */
public class MusicPlayer {
	//��������
	public static enum MusicType {background, boom1, getAward, roundPassed};
	//��������
	public static enum TaskType {play, pause, stop};
	private Context context;
	private SharedPreferences sp;
	//״̬Ϊ�����ֿ����Ƿ��
	private boolean musicOn;
	
	/**
	 * ���캯��
	 * @param context
	 */
	public MusicPlayer(Context context) {
		this.context = context;
		sp = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	/**
	 * ��������
	 * @param musicType
	 */
	public void playMusic(MusicType musicType) {
		musicOn = sp.getBoolean("musicOn", false);
		if (!musicOn)
			return;
		Intent service = new Intent();
		service.setAction("com.niit.project.radiom.MusicService");
		service.putExtra("musicType", musicType);
		service.putExtra("taskType", TaskType.play);
		//��������
		context.startService(service);
	}
	
	/**
	 * ֹͣ����
	 * @param musicType
	 */
	public void stopMusic(MusicType musicType) {
		musicOn = sp.getBoolean("musicOn", false);
		if (!musicOn)
			return;
		Intent service = new Intent();
		service.setAction("com.niit.project.radiom.MusicService");
		service.putExtra("musicType", musicType);
		service.putExtra("taskType", TaskType.stop);
		//ֹͣ����
		context.stopService(service);
	}
	
	/**
	 * ��ͣ����
	 * @param musicType
	 */
	public void pauseMusic(MusicType musicType) {
		musicOn = sp.getBoolean("musicOn", false);
		if (!musicOn)
			return;
		Intent service = new Intent();
		service.setAction("com.niit.project.radiom.MusicService");
		service.putExtra("musicType", musicType);
		service.putExtra("taskType", TaskType.pause);
		//��������
		context.startService(service);
	}
}
