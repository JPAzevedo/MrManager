package pt.jpazevedo.mrmanager.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import pt.jpazevedo.mrmanager.R;

/**
 * Created by joaopedroazevedo11 on 07/03/17.
 */

public class PieChart extends View {

    //http://stackoverflow.com/questions/4397192/draw-pie-chart-in-android
    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private float[] value_degree;
    private int[] COLORS={Color.BLUE,Color.GREEN,Color.MAGENTA,Color.CYAN,Color.RED};
    RectF rectf;
    int temp=0;

    public PieChart(Context context, float[] values) {

        super(context);
        rectf = new RectF (0, 0, context.getResources().getDimension(R.dimen.dash_dimen), context.getResources().getDimension(R.dimen.dash_dimen));
        values = calculateData(values);
        value_degree=new float[values.length];
        for(int i=0;i<values.length;i++)
        {
            value_degree[i]=values[i];
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        temp = 0;

        for (int i = 0; i < value_degree.length; i++) {//values2.length; i++) {
            if (i == 0) {
                paint.setColor(COLORS[i]);
                canvas.drawArc(rectf, 0, value_degree[i], true, paint);
            }
            else
            {
                temp += (int) value_degree[i - 1];
                paint.setColor(COLORS[i]);
                canvas.drawArc(rectf, temp, value_degree[i], true, paint);
            }
        }
    }

    private float[] calculateData(float[] data) {
        // TODO Auto-generated method stub
        float total=0;
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
            data[i]=360*(data[i]/total);
        }
        return data;

    }

}
