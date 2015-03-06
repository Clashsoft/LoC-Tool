package com.clashsoft.loc;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.*;

public class LoCTool
{
	
	private JFrame				frame;
	public JButton				buttonSelectFile;
	public JLabel				labelProjectFolder;
	public JTextField			textFieldProjectRoot;
	
	private final JFileChooser	fileChooser	= new JFileChooser();
	public JButton				buttonScan;
	public JLabel				labelLOC;
	public JLabel				labelSLOC;
	public JLabel				labelCLOC;
	public JTextField			textFieldLOC;
	public JTextField			textFieldSLOC;
	public JTextField			textFieldCLOC;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(() -> {
			try
			{
				LoCTool window = new LoCTool();
				window.frame.setVisible(true);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public LoCTool()
	{
		this.frame = new JFrame();
		this.frame.setTitle("LoC Tool");
		this.frame.setBounds(100, 100, 450, 200);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(null);
		
		this.buttonSelectFile = new JButton("Browse...");
		this.buttonSelectFile.addActionListener(e -> {
			int ret = this.fileChooser.showOpenDialog(this.frame);
			if (ret == JFileChooser.APPROVE_OPTION)
			{
				File file = this.fileChooser.getSelectedFile();
				this.textFieldProjectRoot.setText(file.getAbsolutePath());
			}
		});
		this.buttonSelectFile.setBounds(327, 6, 117, 29);
		this.frame.getContentPane().add(this.buttonSelectFile);
		
		this.labelProjectFolder = new JLabel("Project Folder");
		this.labelProjectFolder.setBounds(6, 11, 86, 16);
		this.frame.getContentPane().add(this.labelProjectFolder);
		
		this.textFieldProjectRoot = new JTextField();
		this.textFieldProjectRoot.setBounds(104, 5, 211, 28);
		this.frame.getContentPane().add(this.textFieldProjectRoot);
		this.textFieldProjectRoot.setColumns(10);
		
		this.buttonScan = new JButton("Scan Lines of Code");
		this.buttonScan.addActionListener(e -> {
			
		});
		this.buttonScan.setBounds(6, 39, 438, 29);
		this.frame.getContentPane().add(this.buttonScan);
		
		this.labelLOC = new JLabel("Lines Of Code (LOC):");
		this.labelLOC.setBounds(6, 80, 211, 16);
		this.frame.getContentPane().add(this.labelLOC);
		
		this.labelSLOC = new JLabel("Source Lines Of Code (SLOC):");
		this.labelSLOC.setBounds(6, 108, 211, 16);
		this.frame.getContentPane().add(this.labelSLOC);
		
		this.labelCLOC = new JLabel("Comment Lines Of Code (CLOC):");
		this.labelCLOC.setBounds(6, 136, 211, 16);
		this.frame.getContentPane().add(this.labelCLOC);
		
		this.textFieldLOC = new JTextField();
		this.textFieldLOC.setEditable(false);
		this.textFieldLOC.setBounds(229, 74, 215, 28);
		this.frame.getContentPane().add(this.textFieldLOC);
		this.textFieldLOC.setColumns(10);
		
		this.textFieldSLOC = new JTextField();
		this.textFieldSLOC.setEditable(false);
		this.textFieldSLOC.setBounds(229, 102, 215, 28);
		this.frame.getContentPane().add(this.textFieldSLOC);
		this.textFieldSLOC.setColumns(10);
		
		this.textFieldCLOC = new JTextField();
		this.textFieldCLOC.setEditable(false);
		this.textFieldCLOC.setBounds(229, 130, 215, 28);
		this.frame.getContentPane().add(this.textFieldCLOC);
		this.textFieldCLOC.setColumns(10);
	}
}
