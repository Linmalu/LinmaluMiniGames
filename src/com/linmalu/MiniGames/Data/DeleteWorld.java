package com.linmalu.MiniGames.Data;

import java.io.File;

import com.linmalu.MiniGames.Main;

public class DeleteWorld implements Runnable
{
	public DeleteWorld()
	{
		new Thread(this).start();
	}
	public void run()
	{
		Delete(new File("./" + Main.world));
	}
	private void Delete(File file)
	{
		try
		{
			if(!file.exists())
			{
				return;
			}
			if(file.isDirectory())
			{
				for(File subFile : file.listFiles())
				{
					Delete(subFile);
				}
			}
			file.delete();
		}
		catch(Exception e)
		{
		}
	}
}
