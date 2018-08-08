package net.zackzhang.lib.circlecolorview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View

class CircleColorView : View {

    private var paint = Paint()
    private var textPaint = TextPaint()

    private var diameter: Int = 0
    private var centerX: Float = 0F
    private var centerY: Float = 0F

    /** 指示样式属性是否已初始化 */
    private var initialized = false

    var fillColor: Int = 0
        set(value) {
            if (value == field) return
            field = value
            if (initialized) {
                invalidate()
            }
        }
    var edgeWidth: Float = 0F
        set(value) {
            if (value == field) return
            field = value
            if (initialized) {
                invalidate()
            }
        }
    var edgeColor: Int = 0
        set(value) {
            if (value == field) return
            field = value
            if (initialized) {
                invalidate()
            }
        }
    var innerText: String? = null
        set(value) {
            if (value == field) return
            field = value
            if (initialized) {
                invalidate()
            }
        }
    var innerTextColor: Int = 0
        set(value) {
            if (value == field) return
            field = value
            if (initialized) {
                invalidate()
            }
        }
    var innerIcon: Drawable? = null
        set(value) {
            if (value == field) return
            field = value
            if (initialized) {
                invalidate()
            }
        }
    var innerIconTintColor: Int = 0
        set(value) {
            if (value == field) return
            field = value
            if (initialized) {
                invalidate()
            }
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        loadAttrs(attrs, defStyle)

        paint.style = Paint.Style.FILL
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true

        textPaint.style = Paint.Style.FILL
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
    }

    private fun loadAttrs(attrs: AttributeSet?, defStyle: Int) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CircleColorView, defStyle, 0)
        fillColor = ta.getColor(R.styleable.CircleColorView_fillColor, Color.BLACK)
        edgeWidth = ta.getDimension(R.styleable.CircleColorView_edgeWidth, 0F)
        edgeColor = ta.getColor(R.styleable.CircleColorView_edgeColor, Color.WHITE)
        innerText = ta.getString(R.styleable.CircleColorView_innerText)
        innerTextColor = ta.getColor(R.styleable.CircleColorView_innerTextColor, Color.WHITE)
        innerIcon = ta.getDrawable(R.styleable.CircleColorView_innerIcon)
        innerIconTintColor = ta.getColor(R.styleable.CircleColorView_innerIconTintColor, Color.WHITE)
        ta.recycle()

        initialized = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        initSize()

        drawCircle(canvas)

        drawText(canvas)

        drawIcon(canvas)
    }

    private fun initSize() {
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        centerX = paddingLeft + contentWidth / 2F
        centerY = paddingTop + contentHeight / 2F

        diameter = Math.min(contentWidth, contentHeight)
    }

    private fun drawCircle(canvas: Canvas) {

        val edgeRadius = diameter / 2F

        if (edgeWidth > 0F) {
            paint.color = edgeColor
            canvas.drawCircle(centerX, centerY, edgeRadius, paint)
        }

        val fillRadius = edgeRadius - edgeWidth

        paint.color = fillColor
        canvas.drawCircle(centerX, centerY, fillRadius, paint)
    }

    private fun drawText(canvas: Canvas) {
        // 若没有文本字符串或者有图标，不绘制文本
        if (TextUtils.isEmpty(innerText) || innerIcon != null) return

        // 经过上句的判断，这里 innerText 肯定不为 null 了，不知道为什么没有自动转换成非空类型
        if (innerText!!.length > 1) {
            innerText = innerText!!.substring(0, 1)
        }

        textPaint.textSize = diameter / 2F
        textPaint.color = innerTextColor

        val textOffsetY = (textPaint.descent() - textPaint.ascent()) / 2f - textPaint.descent()

        canvas.drawText(innerText!!, centerX, centerY + textOffsetY, textPaint)
    }

    private fun drawIcon(canvas: Canvas) {
        if (innerIcon == null) return

        val radius = diameter / 4F
        // 这里 innerIcon 也一定不为空
        innerIcon!!.setBounds(
                (centerX - radius).toInt(),
                (centerY - radius).toInt(),
                (centerX + radius).toInt(),
                (centerY + radius).toInt()
        )
        innerIcon!!.setTint(innerIconTintColor)
        innerIcon!!.draw(canvas)
    }
}