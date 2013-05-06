package com.twitter.example.modules

import com.twitter.util.Future
import com.twitter.mydemo.renamed.{ModuleService, Html, ModulesRepositoryService}
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftServerFramedCodec}
import java.net.InetSocketAddress


object ModulesRepo {

  object Client {
    def buildClient() = {
      val clientService = ClientBuilder()
        .hosts(new InetSocketAddress(7005))
        .codec(ThriftClientFramedCodec())
        .hostConnectionLimit(1)
        .build()
      new ModulesRepositoryService.FinagledClient(clientService)
    }

  }

  object Server {
    class MyModulesRepositoryServiceImpl extends ModulesRepositoryService.FutureIface {
      val IPServer = InPlay.Server.buildServer()
      val IPClient = InPlay.Client.buildClient()

      def moduleFor(name: String): Future[Html] = name match {
        case s: String if s.equals("inplay") => {
          IPClient.render() flatMap { e => Future.value(e) }
        }
      }
    }


    def buildServer() = {
      val protocol = new TBinaryProtocol.Factory()
      ServerBuilder()
        .codec(ThriftServerFramedCodec())
        .name("ModulesRepositoryService")
        .bindTo(new InetSocketAddress(7005))
        .build(new ModulesRepositoryService.FinagledService(new MyModulesRepositoryServiceImpl, protocol))
    }
  }


}
