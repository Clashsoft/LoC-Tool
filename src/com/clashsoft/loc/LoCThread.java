package com.clashsoft.loc;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class LoCThread extends Thread
{
	protected static List<File>	files	= new ArrayList();
	protected static int		dirCount;
	protected static long		loc;
	protected static long		sloc;
	protected static long		cloc;
	
	public static void startThread(ActionEvent e)
	{
		new LoCThread().start();
	}
	
	@Override
	public void run()
	{
		files.clear();
		dirCount = 0;
		loc = sloc = cloc = 0L;
		LoCTool.instance.progressBar.setString("Scanning for Files...");
		LoCTool.instance.progressBar.setStringPainted(true);
		walkFileTree(new File(LoCTool.instance.textFieldProjectRoot.getText()));
		processFiles();
	}
	
	public static void walkFileTree(File file)
	{
		if (file.isDirectory())
		{
			String name = file.getName();
			if (name.startsWith("."))
			{
				return;
			}
			
			dirCount++;
			for (String s : file.list())
			{
				walkFileTree(new File(file, s));
			}
			return;
		}
		
		files.add(file);
	}
	
	public static void processFiles()
	{
		int count = files.size();
		LoCTool.instance.progressBar.setString("0 / " + count);
		LoCTool.instance.progressBar.setMinimum(0);
		LoCTool.instance.progressBar.setMaximum(count - 1);
		LoCTool.instance.labelFileCount.setText("Files: " + count);
		LoCTool.instance.labelDirCount.setText("Directories: " + dirCount);
		
		for (int i = 0; i < count; )
		{
			processFile(files.get(i));
			LoCTool.instance.progressBar.setValue(i);
			LoCTool.instance.progressBar.setString(++i + " / " + count);
			LoCTool.instance.textFieldLOC.setText(Long.toString(loc));
			LoCTool.instance.textFieldSLOC.setText(Long.toString(sloc));
			LoCTool.instance.textFieldCLOC.setText(Long.toString(cloc));
		}
	}
	
	public static void processFile(File file)
	{
		try
		{
			for (String s : Files.readAllLines(file.toPath()))
			{
				loc++;
				if (s.indexOf("//") >= 0)
				{
					cloc++;
				}
				else
				{
					sloc++;
				}
			}
		}
		catch (IOException ex)
		{
		}
	}
}
