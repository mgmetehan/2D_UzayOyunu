import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {// Action her �al��t���nda ate�imiz bir ileri gidecek
	private int x, y;

	public Ates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

public class OyunPanel extends JPanel implements KeyListener, ActionListener {
	Timer timer = new Timer(1, this);// Timer ilk de�eri ka� milisaniyede bir �al��aca��n� belirtiyor
	// ikincisi ise actionList this ile bizim yaratt���m�z action list her 1
	// milisaniyede bir �al��acak

	private int gecenSure = 0, harcananAtes = 0;
	private BufferedImage bfimage;
	private ArrayList<Ates> atesler = new ArrayList<>();// Birden fazla ates olmas� ve bunlar�n saklanmas� i�in
	private int atesdirY = 3;// Ate�ler x kordinat�nda hareketsiz ama y kordinat�nda her seferinde 1
								// ekleyerek yukar� g�t�r�yoruz

	private int topX = 0;// Hedefin hareket etmesi
	private int topdirX = 7;
	private int uzayGemisiX = 0;// Uzay gemisinin panelde ba�lang�� noktas�
	private int dirUzayX = 20;// Uzay gemisi her hareket etti�inde 20 birim gidecek

	public boolean kontrol() {// Burda bizim mermi ile top �arp��t� m� kontrol ediyoruz
		for (Ates ates : atesler) {
			if (new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX, 0, 30, 30))) {
				return true;
			} // Rectangle �arp��acak nesneleri dikd�rtgen �eklinde s�n�rlar�n� �iziyoruz
		} // e�er kesi�me olurse bize true de�er d�nd�recek
		return false;
	}

	public OyunPanel() {
		try {
			bfimage = ImageIO.read(new FileImageInputStream(new File("mekik.png")));// uzay gemisinin resmini ald�k
		} catch (IOException e) {
			Logger.getLogger(OyunPanel.class.getName()).log(Level.SEVERE, null, e);
		}
		setBackground(Color.black);
		timer.start();// timer ba�lat�ld�
	}

	@Override
	public void paint(Graphics g) {// �ekillerimizi �izmek i�in �a��rd�k
		super.paint(g);
		gecenSure += 1;
		g.setColor(Color.red);
		g.fillOval(topX, 0, 30, 30);// topun kordinat� ve b�y�kl���n verdik(20,20)
		g.drawImage(bfimage, uzayGemisiX, 420, bfimage.getWidth() / 5, bfimage.getHeight() / 4, this);
		// uzay gemisin konumu verdik//uzaygemisinin png boyutunu k���ltt�k//this ile bu
		// panelde a��lmas�n� sa�lad�k

		for (Ates ates : atesler) {// frameden ��kan ate�leri siliyoruz
			if (ates.getY() < 0) {
				atesler.remove(ates);
			}
		}
		// Bu �ekilde mermi rengi s�rekli de�i�iyor
		int random = (int) (Math.random() * 5);
		if (random == 0) {
			g.setColor(Color.blue);// ate�in rengi
		} else if (random == 1) {
			g.setColor(Color.MAGENTA);
		} else if (random == 2) {
			g.setColor(Color.YELLOW);
		} else if (random == 3) {
			g.setColor(Color.lightGray);
		} else if (random == 4) {
			g.setColor(Color.PINK);
		}

		for (Ates ates : atesler) {// merminin �eklini olu�turduk
			g.fillRect(ates.getX(), ates.getY(), 10, 20);
		}

		if (kontrol()) {// topu vurunca timer� kapt�p oyunu bittiriyoruz
			timer.stop();
			String msg = "Kazand�n�z..\n" + "Harcanan Ate�: " + harcananAtes + "\nGe�en S�re: " + gecenSure / 100.0
					+ " saniye";
			JOptionPane.showMessageDialog(this, msg);// s�re milisaniye onu b�l�p saniye yapt�k
			System.exit(0);
		}
	}

	@Override
	public void repaint() {// Repaint her �a��rd���m�zda o da tekrar paintti �a��r�yor
		super.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (Ates ates : atesler) {// Ate�elri y kordinat�nda hareket ettirmek i�in
			ates.setY(ates.getY() - atesdirY);
		}

		topX += topdirX;
		if (topX >= 750) {
			topdirX = -topdirX;
		} // Top framein d���na ��kmas�n� engellemek ve frame s�n�ra gelince z�t y�ne//
			// hareket etmesi i�in negit ile �arp�yoruz
		if (topX <= 0) {
			topdirX = -topdirX;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int x = e.getKeyCode();// Bu bizim hangi tu�a bast���m�z� al�cak
		if (x == KeyEvent.VK_LEFT) {
			if (uzayGemisiX <= 0) {// frame s�n�r kontroll
				uzayGemisiX = 0;
			} else {// hareket ettirmek
				uzayGemisiX -= dirUzayX;
			}
		} else if (x == KeyEvent.VK_RIGHT) {
			if (uzayGemisiX >= 690) {
				uzayGemisiX = 690;
			} else {
				uzayGemisiX += dirUzayX;
			}
		} else if (x == KeyEvent.VK_SPACE) {
			atesler.add(new Ates(uzayGemisiX + 45, 415));// ate� tam u�tan ��kmazsa uzayGemisiX+ diyip bir de�er
															// ekleyebilirsin
			harcananAtes++;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
