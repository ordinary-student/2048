package com.game.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.game.thread.PlaySoundThread;
import com.game.ui.GameFrame;

/**
 * 事件处理类
 * 
 * @author ordinary-student
 *
 */
public class Actions extends KeyAdapter implements ActionListener
{
	// 游戏窗口对象
	private GameFrame gameFrame;
	// 存放数据的数组
	private int numbers[][];

	private Random rand = new Random();

	// 用于备份数组，供回退时使用
	private int[][] backup = new int[4][4];
	// 用于备份数组，供起死回生时使用
	private int[][] liveup = new int[4][4];

	// 分数标签
	public JLabel scoreLabel;
	int score = 0;
	int tempscore, tempscore2;// 记录回退的分数值
	public JButton bt, about, back;
	public JMenuItem m1, m2, m3, m4;
	public JCheckBox isSoundBox;
	private boolean isWin = false, relive = false, hasBack = false, isSound = true;

	public Actions(GameFrame gameFrame, int[][] numbers, JLabel scoreLabel, JButton startButton, JButton aboutButton,
			JButton back, JCheckBox soundCheckBok)
	{
		this.gameFrame = gameFrame;
		this.numbers = numbers;
		this.scoreLabel = scoreLabel;
		this.bt = startButton;
		this.about = aboutButton;
		this.back = back;
		this.isSoundBox = soundCheckBok;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

		if (e.getSource() == bt)
		{
			isWin = false;
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++)
					numbers[i][j] = 0;

			score = 0;// 保证每次重置游戏都是0分开始
			scoreLabel.setText("分数：" + score);
			int r1 = rand.nextInt(4);
			int r2 = rand.nextInt(4);
			int c1 = rand.nextInt(4);
			int c2 = rand.nextInt(4);

			while (r1 == r2 && c1 == c2)
			{
				r2 = rand.nextInt(4);
				c2 = rand.nextInt(4);
			}

			// 生成数字（2或者4）
			int value1 = rand.nextInt(2) * 2 + 2;
			int value2 = rand.nextInt(2) * 2 + 2;

			// 把数字存进对应的位置
			numbers[r1][c1] = value1;
			numbers[r2][c2] = value2;
			gameFrame.paint(gameFrame.getGraphics());
		} else if (e.getSource() == about)
		{
			JOptionPane.showMessageDialog(gameFrame,
					"游戏规则：\n" + "开始时棋盘内随机出现两个数字，出现的数字仅可能为2或4\n" + "玩家可以选择上下左右四个方向，若棋盘内的数字出现位移或合并，视为有效移动\n"
							+ "玩家选择的方向上若有相同的数字则合并，每次有效移动可以同时合并，但不可以连续合并\n" + "合并所得的所有新生成数字想加即为该步的有效得分\n"
							+ "玩家选择的方向行或列前方有空格则出现位移\n" + "每有效移动一步，棋盘的空位(无数字处)随机出现一个数字(依然可能为2或4)\n"
							+ "棋盘被数字填满，无法进行有效移动，判负，游戏结束\n" + "棋盘上出现2048，判胜，游戏结束。\n");
		} else if (e.getSource() == back && hasBack == false)
		{
			hasBack = true;
			if (relive == false)
			{
				score = tempscore;
				scoreLabel.setText("分数：" + score);
				for (int i = 0; i < backup.length; i++)
				{
					numbers[i] = Arrays.copyOf(backup[i], backup[i].length);
				}
			} else
			{
				score = tempscore2;
				scoreLabel.setText("分数：" + score);
				for (int i = 0; i < liveup.length; i++)
				{
					numbers[i] = Arrays.copyOf(liveup[i], liveup[i].length);
				}
				relive = false;
			}
			gameFrame.paint(gameFrame.getGraphics());
		} else if (e.getSource().equals(isSoundBox))
		{
			if (isSoundBox.isSelected())
				isSound = false;
			else
				isSound = true;
		} else if (e.getSource() == m1)
		{

		} else if (e.getSource() == m2)
		{
			// bt.doClick(10);

		} else if (e.getSource() == m3)
		{
			about.doClick(10);

		} else if (e.getSource() == m4)
		{
			int chip = JOptionPane.showConfirmDialog(gameFrame, "是否退出游戏？", "提示", JOptionPane.YES_NO_OPTION);
			if (chip == 0)
			{
				JOptionPane.showMessageDialog(gameFrame, "欢迎下次再玩！再见！");
				System.exit(0);
			}
			return;
		}
	}

	// 键盘监听
	public void keyPressed(KeyEvent event)
	{

		int Counter = 0;// 计算器，判断是否移动了
		int NumCounter = 0;// 用于统计整个大方框中数字的个数，判断是否已满
		int NumNearCounter = 0;// 用于统计相邻格子数字相同的个数
		/*
		 * 方向键键值：左：37上：38右：39下：40
		 */

		hasBack = false;

		if (backup != null || backup.length != 0)
		{
			tempscore2 = tempscore;// 先把分数备份好
			// 下面的for循环调用java.util.Arrays.copyOf()方法复制数组，实现备份
			for (int i = 0; i < backup.length; i++)
			{
				liveup[i] = Arrays.copyOf(backup[i], backup[i].length);
			}
		}

		tempscore = score;// 先把分数备份好
		// 下面的for循环调用java.util.Arrays.copyOf()方法复制数组，实现备份
		for (int i = 0; i < numbers.length; i++)
		{
			backup[i] = Arrays.copyOf(numbers[i], numbers[i].length);
		}

		if (isWin == false)
		{
			switch (event.getKeyCode())
				{

				case 37:
					// 向左移动
					if (isSound == true)
						new PlaySoundThread("move.wav").start();
					for (int h = 0; h < 4; h++)
						for (int l = 0; l < 4; l++)
							if (numbers[h][l] != 0)
							{
								int temp = numbers[h][l];
								int pre = l - 1;
								while (pre >= 0 && numbers[h][pre] == 0)
								{
									numbers[h][pre] = temp;
									numbers[h][pre + 1] = 0;
									pre--;
									Counter++;
								}
							}
					for (int h = 0; h < 4; h++)
						for (int l = 0; l < 4; l++)
							if (l + 1 < 4 && (numbers[h][l] == numbers[h][l + 1])
									&& (numbers[h][l] != 0 || numbers[h][l + 1] != 0))
							{
								if (isSound == true)
									new PlaySoundThread("merge.wav").start();
								numbers[h][l] = numbers[h][l] + numbers[h][l + 1];
								numbers[h][l + 1] = 0;
								Counter++;
								score += numbers[h][l];
								if (numbers[h][l] == 2048)
								{
									isWin = true;
								}
							}

					for (int h = 0; h < 4; h++)
						for (int l = 0; l < 4; l++)
							if (numbers[h][l] != 0)
							{
								int temp = numbers[h][l];
								int pre = l - 1;
								while (pre >= 0 && numbers[h][pre] == 0)
								{
									numbers[h][pre] = temp;
									numbers[h][pre + 1] = 0;
									pre--;
									Counter++;
								}
							}
					break;

				case 39:// 向右移动
					if (isSound == true)
						new PlaySoundThread("move.wav").start();
					for (int h = 3; h >= 0; h--)
						for (int l = 3; l >= 0; l--)
							if (numbers[h][l] != 0)
							{
								int temp = numbers[h][l];
								int pre = l + 1;
								while (pre <= 3 && numbers[h][pre] == 0)
								{
									numbers[h][pre] = temp;
									numbers[h][pre - 1] = 0;
									pre++;
									Counter++;
								}
							}

					for (int h = 3; h >= 0; h--)
						for (int l = 3; l >= 0; l--)
							if (l + 1 < 4 && (numbers[h][l] == numbers[h][l + 1])
									&& (numbers[h][l] != 0 || numbers[h][l + 1] != 0))
							{
								if (isSound == true)
									new PlaySoundThread("merge.wav").start();
								numbers[h][l + 1] = numbers[h][l] + numbers[h][l + 1];
								numbers[h][l] = 0;
								Counter++;
								score += numbers[h][l + 1];
								if (numbers[h][l + 1] == 2048)
								{
									isWin = true;
								}
							}
					for (int h = 3; h >= 0; h--)
						for (int l = 3; l >= 0; l--)
							if (numbers[h][l] != 0)
							{
								int temp = numbers[h][l];
								int pre = l + 1;
								while (pre <= 3 && numbers[h][pre] == 0)
								{
									numbers[h][pre] = temp;
									numbers[h][pre - 1] = 0;
									pre++;
									Counter++;
								}
							}
					break;

				case 38:
					// 向上移动
					if (isSound == true)
						new PlaySoundThread("move.wav").start();
					for (int l = 0; l < 4; l++)
						for (int h = 0; h < 4; h++)
							if (numbers[h][l] != 0)
							{
								int temp = numbers[h][l];
								int pre = h - 1;
								while (pre >= 0 && numbers[pre][l] == 0)
								{
									numbers[pre][l] = temp;
									numbers[pre + 1][l] = 0;
									pre--;
									Counter++;
								}
							}
					for (int l = 0; l < 4; l++)
						for (int h = 0; h < 4; h++)
							if (h + 1 < 4 && (numbers[h][l] == numbers[h + 1][l])
									&& (numbers[h][l] != 0 || numbers[h + 1][l] != 0))
							{
								if (isSound == true)
									new PlaySoundThread("merge.wav").start();
								numbers[h][l] = numbers[h][l] + numbers[h + 1][l];
								numbers[h + 1][l] = 0;
								Counter++;
								score += numbers[h][l];
								if (numbers[h][l] == 2048)
								{
									isWin = true;
								}
							}

					for (int l = 0; l < 4; l++)
						for (int h = 0; h < 4; h++)
							if (numbers[h][l] != 0)
							{
								int temp = numbers[h][l];
								int pre = h - 1;
								while (pre >= 0 && numbers[pre][l] == 0)
								{
									numbers[pre][l] = temp;
									numbers[pre + 1][l] = 0;
									pre--;
									Counter++;
								}
							}
					break;

				case 40:
					// 向下移动
					if (isSound == true)
						new PlaySoundThread("move.wav").start();
					for (int l = 3; l >= 0; l--)
						for (int h = 3; h >= 0; h--)
							if (numbers[h][l] != 0)
							{
								int temp = numbers[h][l];
								int pre = h + 1;
								while (pre <= 3 && numbers[pre][l] == 0)
								{
									numbers[pre][l] = temp;
									numbers[pre - 1][l] = 0;
									pre++;
									Counter++;
								}
							}
					for (int l = 3; l >= 0; l--)
						for (int h = 3; h >= 0; h--)
							if (h + 1 < 4 && (numbers[h][l] == numbers[h + 1][l])
									&& (numbers[h][l] != 0 || numbers[h + 1][l] != 0))
							{
								if (isSound == true)
									new PlaySoundThread("merge.wav").start();
								numbers[h + 1][l] = numbers[h][l] + numbers[h + 1][l];
								numbers[h][l] = 0;
								Counter++;
								score += numbers[h + 1][l];
								if (numbers[h + 1][l] == 2048)
								{
									isWin = true;
								}
							}

					for (int l = 3; l >= 0; l--)
						for (int h = 3; h >= 0; h--)
							if (numbers[h][l] != 0)
							{
								int temp = numbers[h][l];
								int pre = h + 1;
								while (pre <= 3 && numbers[pre][l] == 0)
								{
									numbers[pre][l] = temp;
									numbers[pre - 1][l] = 0;
									pre++;
									Counter++;
								}
							}
					break;

				}

			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 3; j++)
				{
					if (numbers[i][j] == numbers[i][j + 1] && numbers[i][j] != 0)
					{
						NumNearCounter++;
					}
					if (numbers[i][j] == numbers[i + 1][j] && numbers[i][j] != 0)
					{
						NumNearCounter++;
					}
					if (numbers[3][j] == numbers[3][j + 1] && numbers[3][j] != 0)
					{
						NumNearCounter++;
					}
					if (numbers[i][3] == numbers[i + 1][3] && numbers[i][3] != 0)
					{
						NumNearCounter++;
					}
				}
			}
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 4; j++)
				{
					if (numbers[i][j] != 0)
					{
						NumCounter++;
					}
				}
			}
			if (Counter > 0)
			{

				scoreLabel.setText("分数：" + score);
				int r1 = rand.nextInt(4);
				int c1 = rand.nextInt(4);
				while (numbers[r1][c1] != 0)
				{
					r1 = rand.nextInt(4);
					c1 = rand.nextInt(4);
				}
				int value1 = rand.nextInt(2) * 2 + 2;
				numbers[r1][c1] = value1;
			}

			if (isWin == true)
			{
				gameFrame.paint(gameFrame.getGraphics());
				JOptionPane.showMessageDialog(gameFrame, "恭喜你赢了!\n您的最终得分为：" + score);
			}

			if (NumCounter == 16 && NumNearCounter == 0)
			{
				relive = true;
				JOptionPane.showMessageDialog(gameFrame,
						"没地方可以合并咯!!" + "\n很遗憾，您输了~>_<~" + "\n悄悄告诉你，游戏有起死回生功能哦，不信你“退一步”试试？" + "\n说不定能扭转乾坤捏 （^_~）");
			}
			gameFrame.paint(gameFrame.getGraphics());
		}

	}

}
