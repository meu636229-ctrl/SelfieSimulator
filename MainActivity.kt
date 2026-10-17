package com.example.selfiesimulator

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var preview: ImageView
    private lateinit var placeholder: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(16, 24, 39)
        window.navigationBarColor = Color.rgb(16, 24, 39)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(24), dp(28), dp(24), dp(24))
            setBackgroundColor(Color.rgb(16, 24, 39))
        }
        val title = TextView(this).apply {
            text = "SelfieSimulator"
            textSize = 26f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        }
        val subtitle = TextView(this).apply {
            text = "Pré-visualização de imagem para testes visuais"
            textSize = 14f
            setTextColor(Color.rgb(190, 200, 215))
            gravity = Gravity.CENTER
            setPadding(0, dp(8), 0, dp(22))
        }
        val frame = FrameLayout(this).apply {
            setBackgroundColor(Color.rgb(31, 41, 55))
        }
        preview = ImageView(this).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
            visibility = ImageView.GONE
            contentDescription = "Imagem selecionada para pré-visualização"
        }
        placeholder = TextView(this).apply {
            text = "Sua imagem aparecerá aqui\n\nEscolha uma foto da galeria para visualizar"
            textSize = 16f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(220, 226, 235))
            setPadding(dp(18), dp(18), dp(18), dp(18))
        }
        frame.addView(placeholder, FrameLayout.LayoutParams(-1, -1))
        frame.addView(preview, FrameLayout.LayoutParams(-1, -1))
        root.addView(title, LinearLayout.LayoutParams(-1, -2))
        root.addView(subtitle, LinearLayout.LayoutParams(-1, -2))
        root.addView(frame, LinearLayout.LayoutParams(-1, 0, 1f))

        val choose = Button(this).apply {
            text = "Selecionar foto da galeria"
            setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
                startActivityForResult(Intent.createChooser(intent, "Escolha uma foto"), 1001)
            }
        }
        root.addView(choose, LinearLayout.LayoutParams(-1, dp(54)).apply { topMargin = dp(18) })
        val note = TextView(this).apply {
            text = "Este app apenas mostra imagens nesta tela. Não substitui a câmera real nem interfere em verificações de identidade."
            textSize = 12f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(170, 182, 200))
            setPadding(dp(4), dp(14), dp(4), 0)
        }
        root.addView(note, LinearLayout.LayoutParams(-1, -2))
        setContentView(root)
    }

    @Deprecated("Legacy picker callback used for broad Android compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                preview.setImageURI(uri)
                preview.visibility = ImageView.VISIBLE
                placeholder.visibility = TextView.GONE
            }
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
