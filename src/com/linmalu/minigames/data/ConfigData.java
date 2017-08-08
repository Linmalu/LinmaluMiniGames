package com.linmalu.minigames.data;

public enum ConfigData
{
	MAP_DEFAULT_SIZE("기본맵 크기"),
	MAP_PLAYER_SIZE("인원수 추가 게임맵 크기"),
	SCORE_DEFAULT("기본 목표점수"),
	SCORE_PLAYER("플레이어 추가 목표점수"),
	MAP_HEIGHT("게임맵 높이"),
	TIME_DEFAULT("기본 제한 시간(초)"),
	TIME_PLAYER("인원수 추가 제한 시간(초)"),
	COOLDOWN("휴식시간"),
	MAP_WORLD("월드"),
	MAP_X1("X1"),
	MAP_Z1("Z1"),
	MAP_X2("X2"),
	MAP_Z2("Z2");

	private final String name;

	private ConfigData(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
}
