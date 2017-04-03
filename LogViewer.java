package es.upm.dit.adsw.ej6;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Visor: dibujos en pantalla.
 * Patron singleton: 1 instancia compartida por todos.
 *
 * @author jam
 * @version 26.1.2015
 */
public class LogViewer {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final Color READER = Color.GREEN;
    private static final Color WRITER = Color.RED;

    // singleton
    private static final LogViewer instance = new LogViewer("readers writers");

    /**
     * Singleton: 1 objeto compartida por todos.
     *
     * @return el visor.
     */
    public static LogViewer getInstance() {
        return instance;
    }

    private final JFrame frame;
    private final JPanel panel;
    private final ArrayList<RWCounter> counterList = new ArrayList<>();
    private final ArrayList<Thing> thingList = new ArrayList<>();

    private LogViewer(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new MyJPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Pintor.
     *
     * @param id       el objeto que lo llama.
     * @param nReaders numero de lectores en este momento.
     * @param nWriters numero de escritores en este momento.
     */
    public synchronized void dump(Object id, int nReaders, int nWriters) {
        updateCounters(id, nReaders, nWriters);
        upDateGraph();
        paint();
        Nap.sleep(200);
    }

    private void updateCounters(Object id, int nReaders, int nWriters) {
        for (RWCounter counter : counterList) {
            if (counter.id == id) {
                counter.nReaders = nReaders;
                counter.nWriters = nWriters;
                return;
            }
        }
        counterList.add(new RWCounter(id, nReaders, nWriters));
    }

    private void upDateGraph() {
        reset();
        for (int i = 0; i < counterList.size(); i++) {
            RWCounter counter = counterList.get(i);
            int base = 20;
            int y = HEIGHT - 20;
            int bx = 20 + (base + 2) * i;
            int wh = 10 * counter.nWriters;
            int rh = 10 * counter.nReaders;
            add(new Rectangle(bx, y, base, wh, Color.BLACK, WRITER));
            add(new Rectangle(bx, y - wh, base, rh, Color.BLACK, READER));
        }
    }

    private void reset() {
        synchronized (thingList) {
            thingList.clear();
        }
    }

    private void add(Thing thing) {
        synchronized (thingList) {
            thingList.add(thing);
        }
    }

    private void paint() {
        frame.repaint();
    }

    private interface Thing {
        void paint(Graphics2D g);
    }

    private class Rectangle
            implements Thing {
        private int bx;
        private int by;
        private int b;
        private int h;
        private Color border;
        private Color body;

        Rectangle(int bx, int by, int b, int h, Color border, Color body) {
            this.bx = bx;
            this.by = by;
            this.b = b;
            this.h = h;
            this.body = body;
            this.border = border;
        }

        @Override
        public void paint(Graphics2D g) {
            if (body != null) {
                g.setColor(body);
                g.fillRect(bx, by - h, b, h);
            }
            if (border != null) {
                g.setColor(border);
                g.drawRect(bx, by - h, b, h);
            }
        }
    }

    private class MyJPanel
            extends JPanel {
        @Override
        public void paint(Graphics g) {
            int width = panel.getWidth();
            int height = panel.getHeight();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            synchronized (thingList) {
                for (Thing thing : thingList)
                    thing.paint((Graphics2D) g);
            }
        }
    }

    private class RWCounter {
        Object id;
        int nReaders;
        int nWriters;

        RWCounter(Object id, int nReaders, int nWriters) {
            this.id = id;
            this.nReaders = nReaders;
            this.nWriters = nWriters;
        }
    }
}