package simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import loismathematiques.Exponentielle;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import utility.Utility;

/**
 * An example to show how we can create a dynamic chart.
*/
public class ProcessusPoisson extends ApplicationFrame implements ActionListener {
    //Graphique
    private final DefaultCategoryDataset dataset;
    private Timer timer;
    private final int stopTime;
    
    // Poisson
    private double actualTime = 0;
    private int nbEvents;
    private int nbTotalEvents;
    private int actualNbEvents;
    private int actualSerie;
    private double intervalleEvenement;
    private double actualIntervalleEvenement;
    
    // Expo
    private double lambdaExpo;
    
    // Nombre de n
    private DefaultCategoryDataset datasetNbN;
    private Map<Integer, Integer> mapNbN; // [Valeur de N][Nb de N]
    
    // Informations
    private Label lbNbTotalEvents;
    private Label lbNbEvents;
    private Label lbNbActualEvents;
    private Label lbActualTime;
    private boolean finished = false;
    private ArrayList<Double> tabIntervalleEvenement = new ArrayList<Double>();
    
    //Settings graphique
    private final int numberBar = 80;
    private int precision;    
    private double step;
    
    /**
     * Constructs a new dynamic chart application.
     *
     * @param title  the frame title.
     */
    public ProcessusPoisson(int precision, double step, double lambdaExpo, int frequenceTimer, int stopTime) {

        super("Simulation de nombre de pannes");
       
        // Graph Nb N
        datasetNbN = new DefaultCategoryDataset();
        // CommonBarGraph graphNbN = new CommonBarGraph("Nombre de N", "Nombre de N", "Valeur de N", datasetNbN);
        this.mapNbN = new TreeMap<Integer, Integer>();
        
        // Settings graphique
        this.precision = precision;
        this.step = step;
        timer = new Timer(frequenceTimer, this);
        this.stopTime = stopTime;
        
        // Poisson
        actualNbEvents = 0;
        actualSerie = 1;
        intervalleEvenement = 0;
        actualIntervalleEvenement = 0;
        nbTotalEvents = 0;
        
        // Expo
        this.lambdaExpo = lambdaExpo;
        
        // Initialisation des labels
        lbNbEvents = new Label("0");
        lbNbActualEvents = new Label("0");
        lbActualTime = new Label("0");
        lbNbTotalEvents = new Label("0");
        lbActualTime.setPreferredSize(new Dimension(50, 30));
        
        // Création du dataset
        dataset = new DefaultCategoryDataset();        
        for(int i = 0; i < this.numberBar; i++) {
            dataset.addValue(0, "", Double.toString( Utility.round(i * this.step, this.precision)));
        }
        
        final JFreeChart chart = createChart(dataset);
       
        //Created JPanel to show graph on screen
        final JPanel content = new JPanel(new BorderLayout());
        final JPanel informationPanel = new JPanel(new GridLayout(2, 1));
        informationPanel.setSize(100, 50);
        
        //Created Chartpanel for chart area
        final ChartPanel chartPanel = new ChartPanel(chart);
        
        final JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chartPanel, informationPanel);
        splitPanel.setResizeWeight(0.7d);
        content.add(splitPanel);
        
        // Informations panel        
        // TIMER
        JPanel nbTimerPanel = new JPanel();
        informationPanel.add(nbTimerPanel);
        nbTimerPanel.add(new JLabel("Temps �coul� : "));
        nbTimerPanel.add(lbActualTime);
        informationPanel.add(new JLabel());
        
        // NB EVENTS
        /*JPanel nbEventPanel = new JPanel();
        informationPanel.add(nbEventPanel);
        nbEventPanel.add(new JLabel("Nombre d'événements : "));
        nbEventPanel.add(lbNbEvents);*/

        // NB EVENTS ACTUEL
        /*JPanel nbActualEventsPanel = new JPanel();
        informationPanel.add(nbActualEventsPanel);
        nbActualEventsPanel.add(new JLabel("Actuel : "));
        nbActualEventsPanel.add(lbNbActualEvents);*/
        
        // NB TOTAL EVENTS
        JPanel nbTotalEvents = new JPanel();
        informationPanel.add(nbTotalEvents);
        nbTotalEvents.add(new JLabel("Nombre total de pannes : "));
        nbTotalEvents.add(lbNbTotalEvents);
        
        // Button Lecture/Pause
        JButton btnPause = new JButton("Pause");
        content.add(btnPause, BorderLayout.SOUTH);
        
