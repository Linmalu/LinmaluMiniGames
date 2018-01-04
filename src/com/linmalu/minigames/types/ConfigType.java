package com.linmalu.minigames.types;

import com.linmalu.minigames.data.Languages;

public enum ConfigType
{
	VERSION,
	LANGUAGE,
	RESOURCE_PACK,
	MAP_DEFAULT_SIZE,
	MAP_PLAYER_SIZE,
	SCORE_DEFAULT,
	SCORE_PLAYER,
	MAP_HEIGHT,
	TIME_DEFAULT,
	TIME_PLAYER,
	COOLDOWN,
	USE_MAP,
	MAP_WORLD,
	MAP_X1,
	MAP_Z1,
	MAP_X2,
	MAP_Z2;

	public String getName()
	{
		return Languages.getConfigTypeName(this);
	}
}
