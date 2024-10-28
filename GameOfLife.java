import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife implements ActionListener {
    JFrame frame = new JFrame("Juego de la Vida");
    JPanel gridPanel = new JPanel();
    static final int SIZE = 150;
    JPanel[][] paneles = new JPanel[SIZE][SIZE];
    boolean[][] current = new boolean[SIZE][SIZE];
    boolean[][] next = new boolean[SIZE][SIZE];
    JButton button = new JButton("Pause");
    boolean paused = false;

    GameOfLife() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 600); // Ajusta el tamaño del frame
        frame.setLayout(new BorderLayout()); // Cambia a BorderLayout

        // Configura el panel de la cuadrícula con GridLayout SIZExSIZE
        gridPanel.setLayout(new GridLayout(SIZE, SIZE));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                paneles[i][j] = new JPanel();
                paneles[i][j].setBackground(Color.black);
                gridPanel.add(paneles[i][j]);
            }
        }

        // Añade el botón al panel inferior
        JPanel bottomPanel = new JPanel();
        button.setPreferredSize(new Dimension(100, 40));
        bottomPanel.add(button);
        button.setBackground(Color.orange);
        button.addActionListener(this);

        // Añade los paneles al frame
        frame.add(gridPanel, BorderLayout.CENTER); // Cuadrícula en el centro
        frame.add(bottomPanel, BorderLayout.NORTH); // Botón en la parte inferior
        frame.setVisible(true);

        // Inicializa el estado del juego
        for (int i = 0; i < SIZE * 10; i++) {
            current[(int) (Math.random() * SIZE)][(int) (Math.random() * SIZE)] = true;
        }

        render();
    }

    public void render() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (current[i][j]) paneles[i][j].setBackground(Color.white);
                else paneles[i][j].setBackground(Color.black);
            }
        }
        frame.repaint();
    }

    public void update() {
     {
            try {
                Thread.sleep(50);
                if(paused)return;
                updateGame();
                boolean[][] temp = current;
                current = next;
                next = temp;
                render();
            } catch (java.lang.InterruptedException e) {
                System.out.println("error");
            }
        }
    }

    public void updateGame() {
        int life_around;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                life_around = 0;
                for (int i1 = -1; i1 < 2; i1++) {
                    for (int j1 = -1; j1 < 2; j1++) {
                        if (i1 == 0 && j1 == 0) continue;
                        if (current[(i + i1 + SIZE) % SIZE][(j + j1 + SIZE) % SIZE]) life_around++;
                    }
                }
                if (life_around == 3 && !(current[i][j])) {
                    next[i][j] = true;
                } else if (current[i][j] && life_around != 2 && life_around != 3) {
                    next[i][j] = false;
                } else {
                    next[i][j] = current[i][j];
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            if(paused){
                paused = false;
                button.setText("pause");
            }
            else{
                paused = true;
                button.setText("resume");
            }
        }
    }
}

