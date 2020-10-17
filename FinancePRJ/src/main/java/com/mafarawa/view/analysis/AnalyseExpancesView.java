package com.mafarawa.view.analysis;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;

public class AnalyseExpancesView {
	private SplitPane rootLayout;
	protected ListView<String> categoryList;
	protected NumberAxis xAxis;
	protected NumberAxis yAxis;
	protected LineChart<Number, Number> lineChart;

	public AnalyseExpancesView() {
		categoryList = new ListView<>();
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		lineChart = new LineChart<>(xAxis, yAxis);

		xAxis.setLabel("Дни");
		yAxis.setLabel("Баланс");
		lineChart.setTitle("Анализ расходов");

		rootLayout = new SplitPane(categoryList, lineChart);
		rootLayout.setDividerPositions(0.3);		
	}

	public SplitPane getLayout() { return this.rootLayout; }
}