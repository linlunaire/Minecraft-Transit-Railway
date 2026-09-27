package mtr.servlet

import mtr.data.RouteType
import mtr.data.TransportMode
import javax.servlet.AsyncContext
import javax.servlet.ServletOutputStream
import javax.servlet.WriteListener
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.Locale

interface IServletHandler {
    companion object {
        @JvmStatic
        fun sendResponse(response: HttpServletResponse?, asyncContext: AsyncContext?, content: String?) {
            val contentBytes = ByteBuffer.wrap(content!!.toByteArray(StandardCharsets.UTF_8))
            try {
                response!!.addHeader("Access-Control-Allow-Origin", "*")
                response.addHeader("Content-Type", "application/json")
                val servletOutputStream: ServletOutputStream = response.outputStream
                servletOutputStream.setWriteListener(object : WriteListener {
                    @Throws(IOException::class)
                    override fun onWritePossible() {
                        while (servletOutputStream.isReady) {
                            if (!contentBytes.hasRemaining()) {
                                response.status = 200
                                asyncContext!!.complete()
                                return
                            }
                            servletOutputStream.write(contentBytes.get().toInt())
                        }
                    }

                    override fun onError(t: Throwable?) {
                        asyncContext!!.complete()
                    }
                })
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        @JvmStatic
        fun createRouteKey(transportMode: TransportMode?, routeType: RouteType?): String =
            (transportMode!!.toString() + "_" + routeType!!.toString()).lowercase(Locale.ENGLISH)
    }
}
