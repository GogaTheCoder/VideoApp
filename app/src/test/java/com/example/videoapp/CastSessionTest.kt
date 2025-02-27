package com.example.videoapp

import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import org.junit.Test
import org.mockito.Mockito.*
import org.powermock.core.classloader.annotations.PrepareForTest


@PrepareForTest(CastSession::class, RemoteMediaClient::class)
class CastSessionTest {
    @Test
    fun testVideoPlayback() {
        val mockCastSession = mock(CastSession::class.java)
        val mockRemoteMediaClient = mock(RemoteMediaClient::class.java)

        `when`(mockCastSession.remoteMediaClient).thenReturn(mockRemoteMediaClient)

        val mediaInfo = MediaInfo.Builder("https://vdt-m.odkl.ru/?pct=1&expires=1740503302079&srcIp=178.155.5.60&pr=40&srcAg=CHROME&ms=185.100.104.136&type=5&sig=orPR-M2jWsw&ct=0&clientType=45&id=228121840273")
            .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
            .setContentType("video/mp4")
            .build()

        mockRemoteMediaClient.load(mediaInfo, true, 0)
        verify(mockRemoteMediaClient).load(mediaInfo, true, 0)
    }
}

