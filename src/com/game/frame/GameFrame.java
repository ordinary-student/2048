package com.game.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.game.main.MyListener;
import com.game.utils.WindowUtil;

/**
 * 2048游戏
 * 
 * @author ordinary-student
 *
 */
public class GameFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	// 用于存放数据的数组
	private int Numbers[][] = new int[4][4];

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
		JCheckBox isSoundBox = new JCheckBox("静音");
		isSoundBox.setBounds(290, 45, 120, 30);
		isSoundBox.setFont(new Font("幼圆", Font.CENTER_BASELINE, 18));
		isSoundBox.setFocusable(false);
		isSoundBox.setBorderPainted(false);
		isSoundBox.setFocusPainted(false);
		isSoundBox.setContentAreaFilled(false);
		this.add(isSoundBox);

		// 右键菜单
		final JPopupMenu popup = new JPopupMenu();// 弹出右键菜单
		JMenuItem m1 = new JMenuItem("退一步");
		JMenuItem m2 = new JMenuItem("新游戏");
		JMenuItem m3 = new JMenuItem("帮助");
		JMenuItem m4 = new JMenuItem("退出");
		m1.setFont(new Font("粗体", Font.CENTER_BASELINE, 18));
		m2.setFont(new Font("粗体", Font.CENTER_BASELINE, 18));
		m3.setFont(new Font("粗体", Font.CENTER_BASELINE, 18));
		m4.setFont(new Font("粗体", Font.CENTER_BASELINE, 18));

		// popup.add(m1);
		// popup.add(m2);
		// popup.add(m3);
		// popup.addSeparator();
		// popup.add(m4);

		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON3)
				{// 弹出右键菜单
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		// 创建事件处理类
		MyListener cl = new MyListener(this, Numbers, scoreLabel, startButton, aboutButton, backButton, isSoundBox, m1,
				m2, m3, m4);
		startButton.addActionListener(cl);
		aboutButton.addActionListener(cl);
		backButton.addActionListener(cl);
		isSoundBox.addActionListener(cl);
		m1.addActionListener(cl);
		m2.addActionListener(cl);
		m3.addActionListener(cl);
		m4.addActionListener(cl);
		this.addKeyListener(cl);

		validate();
		// 显示界面
		setVisible(true);

	}

	// 重写窗体
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(new Color(0xBBADA0));
		g.fillRoundRect(15, 110, 370, 370, 15, 15);// 大矩形框

		g.setColor(new Color(0xCDC1B4));
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				g.fillRoundRect(25 + i * 90, 120 + j * 90, 80, 80, 15, 15);// 小矩形框
			}
		}

		// 调整数字的位置并上色
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (Numbers[j][i] != 0)
				{
					int FontSize = 30;
					int MoveX = 0, MoveY = 0;
					switch (Numbers[j][i])
						{
						case 2:
							g.setColor(new Color(0xeee4da));
							FontSize = 30;
							MoveX = 0;
							MoveY = 0;
							break;
						case 4:
							g.setColor(new Color(0xede0c8));
							FontSize = 30;
							MoveX = 0;
							MoveY = 0;
							break;
						case 8:
							g.setColor(new Color(0xf2b179));
							FontSize = 30;
							MoveX = 0;
							MoveY = 0;
							break;
						case 16:
							g.setColor(new Color(0xf59563));
							FontSize = 29;
							MoveX = -5;
							MoveY = 0;
							break;
						case 32:
							g.setColor(new Color(0xf67c5f));
							FontSize = 29;
							MoveX = -5;
							MoveY = 0;
							break;
						case 64:
							g.setColor(new Color(0xf65e3b));
							FontSize = 29;
							MoveX = -5;
							MoveY = 0;
							break;
						case 128:
							g.setColor(new Color(0xedcf72));
							FontSize = 28;
							MoveX = -10;
							MoveY = 0;
							break;
						case 256:
							g.setColor(new Color(0xedcc61));
							FontSize = 28;
							MoveX = -10;
							MoveY = 0;
							break;
						case 512:
							g.setColor(new Color(0xedc850));
							FontSize = 28;
							MoveX = -10;
							MoveY = 0;
							break;
						case 1024:
							g.setColor(new Color(0xedc53f));
							FontSize = 27;
							MoveX = -15;
							MoveY = 0;
							break;
						case 2048:
							g.setColor(new Color(0xedc22e));
							FontSize = 27;
							MoveX = -15;
							MoveY = 0;
							break;
						default:
							g.setColor(new Color(0x000000));
							break;
						}

					g.fillRoundRect(25 + i * 90, 120 + j * 90, 80, 80, 15, 15);// 小矩形框上色
					g.setColor(new Color(0x000000));
					g.setFont(new Font("Kristen ITC", Font.PLAIN, FontSize));
					g.drawString(Numbers[j][i] + "", 25 + i * 90 + 30 + MoveX, 120 + j * 90 + 50 + MoveY);
				}
			}
		}
	}

}
