package com.twitter.example.routers

import com.twitter.finagle._
import org.jboss.netty.handler.codec.http.{HttpResponse, HttpRequest}
import com.twitter.example.pages.{Index}
import com.twitter.example.utils.Utils
import com.twitter.util.Future
import com.twitter.example.modules.ModulesRepo
import com.twitter.mydemo.renamed.PageService
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder, Server}
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftServerFramedCodec}

/**
 * The service itself. Simply echos back "hello world"
 */
class MainRouter extends Service[HttpRequest, HttpResponse] {

  val server = Index.Server.buildServer()

  def apply(request: HttpRequest) = {

    val client = Index.Client.buildClient(server.localAddress)


    if (request.getUri() == "/exchange") {
      client.render() flatMap { response =>
        Utils.fillHttpResonseFuture(response.content)
      } ensure {
        client.service.close()
      }
      //Utils.fillHttpResonseFuture(client.render()().content) ensure { client.service.close() }
    } else {
      Utils.fillHttpResonseFuture("not exchange")
    }

    com.twitter.finagle.httpproxy

  }
}
