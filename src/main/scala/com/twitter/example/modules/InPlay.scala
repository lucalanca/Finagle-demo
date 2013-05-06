package com.twitter.example.modules

import com.twitter.mydemo.renamed.{ModuleService, Html}
import com.twitter.util.Future
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftServerFramedCodec}
import java.net.{InetSocketAddress}


object InPlay {
  object Client {
    def buildClient() = {
      val clientService = ClientBuilder()
        .hosts(new InetSocketAddress(7004))
        .codec(ThriftClientFramedCodec())
        .hostConnectionLimit(1)
        .build()
      new ModuleService.FinagledClient(clientService)
    }
  }

  object Server {

    class MyInPlayImpl extends ModuleService.FutureIface {
      def render(): Future[Html] = {
        Future.value(Html("<div>Inplay</div>"))
      }
    }

    def buildServer() = {
      val protocol = new TBinaryProtocol.Factory()
      ServerBuilder()
        .codec(ThriftServerFramedCodec())
        .name("InPlayService")
        .bindTo(new InetSocketAddress(7004))
        .build(new ModuleService.FinagledService(new MyInPlayImpl, protocol))
    }
  }
}

