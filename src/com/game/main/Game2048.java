package com.game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 * 2048��Ϸ
 * 
 * @author ordinary-students
 *
 */
public class Game2048 extends JFrame
{

	private static final long serialVersionUID = 1L;
	// ���ڴ�����ݵ�����
	private int Numbers[][] = new int[4][4];

	public static void main(String[] args)
	{
		final Game2048 UI = new Game2048();
		UI.initUI();
		// ���ô���ر�
		UI.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				JOptionPane.showMessageDialog(UI, "��ӭ�´����棡�ټ���");
				System.exit(0);
			}
		});
	}

	/**
	 * ��ʼ������
	 */
	private void initUI()
	{
		this.setTitle("2048С��Ϸ");
		this.setLocation(450, 100);
		this.setSize(400, 500);
		this.setLayout(null);

		// ��ʼ��Ϸ��ť
		ImageIcon imgicon = new ImageIcon("res/start.png");
		JButton bt = new JButton(imgicon);
		bt.setToolTipText("��ʼ����Ϸ");
		bt.setFocusable(false);
		bt.setBorderPainted(false);
		bt.setFocusPainted(false);
		bt.setContentAreaFilled(false);
		bt.setBounds(5, 10, 120, 30);// ���ð�ť��x��y����λ�úͿ����߶�
		this.add(bt);

		// ����һ����ť
		ImageIcon backicon = new ImageIcon("res/backicon.png");
		JButton back = new JButton(backicon);
		back.setToolTipText("����һ��");
		back.setFocusable(false);
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);
		back.setBounds(270, 10, 120, 30);// ���ð�ť��x��y����λ�úͿ����߶�
		this.add(back);

		// ���ڰ�ť
		ImageIcon imgicon2 = new ImageIcon("res/about.png");
		JButton about = new JButton(imgicon2);
		about.setToolTipText("����");
		about.setFocusable(false);
		about.setBorderPainted(false);
		about.setFocusPainted(false);
		about.setContentAreaFilled(false);
		about.setBounds(160, 10, 70, 30);
		this.add(about);

		// ������ʾ
		JLabel lb = new JLabel("������0");
		lb.setBounds(40, 45, 120, 30);
		lb.setFont(new Font("��Բ", Font.CENTER_BASELINE, 18));
		lb.setForeground(new Color(0x000000));
		this.add(lb);

		// ������ťѡ��
		JCheckBox isSoundBox = new JCheckBox("����");
		isSoundBox.setBounds(290, 45, 120, 30);
		isSoundBox.setFont(new Font("��Բ", Font.CENTER_BASELINE, 18));
		isSoundBox.setFocusable(false);
		isSoundBox.setBorderPainted(false);
		isSoundBox.setFocusPainted(false);
		isSoundBox.setContentAreaFilled(false);
		this.add(isSoundBox);

		// �Ҽ��˵�
		final JPopupMenu popup = new JPopupMenu();// �����Ҽ��˵�
		JMenuItem m1 = new JMenuItem("��һ��");
		JMenuItem m2 = new JMenuItem("����Ϸ");
		JMenuItem m3 = new JMenuItem("����");
		JMenuItem m4 = new JMenuItem("�˳�");
		m1.setFont(new Font("����", Font.CENTER_BASELINE, 18));
		m2.setFont(new Font("����", Font.CENTER_BASELINE, 18));
		m3.setFont(new Font("����", Font.CENTER_BASELINE, 18));
		m4.setFont(new Font("����", Font.CENTER_BASELINE, 18));

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
				{// �����Ҽ��˵�
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		this.setDefaultCloseOperation(3);
		this.setResizable(false);
		this.setVisible(true);// ��ʾ����

		// �����¼�������
		MyListener cl = new MyListener(this, Numbers, lb, bt, about, back, isSoundBox, m1, m2, m3, m4);
		bt.addActionListener(cl);
		about.addActionListener(cl);
		back.addActionListener(cl);
		isSoundBox.addActionListener(cl);
		m1.addActionListener(cl);
		m2.addActionListener(cl);
		m3.addActionListener(cl);
		m4.addActionListener(cl);
		this.addKeyListener(cl);

	}

	// ��д����
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(new Color(0xBBADA0));
		g.fillRoundRect(15, 110, 370, 370, 15, 15);// ����ο�

		g.setColor(new Color(0xCDC1B4));
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				g.fillRoundRect(25 + i * 90, 120 + j * 90, 80, 80, 15, 15);// С���ο�
			}
		}

		// �������ֵ�λ�ò���ɫ
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

					g.fillRoundRect(25 + i * 90, 120 + j * 90, 80, 80, 15, 15);// С���ο���ɫ
					g.setColor(new Color(0x000000));
					g.setFont(new Font("Kristen ITC", Font.PLAIN, FontSize));
					g.drawString(Numbers[j][i] + "", 25 + i * 90 + 30 + MoveX, 120 + j * 90 + 50 + MoveY);
				}
			}
		}
	}

}
