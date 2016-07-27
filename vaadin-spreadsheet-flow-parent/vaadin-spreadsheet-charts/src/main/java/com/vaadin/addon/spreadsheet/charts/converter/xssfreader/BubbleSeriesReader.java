package com.vaadin.addon.spreadsheet.charts.converter.xssfreader;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.util.CellReference;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBubbleSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumDataSource;

import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.addon.spreadsheet.charts.converter.Utils;
import com.vaadin.addon.spreadsheet.charts.converter.chartdata.AbstractSeriesData.DataSelectListener;
import com.vaadin.addon.spreadsheet.charts.converter.chartdata.AbstractSeriesData.SeriesPoint;
import com.vaadin.addon.spreadsheet.charts.converter.chartdata.BubbleSeriesData;

public class BubbleSeriesReader
        extends AbstractSeriesReader<CTBubbleSer, BubbleSeriesData> {

    public BubbleSeriesReader(XmlObject ctChart, Spreadsheet spreadsheet) {
        super(ctChart, spreadsheet);
    }

    @Override
    protected BubbleSeriesData createSeriesDataObject(CTBubbleSer serie) {
        return new BubbleSeriesData();
    }

    @Override
    protected void fillSeriesData(BubbleSeriesData seriesData,
            CTBubbleSer serie) {
        seriesData.name = tryGetSeriesName(serie.getTx());

        if (serie.getXVal() == null) {
            createSeriesDataPoints(serie.getYVal(), seriesData);
        } else {
            createSeriesDataPointsForBubble(serie.getXVal(), serie.getYVal(),
                    serie.getBubbleSize(),
                    seriesData);
        }
    }

    private void createSeriesDataPointsForBubble(CTAxDataSource xVal,
            CTNumDataSource yVal, CTNumDataSource bubbleSize,
            BubbleSeriesData seriesData) {
        final List<CellReference> ptListX = Utils
                .getAllReferencedCells(xVal.getNumRef().getF());

        final String formulaY = yVal.getNumRef().getF();
        final List<CellReference> ptListY = Utils
                .getAllReferencedCells(formulaY);

        final List<CellReference> ptListSize = Utils
                .getAllReferencedCells(bubbleSize.getNumRef().getF());

        final List<SeriesPoint> list = new ArrayList<SeriesPoint>();

        for (int i = 0; i < ptListY.size(); i++) {
            list.add(new SeriesPoint(getNumericValueFromCellRef(ptListX.get(i)),
                    getNumericValueFromCellRef(ptListY.get(i)),
                    getNumericValueFromCellRef(ptListSize.get(i))));
        }

        seriesData.seriesData = list;

        // TODO: fix interaction, handle updates has to be made compatible with
        // this type, requires figuring out how to set selection to two ranges.

        handleReferencedValueUpdates(ptListX, seriesData,
                ValueUpdateMode.X_VALUES);
        handleReferencedValueUpdates(ptListY, seriesData,
                ValueUpdateMode.Y_VALUES);
        handleReferencedValueUpdates(ptListSize, seriesData,
                ValueUpdateMode.Z_VALUES);

        seriesData.dataSelectListener = new DataSelectListener() {
            @Override
            public void dataSelected() {
                getSpreadsheet().setSelection(formulaY);
            }
        };
    }

}
