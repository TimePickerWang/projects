package snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake {
	JFrame frame;
	MyJpanel mjp;
	private static final int Boundary = 330;

	public Snake() {
		frame = new JFrame("亮 闪 闪 贪 吃 蛇");
		mjp = new MyJpanel();
		mjp.setFocusable(true); // 设置面板焦距监听键盘
		frame.getContentPane().add(mjp, BorderLayout.CENTER);
		frame.setSize(Boundary - 22, Boundary + 120);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // 窗口居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭退出
	}
}

/*----------------------------------------------------------------------------------*/

class MyJpanel extends JPanel implements ActionListener {
	Timer time;
	ArrayList<Point> list, listBody;
	private static final int Rect = 10; // 蛇的大小
	private static final int Boundary = 300; // 面板的大小

	private static int x = 10; // 蛇头x，y坐标
	private static int y = 10;

	private static int dx = 10; // 移动坐标
	private static int dy = 0;

	private static int fx = (int) (Math.random() * 10) * 30; // 食物坐标
	private static int fy = (int) (Math.random() * 10) * 30;
	private boolean flagzy = false;
	private boolean flagsx = true;
	// 蛇初始为向右移动，所以flagzy==false
	// 如果flagzy==false代表不能按左右键
	// 如果flagsx==true代表能按上下键
	private boolean flagzt = true; // 暂停开关
	private boolean flagwd = true; // 无敌模式开关
	private boolean flagss = true; // 闪闪模式开关
	private String str1, str2, str3, str3_1 = "            - 正常模式 -", str4_1 = "            - 非闪状态 -", str4, str5, str6;
	private int snakebody, Snakebody;

