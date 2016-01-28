package com.linmalu.minigames.data;

import java.io.File;

import com.linmalu.minigames.Main;

public class DeleteWorld implements Runnable
{
	public DeleteWorld()
	{
		new Thread(this).start();
	}
	public void run()
	{
		Delete(new File("./" + Main.WORLD));
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
