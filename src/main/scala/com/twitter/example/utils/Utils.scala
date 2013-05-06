package com.twitter.example.utils

import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpResponse}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.HttpVersion._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import org.jboss.netty.buffer.ChannelBuffers._
import org.jboss.netty.util.CharsetUtil._

object Utils {
  def fillHttpResonseFuture(content: String) : Future[HttpResponse] = {
    Future.value {
      val response = new DefaultHttpResponse(HTTP_1_1, OK)
      response.setContent(copiedBuffer(content, UTF_8))
      response.addHeader("foo", "bar")
      response
    }

  }


}