        // EVENTS
        // Button pause
        btnPause.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(timer.isRunning()) {
                    //timer.stop();
                    btnPause.setText("Lecture");
                } else {
                    //timer.start();
                    btnPause.setText("Pause");
                }
                
            }
        });
        
        //Sets the size of whole window (JPanel)
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 500));
       
        //Puts the whole content on a Frame
        setContentPane(content);
       
        timer.start();
        
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return A sample chart.
     */
    private JFreeChart createChart(final DefaultCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createBarChart(
            "Processus de Poisson",
            "",
            "",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        /** CUSTOM CHART **/
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(false);
        
        CategoryAxis xaxis = plot.getDomainAxis();
        ValueAxis yaxis = plot.getRangeAxis();
        
        xaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
        xaxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        yaxis.setRange(0, 1);
        yaxis.setVisible(false);
        
        chart.removeLegend();
        
        BarRenderer barRender = (BarRenderer)chart.getCategoryPlot().getRenderer();
        barRender.setBarPainter(new StandardBarPainter());
        barRender.setSeriesPaint(0, Color.BLUE);
        //barRender.setSeriesPaint(1, Color.RED);
        /** **/
       
        return chart;
    }
    
    /**
     * Generates an random entry for a particular call made by time for every 1/4th of a second.
     *
     * @param e  the action event.
     */
    public void actionPerformed(final ActionEvent e) {
        if(this.actualTime >= this.stopTime) {
            timer.stop();
            finished=true;
            this.dispose();
        }
               
        // Si on a atteint le nombre d'événement définis par la loi de poisson
        // on change de couleur (série) ; on tire on nouveau nombre d'événéments
        /*if(this.nbEvents == 0 || this.actualNbEvents == this.nbEvents) {
            this.actualSerie = (this.actualSerie == 0 || this.actualSerie == 2) ? 1 : 2;
            Poisson poisson = new Poisson(99999);
            
            do {
                this.nbEvents = (int) poisson.genererNombreSuivantLoi();
            } while(this.nbEvents == 0);
            
            updateGraphNbN();
            
            this.actualNbEvents = 0;
        }*/
        
        this.actualIntervalleEvenement += this.step;
        
        // Générer un nouvel intervalle entre 2 événements
        if(this.intervalleEvenement == 0) {
            // Génération du temps avec la loi Exponentielle
            Exponentielle exponentielle = new Exponentielle(lambdaExpo);
            this.intervalleEvenement = exponentielle.genererNombreSuivantLoi();
            this.tabIntervalleEvenement.add(intervalleEvenement);
            this.actualIntervalleEvenement = 0;
        }
        
        int value = 0;
        
        double actualTimeRound = Utility.round(this.actualTime, this.precision);

        // Si l'intervalle est terminé
        if(Utility.round(this.intervalleEvenement, this.precision) <= Utility.round(this.actualIntervalleEvenement, this.precision)) {
            value = 1;
            this.nbTotalEvents++;
            this.actualNbEvents++;
            this.intervalleEvenement = 0;
        }
        
        // Permet de supprimer la première barre si le graphique devient trop grand
        if(dataset.getColumnCount() > this.numberBar)
            dataset.removeColumn(0);
                
        // dataset.addValue(value, Integer.toString(this.actualSerie), Double.toString(actualTimeRound));
        dataset.addValue(value, "", Double.toString(actualTimeRound));
        
        updateInformationPanel();
        
        this.actualTime += this.step;
    }
    
    private void updateInformationPanel() {
        this.lbNbEvents.setText(Integer.toString(this.nbEvents));
        this.lbNbActualEvents.setText(Integer.toString(this.actualNbEvents));
        this.lbNbTotalEvents.setText(Integer.toString(this.nbTotalEvents));
        this.lbActualTime.setText(Double.toString(Utility.round(this.actualTime, this.precision))+" h");
    }
    
    private void updateGraphNbN() {
        Integer nbN = mapNbN.get(this.nbEvents)==null ? 1 : mapNbN.get(this.nbEvents)+1;
        
        mapNbN.put(this.nbEvents, nbN);
        
        // Si c'est le premier ; on trie l'axes X
        if(nbN==1) {
            
            // Vide le dataset
            while(datasetNbN.getColumnCount() > 0 ) {
                datasetNbN.removeColumn(0);
            }
            
            // Remplit le dataset
            for(Map.Entry<Integer, Integer> entry : mapNbN.entrySet()) {
                datasetNbN.addValue(entry.getValue(), "", Double.toString(entry.getKey()));
            }
        } else {
            datasetNbN.addValue(nbN, "", Double.toString(this.nbEvents));
        }
    }
    
    public double getTime() {
        return this.actualTime;
    }
    
    public void setTimer(int timer) {
        this.timer.setDelay(timer);
    }

    public boolean isFinished() {
        return this.finished;
    }
    
    public int getNbTotalEvents(){
        return this.nbTotalEvents;
    }
    
    public ArrayList getTabIntervalleEvenement(){
        return this.tabIntervalleEvenement;
    }
}  