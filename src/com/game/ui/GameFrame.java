package com.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.game.action.Actions;
import com.game.utils.WindowUtil;

/**
 * 2048小游戏
 * 
 * @author ordinary-student
 *
 */
public class GameFrame extends KFrame
{
	private static final long serialVersionUID = -3578214145718737571L;
	// 用于存放数据的数组
	private int[][] numbers = new int[4][4];

	/*
	 * 构造方法
	 */
	public GameFrame()
	{
		// 初始化界面
		initUI();
	}

	/**
	 * 初始化界面
	 */
	public void initUI()
	{
		// 设置标题
		setTitle("2048小游戏");
		// 设置大小
		setSize(400, 500);
		// 不可改变大小
		setResizable(false);
		// 设置居中
		WindowUtil.center(this);
		// 设置布局
		setLayout(null);
		// 设置关闭方式
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 开始游戏按钮
		ImageIcon startIcon = new ImageIcon("res/start.png");
		JButton startButton = new JButton(startIcon);
		startButton.setToolTipText("开始新游戏");
		startButton.setBounds(5, 10, 120, 30);
		startButton.setFocusable(false);
		startButton.setBorderPainted(false);
		startButton.setFocusPainted(false);
		startButton.setContentAreaFilled(false);
		getContentPane().add(startButton);

		// 后退一步按钮
		ImageIcon backIcon = new ImageIcon("res/back.png");
		JButton backButton = new JButton(backIcon);
		backButton.setToolTipText("后退一步");
		backButton.setBounds(270, 10, 120, 30);
		backButton.setFocusable(false);
		backButton.setBorderPainted(false);
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		getContentPane().add(backButton);

		// 关于按钮
		ImageIcon aboutIcon = new ImageIcon("res/about.png");
		JButton aboutButton = new JButton(aboutIcon);
		aboutButton.setToolTipText("帮助");
		aboutButton.setBounds(160, 10, 70, 30);
		aboutButton.setFocusable(false);
		aboutButton.setBorderPainted(false);
		aboutButton.setFocusPainted(false);
		aboutButton.setContentAreaFilled(false);
		getContentPane().add(aboutButton);

		// 分数显示
		JLabel scoreLabel = new JLabel("分数：0");
		scoreLabel.setBounds(40, 45, 120, 30);
		scoreLabel.setFont(new Font("幼圆", Font.CENTER_BASELINE, 18));
		scoreLabel.setForeground(new Color(0x000000));
		getContentPane().add(scoreLabel);

		// 静音按钮选项
		JCheckBox soundCheckBox = new JCheckBox("静音");
		soundCheckBox.setBounds(290, 45, 120, 30);
		soundCheckBox.setFont(new Font("幼圆", Font.CENTER_BASELINE, 18));
		soundCheckBox.setFocusable(false);
		soundCheckBox.setBorderPainted(false);
		soundCheckBox.setFocusPainted(false);
		soundCheckBox.setContentAreaFilled(false);
		getContentPane().add(soundCheckBox);

		// 创建事件处理类并添加监听
		Actions actions = new Actions(this, numbers, scoreLabel, startButton, aboutButton, backButton, soundCheckBox);
		startButton.addActionListener(actions);
		aboutButton.addActionListener(actions);
		backButton.addActionListener(actions);
		soundCheckBox.addActionListener(actions);

		// 添加按键监听
		addKeyListener(actions);

		validate();
		// 显示界面
		setVisible(true);

	}

	// 重写窗体
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		// 设置颜色
		g.setColor(new Color(0xBBADA0));
		// 大矩形框
		g.fillRoundRect(15, 110, 370, 370, 15, 15);

		// 设置颜色
		g.setColor(new Color(0xCDC1B4));
		// 小矩形框
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				g.fillRoundRect(25 + i * 90, 120 + j * 90, 80, 80, 15, 15);
			}
		}

		// 调整数字的位置并上色
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (numbers[j][i] != 0)
				{
					int fontSize = 30;
					int move_x = 0, move_y = 0;
					// 判断数字，数字越大，颜色越深
					switch (numbers[j][i])
						{
						case 2:
							{
								g.setColor(new Color(0xeee4da));
								fontSize = 30;
								move_x = 0;
								move_y = 0;
								break;
							}

						case 4:
							{
								g.setColor(new Color(0xede0c8));
								fontSize = 30;
								move_x = 0;
								move_y = 0;
								break;
							}

						case 8:
							{
								g.setColor(new Color(0xf2b179));
								fontSize = 30;
								move_x = 0;
								move_y = 0;
								break;
							}

						case 16:
							{
								g.setColor(new Color(0xf59563));
								fontSize = 29;
								move_x = -5;
								move_y = 0;
								break;
							}

						case 32:
							{
								g.setColor(new Color(0xf67c5f));
								fontSize = 29;
								move_x = -5;
								move_y = 0;
								break;
							}

						case 64:
							{
								g.setColor(new Color(0xf65e3b));
								fontSize = 29;
								move_x = -5;
								move_y = 0;
								break;
							}

						case 128:
							{
								g.setColor(new Color(0xedcf72));
								fontSize = 28;
								move_x = -10;
								move_y = 0;
								break;
							}

						case 256:
							{
								g.setColor(new Color(0xedcc61));
								fontSize = 28;
								move_x = -10;
								move_y = 0;
								break;
							}

						case 512:
							{
								g.setColor(new Color(0xedc850));
								fontSize = 28;
								move_x = -10;
								move_y = 0;
								break;
							}

						case 1024:
							{
								g.setColor(new Color(0xedc53f));
								fontSize = 27;
								move_x = -15;
								move_y = 0;
								break;
							}

						case 2048:
							{
								g.setColor(new Color(0xedc22e));
								fontSize = 27;
								move_x = -15;
								move_y = 0;
								break;
							}

						default:
							{
								g.setColor(new Color(0x000000));
								break;
							}
						}

					// 小矩形框上色
					g.fillRoundRect(25 + i * 90, 120 + j * 90, 80, 80, 15, 15);
					g.setColor(new Color(0x000000));
					g.setFont(new Font("Kristen ITC", Font.PLAIN, fontSize));
					g.drawString(numbers[j][i] + "", 25 + i * 90 + 30 + move_x, 120 + j * 90 + 50 + move_y);
				}
			}
		}
	}

}
