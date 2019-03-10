package com.clashsoft.loc;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class LoCThread extends Thread
{
	protected static List<File> files = new ArrayList();
	protected static int        dirCount;
	protected static long       loc;
	protected static long       sloc;
	protected static long       cloc;

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
			processCode(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
		}
		catch (IOException ex)
		{
		}
	}

	private static void processCode(String code)
	{
		int length = code.length();
		int loc = 1;
		int cloc = 0;
		int sloc = 1;
		byte comment = 0; // 0 - none, 1 - line, 2 - multiline

		for (int i = 0; i < length; i++)
		{
			char c = code.charAt(i);
			switch (c)
			{
			case '\n':
				loc++;
				switch (comment)
				{
				case 0:
					sloc++;
					break;
				case 1:
					comment = 0;
				case 2:
					cloc++;
				}
				break;
			case '/':
				switch (comment)
				{
				case 0:
					switch (code.charAt(i + 1))
					{
					case '/':
						comment = 1;
						i++;
						continue;
					case '*':
						comment = 2;
						i++;
						continue;
					}
					break;
				case 2:
					if (code.charAt(i - 1) == '*')
					{
						comment = 0;
					}
				}
			}
		}

		LoCThread.sloc += sloc;
		LoCThread.cloc += cloc;
		LoCThread.loc += loc;
	}
}
