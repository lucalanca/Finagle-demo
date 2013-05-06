//package com.twitter.example.modules
//
//import com.twitter.mydemo.renamed._
//import com.twitter.util.Future
//import org.apache.thrift.protocol.TBinaryProtocol
//import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
//import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftServerFramedCodec}
//import java.net.{SocketAddress, InetSocketAddress}
//
//object Header {
//  object Server {
//    class MyHeaderImpl extends HeaderService.FutureIface {
//      def render(): Future[Html] = {
//        Future.value(Html("<div>Header</div>"))
//      }
//    }
//
//    def build() = {
//      val protocol = new TBinaryProtocol.Factory()
//      val serverService = new HeaderService.FinagledService(new MyHeaderImpl, protocol)
//      ServerBuilder()
//        .codec(ThriftServerFramedCodec())
//        .name("binary_service")
//        .bindTo(new InetSocketAddress(8006))
//        .build(serverService)
//    }
//  }
//
//  object Client {
//    def build(address: SocketAddress) = {
//      val clientService = ClientBuilder()
//        .hosts(Seq(address))
//        .codec(ThriftClientFramedCodec())
//        .hostConnectionLimit(1)
//        .build()
//      new HeaderService.FinagledClient(clientService)
//    }
//  }
//
//  val server = Server.build()
//  val client = Client.build(server.localAddress)
//
//}
