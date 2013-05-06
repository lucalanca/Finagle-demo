package com.twitter.example.pages

import com.twitter.mydemo.renamed.{PageService, ModulesRepositoryService, ModuleService, Html}
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.thrift.{ThriftClientRequest, ThriftClientFramedCodec, ThriftServerFramedCodec}
import java.net.{SocketAddress, InetSocketAddress}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.{HttpRequest, DefaultHttpResponse, HttpResponse}
import org.jboss.netty.handler.codec.http.HttpVersion._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import com.twitter.finagle.Service
import org.jboss.netty.buffer.ChannelBuffers._
import org.jboss.netty.util.CharsetUtil._
import com.twitter.finagle.http.Http
import com.twitter.example.utils.Utils
import com.twitter.example.modules.{ModulesRepo, InPlay}
import org.apache.thrift.protocol.TBinaryProtocol


object Index {

  def port = 7006



  object Server {
    class MyIndexServiceImpl extends PageService.FutureIface {
      def render(): Future[Html] = {
        Future.value(Html("asdasdas"))
      }
    }

    def buildServer() = {
      val protocol = new TBinaryProtocol.Factory()
      var service = new PageService.FinagledService(new MyIndexServiceImpl, protocol)
      ServerBuilder()
        .codec(ThriftServerFramedCodec())
        .name("IndexService")
        .bindTo(new InetSocketAddress(port))
        .build(service)
    }

  }

  object Client {
    def buildClient(address: SocketAddress) = {
      val clientService = ClientBuilder()
        .hosts(Seq(address))
        .codec(ThriftClientFramedCodec())
        .hostConnectionLimit(1)
        .retries(3)
        .build()
      new PageService.FinagledClient(clientService)
    }


  }
}


