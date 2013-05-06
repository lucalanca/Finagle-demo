package com.twitter.example.routers


import com.twitter.finagle.http.Http
import com.twitter.finagle.{Service, SimpleFilter}
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import org.jboss.netty.handler.codec.http.HttpVersion._
import org.jboss.netty.buffer.ChannelBuffers._
import org.jboss.netty.util.CharsetUtil._
import java.net.InetSocketAddress
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.example.pages.Index

object HttpServer {
  /**
   * A simple Filter that catches exceptions and converts them to appropriate
   * HTTP responses.
   */
  class HandleExceptions extends SimpleFilter[HttpRequest, HttpResponse] {
    def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]) = {

      // `handle` asynchronously handles exceptions.
      service(request) handle { case error =>
        val statusCode = error match {
          case _: IllegalArgumentException =>
            FORBIDDEN
          case _ =>
            INTERNAL_SERVER_ERROR
        }
        val errorResponse = new DefaultHttpResponse(HTTP_1_1, statusCode)
        errorResponse.setContent(copiedBuffer(error.getStackTraceString, UTF_8))

        errorResponse
      }
    }
  }

  def main(args: Array[String]) {


    val handleExceptions = new HandleExceptions

    val respond = new MainRouter




    // compose the Filters and Service together:
    val myService: Service[HttpRequest, HttpResponse] = handleExceptions andThen respond
    val server: Server = ServerBuilder()
      .codec(Http())
      .bindTo(new InetSocketAddress(8080))
      .name("httpserver")
      .build(myService)


    server.close()








  }




}