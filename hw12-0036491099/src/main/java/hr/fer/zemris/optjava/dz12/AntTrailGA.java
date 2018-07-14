package hr.fer.zemris.optjava.dz12;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AntTrailGA extends JFrame {

    private JButton[][] buttonMatrix;
    private String[] put;
    private int putPosition;
    private JButton dalje;
    private JLabel status;
    private int x;
    private int y;
    private int dir;
    private JButton doKraja;
    private ImageIcon janje = new ImageIcon(getScaledImage(ImageIO.read(new File("Janje.jpg")), 30, 30));
    private ImageIcon vuco1 = new ImageIcon(getScaledImage(ImageIO.read(new File("Vuco1.jpg")), 30, 30));
    private ImageIcon vuco2 = new ImageIcon(getScaledImage(ImageIO.read(new File("Vuco2.jpg")), 30, 30));
    private ImageIcon vuco3 = new ImageIcon(getScaledImage(ImageIO.read(new File("Vuco3.jpg")), 30, 30));
    private ImageIcon vuco4 = new ImageIcon(getScaledImage(ImageIO.read(new File("Vuco4.jpg")), 30, 30));


    public AntTrailGA(String put) throws IOException {
        this.put = put.split("\\|");
        this.putPosition = 0;
        this.x = 0;
        this.y = 0;
        this.dir = 2;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(0, 0);
        setTitle("Vuco i janjetina");
        initGui();
        pack();
    }

    private void initGui() {
        boolean[][] field = EngineSingleton.getEngine().getInitField();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setPreferredSize(new Dimension(1500, 1000));
        buttonMatrix = new JButton[field.length][field[0].length];

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(field.length, field[0].length));
        for (int i = 0; i < field.length; i++) {
            boolean[] row = field[i];
            for (int j = 0; j < row.length; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));
                button.setBackground(Color.BLUE);
                if (field[i][j]) {
                    button.setIcon(janje);
                    button.setDisabledIcon(janje);
                }
                button.setEnabled(false);
                buttonPanel.add(button);
                buttonMatrix[i][j] = button;
            }
        }

        buttonMatrix[0][0].setIcon(vuco2);
        buttonMatrix[0][0].setDisabledIcon(vuco2);


        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        this.dalje = new JButton("DALJE");
        this.dalje.addActionListener(e -> dalje());

        getContentPane().add(this.dalje, BorderLayout.SOUTH);
        this.status = new JLabel("POJEDENO KILA JANJETINE: 0  | PREOSTALO AKCIJA: 600");

        getContentPane().add(this.status, BorderLayout.NORTH);

        this.doKraja = new JButton("DO KRAJA");
        this.doKraja.addActionListener(e -> {
            dalje.setEnabled(false);
            doKraja.setEnabled(false);
            play();
            Thread thread = new Thread(() -> {
                while (EngineSingleton.getEngine().moreActions()) {
                    dalje();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            thread.start();
        });
        getContentPane().add(this.doKraja, BorderLayout.EAST);
    }

    private void play() {
        try {
            File file = new File("volimPiti.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
            //Thread.sleep(clip.getMicrosecondLength());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void dalje() {
        String action = put[putPosition];
        switch (action) {
            case "MOVE":
                x = EngineSingleton.getEngine().getX();
                y = EngineSingleton.getEngine().getY();
                dir = EngineSingleton.getEngine().getDir();
                buttonMatrix[x][y].setIcon(null);
                buttonMatrix[x][y].setDisabledIcon(null);
                EngineSingleton.getEngine().move();
                putPosition++;
                x = EngineSingleton.getEngine().getX();
                y = EngineSingleton.getEngine().getY();
                dir = EngineSingleton.getEngine().getDir();

                ImageIcon icon = null;

                switch (dir) {
                    case 1:
                        icon = vuco1;
                        break;
                    case 2:
                        icon = vuco2;
                        break;
                    case 3:
                        icon = vuco3;
                        break;
                    case 4:
                        icon = vuco4;
                        break;
                    default:
                        icon = vuco1;
                        break;
                }
                buttonMatrix[x][y].setIcon(icon);
                buttonMatrix[x][y].setDisabledIcon(icon);
                break;
            case "RIGHT":
                putPosition++;
                EngineSingleton.getEngine().right();
                x = EngineSingleton.getEngine().getX();
                y = EngineSingleton.getEngine().getY();
                dir = EngineSingleton.getEngine().getDir();

                ImageIcon icon2 = null;

                switch (dir) {
                    case 1:
                        icon2 = vuco1;
                        break;
                    case 2:
                        icon2 = vuco2;
                        break;
                    case 3:
                        icon2 = vuco3;
                        break;
                    case 4:
                        icon2 = vuco4;
                        break;
                    default:
                        icon2 = vuco1;
                        break;
                }
                buttonMatrix[x][y].setIcon(icon2);
                buttonMatrix[x][y].setDisabledIcon(icon2);
                break;
            case "LEFT":
                putPosition++;
                EngineSingleton.getEngine().left();
                x = EngineSingleton.getEngine().getX();
                y = EngineSingleton.getEngine().getY();
                dir = EngineSingleton.getEngine().getDir();

                ImageIcon icon3 = null;

                switch (dir) {
                    case 1:
                        icon3 = vuco1;
                        break;
                    case 2:
                        icon3 = vuco2;
                        break;
                    case 3:
                        icon3 = vuco3;
                        break;
                    case 4:
                        icon3 = vuco4;
                        break;
                    default:
                        icon3 = vuco1;
                        break;
                }
                buttonMatrix[x][y].setIcon(icon3);
                buttonMatrix[x][y].setDisabledIcon(icon3);
                break;
            default:
                break;
        }
        if (putPosition == put.length) {
            putPosition = 0;
        }
        int kilaJanjetine = EngineSingleton.getEngine().getKilaJanjetine();
        int noOfActions = 600 - EngineSingleton.getEngine().getNoOfAction();
        status.setText("POJEDENO KILA JANJETINE: " + kilaJanjetine + "  | PREOSTALO AKCIJA: " + noOfActions);

        if (!EngineSingleton.getEngine().moreActions()) {
            dalje.setEnabled(false);
            doKraja.setEnabled(false);
        }
    }

    //Preuzeto sa https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }


    public static void main(String[] args) throws IOException {

        if (args.length != 5) {
            System.out.println("NEISPRAVAN BROJ ARGUMENATA");
            System.exit(1);
        }

        EngineSingleton.getEngine().parse(args[0]);

        int maxGenerations = Integer.parseInt(args[1]);
        int populationSize = Integer.parseInt(args[2]);
        int minFitness = Integer.parseInt(args[3]);

        GeneticAlgorithm alg = new GeneticAlgorithm(maxGenerations, populationSize, minFitness);
        Solution best = alg.run();
        System.out.println();
        System.out.println(best.getPut());
        System.out.println(best.getKilaJanjetine());

        FileOutputStream stream = new FileOutputStream(args[4]);

        stream.write(best.getPut().getBytes());

        stream.close();

        SwingUtilities.invokeLater(() -> {
            try {
                new AntTrailGA(best.getPut()).setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