	// private int speed=100;
	public MyJpanel() { // 面板构造方法
		time = new Timer(100, this); // 创建定时器
		time.start();
		list = new ArrayList<Point>();
		listBody = new ArrayList<Point>();
		list.add(new Point(x, y));
		listBody.add(list.get(0));
		snakebody = 1;
		Snakebody = 0;
		addKeyListener(new KeyAdapter() { // 设置键盘监听
			public void keyPressed(KeyEvent e) {
				// 如果暂停了就不能改变方向
				if (flagzt) {
					// ------------------------------方向控制↓-----------------------------------------
					if (flagsx) {
						if (e.getKeyCode() == KeyEvent.VK_DOWN) { // 控制蛇下转
							dx = 0;
							dy = 10;
							flagsx = false;
							flagzy = true;
						}
						if (e.getKeyCode() == KeyEvent.VK_UP) { // 控制蛇上转
							dx = 0;
							dy = -10;
							flagsx = false;
							flagzy = true;
						}
					}
					if (flagzy) {
						if (e.getKeyCode() == 37) { // 控制蛇左转
							dx = -10;
							dy = 0;
							flagzy = false;
							flagsx = true;
						}
						if (e.getKeyCode() == 39) { // 控制蛇右转
							dx = 10;
							dy = 0;
							flagzy = false;
							flagsx = true;
						}
					}
				}
				// ------------------------------方向控制↑-----------------------------------------
				if (e.getKeyCode() == 80) { // P暂停
					if (flagzt) {
						time.stop();
						flagzt = false;
					} else {
						time.start();
						flagzt = true;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_W) { // W无敌
					if (flagwd) {
						flagwd = false;
						str3_1 = "            - 无敌模式 -";
					} else {
						flagwd = true;
						str3_1 = "            - 正常模式 -";
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					if (flagss) {
						flagss = false;
						str4_1 = "            - 闪闪状态-";
					} else {
						flagss = true;
						str4_1 = "            - 非闪状态 -";
					}
				}
			}
		});
	}

	// -------------------------------构造方法结束-------------------------------
	public void paint(Graphics g) {
		// 设置背景为白色
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, Boundary, Boundary);
		// 设置边框为红色
		g.setColor(Color.RED);
		g.drawLine(0, 0, 0, Boundary);
		g.drawLine(0, 0, Boundary, 0);
		g.drawLine(Boundary, 0, Boundary, Boundary);
		g.drawLine(Boundary, Boundary, 0, Boundary);
		// 设置蛇身为随机色
		if (flagss)
			g.setColor(Color.MAGENTA);
		else
			g.setColor(new Color((int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1,
					(int) (Math.random() * 255) + 1));
		g.fillRect(x, y, Rect, Rect);
		for (int i = 0; i < listBody.size(); i++) {
			g.fillRect(listBody.get(i).x, listBody.get(i).y, 9, 9);
		}
		// 随机产生食物
		g.setColor(Color.YELLOW);
		g.fillRect(fx, fy, Rect, Rect);

		str1 = "操作说明：↑ ↓ ← → 键控制小蛇方向，P键暂停游戏、";
		str2 = "                     W键开启无敌模式、S键开启闪闪状态！！！";
		// str2=" 分数按照一次函数 f(x,y)=x2+2x+3 增加！";
		str3 = "当前模式：" + str3_1;
		str4 = "当前状态：" + str4_1;
		str5 = "当前等级：            - " + ((snakebody / 10) + 1) + " 级 -";
		str6 = "当前得分：            - " + Snakebody + " 分 -";

		g.setColor(Color.pink);
		g.fillRect(0, Boundary + 1, Boundary, 120);
		g.setColor(Color.BLUE);
		g.drawString(str1, 5, Boundary + 20);
		g.drawString(str2, 5, Boundary + 40);
		g.drawLine(0, 344, Boundary, 344);
		g.setColor(Color.BLUE);
		g.setFont(new Font("黑体", Font.PLAIN, 16));
		g.drawString(str3, 5, Boundary + 60);
		g.drawString(str4, 5, Boundary + 80);
		g.drawString(str5, 5, Boundary + 100);
		g.drawString(str6, 5, Boundary + 120);

	}

	public void actionPerformed(ActionEvent e) {
		x += dx;
		y += dy;
		// 吃到食物
		if (x == fx && y == fy) {
			// 随机产生食物
			fx = (int) (Math.random() * 10) * 30;
			fy = (int) (Math.random() * 10) * 30;
			snakebody++;
			// 一次函数
			Snakebody = snakebody * snakebody + 2 * snakebody + 3;

			// 设置等级
			/*
			 * if(snakebody==5){ speed=95; //time=new Timer(speed,this);
			 * 
			 * } if(snakebody==10){ speed=90; // time=new Timer(speed,this);
			 * 
			 * } if(snakebody==15){ speed=85; // time=new Timer(speed,this);
			 * 
			 * } time.start();
			 */
		}
		if (flagwd) { // 正常模式死亡
			// 撞到边界死亡
			if (x < 0 || y < 0 || x > Boundary - Rect || y > Boundary - Rect) {
				time.stop();
				JOptionPane.showMessageDialog(null,
						"            " + "得 分：" + Snakebody + " 分 !\n            重 新 开 始 吧 ！ ");
				x = 10;
				y = 10;

				dx = 10;
				dy = 0;

				snakebody = 1;
				Snakebody = 0;
				list.clear();
				listBody.clear();
				list.add(new Point(x, y));
				listBody.add(list.get(0));

				flagzy = false;
				flagsx = true;
				time.start();
			}
			// 撞身体死亡
			for (int i = 0; i < listBody.size() - 1; i++) {
				for (int j = i + 1; j < listBody.size(); j++) {
					if (listBody.get(i).equals(listBody.get(j))) {
						time.stop();
						JOptionPane.showMessageDialog(null,
								"            " + "得 分：" + Snakebody + " 分 !\n            重 新 开 始 吧 ！ ");
						x = 10;
						y = 10;

						dx = 10;
						dy = 0;

						snakebody = 1;
						Snakebody = 0;
						list.clear();
						listBody.clear();
						list.add(new Point(x, y));
						listBody.add(list.get(0));

						flagzy = false;
						flagsx = true;
						time.start();
					}
				}
			}
		} else { // 无敌模式
			if (x > Boundary)
				x = 0;
			if (y > Boundary)
				y = 0;
			if (x < 0)
				x = Boundary;
			if (y < 0)
				y = Boundary;
		}
		addPoint(x, y);
		repaint();
	}

	public void addPoint(int xx, int yy) {
		// 动态的记录最新发生的移动过的坐标
		// 并画出最新的蛇
		if (list.size() < 100) {// 蛇身长度最长为100
			list.add(new Point(xx, yy));
		} else {
			list.remove(0);
			list.add(new Point(xx, yy));
		}
		if (snakebody == 1) {
			listBody.remove(0);
			listBody.add(0, list.get(list.size() - 1));
		} else {
			listBody.clear();
			if (list.size() < snakebody) {
				for (int i = list.size() - 1; i > 0; i--)
					listBody.add(list.get(i));
			} else {
				for (int i = list.size() - 1; listBody.size() < snakebody; i--)
					listBody.add(list.get(i));
			}
		}
	}

	public static void main(String[] args) {
		new Snake();
	}
}
