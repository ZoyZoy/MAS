package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class CommonBarGraph extends ApplicationFrame {
    
    private String title;
    
    private String strY;
     
    private String strX;
    
    private DefaultCategoryDataset dataset;
    
    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public CommonBarGraph(String title, String strY, String strX, DefaultCategoryDataset dataset) {
        
        super(title);
        
        this.title = title;
        this.strY = strY;
        this.strX = strX;
        
        this.dataset = dataset;


        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
        
        /** CUSTOM CHART **/
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(false);
        
        CategoryAxis xaxis = plot.getDomainAxis();
        ValueAxis yaxis = plot.getRangeAxis();
        
        xaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
        xaxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        
        chart.removeLegend();
        
        BarRenderer barRender = (BarRenderer)chart.getCategoryPlot().getRenderer();
        barRender.setBarPainter(new StandardBarPainter());
        barRender.setSeriesPaint(0, Color.BLUE);
        /** **/
        
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
    

    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            title,                      // chart title
            strX,                       // domain axis label
            strY,                       // range axis label
            dataset,                    // data
            PlotOrientation.VERTICAL,   // orientation
            false,                      // include legend
            true,                       // tooltips?
            false                       // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);


        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;
    }
	
}
