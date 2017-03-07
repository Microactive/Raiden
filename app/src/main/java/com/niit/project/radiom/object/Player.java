package com.niit.project.radiom.object;

/**
 * @author songhui
 * ���ID
 * �������
 * ��һ���
 */
public class Player {
	private String ID;
	private String name;
	private int scores;
	
	
	public Player(String iD, String name, int scores) {
		super();
		ID = iD;
		this.name = name;
		this.scores = scores;
	}
	
	/*
	 * getter��setter����
	 * */
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScores() {
		return scores;
	}
	public void setScores(int scores) {
		this.scores = scores;
	}
	public void addScores(int award) {
		scores += award;
	}
	
}
