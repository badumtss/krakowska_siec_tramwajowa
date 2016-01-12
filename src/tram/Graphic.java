package tram;
// License: GPL. For details, see Readme.txt file.

import org.openstreetmap.gui.jmapviewer.*;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.text.html.parser.Parser;

import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;



public class Graphic extends JFrame implements JMapViewerEventListener  {

    private static final long serialVersionUID = 1L;

    private final JMapViewerTree treeMap;

    private final JLabel zoomLabel;
    private final JLabel zoomValue;

    private final JLabel mperpLabelName;
    private final JLabel mperpLabelValue;
    private final JLabel clockLabel;


    public Graphic() {
        super("Trams");
        setSize(400, 400);

        treeMap = new JMapViewerTree("Zones");

        // Listen to the map viewer for user operations so components will
        // receive events and update
        map().addJMVListener(this);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        JPanel helpPanel = new JPanel();

        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s", map().getMeterPerPixel()));

        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", map().getZoom()));
        ClockThread.panel=panelTop;
        clockLabel= new JLabel("Time: ");
        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout());
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);
		map().setZoom(13);
		Point position = new Point(1164700, 710700);
		map().setCenter(position);

        JLabel helpLabel = new JLabel("Use right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);
        JButton button = new JButton("setDisplayToFitMapMarkers");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setDisplayToFitMapMarkers();
            }
        });

        final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
        showMapMarker.setSelected(map().getMapMarkersVisible());
        showMapMarker.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                map().setMapMarkerVisible(showMapMarker.isSelected());
            }
        });
        panelBottom.add(showMapMarker);
        
        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        showZoomControls.setSelected(map().getZoomContolsVisible());
        showZoomControls.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setZoomContolsVisible(showZoomControls.isSelected());
            }
        });
        panelBottom.add(showZoomControls);

        panelBottom.add(button);

        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);
        panelTop.add(clockLabel);
        
        add(treeMap, BorderLayout.CENTER);
        
        map().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map().getAttribution().handleAttribution(e.getPoint(), true);
                }
            }
        });

        map().addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean cursorHand = map().getAttribution().handleAttributionCursor(p);
                if (cursorHand) {
                    map().setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    map().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
        
        ClockThread c=new ClockThread("src/newdata",map());
        c.start();
        
        JSpinner spinner1 = new JSpinner();
        SpinnerDateModel spinnermodel = new SpinnerDateModel();
        spinnermodel.setCalendarField(Calendar.MINUTE);
        spinner1.setModel(spinnermodel);
        spinner1.setEditor(new JSpinner.DateEditor(spinner1 , "HH:mm"));
        
        JButton spawn = new JButton("Start");
        spawn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                while(true)
//                {
//                    LocalTime time = LocalTime.now();
//                }
//                for(int i=0; i<ParserXML.relations.size(); i++)
//                {
//                    DrawThread t = new DrawThread((ParserXML.relations.get("14")), map());
//                    t.start();

                Calendar cal = Calendar.getInstance();
                cal.setTime((Date) spinner1.getValue());
                
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);
                ClockThread.thisSec=LocalTime.of(hours, minutes);
                
                c.stopThreads();
                c.removeEmptythreads();

            }
        });
        JComboBox<Integer> speedselector = new JComboBox<>(new Integer[]{1,2,3,10});
        speedselector.addItemListener(new ItemListener(){
        	public void itemStateChanged(ItemEvent e) {
        		Interf.acc=(int)e.getItem();
        	}
        });
        panelBottom.add(spinner1);
        panelBottom.add(spawn);
        panelBottom.add(new JLabel("Speed: "));
        panelBottom.add(speedselector);
        new DrawWays(map());//rysowanie tor�w
        this.setVisible(true);

    }

    private JMapViewer map() {
        return treeMap.getViewer();
    }

    private void updateZoomParameters() {
        if (mperpLabelValue != null)
            mperpLabelValue.setText(String.format("%s", map().getMeterPerPixel()));
        if (zoomValue != null)
            zoomValue.setText(String.format("%s", map().getZoom()));
    }

    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
            updateZoomParameters();
        }
    }
}
