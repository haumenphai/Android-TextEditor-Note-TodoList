package promax.dohaumen.text_edittor_mvvm.views.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class LineNumberedEditText extends EditText
{
    private final Context context;
    private Rect rect;
    private Paint paint;

    public LineNumberedEditText(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public LineNumberedEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LineNumberedEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context=context;
        init();
    }

    private void init()
    {
        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(getTextSize());
        paint.setTypeface(Typeface.MONOSPACE);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        int baseline;
        int lineCount = getLineCount();
        int lineNumber = 1;

        for (int i = 0; i < lineCount; ++i) 
        {
            baseline=getLineBounds(i, null);
            if (i == 0) 
            {
                canvas.drawText(""+lineNumber, rect.left, baseline, paint);

                ++lineNumber;
            } 
            else if (getText().charAt(getLayout().getLineStart(i) - 1) == '\n') 
            {
                canvas.drawText(""+lineNumber, rect.left, baseline, paint);
                ++lineNumber;
            }
        }   


// for setting edittext start padding
        if(lineCount<100)
        {
            setPadding(40,getPaddingTop(),getPaddingRight(),getPaddingBottom());
        }
        else if(lineCount>99 && lineCount<1000)
        {
            setPadding(50,getPaddingTop(),getPaddingRight(),getPaddingBottom());
        }
        else if(lineCount>999 && lineCount<10000)
        {
            setPadding(60,getPaddingTop(),getPaddingRight(),getPaddingBottom());
        }
        else if(lineCount>9999 && lineCount<100000)
        {
            setPadding(70,getPaddingTop(),getPaddingRight(),getPaddingBottom());
        }

        super.onDraw(canvas);
    }
}