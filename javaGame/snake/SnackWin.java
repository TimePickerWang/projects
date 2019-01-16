package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class SnackWin extends JPanel implements ActionListener, KeyListener, Runnable {
	JButton newGame, stopGame;
	int score = 0, speed = 0;
	boolean start = false;
	Random r = new Random();
	int rx = 0, ry = 0;
	List<SnackAct> list = new ArrayList<SnackAct>();
	int temp = 0, tempEat1 = 0, tempEat2 = 0;
	JDialog dialog = new JDialog();
	JLabel label = new JLabel();
	JButton ok = new JButton("好吧");
	Thread nThread;

	public SnackWin() {
		newGame = new JButton("开始");
		stopGame = new JButton("退出");
		this.setLayout(new FlowLayout(FlowLayout.LEFT));// 左对齐
		newGame.addActionListener(this);
		stopGame.addActionListener(this);
		ok.addActionListener(this);
		this.addKeyListener(this);
		this.add(newGame);
		this.add(stopGame);
		dialog.setLayout(new GridLayout(2, 1));
		dialog.add(label);
		dialog.add(ok);
		dialog.setSize(200, 200);
		dialog.setLocation(570, 250);
		dialog.setVisible(false);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(10, 40, 400, 300);
		g.drawString("分数: " + score, 150, 20);
		g.drawString("速度: " + speed, 250, 20);
		g.setColor(new Color(0, 255, 0));// 绿
		if (start) {
			g.fillRect(10 + rx * 10, 40 + ry * 10, 10, 10);// 绿色画实物
			g.setColor(new Color(255, 0, 0));// 红
			for (int i = 0; i < list.size(); i++) {
				g.fillRect(10 + list.get(i).getX() * 10, 40 + list.get(i).getY() * 10, 10, 10);// 红色画蛇身
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGame) {
			newGame.setEnabled(false);
			start = true;
			rx = r.nextInt(40);
			ry = r.nextInt(30);
			SnackAct tempAct = new SnackAct();
			tempAct.setX(20);
			tempAct.setY(15);
			list.add(tempAct);// list里面的第一个元素
			requestFocus(true); // *****
			nThread = new Thread(this);
			nThread.start();
			repaint();
		}
		if (e.getSource() == stopGame) {
			System.exit(0);
		}
		if (e.getSource() == ok) {
			list.clear();
			score = 0;
			speed = 0;
			start = false;
			newGame.setEnabled(true);
			dialog.setVisible(false);
			repaint();
		}
	}

	private void eat() {
		if (rx == list.get(0).getX() && ry == list.get(0).getY()) {
			rx = r.nextInt(40);// 生成下一个食物
			ry = r.nextInt(30);
			SnackAct tempAct = new SnackAct();
			tempAct.setX(list.get(list.size() - 1).getX());
			tempAct.setY(list.get(list.size() - 1).getY());
			list.add(tempAct);
			score += 100 + 10 * speed;
			tempEat1 += 1;
			if (tempEat1 - tempEat2 >= 10) {
				tempEat2 = tempEat1;
				if (speed <= 9) {
					speed += 1;
				}
			}
		}
	}

	public void otherMove() {
		SnackAct tempAct = new SnackAct();
		for (int i = 0; i < list.size(); i++) {
			if (i == 1) {
				list.get(i).setX(list.get(0).getX());
				list.get(i).setY(list.get(0).getY());
			} else if (i > 1) {
				tempAct = list.get(i - 1);
				list.set(i - 1, list.get(i));
				list.set(i, tempAct);
			}
		}
	}

	public void move(int x, int y) {
		if (minYes(x, y)) {
			otherMove();
			list.get(0).setX(list.get(0).getX() + x);
			list.get(0).setY(list.get(0).getY() + y);
			eat();
			repaint();
		} else {// 死亡方法
			nThread = null;
			label.setText("        你挂了，你的分数是" + score);
			dialog.setVisible(true);

		}

	}

	public boolean minYes(int x, int y) {// 边界判定
		if (!maxYes(list.get(0).getX() + x, list.get(0).getY() + y)) {
			return false;
		}
		return true;
	}

	public boolean maxYes(int x, int y) {
		if (x < 0 || x >= 40 || y < 0 || y >= 30) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			if (i > 1 && list.get(0).getX() == list.get(i).getX() && list.get(0).getY() == list.get(i).getY()) {// 头撞到身子
				return false;
			}
		}
		return true;
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (start) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				move(0, -1);
				temp = 1;
				break;
			case KeyEvent.VK_DOWN:
				move(0, 1);
				temp = 2;
				break;
			case KeyEvent.VK_LEFT:
				move(-1, 0);
				temp = 3;
				break;
			case KeyEvent.VK_RIGHT:
				move(1, 0);
				temp = 4;
				break;
			default:
				break;
			}
		}
	}

	public void run() {
		while (start) {
			switch (temp) {
			case 1:
				move(0, -1);
				break;
			case 2:
				move(0, 1);
				break;
			case 3:
				move(-1, 0);
				break;
			case 4:
				move(1, 0);
				break;
			default:
				move(1, 0);
				break;
			}
			repaint();
			try {
				Thread.sleep(500 - 50 * speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
