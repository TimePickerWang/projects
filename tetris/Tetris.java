package tetris;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;//右击Source->Ogranize Imports，整理引入的类
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tetris extends JFrame {
	private int x = 0, y = 0;
	int blockType = 0;// 7种图形
	int turnState = 0;// 一种图形的不同状态
	private final int shapes[][][] = new int[][][] {
			// I
			{ { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
					{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
			// S
			{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
			// Z
			{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
			// J
			{ { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
			// O
			{ { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
			// L
			{ { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
			// T
			{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } } };

	public Tetris() {
		TetrisPanel tpanel = new TetrisPanel();
		add(tpanel);
		addKeyListener(tpanel);
	}

	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			y++;
			repaint();
		}
	}

	private class TetrisPanel extends JPanel implements KeyListener {
		private int map[][] = new int[13][23];

		public void add() {
			int i = 0;
			for (int a = 0; a < 4; a++)
				for (int b = 0; b < 4; b++) {
					if (map[x + 1 + b][y + a] == 0)
						map[x + 1 + b][y + a] = shapes[blockType][turnState][i];
					i++;
				}
		}

		public void right() {
			if (!isCollied(x + 1, y))
				x++; // 数据模型刷新后调用repaint()方法使状态的改变体现出来
			repaint();
		}

		public void left() {
			if (!isCollied(x - 1, y))
				x--;
			repaint();
		}

		public void turn() {// 翻转图形
			int temp = turnState;
			turnState = (turnState + 1) % 4;
			if (!isCollied(x, y))
				turnState = temp; // 倒回变换前的状态
			repaint();
		}

		public void down() {
			if (!isCollied(x, y + 1))
				y++;
			else {
				add();
				deletLine();
				newBlock();
			}
			repaint();
		}

		int score = 0;

		public void deletLine() { // 消除一行 ******
			int c = 0;
			for (int b = 0; b < 22; b++)
				for (int a = 0; a < 11; a++)
					if (map[a][b] == 1) {
						c++;
						if (c == 10) {
							score += 10;
							for (int d = b; d > 0; d--)
								for (int e = 0; e > 11; e++) {
									map[e][d] = map[e][d - 1];
								}
						}
					}
			c = 0;// c在遍历一行后归零
		}

		public void keyTyped(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP: // 向上键,翻转图形
				turn();
				break;
			case KeyEvent.VK_DOWN: // 向下键
				down();
				break;
			case KeyEvent.VK_LEFT: // 向左键
				left();
				break;
			case KeyEvent.VK_RIGHT: // 向右键
				right();
				break;
			}
		}

		public TetrisPanel() {
			cleanMap();
			drawWall();
			newBlock();
			Timer timer = new Timer(1000, new TimerListener());
			timer.start();
		}

		public void cleanMap() {// 清除已堆好的区域
			for (int i = 1; i < 11; i++)
				for (int j = 0; j < 21; j++) {
					map[i][j] = 0;
				}
		}

		public void newBlock() {
			blockType = (int) (Math.random() * 1000) % 7;// 0到6之间的随机数，随机选择一种图形
			turnState = (int) (Math.random() * 1000) % 4;// 0到4之间的随机数，随机选择图形的一种状态
			x = 4;
			y = 0;
		}

		public void drawWall() {// 三面墙满足的要求
			for (int i = 0; i < 12; i++) {
				map[i][21] = 2;
			}
			for (int j = 0; j < 22; j++) {
				map[0][j] = 2;
				map[11][j] = 2;
			}
		}

		public boolean isCollied(int x, int y) {// 碰撞检测 *****
			for (int a = 0; a < 4; a++)
				for (int b = 0; b < 4; b++) {
					if ((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + 1 + b][y + a] == 1)) { // 与图像相碰
						return true;
					} else if ((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + 1 + b][y + a] == 2)) { // 与墙壁相碰
						return true;
					}

				}
			return false;
		}

		public void paintComponent(Graphics g) {// 方法覆盖
			super.paintComponent(g);// 调用父类的方法，用默认的底色清除画布
			for (int j = 0; j < 22; j++)
				for (int i = 0; i < 12; i++) {
					if (map[i][j] == 2) {
						g.drawRect(i * 10, j * 10, 10, 10);// 画矩形(空心)
					}
					if (map[i][j] == 1) {
						g.fillRect(i * 10, j * 10, 10, 10);// 画矩形(实心)
					}
				}
			for (int i = 0; i < 16; i++) {
				if (shapes[blockType][turnState][i] == 1) {
					g.fillRect((x + 1 + i % 4) * 10, (y + i / 4) * 10, 10, 10);// 画矩形(实心)
																				// *********
				}
			}
		}
	}

	public static void main(String[] args) {
		Tetris frame = new Tetris();
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);// 设置菜单条
		JMenu help = new JMenu("帮助");
		JMenuItem about = help.add("关于"); // *
		JMenu game = new JMenu("游戏");
		JMenuItem newgame = game.add("新游戏");
		JMenuItem pause = game.add("暂停");
		JMenuItem goon = game.add("继续");
		JMenuItem exit = game.add("退出");
		menubar.add(help);
		menubar.add(game);

		frame.setTitle("俄罗斯方块");// 设置题目
		// frame.setLocationRelativeTo(null);// 不参考任何坐标，使窗口不停在左上角
		frame.setSize(250, 300);
		frame.setLocation(450, 150);
		frame.setVisible(true);
		frame.setResizable(false);// 不能调整窗口大小
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

}
