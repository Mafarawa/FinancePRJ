package com.mafarawa.view.analysis;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class AnalyseExpancesView {
	private HBox rootLayout;
	protected NumberAxis xAxis;
	protected NumberAxis yAxis;
	protected LineChart<Number, Number> lineChart;

	public AnalyseExpancesView() {
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		lineChart = new LineChart<>(xAxis, yAxis);

		xAxis.setLabel("Дни");
		yAxis.setLabel("Баланс");
		lineChart.setTitle("Анализ расходов");

		rootLayout = new HBox(lineChart);
		rootLayout.setAlignment(Pos.CENTER);
	}

	public HBox getLayout() { return this.rootLayout; }
}