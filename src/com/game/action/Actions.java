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

	// 随机种子
	private Random rand = new Random();

	// 存放数据的数组
	private int numbers[][];
	// 用于备份数组，供回退时使用
	private int[][] backup = new int[4][4];
	// 用于备份数组，供起死回生时使用
	private int[][] liveup = new int[4][4];

	// 分数标签
	private JLabel scoreLabel;
	// 分数
	private int score = 0;
	// 记录回退的分数值
	private int tempScore, tempScore2;
	// 按钮
	private JButton startButton, aboutButton, backButton;
	// 静音勾选框
	private JCheckBox soundCheckBok;
	// 标志
	private boolean isWin = false, relive = false, hasBack = false, soundFlag = true;

	/*
	 * 构造方法
	 */
	public Actions(GameFrame gameFrame, int[][] numbers, JLabel scoreLabel, JButton startButton, JButton aboutButton,
			JButton backButton, JCheckBox soundCheckBok)
	{
		this.gameFrame = gameFrame;
		this.numbers = numbers;
		this.scoreLabel = scoreLabel;
		this.startButton = startButton;
		this.aboutButton = aboutButton;
		this.backButton = backButton;
		this.soundCheckBok = soundCheckBok;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// 判断来源
		if (e.getSource() == startButton)
		{
			// 新游戏
			newGame();

		} else if (e.getSource() == aboutButton)
		{
			// 显示关于信息
			showAboutDialog();

		} else if ((e.getSource() == backButton) && (hasBack == true))
		{
			// 回退
			backup();

		} else if (e.getSource().equals(soundCheckBok))
		{
			soundFlag = !soundCheckBok.isSelected();
		}
	}

	/**
	 * 新游戏
	 */
	private void newGame()
	{
		// 重置标志
		isWin = false;
		// 重置数字
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				numbers[i][j] = 0;
			}
		}

		// 重置分数
		score = 0;
		scoreLabel.setText("分数：" + score);

		// 生成随机位置
		// 随机行号
		int randomRow1 = rand.nextInt(4);
		int randomRow2 = rand.nextInt(4);
		// 随机列号
		int randomColumn1 = rand.nextInt(4);
		int randomColumn2 = rand.nextInt(4);

		// 当有重复时，重新生成
		while ((randomRow1 == randomRow2) && (randomColumn1 == randomColumn2))
		{
			randomRow2 = rand.nextInt(4);
			randomColumn2 = rand.nextInt(4);
		}

		// 生成随机数字（2或者4）
		int randomNum1 = rand.nextInt(2) * 2 + 2;
		int randomNum2 = rand.nextInt(2) * 2 + 2;

		// 把随机数字存进对应的随机位置
		numbers[randomRow1][randomColumn1] = randomNum1;
		numbers[randomRow2][randomColumn2] = randomNum2;
		// 绘制
		gameFrame.paint(gameFrame.getGraphics());
	}

	/**
	 * 显示关于窗口
	 */
	private void showAboutDialog()
	{
		JOptionPane.showMessageDialog(gameFrame,
				"游戏规则：\r\n" + "开始时棋盘内随机出现两个数字，出现的数字仅可能为2或4\r\n" + "玩家可以选择上下左右四个方向，若棋盘内的数字出现位移或合并，视为有效移动\r\n"
						+ "玩家选择的方向上若有相同的数字则合并，每次有效移动可以同时合并，但不可以连续合并\r\n" + "合并所得的所有新生成数字想加即为该步的有效得分\r\n"
						+ "玩家选择的方向行或列前方有空格则出现位移\r\n" + "每有效移动一步，棋盘的空位(无数字处)随机出现一个数字(依然可能为2或4)\r\n"
						+ "棋盘被数字填满，无法进行有效移动，判负，游戏结束\r\n" + "棋盘上出现2048，判胜，游戏结束。\r\n");
	}

	/**
	 * 回退
	 */
	private void backup()
	{
		// 设置不可回退，只能回退一次
		hasBack = false;

		// 可起死回生
		if (relive)
		{
			// 回退分数
			score = tempScore2;
			scoreLabel.setText("分数：" + score);
			// 回退数据
			for (int i = 0; i < liveup.length; i++)
			{
				numbers[i] = Arrays.copyOf(liveup[i], liveup[i].length);
			}

			relive = false;
		} else
		{
			// 回退分数
			score = tempScore;
			scoreLabel.setText("分数：" + score);
			// 回退数据
			for (int i = 0; i < backup.length; i++)
			{
				numbers[i] = Arrays.copyOf(backup[i], backup[i].length);
			}
		}

		// 重新绘制
		gameFrame.paint(gameFrame.getGraphics());
	}

	// 键盘监听
	@Override
	public void keyPressed(KeyEvent event)
	{
		// 判断是否移动了
		boolean isMove = false;
		// 用于统计整个大方框中数字的个数，判断是否已满
		// int numberCount = 0;
		// 判断相邻格子数字是否相同
		boolean hasSameNumber = false;

		// 备份分数和数据
		backupData();

		// 没赢才进去
		if (!isWin)
		{
			// 判断按键方向
			switch (event.getKeyCode())
				{

				case KeyEvent.VK_LEFT:
					{
						// 向左移动
						isMove = moveLeft();
						break;
					}

				case KeyEvent.VK_RIGHT:
					{
						// 向右移动
						isMove = moveRight();
						break;
					}

				case KeyEvent.VK_UP:
					{
						// 向上移动
						isMove = moveUp();
						break;
					}

				case KeyEvent.VK_DOWN:
					{
						// 向下移动
						isMove = moveDown();
						break;
					}
				}

			// 判断是否有相同的相邻数字
			hasSameNumber = hasSameAdjacentNumbers();

			// 遍历-判断数字是否已经填满
			isFilled();

			// 是否有移动
			if (isMove)
			{
				// 显示分数
				scoreLabel.setText("分数：" + score);
				// 生成随机位置
				int randomRow = rand.nextInt(4);
				int randomCol = rand.nextInt(4);

				// 如果该位置已经有数字，重新生成
				while (numbers[randomRow][randomCol] != 0)
				{
					randomRow = rand.nextInt(4);
					randomCol = rand.nextInt(4);
					System.out.println(randomRow);
				}

				// 生产随机数字2或4
				int randomNum = rand.nextInt(2) * 2 + 2;
				numbers[randomRow][randomCol] = randomNum;
			}

			// 判断是否胜利
			if (isWin)
			{
				gameFrame.paint(gameFrame.getGraphics());
				JOptionPane.showMessageDialog(gameFrame, "恭喜你赢了!\r\n您的最终得分为：" + score);
			}

			// 若格子已经填满且相邻数字没有相同的，判负
			if ((isFilled()) && (!hasSameNumber))
			{
				relive = true;
				JOptionPane.showMessageDialog(gameFrame, "没地方可以合并啦!\r\n很遗憾，您输了~>_<~\r\n您可以尝试退一步看看\r\n说不定能扭转乾坤捏 （^_~）");
			}

			// 绘制
			gameFrame.paint(gameFrame.getGraphics());
		}

		// 设置可回退
		hasBack = true;

	}

	/**
	 * 备份数据
	 */
	private void backupData()
	{
		// 备份数据和分数
		// 备份复活数据
		if (backup != null || backup.length != 0)
		{
			// 备份复活分数
			tempScore2 = tempScore;
			// 复制数组，备份数据
			for (int i = 0; i < backup.length; i++)
			{
				liveup[i] = Arrays.copyOf(backup[i], backup[i].length);
			}
		}

		// 备份回退分数
		tempScore = score;
		// 复制数组，备份回退数据
		for (int i = 0; i < numbers.length; i++)
		{
			backup[i] = Arrays.copyOf(numbers[i], numbers[i].length);
		}

	}

	/**
	 * 判断是否有相同的相邻数字
	 * 
	 * @return
	 */
	private boolean hasSameAdjacentNumbers()
	{
		// 标志
		boolean hasSameNumber = false;

		// 遍历-判断相邻数字是否相同
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if ((numbers[i][j] == numbers[i][j + 1]) && (numbers[i][j] != 0))
				{
					hasSameNumber = true;
				}

				if ((numbers[i][j] == numbers[i + 1][j]) && (numbers[i][j] != 0))
				{
					hasSameNumber = true;
				}

				if ((numbers[3][j] == numbers[3][j + 1]) && (numbers[3][j] != 0))
				{
					hasSameNumber = true;
				}

				if ((numbers[i][3] == numbers[i + 1][3]) && (numbers[i][3] != 0))
				{
					hasSameNumber = true;
				}
			}
		}

		// 返回
		return hasSameNumber;
	}

	/**
	 * 判断是否已经填满所有格子
	 * 
	 * @return
	 */
	private boolean isFilled()
	{
		// 用于统计整个大方框中数字的个数，判断是否已满
		int numberCount = 0;
		// 遍历-判断数字是否已经填满
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (numbers[i][j] != 0)
				{
					numberCount++;
				}
			}
		}

		// 判断是否填满
		if (numberCount == 16)
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * 向左移动
	 */
	private boolean moveLeft()
	{
		// 移动标志
		boolean isMove = false;

		// 播放移动音效
		if (soundFlag)
		{
			new PlaySoundThread("move.wav").start();
		}

		// 遍历-数字向左移动到尽头
		for (int row = 0; row < 4; row++)
		{
			for (int col = 0; col < 4; col++)
			{
				// 数字不为零
				if (numbers[row][col] != 0)
				{

					int temp = numbers[row][col];
					// 前一列
					int preCol = col - 1;
					// 前一列存在且数字为0，则交换数字
					while ((preCol >= 0) && (numbers[row][preCol] == 0))
					{
						// 交换数字
						numbers[row][preCol] = temp;
						numbers[row][preCol + 1] = 0;
						// 继续查找前一列，直至数字向左移动到尽头
						preCol--;
						// 更改移动标志
						isMove = true;
					}
				}
			}
		}

		// 遍历-合并相同数字
		for (int row = 0; row < 4; row++)
		{
			for (int col = 0; col < 4; col++)
			{
				// 若相邻两数字相等，且不为零
				if ((col + 1 < 4) && (numbers[row][col] == numbers[row][col + 1])
						&& ((numbers[row][col] != 0) || (numbers[row][col + 1] != 0)))
				{
					// 播放合并音效
					if (soundFlag)
					{
						new PlaySoundThread("merge.wav").start();
					}

					// 向左合并数字
					numbers[row][col] = numbers[row][col] + numbers[row][col + 1];
					// 右列变0
					numbers[row][col + 1] = 0;

					// 更改移动标志
					isMove = true;

					// 加分数
					score = score + numbers[row][col];

					// 若出现2048则胜利
					if (numbers[row][col] == 2048)
					{
						isWin = true;
					}
				}
			}
		}

		// 遍历-数字向左移动到尽头
		for (int row = 0; row < 4; row++)
		{
			for (int col = 0; col < 4; col++)
			{
				// 数字不为零
				if (numbers[row][col] != 0)
				{
					int temp = numbers[row][col];
					// 前一列
					int preCol = col - 1;
					// 前一列存在且数字为0，则交换数字
					while ((preCol >= 0) && (numbers[row][preCol] == 0))
					{
						// 交换数字
						numbers[row][preCol] = temp;
						numbers[row][preCol + 1] = 0;
						// 继续查找前一列，直至数字向左移动到尽头
						preCol--;
						// 更改移动标志
						isMove = true;
					}
				}
			}
		}

		// 返回移动标志
		return isMove;

	}

	/**
	 * 向右移动
	 */
	private boolean moveRight()
	{
		// 移动标志
		boolean isMove = false;
		// 播放移动音效
		if (soundFlag)
		{
			new PlaySoundThread("move.wav").start();
		}

		// 遍历-数字向右移至尽头
		for (int row = 3; row >= 0; row--)
		{
			for (int col = 3; col >= 0; col--)
			{
				// 不为零
				if (numbers[row][col] != 0)
				{
					int temp = numbers[row][col];
					int nextCol = col + 1;
					// 交换数字
					while ((nextCol <= 3) && (numbers[row][nextCol] == 0))
					{
						numbers[row][nextCol] = temp;
						numbers[row][nextCol - 1] = 0;
						nextCol++;
						isMove = true;
					}
				}
			}
		}

		// 遍历-向右合并相同数字
		for (int row = 3; row >= 0; row--)
		{
			for (int col = 3; col >= 0; col--)
			{
				if ((col + 1 < 4) && (numbers[row][col] == numbers[row][col + 1])
						&& ((numbers[row][col] != 0) || (numbers[row][col + 1] != 0)))
				{
					// 播放合并音效
					if (soundFlag)
					{
						new PlaySoundThread("merge.wav").start();
					}

					// 合并数字
					numbers[row][col + 1] = numbers[row][col] + numbers[row][col + 1];
					numbers[row][col] = 0;

					isMove = true;

					// 加分数
					score = score + numbers[row][col + 1];
					// 判断是否胜利
					if (numbers[row][col + 1] == 2048)
					{
						isWin = true;
					}
				}
			}
		}

		// 遍历-数字向右移至尽头
		for (int row = 3; row >= 0; row--)
		{
			for (int col = 3; col >= 0; col--)
			{
				if (numbers[row][col] != 0)
				{
					int temp = numbers[row][col];
					int nextCol = col + 1;
					while ((nextCol <= 3) && (numbers[row][nextCol] == 0))
					{
						numbers[row][nextCol] = temp;
						numbers[row][nextCol - 1] = 0;
						nextCol++;
						isMove = true;
					}
				}
			}
		}

		// 返回移动标志
		return isMove;
	}

	/**
	 * 向上移动
	 */
	private boolean moveUp()
	{
		// 移动标志
		boolean isMove = false;

		// 播放移动音效
		if (soundFlag == true)
		{
			new PlaySoundThread("move.wav").start();
		}

		// 遍历-数字向上移至尽头
		for (int col = 0; col < 4; col++)
		{
			for (int row = 0; row < 4; row++)
			{
				if (numbers[row][col] != 0)
				{
					int temp = numbers[row][col];
					// 前一行
					int preRow = row - 1;
					// 交换数字
					while ((preRow >= 0) && (numbers[preRow][col] == 0))
					{
						numbers[preRow][col] = temp;
						numbers[preRow + 1][col] = 0;
						preRow--;
						isMove = true;
					}
				}
			}
		}

		// 遍历-向上合并相同的数字
		for (int col = 0; col < 4; col++)
		{
			for (int row = 0; row < 4; row++)
			{
				if ((row + 1 < 4) && (numbers[row][col] == numbers[row + 1][col])
						&& ((numbers[row][col] != 0) || (numbers[row + 1][col] != 0)))
				{
					// 播放合并音效
					if (soundFlag == true)
					{
						new PlaySoundThread("merge.wav").start();
					}

					// 向上合并相同数字
					numbers[row][col] = numbers[row][col] + numbers[row + 1][col];
					numbers[row + 1][col] = 0;

					isMove = true;

					// 加分数
					score = score + numbers[row][col];

					// 判断
					if (numbers[row][col] == 2048)
					{
						isWin = true;
					}
				}
			}
		}

		// 遍历-数字向上移至尽头
		for (int col = 0; col < 4; col++)
		{
			for (int row = 0; row < 4; row++)
			{
				if (numbers[row][col] != 0)
				{
					int temp = numbers[row][col];
					// 前一行
					int preRow = row - 1;
					// 交换数字
					while ((preRow >= 0) && (numbers[preRow][col] == 0))
					{
						numbers[preRow][col] = temp;
						numbers[preRow + 1][col] = 0;
						preRow--;
						isMove = true;
					}
				}
			}
		}

		// 返回
		return isMove;
	}

	/**
	 * 向下移动
	 */
	private boolean moveDown()
	{
		// 移动标志
		boolean isMove = false;

		// 播放移动音效
		if (soundFlag)
		{
			new PlaySoundThread("move.wav").start();
		}

		// 遍历-数字向下移至尽头
		for (int col = 3; col >= 0; col--)
		{
			for (int row = 3; row >= 0; row--)
			{
				// 交换数字
				if (numbers[row][col] != 0)
				{
					int temp = numbers[row][col];
					// 下一行
					int nextRow = row + 1;
					// 交换数字
					while ((nextRow <= 3) && (numbers[nextRow][col] == 0))
					{
						numbers[nextRow][col] = temp;
						numbers[nextRow - 1][col] = 0;
						nextRow++;
						isMove = true;
					}
				}
			}
		}

		// 遍历-向下合并相同数字
		for (int col = 3; col >= 0; col--)
		{
			for (int row = 3; row >= 0; row--)
			{
				if ((row + 1 < 4) && (numbers[row][col] == numbers[row + 1][col])
						&& ((numbers[row][col] != 0) || (numbers[row + 1][col] != 0)))
				{
					// 播放合并音效
					if (soundFlag)
					{
						new PlaySoundThread("merge.wav").start();
					}

					// 合并
					numbers[row + 1][col] = numbers[row][col] + numbers[row + 1][col];
					numbers[row][col] = 0;

					isMove = true;

					// 加分
					score = score + numbers[row + 1][col];

					// 判断
					if (numbers[row + 1][col] == 2048)
					{
						isWin = true;
					}
				}
			}
		}

		// 遍历-数字向下移至尽头
		for (int col = 3; col >= 0; col--)
		{
			for (int row = 3; row >= 0; row--)
			{
				// 交换数字
				if (numbers[row][col] != 0)
				{
					int temp = numbers[row][col];
					int nextRow = row + 1;

					while ((nextRow <= 3) && (numbers[nextRow][col] == 0))
					{
						numbers[nextRow][col] = temp;
						numbers[nextRow - 1][col] = 0;
						nextRow++;
						isMove = true;
					}
				}
			}
		}

		// 返回
		return isMove;
	}

}
