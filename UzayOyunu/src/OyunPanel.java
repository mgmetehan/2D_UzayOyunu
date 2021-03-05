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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {// Action her calistiginda atesimiz bir ileri gidecek
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
	Timer timer = new Timer(1, this);// Timer ilk degeri kac milisaniyede bir calisacagini belirtiyor
	// ikincisi ise actionList this ile bizim yarattigimiz action list her 1
	// milisaniyede bir calisacak

	private int gecenSure = 0, harcananAtes = 0;
	private BufferedImage bfimage;
	private ArrayList<Ates> atesler = new ArrayList<>();// Birden fazla ates olmasi ve bunlarin saklanmasi icin
	private int atesdirY = 3;// Atesler x kordinatinda hareketsiz ama y kordinatinda her seferinde 1
								// ekleyerek yukari goturuyoruz

	private int topX = 0;// Hedefin hareket etmesi
	private int topdirX = 7;
	private int uzayGemisiX = 0;// Uzay gemisinin panelde baslangic noktasi
	private int dirUzayX = 20;// Uzay gemisi her hareket ettiginde 20 birim gidecek

	public boolean kontrol() {// Burda bizim mermi ile top carpisti mi kontrol ediyoruz
		for (Ates ates : atesler) {
			if (new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX, 0, 30, 30))) {
				return true;
			} // Rectangle carpisacak nesneleri dikdortgen seklinde sinirlarini ciziyoruz
		} // eger kesisme olurse bize true deger dondurecek
		return false;
	}

	public OyunPanel() {
		try {
			bfimage = ImageIO.read(new FileImageInputStream(new File("mekik.png")));// uzay gemisinin resmini aldik
		} catch (IOException e) {
			Logger.getLogger(OyunPanel.class.getName()).log(Level.SEVERE, null, e);
		}
		setBackground(Color.black);
		timer.start();// timer baslatildi
	}

	@Override
	public void paint(Graphics g) {// Sekillerimizi cizmek icin cagirdik
		super.paint(g);
		gecenSure += 1;
		g.setColor(Color.red);
		g.fillOval(topX, 0, 30, 30);// topun kordinati ve buyuklugun verdik(20,20)
		g.drawImage(bfimage, uzayGemisiX, 420, bfimage.getWidth() / 5, bfimage.getHeight() / 4, this);
		// uzay gemisin konumu verdik//uzaygemisinin png boyutunu kuculttuk//this ile bu
		// panelde acilmasini sagladik

		for (Ates ates : atesler) {// frameden cikan atesleri siliyoruz
			if (ates.getY() < 0) {
				atesler.remove(ates);
			}
		}

		// Bu sekilde mermi rengi surekli degisiyor
		Random rand = new Random();// Bu iki yolda rengi degistir ama bu daha ksia alttakine gore
		g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
		/*
		 * int random =(int) (Math.random() * 5); if (random == 0) {
		 * g.setColor(Color.blue);// atesin rengi } else if (random == 1) {
		 * g.setColor(Color.MAGENTA); } else if (random == 2) {
		 * g.setColor(Color.YELLOW); } else if (random == 3) {
		 * g.setColor(Color.lightGray); } else if (random == 4) {
		 * g.setColor(Color.PINK); }
		 */

		for (Ates ates : atesler) {// merminin seklini olusturduk
			g.fillRect(ates.getX(), ates.getY(), 10, 20);
		}

		if (kontrol()) {// topu vurunca timeri kaptip oyunu bittiriyoruz
			timer.stop();
			String msg = "Kazandiniz..\n" + "Harcanan Ates: " + harcananAtes + "\nGecen Sure: " + gecenSure / 100.0
					+ " saniye";
			JOptionPane.showMessageDialog(this, msg);// sure milisaniye onu bolup saniye yaptik
			System.exit(0);
		}
	}

	@Override
	public void repaint() {// Repaint her cagirdigimizda o da tekrar paintti cagiriyor
		super.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (Ates ates : atesler) {// Ateselri y kordinatinda hareket ettirmek icin
			ates.setY(ates.getY() - atesdirY);
		}

		topX += topdirX;
		if (topX >= 750) {
			topdirX = -topdirX;
		} // Top framein disina cikmasini engellemek ve frame sinira gelince zit yone//
			// hareket etmesi icin negit ile carpiyoruz
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
		int x = e.getKeyCode();// Bu bizim hangi tusa bastigimizi alicak
		if (x == KeyEvent.VK_LEFT) {
			if (uzayGemisiX <= 0) {// frame sinir kontroll
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
			atesler.add(new Ates(uzayGemisiX + 45, 415));// ates tam uctan cikmazsa uzayGemisiX+ diyip bir deger
															// ekleyebilirsin
			harcananAtes++;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
