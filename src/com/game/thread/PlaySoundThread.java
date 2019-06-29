package com.game.thread;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.SourceDataLine;

/**
 * 播放声音的线程
 * 
 * @author ordinary-student
 *
 */
public class PlaySoundThread extends Thread
{
	private String filename;

	/*
	 * 构造方法
	 */
	public PlaySoundThread(String wavfile)
	{
		this.filename = "res/" + wavfile;
	}

	@Override
	public void run()
	{
		// 数据行
		SourceDataLine sourceDataLine = null;
		try
		{
			// 封装声音文件
			File soundFile = new File(filename);
			// 音频输入流
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

			// 音频格式
			AudioFormat format = audioInputStream.getFormat();
			// 格式化
			Info info = new Info(SourceDataLine.class, format);

			// 数据行
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
			// 打开
			sourceDataLine.open(format);

			// 开始
			sourceDataLine.start();

			// 数据
			int audioData = 0;
			// 缓冲区
			byte[] dataByte = new byte[512];
			// 循环读写
			while (audioData != -1)
			{
				// 读取
				audioData = audioInputStream.read(dataByte, 0, dataByte.length);
				if (audioData >= 0)
				{
					sourceDataLine.write(dataByte, 0, audioData);
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		} finally
		{
			// 关闭流
			sourceDataLine.drain();
			sourceDataLine.close();
		}
	}
}