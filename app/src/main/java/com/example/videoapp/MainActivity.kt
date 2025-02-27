package com.example.videoapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.SessionManagerListener

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var castContext: CastContext? = null
    private var castSession: CastSession? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        castContext = CastContext.getSharedInstance(this)

        val sessionManager = castContext?.sessionManager

        sessionManager?.addSessionManagerListener(
            object : SessionManagerListener<CastSession> {
                override fun onSessionStarted(session: CastSession, sessionId: String) {
                    castSession = session
                    // Логирование или уведомление пользователя о начале сессии
                    println("Сессия началась: $sessionId")
                }

                override fun onSessionEnded(session: CastSession, error: Int) {
                    castSession = null
                    // Обработка завершения сессии
                    println("Сессия завершена с ошибкой: $error")
                }

                override fun onSessionResumed(session: CastSession, wasSuspended: Boolean) {
                    castSession = session
                    // Логирование или уведомление пользователя о возобновлении сессии
                    println("Сессия возобновлена. Была приостановлена: $wasSuspended")
                }

                override fun onSessionResumeFailed(session: CastSession, error: Int) {
                    // Обработка неудачи возобновления сессии
                    println("Не удалось возобновить сессию. Ошибка: $error")
                }

                override fun onSessionStartFailed(session: CastSession, error: Int) {
                    // Обработка неудачи запуска сессии
                    println("Не удалось начать сессию. Ошибка: $error")
                }

                override fun onSessionEnding(session: CastSession) {
                    // Обработка завершения сессии
                    println("Сессия завершается")
                }

                override fun onSessionStarting(session: CastSession) {
                    // Обработка начала сессии
                    println("Сессия начинается")
                }

                override fun onSessionResuming(session: CastSession, reason: String) {
                    // Обработка возобновления сессии
                    println("Сессия возобновляется. Причина: $reason")
                }

                override fun onSessionSuspended(session: CastSession, reason: Int) {
                    // Обработка приостановки сессии
                    println("Сессия приостановлена. Причина: $reason")
                }
            },
            CastSession::class.java
        )

        findViewById<Button>(R.id.castButton).setOnClickListener {
            startVideo()
        }
    }

    private fun startVideo() {
        if (castSession != null) {
            val remoteMediaClient = castSession?.remoteMediaClient
            val mediaInfo = MediaInfo.Builder(
                "https://vdt-m.odkl.ru/?pct=1&expires=1740503302079&srcIp=178.155.5.60&pr=40&srcAg=CHROME&ms=185.100.104.136&type=5&sig=orPR-M2jWsw&ct=0&clientType=45&id=228121840273"
            ).setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType("video/mp4")
                .setMetadata(MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE).apply {
                    putString(MediaMetadata.KEY_TITLE, "Test Video")
                })
                .build()

            remoteMediaClient?.load(mediaInfo, true, 0)
        } else {
            Toast.makeText(this, "Google Cast устройство не найдено", Toast.LENGTH_SHORT).show()
        }
    }
}