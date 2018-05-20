package edu.monash.swan.ass2.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

import edu.monash.swan.ass2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationReportBar extends Fragment implements View.OnClickListener, DatePicker.OnDateChangedListener{
    private BarChart mBarChart;
    private Button button;
    protected String[] mMonths = new String[]{"文汇", "文萃", "文星","学校"};//X轴标签
    private Context context;
    private TextView startdate, endingdate,startdateE,endingdateE;
    private int year, month, day;
    //在TextView上显示的字符
    private StringBuffer date, time;
    public LocationReportBar() {
        // Required empty public constructor
        this.context=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_location_report_bar, container, false);
        super.onCreate(savedInstanceState);
//        view.setContentView(R.layout.fragment_location_report_bar);
        button=view.findViewById(R.id.button);
        mBarChart = view.findViewById(R.id.mBarChart);
        mBarChart.setNoDataText("");
        //        context = this;
        date = new StringBuffer();
        //初始化控件
        startdate=view.findViewById(R.id.statingdate);
        endingdate=view.findViewById(R.id.endingdate);
        startdateE=view.findViewById(R.id.statingdateEdit);
        endingdateE=view.findViewById(R.id.endingdateEdit);
        startdate.setOnClickListener(this);
        endingdate.setOnClickListener(this);
        initDateTime();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initview();
            }
        });
        return view;
    }
    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.statingdate:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) { //清除上次记录的日期
                            date.delete(0, date.length());
                        }
                        startdateE.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month)).append("月").append(day).append("日"));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                dialog.setTitle("设置日期");
                dialog.setView(dialogView);
                dialog.show();
                //初始化日期监听事件
                datePicker.init(year, month - 1, day, this);
                break;
            case R.id.endingdate:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) { //清除上次记录的日期
                            date.delete(0, date.length());
                        }
                        endingdateE.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month)).append("月").append(day).append("日"));
                        dialog.dismiss();
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog2 = builder2.create();
                View dialogView2 = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker2 = (DatePicker) dialogView2.findViewById(R.id.datePicker);

                dialog2.setTitle("设置日期");
                dialog2.setView(dialogView2);
                dialog2.show();
                //初始化日期监听事件
                datePicker2.init(year, month - 1, day, this);
                break;
        }
    }
    /**
     * 日期改变的监听事件
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }
    //初始化柱状图
    public void initview(){
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setMaxVisibleValueCount(10);
        mBarChart.setPinchZoom(true);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawGridBackground(true);
//        mBarChart.setUnit("time");

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if (value==0||value==mMonths.length+1){
                    return "";
                }
                return mMonths[(int) (value-1) % mMonths.length];
            }
        });

        mBarChart.getAxisLeft().setDrawGridLines(false);
        mBarChart.animateY(2500);
        mBarChart.getLegend().setEnabled(false);
        setData();
    }
    //设置数据
    private void setData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 1; i <=4; i++) {
            int mult = 8;
            int val = (int) (Math.random()%1 * mult) + mult % 2+1;
            yVals1.add(new BarEntry(i,val));
        }

        BarDataSet set1;
        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "日期设置");
            //设置多彩 也可以单一颜色
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(true); //显示顶点值

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            mBarChart.setData(data);
            mBarChart.setFitBars(true);
        }
        mBarChart.invalidate();
    }

}
